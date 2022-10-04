package dao.Impl;

import dao.MenuDao;
import utils.JdbcUtil;
import utils.MultiResultHandler;
import vo.MenuVo;

import java.util.ArrayList;
import java.util.List;

public class MenuDaoImpl implements MenuDao {
    @Override
    public List<MenuVo> getAllMenus() {
        String sql = "SELECT id 'key',name label FROM menu";
        return JdbcUtil.query(new MultiResultHandler<>(MenuVo.class), sql);
    }

    @Override
    public List<Integer> getMenuIds(String roleId) {
        String sql = "SELECT menu_id 'key' FROM role_menu WHERE role_id = ?";
        List<MenuVo> query = JdbcUtil.query(new MultiResultHandler<>(MenuVo.class), sql, roleId);
        List<Integer> menuIds = new ArrayList<>();
        if (query != null) {
            query.forEach(m -> {
                menuIds.add(m.getKey());
            });
        }
        return menuIds;
    }
}
