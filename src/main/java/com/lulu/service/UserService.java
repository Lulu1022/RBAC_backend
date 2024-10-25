package com.lulu.service;

import com.lulu.model.dto.user.UserCreateRequest;
import com.lulu.model.entity.PageBean;
import com.lulu.model.entity.User;
import com.lulu.model.dto.user.UserUpdateRequest;
import com.lulu.model.vo.UserLoginVo;

import java.util.List;
import java.util.Map;

public interface UserService {
    int registerUser(User registrationRequest);
    UserLoginVo login(User user);
    void createUser(UserCreateRequest userCreateRequest);
    PageBean getUsers(int page, int pageSize);
    User getById(Integer id);

    void deleteUser(Integer id);

    void updateUser(UserUpdateRequest userUpdateRequest);

    void updateUserStatus(int id, int status);

    List<Map<String, Object>> getRegisterUser(int month);
}
