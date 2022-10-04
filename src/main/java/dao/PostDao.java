package dao;

import dto.AuditDto;
import pojo.Post;

import java.util.List;

public interface PostDao {
    List<Post> getAllPostsByDept(String deptId);

    int audit(AuditDto auditDto);

    int delete(String ids);

    void deleteDeptPost(String ids);

    int update(Post post);

    int add(Post post);
}
