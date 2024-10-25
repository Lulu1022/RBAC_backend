package com.lulu.mapper;

import com.lulu.model.entity.User;
import com.lulu.model.vo.UserListVo;
import com.lulu.model.vo.UserLoginVo;
import com.lulu.model.entity.UserRoles;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Mapper
public interface UserMapper {
    /**
     * 查詢使用者是否存在(註冊帳號)
     * @param username
     * @return
     */
    @Select("select count(*) from users where username = #{username}")
    public int existsByUsername(String username);

    /**
     * 查詢使用者是否存在(註冊信箱)
     * @param email
     * @return
     */
    @Select("select count(*) from users where email = #{email}")
    public int existsByEmail(String email);



    /**
     * 保存使用者資料(註冊帳號)
     * @param username
     * @param password
     */
    @Insert("insert into users(username, password) values(#{username}, #{password})")
    public void save(@RequestBody String username, String password);

    /**
     * 新增使用者
     * @param user
     */
    public void insertUser(User user);


    /**
     * 登入
     * @param user
     * @return
     */
    @Select("select * from users where username = #{username}")
    User getByUserNameAndPassword(User user);
    @Select("select password from users where username = #{username}")
    String getPasswordByUsername(User user);

    /**
     * 查詢使用者清單
     * @param start
     * @param pageSize
     * @return
     */
    List<UserListVo> getUsers(Integer start, Integer pageSize);

    /**
     * 查詢使用者清單總數
     * @return
     */
    @Select("select count(*) from users")
    Long count();


    /**
     * 查詢指定使用者
     * @param id
     * @return
     */
    @Select("select * from users where user_id = #{id}")
    User getById(Integer id);

    /**
     * 刪除使用者
     * @param id
     */
    @Delete("delete from users where user_id = #{id}")
    void delete(Integer id);

    /**
     * 刪除使用者角色
     * @param userId
     */
    @Delete("delete from user_roles where user_id = #{userId}")
    void deleteUserRole(Integer userId);

    /**
     * 更新使用者
     * @param user
     */
    void updateUser(User user);

    /**
     * 新增使用者角色
     * @param userRoles
     */

    void insertUserRole(UserRoles userRoles);

    /**
     * 更新使用者狀態
     * @param id
     * @param status
     */
    @Update("update users set status = #{status} where user_id = #{id}")
    void updateUserStatus(int id, int status);

    /**
     * 輸入帳號密碼登入系統(帳號即 userName)
     * @param userName
     * @return
     */
    @Select("select u.user_id, username, password, email, department, status,\n" +
            "       ur.role_id as role, r.role_name as role_name\n" +
            "from users u\n" +
            "    join user_roles ur on u.user_id = ur.user_id\n" +
            "    join roles r on r.role_id = ur.role_id\n" +
            "         where u.username = #{userName}")
    UserLoginVo getUserByUsernameAndPassword(String userName);

    /**
     * 本月申請帳號的使用者們
     * @param lastMonth
     * @param nextMonth
     * @return
     */
    @Select("SELECT * FROM users " +
            "WHERE MONTH(created_at) BETWEEN #{lastMonth} AND #{nextMonth}")
    List<UserListVo> getRegisterUser(int lastMonth, int nextMonth);
}
