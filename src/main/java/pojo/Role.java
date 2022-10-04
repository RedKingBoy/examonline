package pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Role {
    private Integer id;
    private String name;
    private Date createTime;
    private String createUser;
    private Integer auditState;
    private Integer isActive;
}
