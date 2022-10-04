package service;

import dto.SettingDto;
import vo.SettingVo;

import java.util.List;

public interface SettingService {
    List<SettingVo> search(String tableName);

    int delete(String tableName,String id);

    int add(SettingDto settingDto);

    int update(SettingDto settingDto);
}
