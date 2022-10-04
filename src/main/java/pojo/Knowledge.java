package pojo;

import lombok.Data;

@Data
public class Knowledge {
    private Integer id;
    private String name;
    private Integer courseId;
    private Integer auditState;
}
