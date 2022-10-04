package dao;

import vo.MenuVo;

import java.util.List;

public interface MenuDao {
    List<MenuVo> getAllMenus();

    List<Integer> getMenuIds(String roleId);
}
