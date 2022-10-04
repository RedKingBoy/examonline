package service;

import dto.AssignMenuDto;
import dto.AuditDto;
import pojo.Role;

import java.util.List;

public interface RoleService {
    List<Role> getRoles(String searchInfo,String auditState,String isActive);

    int delete(String ids);

    int audit(AuditDto auditDto);

    int add(Role role);

    int update(Role role);

    int assignMenus(AssignMenuDto assignMenuDto);
}
