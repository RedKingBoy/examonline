package service.Impl;

import common.Constant;
import dao.Impl.UserDaoImpl;
import dao.UserDao;
import dto.AssignRoleDto;
import dto.AuditDto;
import dto.UserDto;
import page.PageObject;
import pojo.Menu;
import pojo.User;
import service.UserService;
import utils.Md5Encryption;
import vo.UserDetailVo;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();

    @Override
    public int login(User user) {
        User loginUser = userDao.getUser(user.getUsername());
        if (loginUser == null)
            return -1;//账号为空
        if (loginUser.getAuditState() == 0)
            return -2;//账号正在审核
        if (loginUser.getIsActive() == 1)
            return -3;//账号被删除
        if (loginUser.getAuditState() == 2)
            return -4;//账号审核被驳回
        String encryption = null;
        try {
            encryption = Md5Encryption.getEncryption(user.getPassword(), loginUser.getSalt());
            if (encryption.equals(loginUser.getPassword())) {
                return 1;//登录成功
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return 0;//账号或密码错误
    }

    @Override
    public int register(User user) {
        User registerUser = userDao.getUser(user.getUsername());
        if (registerUser != null) {
            return 0;//账号已被注册
        } else {
            String randomSalt = Md5Encryption.getRandomSalt(10);
            String encryption = null;
            try {
                encryption = Md5Encryption.getEncryption(user.getPassword(), randomSalt);
                user.setPassword(encryption);
                user.setSalt(randomSalt);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return userDao.addUser(user);
        }

    }

    @Override
    public int userExists(String username) {
        User user = userDao.getUser(username);
        if (user == null)
            return 0;//账号不存在
        return 1;//账号存在
    }

    @Override
    public List<Menu> initMenu(User user) {
        String username = user.getUsername();
        return userDao.getMenusByName(username);
    }

    @Override
    public UserDetailVo userDetail(String username) {
        return userDao.getUserDetail(username);
    }

    @Override
    public PageObject<UserDetailVo> queryUsers(UserDto userDto) {
        Integer currentPage = userDto.getCurrentPage();
        Integer pageSize = userDto.getPageSize();
        Integer offset = (currentPage - 1) * pageSize;
        List<UserDetailVo> usersWithPage = userDao.getUsersWithPage(userDto, offset, pageSize);
        Integer usersTotalCount = userDao.getUsersTotalCount(userDto);
        return new PageObject<UserDetailVo>(usersWithPage, usersTotalCount);
    }

    @Override
    public Integer audit(AuditDto auditDto) {
        return userDao.auditUser(auditDto);
    }

    @Override
    public int delete(String ids) {
        return userDao.deleteUser(ids);
    }

    @Override
    public void uploadUsers(List<User> data) throws NoSuchAlgorithmException {
        String randomSalt = Md5Encryption.getRandomSalt(10);
        String encryption = Md5Encryption.getEncryption(Constant.DEFAULT_PASSWORD, randomSalt);
        userDao.addUsers(data, encryption, randomSalt);
    }

    @Override
    public int exportCount(UserDto userDto) {
        if (!userDto.getIsAll()){
            Integer pageSize = userDto.getPageSize();
            Integer currentPage = userDto.getCurrentPage();
            Integer offset = (currentPage - 1) * pageSize;
            return userDao.getExportCount(userDto, offset, pageSize);
        }
        return userDao.getExportCount(userDto, null, null);
    }

    @Override
    public List<User> exportUsers(UserDto userDto, Integer sheet, Integer sheetSize) {
        if (userDto.getIsAll()) {//导出搜索的全部信息,使用的是sheet和sheetSize
            return userDao.getExportUsers(userDto, sheet, sheetSize);
        } else {//导出当前页信息,在sheet基础上考虑前端分页情况
            Integer currentPage = userDto.getCurrentPage();
            int pageSize = userDto.getPageSize();
            Integer initOffset = (currentPage - 1) * pageSize;
            return userDao.getExportUsers(userDto, initOffset, pageSize);
        }
    }

    @Override
    public int assign(AssignRoleDto assignRoleDto) {
        return userDao.assignRole(assignRoleDto);
    }

    @Override
    public List<UserDetailVo> queryUsersByName(String query) {
        return userDao.getUsersByName(query);
    }
}
