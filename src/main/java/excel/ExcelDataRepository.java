package excel;

import java.util.List;

public interface ExcelDataRepository<T> {//接口自己实现如何从数据库获取数据
    //获取查询的总数据
    int getAllDataCount();
    //获取当前sheet的数据集合
    List<T> getExcelDataPerPage(int page,int pageSize);

}
