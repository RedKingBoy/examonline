package service.Impl;

import dao.DeptDao;
import dao.Impl.DeptDaoImpl;
import dao.Impl.PostDaoImpl;
import dao.PostDao;
import dto.AuditDto;
import pojo.Department;
import service.DeptService;
import vo.DeptVo;

import java.util.List;

public class DeptServiceImpl implements DeptService {
    private PostDao postDao = new PostDaoImpl();
    private DeptDao deptDao = new DeptDaoImpl();
    @Override
    public List<Department> queryAll() {
        return deptDao.getAllDepts();
    }

    @Override
    public List<DeptVo> search(String deptName, String auditState) {
        return deptDao.search(deptName,auditState);
    }

    @Override
    public int audit(AuditDto auditDto) {
        return deptDao.audit(auditDto);
    }

    @Override
    public int delete(String ids) {
        postDao.deleteDeptPost(ids);
        return deptDao.delete(ids);
    }

    @Override
    public int update(DeptVo deptVo) {
        return deptDao.update(deptVo);
    }

    @Override
    public int add(String deptName) {
        return deptDao.add(deptName);
    }
}
