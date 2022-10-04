package pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Post {
    private Integer postId;
    private String postName;
    private Integer deptId;
    private Integer auditState;
}
