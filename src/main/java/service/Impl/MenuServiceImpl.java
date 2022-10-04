package service.Impl;

import dao.Impl.MenuDaoImpl;
import dao.MenuDao;
import service.MenuService;
import vo.MenuVo;

import java.util.List;

public class MenuServiceImpl implements MenuService {
    private MenuDao menuDao = new MenuDaoImpl();
    @Override
    public List<MenuVo> getAllMenus() {
        return menuDao.getAllMenus();
    }

    @Override
    public List<Integer> getMenuIds(String roleId) {
        return menuDao.getMenuIds(roleId);
    }
}
