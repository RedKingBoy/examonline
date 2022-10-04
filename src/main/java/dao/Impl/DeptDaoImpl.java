package dao.Impl;

import dao.DeptDao;
import dto.AuditDto;
import pojo.Department;
import pojo.Post;
import utils.JdbcUtil;
import utils.MultiResultHandler;
import vo.DeptVo;

import java.util.ArrayList;
import java.util.List;

public class DeptDaoImpl implements DeptDao {
    @Override//注册部门选项必须审核通过且未被删除的
    public List<Department> getAllDepts() {
        String sql = "SELECT dept_id deptId,dept_name deptName FROM department WHERE is_active = 0 AND audit_state = 1";
        return JdbcUtil.query(new MultiResultHandler<>(Department.class), sql);
    }

    @Override
    public List<DeptVo> search(String deptName, String auditState) {
        String sql = "SELECT dept_id deptId,dept_name deptName,audit_state auditState FROM department WHERE is_active = 0";
        ArrayList<Object> params = new ArrayList<>();
        if (deptName != null && !"".equals(deptName)) {
            sql += " AND dept_name LIKE ?";
            params.add("%" + deptName + "%");
        }
        if (auditState != null && !"".equals(auditState)) {
            sql += " AND audit_state = ?";
            params.add(auditState);
        }
        List<Department> dept = JdbcUtil.query(new MultiResultHandler<>(Department.class), sql, params.toArray());
        List<DeptVo> deptVos = new ArrayList<>();
        dept.forEach(d -> {
            String sql1 = "SELECT post_id postId,post_name postName,audit_state auditState,dept_id deptId FROM post WHERE is_active = 0 AND dept_id = ?";
            List<Post> posts = JdbcUtil.query(new MultiResultHandler<>(Post.class), sql1, d.getDeptId());
            DeptVo deptVo = new DeptVo();
            deptVo.setId(d.getDeptId());
            deptVo.setName(d.getDeptName());
            deptVo.setPosts(posts);
            deptVo.setAuditState(d.getAuditState());
            deptVos.add(deptVo);
        });
        return deptVos;
    }

    @Override
    public int audit(AuditDto auditDto) {
        String sql = "UPDATE department SET audit_state = ? WHERE is_active = 0 AND dept_id IN(";
        List<Integer> ids = auditDto.getIds();
        String idsStr = ids.toString();
        idsStr = idsStr.substring(1, idsStr.length() - 1);
        sql += idsStr + ")";
        return JdbcUtil.update(sql, auditDto.getAuditState());

    }

    @Override
    public int delete(String ids) {
        String sql = "UPDATE department SET is_active = 1 WHERE dept_id IN (" + ids + ")";
        return JdbcUtil.update(sql);
    }

    @Override
    public int update(DeptVo deptVo) {
        String sql = "UPDATE department SET dept_name = ? WHERE dept_id = ?";
        return JdbcUtil.update(sql,deptVo.getName(),deptVo.getId());
    }

    @Override
    public int add(String deptName) {
        String sql = "INSERT INTO department(dept_name) VALUES (?)";
        return JdbcUtil.update(sql,deptName);
    }
}
