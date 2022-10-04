package service.Impl;

import dao.Impl.SettingDaoImpl;
import dao.SettingDao;
import dto.SettingDto;
import service.SettingService;
import vo.SettingVo;

import java.util.List;

public class SettingServiceImpl implements SettingService {
    private SettingDao settingDao = new SettingDaoImpl();
    @Override
    public List<SettingVo> search(String tableName) {
       return settingDao.search(tableName);
    }

    @Override
    public int delete(String tableName,String id) {
        return settingDao.delete(tableName,id);
    }

    @Override
    public int add(SettingDto settingDto) {
        return settingDao.add(settingDto);
    }

    @Override
    public int update(SettingDto settingDto) {
        return settingDao.update(settingDto);
    }
}
