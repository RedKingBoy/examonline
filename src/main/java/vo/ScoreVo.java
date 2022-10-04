package vo;

import lombok.Data;

import java.util.Date;

@Data
public class ScoreVo {
    private Integer examId;
    private String examName;
    private Date examTime;
    private Integer totalScore;
    private String deptName;
    private String postName;
    private String examUserName;
    private Integer score;
}
