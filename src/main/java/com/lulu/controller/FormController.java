package com.lulu.controller;

import com.lulu.aop.ApiPermission;
import com.lulu.model.entity.Form;
import com.lulu.model.entity.PageBean;
import com.lulu.model.entity.Result;
import com.lulu.service.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/form")
@CrossOrigin(origins = "*")
public class FormController {
    @Autowired
    private FormService formService;


    @ApiPermission({"表單填寫"})  // 這個 API 屬於 表單填寫頁面
    @GetMapping("/{reviewStatus}")
    public Result getFormsByUserIdAndReviewStatus(
            @RequestParam(required = false) Integer userId,
            @PathVariable("reviewStatus") String reviewStatus
    ) {
        List<Form> form = formService.getFormsByUserIdAndReviewStatus(userId, reviewStatus);
        return Result.success(form);
    }

    @ApiPermission({"表單清單","待審核表單"})  // 這個 API 屬於 表單填寫頁面,待審核表單頁面
    @GetMapping
    public Result getForms(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String review_status
    ) {
        PageBean pageBean = formService.getForms(page, pageSize,review_status);
        return Result.success(pageBean);
    }
    @ApiPermission({"表單填寫"})  // 這個 API 屬於 表單填寫頁面
    @PostMapping
    public Result addForm(@RequestBody Form form, @RequestParam("userId") int userId ) {
        Integer formId = formService.addForm(form, userId);
        return Result.success(formId);
    }

    @ApiPermission({"待審核表單"})  // 這個 API 屬於 待審核表單頁面
    @PutMapping("/{id}")
    public Result changeReviewStatus(
            @PathVariable Integer id,
            @RequestParam String review_status
    ){
        formService.changeReviewStatus(id,review_status);
        return Result.success("更新審核狀態成功");
    }
}
