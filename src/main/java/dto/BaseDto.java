package dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BaseDto {//基础dto,即所有的dto的共性
    private Integer currentPage;
    private Integer pageSize;
}
