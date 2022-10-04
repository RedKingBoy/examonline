package dao;

import dto.AssignRoleDto;
import dto.AuditDto;
import dto.UserDto;
import pojo.Menu;
import pojo.User;
import vo.UserDetailVo;
import vo.UserVo;

import java.util.List;

public interface UserDao {
    User getUser(String username);

    int addUser(User user);

    List<Menu> getMenusByName(String username);

    UserDetailVo getUserDetail(String username);

    List<UserDetailVo> getUsersWithPage(UserDto userDto, Integer offset, Integer pageSize);

    Integer getUsersTotalCount(UserDto userDto);

    Integer auditUser(AuditDto auditDto);

    int deleteUser(String ids);

    int addUsers(List<User> data,String password,String salt);

    int getExportCount(UserDto userDto, Integer offset, Integer pageSize);

    List<User> getExportUsers(UserDto userDto, Integer offset, Integer pageSize);

    int assignRole(AssignRoleDto assignRoleDto);

    List<UserDetailVo> getUsersByName(String query);

    UserVo getExamUsers(String username);
}
