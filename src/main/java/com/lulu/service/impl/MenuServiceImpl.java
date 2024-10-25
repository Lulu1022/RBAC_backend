package com.lulu.service.impl;

import com.lulu.mapper.MenuMapper;
import com.lulu.model.entity.Menu;
import com.lulu.model.entity.MenuItem;
import com.lulu.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;

    // 獲取完整的選單結構
    public List<MenuItem> getMenu() {

        List<MenuItem> menuList = menuMapper.findAllMenus();
        // 新建菜單樹列表，用於存放創建好的菜單樹
        List<MenuItem> menuTree = new ArrayList<>();
        for (MenuItem menu : menuList) {
            // 判斷當前菜單項是否為一級菜單
            if (menu.getParentId() == null || menu.getParentId() == 0) {
                menuTree.add(findChildren(menu, menuList));
            }
        }

        return menuTree;
    }

    private static MenuItem findChildren(MenuItem menu, List<MenuItem> menuList) {
        // 設置當前菜單的子菜單列表為空
        menu.setChildren(new ArrayList<>());
        // 遍歷所有菜單列表
        for (MenuItem item : menuList) {
            // 判斷當前菜單的id和菜單列表中哪些菜單的parentId相同
            if (menu.getId() == item.getParentId()) {
                // 如果子菜單列表為空，則初始化子菜單列表
                if (menu.getChildren() == null) {
                    menu.setChildren(new ArrayList<MenuItem>());
                }
                // 設置當前菜單的子菜單列表，並進行遞迴查詢
                menu.getChildren().add(findChildren(item, menuList));
            }
        }
        return menu;
    }
}
