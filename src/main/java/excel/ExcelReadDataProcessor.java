package excel;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface ExcelReadDataProcessor<T> {
    void process(List<T> data) throws NoSuchAlgorithmException;
}
