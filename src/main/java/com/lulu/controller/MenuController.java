package com.lulu.controller;

import com.lulu.model.entity.MenuItem;
import com.lulu.service.MenuService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping
@Slf4j
public class MenuController {
    @Autowired
    private MenuService menuService;

    @GetMapping("/api/menu")
    public List<MenuItem> getMenu(HttpServletRequest request) {
        return menuService.getMenu();
    }
}
