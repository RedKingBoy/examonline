package vo;

import lombok.Data;
import pojo.SubjectOption;

import java.util.List;

@Data
public class SubjectVo {
    private Integer id;
    private String title;
    private Integer deptId;
    private String deptName;
    private Integer postId;
    private String postName;
    private Integer courseId;
    private String courseName;
    private Integer knowledgeId;
    private String knowledgeName;
    private Integer examTypeId;
    private String examTypeName;
    private Integer subjectTypeId;
    private String subjectTypeName;
    private Integer subjectLevelId;
    private String subjectLevelName;
    private String answer;
    private Integer auditState;
    private Integer mode;
    private List<SubjectOption> options;
}
