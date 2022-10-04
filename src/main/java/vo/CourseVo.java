package vo;

import lombok.Data;
import pojo.Knowledge;

import java.util.List;

@Data
public class CourseVo {
    private Integer id;
    private String name;
    private Integer auditState;
    private List<Knowledge> knowledges;
}
