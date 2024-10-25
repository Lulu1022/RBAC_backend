package com.lulu.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuItem {
    private Integer id;
    private String name;
    private String icon;
    private String path;
    private Integer parentId;
    List<MenuItem> children = new ArrayList<MenuItem>();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"id\":").append(id).append(",");
        sb.append("\"name\":\"").append(name).append("\",");
        sb.append("\"icon\":\"").append(getIcon()).append("\",");
        sb.append("\"path\":\"").append(getPath()).append("\",");
        sb.append("\"parentId\":").append(parentId).append(",");
        if (children != null) {
            sb.append(",\"children\":").append(children.toString());
        }
        sb.append("}");
        return sb.toString();
    }
}
