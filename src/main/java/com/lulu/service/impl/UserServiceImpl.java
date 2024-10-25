package com.lulu.service.impl;

import com.lulu.mapper.UserMapper;
import com.lulu.model.dto.user.UserCreateRequest;
import com.lulu.model.entity.PageBean;
import com.lulu.model.entity.User;
import com.lulu.model.dto.user.UserUpdateRequest;
import com.lulu.model.vo.UserListVo;
import com.lulu.model.vo.UserLoginVo;
import com.lulu.service.UserService;
import com.lulu.model.entity.UserRoles;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.lulu.utils.JwtUtils.comparePasswords;
import static com.lulu.utils.JwtUtils.hashPassword;


@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private UserMapper userMapper;
    // 從 application.properties 中讀取寄件者的電子郵件地址
    @Value("${spring.mail.username}")
    private String fromEmail;

    public int registerUser(User registrationRequest) {
        // 1.檢查使用者是否已存在
        if (userMapper.existsByUsername(registrationRequest.getUsername()) != 0) {
            return -1;
        }
        // 2.檢查信箱是否已經存在
        if (userMapper.existsByEmail(registrationRequest.getEmail()) != 0) {
            return -2;
        }

        // 2. 密碼需要加密
        String hashedPassword = hashPassword(registrationRequest.getPassword());

        // 3.將使用者資訊保存到資料庫
        userMapper.save(registrationRequest.getUsername(), hashedPassword);

        // 4. 發送 Gmail 通知
        sendNotificationEmail(registrationRequest.getEmail());

        return 1;
    }

    // 發送通知郵件
    private void sendNotificationEmail(String toEmail) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromEmail);  // 寄件者地址
            helper.setTo(toEmail);  // 收件者地址
            helper.setSubject("註冊成功通知 🎉");  // 郵件主題
            // 使用 HTML 編寫郵件內容，並加入內聯樣式
            String htmlContent = "<html>" +
                    "<body style='font-family: Arial, sans-serif; margin: 20px; padding: 20px;'>" +
                    "<div style='max-width: 600px; margin: 0 auto;'>" +
                    "<h2 style='color: #4CAF50;'>歡迎使用 RBAC權限管理系統 </h2>" +
                    "<p>您好，</p>" +
                    "<p>感謝您註冊我們的服務，您的賬戶已經成功創建。</p>" +
                    "<p style='color: #333;'>需等待 3 個工作天，管理員將啟動您的帳號。</p>" +
                    "<br>" +
                    "<p>祝您有美好的一天！</p>" +
                    "<hr style='border: 1px solid #ddd;'/>" +
                    "<p style='font-size: 12px; color: #999;'>此郵件為系統自動發送，請勿回覆。</p>" +
                    "</div>" +
                    "</body>" +
                    "</html>";

            helper.setText(htmlContent, true);  // 第二個參數為 true 表示內容為 HTML

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用者輸入的密碼比對資料庫密碼
     * @param user
     * @return
     */
    @Override
    public UserLoginVo login(User user) {
        String userInputName = user.getUsername();
        String userInputPassword = user.getPassword();
        String storedHashedPassword = userMapper.getPasswordByUsername(user);
        boolean pass = comparePasswords(userInputPassword,storedHashedPassword);
        if(!pass){
            return null;
        }
        return userMapper.getUserByUsernameAndPassword(userInputName);
    }

    @Override
    @Transactional
    public void createUser(UserCreateRequest userCreateRequest) {
        log.info("新增使用者:{}",userCreateRequest);

        User user = new User();
        user.setUsername(userCreateRequest.getUsername());
        user.setPassword(userCreateRequest.getPassword());
        user.setEmail(userCreateRequest.getEmail());
        user.setDepartment(userCreateRequest.getDepartment());
        user.setCreatedAt(userCreateRequest.getCreatedAt());
        user.setUpdatedAt(userCreateRequest.getUpdatedAt());
        user.setStatus(userCreateRequest.getStatus());
        userMapper.insertUser(user);
        log.info("新增使用者的 id:{}",user.getUserId());

        UserRoles userRoles = new UserRoles();

        userRoles.setRoleId(userCreateRequest.getRole());
        userRoles.setUserId(user.getUserId());

        userMapper.insertUserRole(userRoles);
    }


    @Override
    public PageBean getUsers(int page, int pageSize) {
        //1.獲得紀錄總數
        Long count = userMapper.count();
        //2.獲得分頁查詢結果
        Integer start = (page - 1) * pageSize;
        List<UserListVo> userList = userMapper.getUsers(start, pageSize);
        //3.封裝成 PageBean
        PageBean pageBean = new PageBean(count,userList);
        return pageBean;
    }

    @Override
    public User getById(Integer id) {
        User user = userMapper.getById(id);
        return user;
    }

    @Override
    @Transactional
    public void deleteUser(Integer id) {
        log.info("刪除使用者:{}",id);
        userMapper.deleteUserRole(id);
        userMapper.delete(id);
    }

    /**
     * 更新使用者
     * @param userUpdateRequest
     */
    @Override
    @Transactional
    public void updateUser(UserUpdateRequest userUpdateRequest) {
        User user = new User();
        user.setUserId(userUpdateRequest.getUserId());
        user.setUsername(userUpdateRequest.getUsername());
        user.setPassword(userUpdateRequest.getPassword());
        user.setEmail(userUpdateRequest.getEmail());
        user.setDepartment(userUpdateRequest.getDepartment());
        user.setUpdatedAt(new Timestamp(new java.util.Date().getTime()));
        user.setStatus(userUpdateRequest.getStatus());

        UserRoles userRoles = new UserRoles();
        userRoles.setUserId(userUpdateRequest.getUserId());
        userRoles.setRoleId(userUpdateRequest.getRole());

        userMapper.updateUser(user);
        userMapper.deleteUserRole(userUpdateRequest.getUserId());
        userMapper.insertUserRole(userRoles);
    }

    /**
     * 更新使用者狀態
     * @param id
     * @param status
     */
    @Override
    public void updateUserStatus(int id, int status) {
        userMapper.updateUserStatus(id, status);
    }
    @Override
    public List<Map<String, Object>> getRegisterUser(int month) {
        int lastMonth = month - 1;
        int nextMonth = month + 1;
        List<UserListVo> users = userMapper.getRegisterUser(lastMonth, nextMonth);
        // 分组
        Map<LocalDate, List<UserListVo>> groupedByDate = users.stream()
                .collect(Collectors.groupingBy(user ->
                        user.getCreatedAt().toLocalDateTime().toLocalDate()
                ));
        // 2. 轉換格式
        return groupedByDate.entrySet().stream()
                .map(entry -> {
                    LocalDate date = entry.getKey();
                    List<UserListVo> userList = entry.getValue();

                    long activeCount = userList.stream()
                            .filter(user -> 1 == user.getStatus())
                            .count();

                    long inactiveCount = userList.stream()
                            .filter(user -> 0 == user.getStatus())
                            .count();

                    List<Map<String, String>> usersInfo = userList.stream()
                            .map(user -> {
                                Map<String, String> userInfo = new HashMap<>();
                                userInfo.put("username", user.getUsername());
                                userInfo.put("userId", String.valueOf(user.getUserId()));
                                return userInfo;
                            })
                            .collect(Collectors.toList());

                    Map<String, Object> result = new HashMap<>();
                    result.put("day", date.toString());
                    result.put("onNum", activeCount);
                    result.put("offNum", inactiveCount);
                    result.put("users", usersInfo);

                    return result;
                })
                .sorted(Comparator.comparing(entry -> LocalDate.parse((String) entry.get("day"))))
                .collect(Collectors.toList());
    }

}



