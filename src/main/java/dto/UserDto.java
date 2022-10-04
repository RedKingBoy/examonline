package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto extends BaseDto {//用户查询的数据传输对象
    private String searchName;
    private Integer searchSex;
    private Integer searchDept;
    private Integer searchPost;
    private String searchRole;
    private Integer searchAudit;
    private Boolean isAll;
}
