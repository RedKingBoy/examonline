package dto;

import lombok.Data;

import java.util.List;

@Data
public class ExamDispatchDto {
    private Integer examId;
    private List<String> usernames;
}
