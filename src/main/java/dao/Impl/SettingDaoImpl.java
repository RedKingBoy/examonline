package dao.Impl;

import dao.SettingDao;
import dto.SettingDto;
import utils.JdbcUtil;
import utils.MultiResultHandler;
import vo.SettingVo;

import java.util.List;

public class SettingDaoImpl implements SettingDao {
    @Override
    public List<SettingVo> search(String tableName) {
        String sql = "SELECT id,name FROM " + tableName;
        return JdbcUtil.query(new MultiResultHandler<>(SettingVo.class),sql);
    }

    @Override
    public int delete(String tableName,String id) {
        String sql = "DELETE FROM "+tableName+" WHERE id = ?";
        return JdbcUtil.update(sql,id);
    }

    @Override
    public int add(SettingDto settingDto) {
        String sql = "INSERT INTO "+settingDto.getTableName()+"(name) VALUES(?)";
        return JdbcUtil.update(sql,settingDto.getSettingVo().getName());
    }

    @Override
    public int update(SettingDto settingDto) {
        String sql = "UPDATE "+settingDto.getTableName()+ " SET name = ? WHERE id = ?";
        return JdbcUtil.update(sql,settingDto.getSettingVo().getName(),settingDto.getSettingVo().getId());
    }
}
