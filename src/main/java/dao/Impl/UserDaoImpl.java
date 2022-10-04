package dao.Impl;

import dao.UserDao;
import dto.AssignRoleDto;
import dto.AuditDto;
import dto.UserDto;
import pojo.Menu;
import pojo.User;
import utils.JdbcUtil;
import utils.MultiResultHandler;
import utils.SingleResultHandler;
import vo.UserDetailVo;
import vo.UserVo;

import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public User getUser(String username) {
        //union有去重功能查询输入的是用户名或手机号登录的user
        String sql = "(SELECT id,username,password,salt,name,sex,idcard idCard,dept_id deptId,post_id postId,audit_state auditState,mobile,role_id roleId,is_active isActive FROM user WHERE username = ?)" +
                " UNION (SELECT id,username,password,salt,name,sex,idcard idCard,dept_id deptId,post_id postId,audit_state auditState,mobile,role_id roleId,is_active isActive FROM user WHERE mobile = ?)";
        List<Object> params = new ArrayList<>();
        params.add(username);
        params.add(username);
        return JdbcUtil.query(new SingleResultHandler<>(User.class), sql, params.toArray());
    }

    @Override
    public int addUser(User user) {
        String sql = "INSERT INTO user(username,password,salt,name,sex,idcard,dept_id,post_id,mobile) VALUES (?,?,?,?,?,?,?,?,?)";
        Object[] params = {
                user.getUsername(),
                user.getPassword(),
                user.getSalt(),
                user.getName(),
                user.getSex(),
                user.getIdCard(),
                user.getDeptId(),
                user.getPostId(),
                user.getMobile()
        };
        return JdbcUtil.update(sql, params);
    }

    @Override
    public List<Menu> getMenusByName(String username) {
        String sql = "SELECT m.id,m.name,route,icon FROM `user` u \n" +
                "JOIN role r \n" +
                "ON u.role_id = r.id\n" +
                "JOIN role_menu rm \n" +
                "ON r.id = rm.role_id\n" +
                "JOIN menu m \n" +
                "ON rm.menu_id = m.id\n" +
                "WHERE username = ?";
        return JdbcUtil.query(new MultiResultHandler<>(Menu.class), sql, username);
    }

    @Override
    public UserDetailVo getUserDetail(String username) {
        String sql = "SELECT u.id,u.username,u.sex,u.name,u.idcard idCard,u.mobile,r.name roleName,d.dept_name deptName,p.post_name postName\n" +
                "FROM `user` u \n" +
                "JOIN role r \n" +
                "ON u.role_id = r.id\n" +
                "JOIN department d\n" +
                "ON d.dept_id = u.dept_id\n" +
                "JOIN post p\n" +
                "ON u.post_id = p.post_id\n" +
                "WHERE username = ?";
        return JdbcUtil.query(new SingleResultHandler<>(UserDetailVo.class), sql, username);
    }

    @Override
    public List<UserDetailVo> getUsersWithPage(UserDto userDto, Integer offset, Integer pageSize) {
        String sql = "SELECT u.id,u.username,u.sex,u.name,u.idcard idCard,u.mobile,r.name roleName,d.dept_name deptName,p.post_name postName,u.audit_state auditState\n" +
                "FROM `user` u \n" +
                "LEFT JOIN role r \n" +
                "ON u.role_id = r.id\n" +
                "JOIN department d\n" +
                "ON d.dept_id = u.dept_id\n" +
                "JOIN post p\n" +
                "ON u.post_id = p.post_id\n" +
                "WHERE 1=1";
        List<Object> params = new ArrayList<>();
        if (userDto.getSearchName() != null && !"".equals(userDto.getSearchName())) {
            sql += " AND u.name LIKE ?";
            params.add("%" + userDto.getSearchName() + "%");
        }
        if (userDto.getSearchSex() != null) {
            sql += " AND u.sex = ?";
            params.add(userDto.getSearchSex());
        }
        if (userDto.getSearchDept() != null) {
            sql += " AND u.dept_id = ?";
            params.add(userDto.getSearchDept());
        }
        if (userDto.getSearchPost() != null) {
            sql += " AND p.post_id = ?";
            params.add(userDto.getSearchPost());
        }
        if (userDto.getSearchAudit() != null) {
            sql += " AND u.audit_state = ?";
            params.add(userDto.getSearchAudit());
        }
        if (userDto.getSearchRole() != null && !"".equals(userDto.getSearchRole())) {
            sql += " AND r.id = ?";
            params.add(userDto.getSearchRole());
        }
        sql += " AND u.is_active = 0 LIMIT ?,?";
        params.add(offset);
        params.add(pageSize);
        return JdbcUtil.query(new MultiResultHandler<>(UserDetailVo.class), sql, params.toArray());
    }

    public Integer getUsersTotalCount(UserDto userDto) {
        String sql = "SELECT COUNT(*)\n" +
                "FROM `user` u \n" +
                "LEFT JOIN role r \n" +
                "ON u.role_id = r.id\n" +
                "JOIN department d\n" +
                "ON d.dept_id = u.dept_id\n" +
                "JOIN post p\n" +
                "ON u.post_id = p.post_id\n" +
                "WHERE 1=1";
        List<Object> params = new ArrayList<>();
        if (userDto.getSearchName() != null && !"".equals(userDto.getSearchName())) {
            sql += " AND u.name LIKE ?";
            params.add("%" + userDto.getSearchName() + "%");
        }
        if (userDto.getSearchSex() != null) {
            sql += " AND u.sex = ?";
            params.add(userDto.getSearchSex());
        }
        if (userDto.getSearchDept() != null) {
            sql += " AND u.dept_id = ?";
            params.add(userDto.getSearchDept());
        }
        if (userDto.getSearchPost() != null) {
            sql += " AND p.post_id = ?";
            params.add(userDto.getSearchPost());
        }
        if (userDto.getSearchAudit() != null) {
            sql += " AND u.audit_state = ?";
            params.add(userDto.getSearchAudit());
        }
        if (userDto.getSearchRole() != null && !"".equals(userDto.getSearchRole())) {
            sql += " AND r.id = ?";
            params.add(userDto.getSearchRole());
        }
        sql += " AND u.is_active = 0";
        return JdbcUtil.query(new SingleResultHandler<>(Integer.class), sql, params.toArray());
    }

    @Override
    public Integer auditUser(AuditDto auditDto) {
        String sql = "UPDATE user SET audit_state = ? WHERE id IN (";
        List<Object> params = new ArrayList<>();
        params.add(auditDto.getAuditState());
        List<Integer> ids = auditDto.getIds();
        for (Integer id : ids) {
            sql += "?,";
            params.add(id);
        }
        sql = sql.substring(0,sql.length()-1);
        sql += ")";
        return JdbcUtil.update(sql,params.toArray());
    }

    @Override
    public int deleteUser(String ids) {
        String sql = "UPDATE user SET is_active = 1 WHERE id IN ("+ids+")";
        return JdbcUtil.update(sql);
    }

    @Override
    public int addUsers(List<User> data,String password,String salt) {
        StringBuilder stringBuilder = new StringBuilder("INSERT INTO user(username,password,salt,name,sex,idcard,dept_id,post_id,mobile) VALUES ");
        List<Object> params = new ArrayList<>();
        for (User user:data){
            stringBuilder.append("(?,?,?,?,?,?,?,?,?),");
            params.add(user.getUsername());
            params.add(password);
            params.add(salt);
            params.add(user.getName());
            params.add(user.getSex());
            params.add(user.getIdCard());
            params.add(user.getDeptId());
            params.add(user.getPostId());
            params.add(user.getMobile());
        }
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        return JdbcUtil.update(stringBuilder.toString(),params.toArray());
    }

    @Override
    public int getExportCount(UserDto userDto,Integer offset,Integer pageSize) {
        if (userDto.getIsAll()){
            return getUsersTotalCount(userDto);
        }else {
            StringBuilder stringBuilder = new StringBuilder("SELECT COUNT(*) FROM (SELECT * FROM user WHERE 1=1");
            List<Object> params = new ArrayList<>();
            if (userDto.getSearchName() != null && !"".equals(userDto.getSearchName())) {
                stringBuilder.append(" AND name LIKE ?") ;
                params.add("%" + userDto.getSearchName() + "%");
            }
            if (userDto.getSearchSex() != null) {
                stringBuilder.append(" AND sex = ?");
                params.add(userDto.getSearchSex());
            }
            if (userDto.getSearchDept() != null) {
                stringBuilder.append(" AND dept_id = ?");
                params.add(userDto.getSearchDept());
            }
            if (userDto.getSearchPost() != null) {
                stringBuilder.append(" AND post_id = ?");
                params.add(userDto.getSearchPost());
            }
            if (userDto.getSearchAudit() != null) {
                stringBuilder.append(" AND audit_state = ?");
                params.add(userDto.getSearchAudit());
            }
            if (userDto.getSearchRole() != null && !"".equals(userDto.getSearchRole())) {
                stringBuilder.append(" AND role_id = ?");
                params.add(userDto.getSearchRole());
            }
            stringBuilder.append(" AND is_active = 0");
            if (offset!=null&&pageSize!=null){
                stringBuilder.append(" LIMIT ?,?) a");
                params.add(offset);
                params.add(pageSize);
            }
            return JdbcUtil.query(new SingleResultHandler<>(Integer.class), stringBuilder.toString(), params.toArray());
        }
    }

    @Override
    public List<User> getExportUsers(UserDto userDto, Integer offset, Integer pageSize) {
        String sql = "SELECT username,name,sex,idcard idCard,dept_id deptId,post_id postId,mobile FROM user WHERE 1=1";
        List<Object> params = new ArrayList<>();
        if (userDto.getSearchName() != null && !"".equals(userDto.getSearchName())) {
            sql += " AND name LIKE ?";
            params.add("%" + userDto.getSearchName() + "%");
        }
        if (userDto.getSearchSex() != null) {
            sql += " AND sex = ?";
            params.add(userDto.getSearchSex());
        }
        if (userDto.getSearchDept() != null) {
            sql += " AND dept_id = ?";
            params.add(userDto.getSearchDept());
        }
        if (userDto.getSearchPost() != null) {
            sql += " AND post_id = ?";
            params.add(userDto.getSearchPost());
        }
        if (userDto.getSearchAudit() != null) {
            sql += " AND audit_state = ?";
            params.add(userDto.getSearchAudit());
        }
        if (userDto.getSearchRole() != null && !"".equals(userDto.getSearchRole())) {
            sql += " AND role_id = ?";
            params.add(userDto.getSearchRole());
        }
        sql += " AND is_active = 0";
        if (offset!=null&&pageSize!=null){
            sql += " LIMIT ?,?";
            params.add(offset);
            params.add(pageSize);
        }
        return JdbcUtil.query(new MultiResultHandler<>(User.class), sql, params.toArray());
    }

    @Override
    public int assignRole(AssignRoleDto assignRoleDto) {
        String sql = "UPDATE user SET role_id = ? WHERE id = ?";
        return JdbcUtil.update(sql,assignRoleDto.getRoleId(),assignRoleDto.getId());
    }

    @Override
    public List<UserDetailVo> getUsersByName(String query) {
        String sql = "SELECT username,name FROM user WHERE 1=1 AND is_active = 0 AND audit_state = 1";
        List<Object> params = new ArrayList<>();
        if (query!=null&&!"".equals(query)){
            sql += " AND name LIKE ?";
            params.add("%"+query+"%");
        }
        return JdbcUtil.query(new MultiResultHandler<>(UserDetailVo.class),sql,params.toArray());

    }

    @Override
    public UserVo getExamUsers(String username) {
        String sql = "SELECT username,name,(SELECT dept_name FROM department d WHERE d.dept_id = u.dept_id) deptName,(SELECT post_name FROM post p WHERE p.post_id = u.post_id) postName FROM `user` u WHERE u.username = ?";
        return JdbcUtil.query(new SingleResultHandler<>(UserVo.class),sql,username);
    }
}
