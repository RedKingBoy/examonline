package pojo;

import dto.BaseDto;
import lombok.Data;

@Data
public class Paper extends BaseDto {
    private Integer id;
    private String title;
    private Integer totalScore;
    private Integer auditState;
    private Integer useState;
    private String createdUser;
    private Integer deptId;
    private Integer postId;
}
