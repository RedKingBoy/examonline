package dao;

import dto.SettingDto;
import vo.SettingVo;

import java.util.List;

public interface SettingDao {
    List<SettingVo> search(String tableName);

    int delete(String tableName,String id);

    int add(SettingDto settingDto);

    int update(SettingDto settingDto);
}
