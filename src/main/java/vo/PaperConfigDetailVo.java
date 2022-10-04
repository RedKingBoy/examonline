package vo;

import dto.SubjectDto;
import lombok.Data;
import pojo.Subject;

import java.util.List;

@Data
public class PaperConfigDetailVo {
    private Integer id;
    private String title;
    private Integer subjectScore;
    private List<SubjectDto> subjects;
}
