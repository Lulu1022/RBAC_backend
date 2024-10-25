package com.lulu.mapper;

import com.lulu.model.entity.Permissions;
import com.lulu.model.entity.RolePermission;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface PermissionsMapper {

    @Select("SELECT * FROM permissions WHERE permission_id = #{id}")
    Permissions findPermissionById(int id);


    List<Permissions> findAll();

    @Insert("INSERT INTO permissions (permission_name, parent_id, tag, created_at, updated_at) VALUES (#{permissionName},#{parentId},#{tag},#{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "permissionId")
    void createPermission(Permissions permission);

    @Update("UPDATE permissions SET permission_name = #{permissionName}, updated_at = #{updatedAt} WHERE permission_id = #{permissionId}")
    void updatePermission(Permissions permission);

    @Delete("DELETE FROM permissions WHERE permission_id = #{id}")
    void deletePermission(int id);

    List<Permissions> findPermissionsByUserId(int userId);

    List<String> findPermissionsPathByUserId(int userId);

    @Select("select rp.permission_id, p.permission_name\n" +
            "from role_permissions rp\n" +
            "inner join permissions p on rp.permission_id = p.permission_id\n" +
            "where rp.role_id = #{roleId}")
    List<Permissions> findPermissionsByRoleId(int roleId);

    @Select("SELECT distinct p.permission_id, p.permission_name\n" +
            "FROM permissions p\n" +
            "inner join role_permissions rp\n" +
            "WHERE rp.role_id = #{roleId} \n" +
            "  and p.permission_id = rp.permission_id \n" +
            "  and rp.permission_id NOT IN (select parent_id from permissions)\n")
    List<Permissions> findPermissionsByRoleIdWithoutParentIsNull(int roleId);

@Select("SELECT * FROM permissions")
    List<RolePermission> findMenuPermissions();

@Delete("delete from role_permissions where role_id = #{roldId}")
void deletePermissionByRoleId(int roleId);
}
