package pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    @ExcelIgnore
    private Integer id;
    @ExcelProperty(value = "账号",index = 0)
    private String username;
    @ExcelIgnore
    private String password;
    @ExcelIgnore
    private String salt;
    @ExcelProperty(value = "用户名",index = 1)
    private String name;
    @ExcelProperty(value = "性别",index = 2)
    private Integer sex;
    @ExcelProperty(value = "身份证",index = 3)
    private String idCard;
    @ExcelProperty(value = "部门",index = 4)
    private Integer deptId;
    @ExcelProperty(value = "岗位",index = 5)
    private Integer postId;
    @ExcelIgnore
    private Integer auditState;
    @ExcelProperty(value = "手机号",index = 6)
    private String mobile;
    @ExcelIgnore
    private Integer roleId;
    @ExcelIgnore
    private Integer isActive;
}
