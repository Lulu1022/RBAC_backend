package com.lulu.mapper;

import com.lulu.model.entity.Roles;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RolesMapper {
    @Select("SELECT * FROM roles WHERE role_id = #{roleId}")
    Roles findById(int roleId);

    @Select("select * FROM roles ORDER BY role_id desc limit #{start}, #{pageSize}")
    List<Roles> findAll(Integer start, Integer pageSize);

    @Insert("INSERT INTO roles(role_name, description, created_at, updated_at) VALUES(#{roleName}, #{description}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "roleId")
    void createRole(Roles role);

    @Update("UPDATE roles SET role_name = #{role.roleName}, description = #{role.description}, updated_at = #{role.updatedAt} WHERE role_id = #{id}")
    void updateRole(@Param("role") Roles role, @Param("id") Integer id);

    @Delete("delete from roles where role_id = #{roleId}")
    void deleteRole(int roleId);
    Roles findRoleById(int id);

    void assignPermissionToRole(int roleId, List<Integer> permissionsList);
    void removePermissionFromRole(int roleId);
    @Select("select count(*) from roles")
    Long count();
}
