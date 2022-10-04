package dao;

import dto.AssignMenuDto;
import dto.AuditDto;
import pojo.Role;

import java.util.List;

public interface RoleDao {
    List<Role> getRoles(String searchInfo,String auditState,String isActive);

    int deleteRole(String ids);

    int auditRoles(AuditDto auditDto);

    int addRole(Role role);

    int updateRole(Role role);

    void deleteMenus(Integer roleId);

    int assignMenus(AssignMenuDto assignMenuDto);
}
