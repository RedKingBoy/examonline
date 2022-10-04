package service;

import vo.MenuVo;

import java.util.List;

public interface MenuService {
    List<MenuVo> getAllMenus();

    List<Integer> getMenuIds(String roleId);
}
