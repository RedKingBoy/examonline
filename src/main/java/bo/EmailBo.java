package bo;

import lombok.Data;

//bo是业务对象,用来处理业务中产生的对象
@Data
public class EmailBo {
    private String startTime;
    private String examName;
    private String email;
    private String costTime;
}
