package service;

import dto.AuditDto;
import pojo.Post;

import java.util.List;

public interface PostService {
    List<Post> queryByDept(String deptId);

    int audit(AuditDto auditDto);

    int delete(String ids);

    int update(Post post);

    int add(Post post);
}
