package vo;

import lombok.Data;

import java.util.List;

@Data
public class PaperDetailVo {
    private Integer id;
    private Integer paperId;//考虑到后面算成绩时需要当前考试的id和试卷id
    private String startTime;
    private Integer costTime;//开始时间和花费时间主要用于计时器的数据使用
    private Integer examScore;
    private String title;
    private String deptName;
    private String postName;
    private Integer totalScore;
    private List<PaperConfigDetailVo> paperConfigs;
    private UserVo examUser;
}
