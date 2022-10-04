package dto;

import lombok.Data;

import java.util.List;

@Data
public class AssignMenuDto {
    private Integer roleId;//角色id
    private List<Integer> menuIds;//分配的菜单集合
}
