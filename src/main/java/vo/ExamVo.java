package vo;

import lombok.Data;

import java.util.Date;

@Data
public class ExamVo {
    private Integer id;
    private Date startTime;
    private Integer costTime;
    private Integer auditState;
    private Integer useState;
    private String examiner;
    private Integer paperAId;
    private String paperAName;
    private Integer paperBId;
    private String paperBName;
    private String createdUser;
}
