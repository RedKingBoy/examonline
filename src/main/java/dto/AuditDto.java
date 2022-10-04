package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
//数据传输对象,专门用来接收前端传回来的数据
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AuditDto {//审核角色的数据传输对象(通用)
    private List<Integer> ids;//需要审核角色的id
    private String auditState;//审核后达到的状态
}
