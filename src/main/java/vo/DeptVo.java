package vo;

import lombok.Data;
import pojo.Post;

import java.util.List;

@Data
public class DeptVo {//带岗位的部门查询
    private Integer id;
    private String name;
    private Integer auditState;
    private List<Post> posts;
}
