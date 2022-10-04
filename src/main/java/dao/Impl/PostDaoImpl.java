package dao.Impl;

import dao.PostDao;
import dto.AuditDto;
import pojo.Post;
import utils.JdbcUtil;
import utils.MultiResultHandler;

import java.util.List;

public class PostDaoImpl implements PostDao {
    @Override//注册选择的岗位必须为未删除且审核通过的
    public List<Post> getAllPostsByDept(String deptId) {
        String sql = "SELECT post_id postId,post_name postName,dept_id deptId FROM post WHERE dept_id = ? AND is_active = 0 AND audit_state = 1";
        return JdbcUtil.query(new MultiResultHandler<>(Post.class), sql, deptId);
    }

    @Override
    public int audit(AuditDto auditDto) {
        String sql = "UPDATE post SET audit_state = ? WHERE is_active = 0 AND post_id IN(";
        List<Integer> ids = auditDto.getIds();
        String idsStr = ids.toString();
        idsStr = idsStr.substring(1, idsStr.length() - 1);
        sql += idsStr + ")";
        return JdbcUtil.update(sql, auditDto.getAuditState());
    }

    @Override
    public int delete(String ids) {
        String sql = "UPDATE post SET is_active = 1 WHERE post_id IN (" + ids + ")";
        return JdbcUtil.update(sql);
    }

    @Override
    public void deleteDeptPost(String ids) {
        String sql = "UPDATE post SET is_active = 1 WHERE is_active = 0 AND dept_id IN (" + ids + ")";
        JdbcUtil.update(sql);
    }

    @Override
    public int update(Post post) {
        String sql = "UPDATE post SET post_name = ? WHERE post_id = ?";
        return JdbcUtil.update(sql, post.getPostName(), post.getPostId());
    }

    @Override
    public int add(Post post) {
        String sql = "INSERT INTO post(post_name,dept_id) VALUES (?,?)";
        return JdbcUtil.update(sql,post.getPostName(),post.getDeptId());
    }
}
