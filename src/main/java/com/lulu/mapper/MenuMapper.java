package com.lulu.mapper;

import com.lulu.model.entity.Menu;
import com.lulu.model.entity.MenuItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MenuMapper {
    List<MenuItem> findAllMenus();

}
