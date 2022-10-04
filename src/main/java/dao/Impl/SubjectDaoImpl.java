package dao.Impl;

import dao.SubjectDao;
import dto.AuditDto;
import dto.SubjectDto;
import pojo.Subject;
import utils.JdbcUtil;
import utils.MultiResultHandler;
import utils.SingleResultHandler;
import vo.SubjectVo;

import java.util.ArrayList;
import java.util.List;

public class SubjectDaoImpl implements SubjectDao {
    @Override
    public int addSubject(SubjectDto subjectDto) {
        String sql = "INSERT INTO `subject`(title,answer,post_id,dept_id,exam_type_id,subject_type_id,subject_level_id,course_id,knowledge_id,created_user) VALUES (?,?,?,?,?,?,?,?,?,?);";
        Object[] params = {
                subjectDto.getTitle(),
                subjectDto.getAnswer(),
                subjectDto.getPostId(),
                subjectDto.getDeptId(),
                subjectDto.getExamTypeId(),
                subjectDto.getSubjectTypeId(),
                subjectDto.getSubjectLevelId(),
                subjectDto.getCourseId(),
                subjectDto.getKnowledgeId(),
                subjectDto.getCreatedUser(),
        };
        return JdbcUtil.update(sql,params);
    }

    @Override
    public Integer getLastInsertId() {
        String sql = "SELECT Max(id) FROM subject";
        return JdbcUtil.query(new SingleResultHandler<>(Integer.class),sql);
    }

    @Override
    public List<SubjectVo> query(Subject subject, Integer offset, Integer pageSize) {
        StringBuilder stringBuilder = new StringBuilder("SELECT \n" +
                "\ta.id, \n" +
                "\ta.title, \n" +
                "\ta.answer, \n" +
                "\ta.post_id postId,\n" +
                "\te.post_name postName, \n" +
                "\ta.dept_id deptId,\n" +
                "\td.dept_name deptName, \n" +
                "\ta.exam_type_id examTypeId,\n" +
                "\tf.`name` examTypeName, \n" +
                "\ta.subject_level_id subjectLevelId, \n" +
                "\th.`name` subjectLevelName,\n" +
                "\ta.knowledge_id knowledgeId, \n" +
                "\tc.`name` knowledgeName,\n" +
                "\ta.subject_type_id subjectTypeId, \n" +
                "\tg.`name` subjectTypeName,\n" +
                "\ta.audit_state auditState, \n" +
                "\ta.course_id courseId,\n" +
                "\tb.`name` courseName\n" +
                "FROM subject a\n" +
                "JOIN course b \n" +
                "ON a.course_id = b.id\n" +
                "JOIN knowledge c \n" +
                "ON a.knowledge_id = c.id\n" +
                "LEFT JOIN department d \n" +//left左外连接应该明确为空的字段具体指代的哪一个表添加left进行连接
                "ON a.dept_id = d.dept_id\n" +
                "LEFT JOIN post e \n" +//left左外连接应该明确为空的字段具体指代的哪一个表添加left进行连接
                "ON a.post_id = e.post_id\n" +
                "JOIN exam_type f \n" +
                "ON a.exam_type_id = f.id\n" +
                "JOIN subject_type g \n" +
                "ON a.subject_type_id = g.id\n" +
                "JOIN subject_level h \n" +
                "ON a.subject_level_id = h.id WHERE a.is_active = 0 AND 1=1");
        List<Object> params = new ArrayList<>();
        String title = subject.getTitle();
        if (title!=null&&!"".equals(title)){
            stringBuilder.append(" AND a.title LIKE ?");
            params.add("%"+title+"%");
        }
        Integer auditState = subject.getAuditState();
        if (auditState!=null){
            stringBuilder.append(" AND a.audit_state = ?");
            params.add(auditState);
        }
        Integer courseId = subject.getCourseId();
        if (courseId!=null){
            stringBuilder.append(" AND a.course_id = ?");
            params.add(courseId);
        }
        Integer deptId = subject.getDeptId();
        if (deptId!=null){
            stringBuilder.append(" AND a.dept_id = ?");
            params.add(deptId);
        }
        Integer knowledgeId = subject.getKnowledgeId();
        if (knowledgeId!=null){
            stringBuilder.append(" AND a.knowledge_id = ?");
            params.add(knowledgeId);
        }
        Integer examTypeId = subject.getExamTypeId();
        if (examTypeId!=null){
            stringBuilder.append(" AND a.exam_type_id = ?");
            params.add(examTypeId);
        }
        Integer subjectTypeId = subject.getSubjectTypeId();
        if (subjectTypeId!=null){
            stringBuilder.append(" AND a.subject_type_id = ?");
            params.add(subjectTypeId);
        }
        Integer subjectLevelId = subject.getSubjectLevelId();
        if (subjectLevelId!=null){
            stringBuilder.append(" AND a.subject_level_id = ?");
            params.add(subjectLevelId);
        }
        Integer postId = subject.getPostId();
        if (postId!=null){
            stringBuilder.append(" AND a.post_id = ?");
            params.add(postId);
        }
        stringBuilder.append(" LIMIT ?,?");
        params.add(offset);
        params.add(pageSize);
        return JdbcUtil.query(new MultiResultHandler<>(SubjectVo.class),stringBuilder.toString(),params.toArray());
    }

