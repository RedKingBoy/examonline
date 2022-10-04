package pojo;

import lombok.Data;

@Data
public class PaperConfig {
    private Integer id;
    private String title;
    private Integer subjectScore;
    private Integer paperId;
    private String subjectIds;
}
