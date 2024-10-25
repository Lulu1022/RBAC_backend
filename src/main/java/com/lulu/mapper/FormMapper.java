package com.lulu.mapper;

import com.lulu.model.entity.Form;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

@Mapper
public interface FormMapper {

    Long count(String review_status);
    Integer insertForm(Form form);


    List<Form> findByUserIdAndReviewStatus(Integer userId, String reviewStatus);

    List<Form> findForms(Integer start, Integer pageSize, String review_status);

    void changeReviewStatus(Integer id, String review_status);
}