    @Override
    public int getSubjectsCount(Subject subject) {
        StringBuilder stringBuilder = new StringBuilder("SELECT COUNT(*) FROM subject WHERE 1=1 AND is_active = 0");
        List<Object> params = new ArrayList<>();
        String title = subject.getTitle();
        if (title!=null&&!"".equals(title)){
            stringBuilder.append(" AND title LIKE ?");
            params.add("%"+title+"%");
        }
        Integer auditState = subject.getAuditState();
        if (auditState!=null){
            stringBuilder.append(" AND audit_state = ?");
            params.add(auditState);
        }
        Integer courseId = subject.getCourseId();
        if (courseId!=null){
            stringBuilder.append(" AND course_id = ?");
            params.add(courseId);
        }
        Integer deptId = subject.getDeptId();
        if (deptId!=null){
            stringBuilder.append(" AND dept_id = ?");
            params.add(deptId);
        }
        Integer knowledgeId = subject.getKnowledgeId();
        if (knowledgeId!=null){
            stringBuilder.append(" AND knowledge_id = ?");
            params.add(knowledgeId);
        }
        Integer examTypeId = subject.getExamTypeId();
        if (examTypeId!=null){
            stringBuilder.append(" AND exam_type_id = ?");
            params.add(examTypeId);
        }
        Integer subjectTypeId = subject.getSubjectTypeId();
        if (subjectTypeId!=null){
            stringBuilder.append(" AND subject_type_id = ?");
            params.add(subjectTypeId);
        }
        Integer subjectLevelId = subject.getSubjectLevelId();
        if (subjectLevelId!=null){
            stringBuilder.append(" AND subject_level_id = ?");
            params.add(subjectLevelId);
        }
        Integer postId = subject.getPostId();
        if (postId!=null){
            stringBuilder.append(" AND post_id = ?");
            params.add(postId);
        }
        return JdbcUtil.query(new SingleResultHandler<>(Integer.class),stringBuilder.toString(),params.toArray());
    }

    @Override
    public int updateSubject(SubjectDto subjectDto) {
        String sql = "UPDATE subject SET title = ?,post_id = ?,dept_id = ?,exam_type_id = ?,subject_type_id = ?,subject_level_id = ?" +
                ",answer = ?,course_id = ?,knowledge_id = ? WHERE id = ?";
        Object[] params = {
                subjectDto.getTitle(),
                subjectDto.getPostId(),
                subjectDto.getDeptId(),
                subjectDto.getExamTypeId(),
                subjectDto.getSubjectTypeId(),
                subjectDto.getSubjectLevelId(),
                subjectDto.getAnswer(),
                subjectDto.getCourseId(),
                subjectDto.getKnowledgeId(),
                subjectDto.getId()
        };
        return JdbcUtil.update(sql,params);
    }

    @Override
    public int audit(AuditDto auditDto) {
        String ids = auditDto.getIds().toString();
        ids = ids.substring(1,ids.length()-1);
        String sql = "UPDATE subject SET audit_state = ? WHERE id IN ("+ids+")";
        return JdbcUtil.update(sql,auditDto.getAuditState());
    }

    @Override
    public int deleteSubject(String id) {
        String sql = "UPDATE subject SET is_active = 1 WHERE id = ?";
        return JdbcUtil.update(sql,id);
    }
}
