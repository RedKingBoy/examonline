package dto;

import lombok.Data;

@Data
public class ScoreDto extends BaseDto {
    private String name;
    private String paperName;
    private String start;
    private String end;
}
