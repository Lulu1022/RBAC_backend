package com.lulu.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tree {
    private int id;
    private int parentId;
    private String label;
    private List<Tree> children;
    private String tag;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{label: '").append(label).append("'");
        if (children != null && !children.isEmpty()) {
            sb.append(", children: ").append(children);
        }
        sb.append("}");
        return sb.toString();
    }
}
