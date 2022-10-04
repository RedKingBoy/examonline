package dao;

import pojo.SubjectOption;

import java.util.List;

public interface OptionsDao {
    int addOptions(List<SubjectOption> options,Integer subjectId);

    List<SubjectOption> query(Integer id);

    int deleteOptions(Integer id);
}
