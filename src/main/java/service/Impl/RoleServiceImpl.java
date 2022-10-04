package service.Impl;

import dao.Impl.RoleDaoImpl;
import dao.RoleDao;
import dto.AssignMenuDto;
import dto.AuditDto;
import pojo.Role;
import service.RoleService;

import java.util.List;

public class RoleServiceImpl implements RoleService {
    private RoleDao roleDao = new RoleDaoImpl();
    @Override
    public List<Role> getRoles(String searchInfo,String auditState,String isActive) {
        return roleDao.getRoles(searchInfo,auditState,isActive);
    }

    @Override
    public int delete(String ids) {
        return roleDao.deleteRole(ids);
    }

    @Override
    public int audit(AuditDto auditDto) {
        return roleDao.auditRoles(auditDto);
    }

    @Override
    public int add(Role role) {
        return roleDao.addRole(role);
    }

    @Override
    public int update(Role role) {
        return roleDao.updateRole(role);
    }

    @Override
    public int assignMenus(AssignMenuDto assignMenuDto) {
        //因为我们不知道角色对应哪些菜单所以在分配菜单之前应该将原有的菜单都给删除
        //因为我们无法确定删除数量所以不需要返回值
        roleDao.deleteMenus(assignMenuDto.getRoleId());
        //可以添加菜单了
        return roleDao.assignMenus(assignMenuDto);
    }
}
