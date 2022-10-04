package dao.Impl;

import dao.RoleDao;
import dto.AssignMenuDto;
import dto.AuditDto;
import pojo.Role;
import utils.JdbcUtil;
import utils.MultiResultHandler;

import java.util.ArrayList;
import java.util.List;

public class RoleDaoImpl implements RoleDao {
    @Override
    public List<Role> getRoles(String searchInfo,String auditState,String isActive) {
        String sql = "SELECT id,name,create_time createTime,create_user createUser,audit_state auditState,is_active isActive FROM role WHERE 1=1";
        List<Object> params = new ArrayList<>();
        if (searchInfo!=null&&!"".equals(searchInfo)){
            sql += " AND name LIKE ?";
            params.add("%"+searchInfo+"%");
        }
        if (auditState!=null&&!"".equals(auditState)){
            sql += " AND audit_state = ?";
            params.add(auditState);
        }
        if (isActive!=null&&!"".equals(isActive)){
            sql += " AND is_active = ?";
            params.add(isActive);
        }
        sql += " ORDER BY is_active ASC";
        return JdbcUtil.query(new MultiResultHandler<>(Role.class),sql,params.toArray());
    }

    @Override
    public int deleteRole(String ids) {
        String sql = "UPDATE role SET is_active = 1 WHERE id IN ("+ids+") AND is_active = 0";
        return JdbcUtil.update(sql);
    }

    @Override
    public int auditRoles(AuditDto auditDto) {
        List<Integer> ids = auditDto.getIds();
        String allIds = ids.toString();
        allIds = allIds.substring(1,allIds.length()-1);
        String sql = "UPDATE role SET audit_state = ? WHERE id IN ("+allIds+")";
        return JdbcUtil.update(sql, auditDto.getAuditState());
    }

    @Override
    public int addRole(Role role) {
        String sql = "INSERT INTO role(name,create_user) VALUES (?,?)";
        return JdbcUtil.update(sql,role.getName(),role.getCreateUser());
    }

    @Override
    public int updateRole(Role role) {
        String sql = "UPDATE role SET name = ? WHERE id = ?";
        return JdbcUtil.update(sql,role.getName(),role.getId());
    }

    @Override
    public void deleteMenus(Integer roleId) {
        String sql = "DELETE FROM role_menu WHERE role_id = ?";
        JdbcUtil.update(sql,roleId);
    }

    @Override
    public int assignMenus(AssignMenuDto assignMenuDto) {
        String sql = "INSERT INTO role_menu(role_id,menu_id) VALUES";
        ArrayList<Object> params = new ArrayList<>();
        for (Integer menuId : assignMenuDto.getMenuIds()){
            sql += "(?,?),";
            params.add(assignMenuDto.getRoleId());
            params.add(menuId);
        }
        sql = sql.substring(0,sql.length()-1);
        return JdbcUtil.update(sql,params.toArray());
    }
}
