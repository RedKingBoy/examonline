package dto;

import lombok.Data;

import java.util.List;

@Data
public class ExamSubmitDto {
    private Integer examId;
    private Integer paperId;
    private Integer totalScore;
    private String username;
    private List<ConfigInfo> configInfos;
}
