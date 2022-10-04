package pojo;

import lombok.Data;

@Data
public class SubjectOption {//试题的选项
    private Integer id;//编号
    private String content;//选项内容
    private Integer orders;//选项的序号用于abcd的排序
    private Integer subjectId;//所属试题的编号
}
