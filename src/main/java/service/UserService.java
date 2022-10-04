package service;

import dto.AssignRoleDto;
import dto.AuditDto;
import dto.UserDto;
import page.PageObject;
import pojo.Menu;
import pojo.User;
import vo.UserDetailVo;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface UserService {
    int login(User user);

    int register(User user);

    int userExists(String username);

    List<Menu> initMenu(User user);

    UserDetailVo userDetail(String username);

    PageObject<UserDetailVo> queryUsers(UserDto userDto);

    Integer audit(AuditDto auditDto);

    int delete(String ids);

    void uploadUsers(List<User> data) throws NoSuchAlgorithmException;

    int exportCount(UserDto userDto);

    List<User> exportUsers(UserDto userDto,Integer sheet,Integer sheetSize);

    int assign(AssignRoleDto assignRoleDto);

    List<UserDetailVo> queryUsersByName(String query);
}
