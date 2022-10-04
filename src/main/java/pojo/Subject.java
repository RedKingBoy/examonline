package pojo;

import dto.BaseDto;
import lombok.Data;

@Data
public class Subject extends BaseDto {
    private String title;
    private Integer deptId;
    private Integer postId;
    private Integer courseId;
    private Integer knowledgeId;
    private Integer examTypeId;
    private Integer subjectTypeId;
    private Integer subjectLevelId;
    private Integer auditState;
}
