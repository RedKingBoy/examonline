package excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExcelUtil {
    private static final int BATCH_PROCESS_COUNT = 1000;//读取excel数据批量处理数
    private static final int SHEET_DATA_COUNT = 5000;//每张sheet写出的数据量
    private static final LinkedBlockingQueue<Runnable> queue;
    private static final ThreadPoolExecutor threadPools;

    static {
        queue = new LinkedBlockingQueue<Runnable>(2048);
        threadPools = new ThreadPoolExecutor(5, 20, 1, TimeUnit.MINUTES, queue);
    }
    //path只适合本地
    public static <T> void exportExcel(String path, Class<T> clazz, ExcelDataRepository<T> repository) throws FileNotFoundException {
        OutputStream os = new FileOutputStream(path);
        exportExcel(os, clazz, repository);
    }

    //方法重载可以通过流对excel操作,万能方法
    public static <T> void exportExcel(OutputStream os, Class<T> clazz, ExcelDataRepository<T> repository) {
        ExcelWriterBuilder builder = EasyExcel.write(os, clazz);
        //excel写入器
        ExcelWriter excelWriter = builder.build();
        //获取总查询数
        int total = repository.getAllDataCount();
        //获取sheet的数量
        int sheetCount = total / SHEET_DATA_COUNT;
        if (total % SHEET_DATA_COUNT > 0) {
            sheetCount += 1;
        }
        for (int i = 0; i < sheetCount; i++) {
            WriteSheet writeSheet = new WriteSheet();
            writeSheet.setSheetNo(i);
            writeSheet.setSheetName(clazz.getName() + "" + (i + 1));
            excelWriter.write(repository.getExcelDataPerPage(i + 1, SHEET_DATA_COUNT), writeSheet);
        }
        //必须finish才会输出数据
        excelWriter.finish();
    }

    public static <T> void parseExcel(String path, Class<T> clazz, ExcelReadDataProcessor<T> dataProcessor) throws FileNotFoundException {
        InputStream is = new FileInputStream(path);
        parseExcel(is,clazz,dataProcessor);
    }
    public static <T> void parseExcel(InputStream is, Class<T> clazz, ExcelReadDataProcessor<T> dataProcessor) {
        ExcelReadListener<T> listener = new ExcelReadListener<>(dataProcessor);
        ExcelReaderBuilder builder = EasyExcel.read(is, clazz, listener);
        builder.sheet().doRead();
    }

    //因为调用该内部类的是静态方法所以要写成静态内部类
    private static class ExcelReadListener<T> implements ReadListener<T> {
        private List<T> datas = new ArrayList<>();
        private ExcelReadDataProcessor<T> dataProcessor;

        public ExcelReadListener(ExcelReadDataProcessor<T> dataProcessor) {
            this.dataProcessor = dataProcessor;
        }

        @Override
        public void invoke(T t, AnalysisContext analysisContext) {
            if (datas.size() == BATCH_PROCESS_COUNT) {
                threadPools.submit(new ExcelReadRunnable<T>(dataProcessor, datas));
                datas = new ArrayList<>();
            }
            datas.add(t);
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext analysisContext) {
            if (datas.size() > 0) {
                //自己创造线程任务把集合传进去避免线程不安全问题
                threadPools.submit(new ExcelReadRunnable<T>(dataProcessor, datas));
            }
        }
    }

    private static class ExcelReadRunnable<T> implements Runnable {
        private ExcelReadDataProcessor<T> dataProcessor;
        private List<T> dataList;

        public ExcelReadRunnable(ExcelReadDataProcessor<T> dataProcessor, List<T> dataList) {
            this.dataProcessor = dataProcessor;
            this.dataList = dataList;
        }

        @Override
        public void run() {
            try {
                dataProcessor.process(dataList);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
    }
}
