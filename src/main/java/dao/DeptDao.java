package dao;

import dto.AuditDto;
import pojo.Department;
import vo.DeptVo;

import java.util.List;

public interface DeptDao {
    List<Department> getAllDepts();

    List<DeptVo> search(String deptName, String auditState);

    int audit(AuditDto auditDto);

    int delete(String ids);

    int update(DeptVo deptVo);

    int add(String deptName);
}
