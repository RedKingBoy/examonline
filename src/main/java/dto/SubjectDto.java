package dto;

import lombok.Data;
import pojo.SubjectOption;

import java.util.List;

@Data
public class SubjectDto {
    private Integer id;
    private String title;
    private Integer deptId;
    private Integer postId;
    private Integer courseId;
    private Integer knowledgeId;
    private Integer examTypeId;
    private Integer subjectTypeId;
    private Integer subjectLevelId;
    private String createdUser;
    private String answer;
    private List<SubjectOption> options;
}
