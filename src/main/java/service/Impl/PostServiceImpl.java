package service.Impl;

import dao.Impl.PostDaoImpl;
import dao.PostDao;
import dto.AuditDto;
import pojo.Post;
import service.PostService;

import java.util.List;

public class PostServiceImpl implements PostService {
    private PostDao postDao = new PostDaoImpl();
    @Override
    public List<Post> queryByDept(String deptId) {
        return postDao.getAllPostsByDept(deptId);
    }

    @Override
    public int audit(AuditDto auditDto) {
        return postDao.audit(auditDto);
    }

    @Override
    public int delete(String ids) {
        return postDao.delete(ids);
    }

    @Override
    public int update(Post post) {
        return postDao.update(post);
    }

    @Override
    public int add(Post post) {
        return postDao.add(post);
    }
}
