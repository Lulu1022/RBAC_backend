package com.lulu.service;

import com.lulu.model.entity.Form;
import com.lulu.model.entity.PageBean;

import java.util.List;

public interface FormService {
    Integer addForm(Form form, Integer userId);
    List<Form> getFormsByUserIdAndReviewStatus(Integer userId, String reviewStatus);
    PageBean getForms(int page, int pageSize,String review_status);
    void changeReviewStatus(Integer id, String review_status);
}
