package vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//创建视图对象,为多个表多属性拼接而成的对象
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailVo {
    private Integer id;
    private String username;
    private String name;
    private Integer sex;
    private String idCard;
    private String mobile;
    private String roleName;
    private Integer deptId;
    private String deptName;
    private Integer postId;
    private String postName;
    private Integer auditState;
}
