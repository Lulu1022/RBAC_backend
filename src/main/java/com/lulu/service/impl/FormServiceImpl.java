package com.lulu.service.impl;


import com.lulu.mapper.FormMapper;
import com.lulu.model.entity.Form;
import com.lulu.model.entity.PageBean;
import com.lulu.service.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FormServiceImpl implements FormService {

    @Autowired
    private FormMapper formMapper;

    @Override
    @Transactional
    public Integer addForm(Form form, Integer userId) {
        form.setReviewStatus("pending");  // 預設審核狀態為待審核
        form.setUserId(userId);
        formMapper.insertForm(form);
        return form.getId();
    }

    @Override
    public List<Form> getFormsByUserIdAndReviewStatus(Integer userId, String reviewStatus) {
        return formMapper.findByUserIdAndReviewStatus(userId, reviewStatus);
    }


    @Override
    public PageBean getForms(int page, int pageSize,String review_status) {
        //1.獲得紀錄總數
        Long count = formMapper.count(review_status);
        //2.獲得分頁查詢結果
        Integer start = (page - 1) * pageSize;
        List<Form> formList = formMapper.findForms(start, pageSize, review_status);
        //3.封裝成 PageBean
        PageBean pageBean = new PageBean(count,formList);
        return pageBean;
    }

    @Override
    public void changeReviewStatus(Integer id, String review_status) {
        formMapper.changeReviewStatus(id, review_status);
    }

}