package vo;

import lombok.Data;

@Data
public class MenuVo {//查询菜单的vo主要是匹配穿梭框才命别名
    private Integer key;//菜单id
    private String label;//菜单名称
}
