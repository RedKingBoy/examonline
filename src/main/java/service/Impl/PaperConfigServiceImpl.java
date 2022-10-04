package service.Impl;

import dao.Impl.PaperConfigDaoImpl;
import dao.PaperConfigDao;
import dto.PaperConfigDto;
import service.PaperConfigService;

public class PaperConfigServiceImpl implements PaperConfigService {
    private PaperConfigDao paperConfigDao = new PaperConfigDaoImpl();
    @Override
    public int add(PaperConfigDto paperConfigDto) {
        return paperConfigDao.add(paperConfigDto);
    }
}
