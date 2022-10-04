package pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Exam {
    private Integer id;
    private String startTime;
    private Integer costTime;
    private Integer paperAId;
    private Integer paperBId;
    private String examiner;
    private String createdUser;
}
