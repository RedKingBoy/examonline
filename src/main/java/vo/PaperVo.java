package vo;

import lombok.Data;

import java.util.Date;

@Data
public class PaperVo {
    private Integer id;
    private String title;
    private Integer totalScore;
    private Integer auditState;
    private Integer useState;
    private String createdUser;
    private Integer deptId;
    private String deptName;
    private Integer postId;
    private String postName;
    private Date createdTime;
}
