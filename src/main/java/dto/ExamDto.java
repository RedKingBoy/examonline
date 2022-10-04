package dto;

import lombok.Data;

import java.util.List;

@Data
public class ExamDto extends BaseDto {
    private Integer useState;
    private Integer auditState;
    private String start;
    private String end;
}
