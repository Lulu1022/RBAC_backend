package com.lulu.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Menu {
    private Integer id; // 選單ID
    private String name; // 選單名稱
    private List<MenuItem> children; // 子級選單項目列表
    private String icon;
}
