package com.rongmei.util;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PoiUtil {

    private static Logger log = LoggerFactory.getLogger(PoiUtil.class);

    public static void expoerDataExcel(HttpServletResponse response, ArrayList<String> titleKeyList, Map<String, String> titleMap, List<Map<String, Object>> src_list) throws IOException {

        String xlsFile_name = "test" + ".xlsx";     //输出xls文件名称
        //内存中只创建100个对象
        Workbook wb = new SXSSFWorkbook(100);           //关键语句
        Sheet sheet = null;     //工作表对象
        Row nRow = null;        //行对象
        Cell nCell = null;      //列对象

        int rowNo = 0;      //总行号
        int pageRowNo = 0;  //页行号

        for (int k = 0; k < src_list.size(); k++) {
            Map<String, Object> srcMap = src_list.get(k);
            //写入300000条后切换到下个工作表
            if (rowNo % 300000 == 0) {
                wb.createSheet("工作簿" + (rowNo / 300000));//创建新的sheet对象
                sheet = wb.getSheetAt(rowNo / 300000);        //动态指定当前的工作表
                pageRowNo = 0;      //新建了工作表,重置工作表的行号为0
                // -----------定义表头-----------
                nRow = sheet.createRow(pageRowNo++);
                // 列数 titleKeyList.size()
                for (int i = 0; i < titleKeyList.size(); i++) {
                    Cell cell_tem = nRow.createCell(i);
                    cell_tem.setCellValue(titleMap.get(titleKeyList.get(i)));
                }
                rowNo++;
                // ---------------------------
            }
            rowNo++;
            nRow = sheet.createRow(pageRowNo++);    //新建行对象

            // 行，获取cell值
            for (int j = 0; j < titleKeyList.size(); j++) {
                nCell = nRow.createCell(j);
                if (srcMap.get(titleKeyList.get(j)) != null) {
                    nCell.setCellValue(srcMap.get(titleKeyList.get(j)).toString());
                } else {
                    nCell.setCellValue("");
                }
            }
        }
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + xlsFile_name);
        response.flushBuffer();
        OutputStream outputStream = response.getOutputStream();
        wb.write(response.getOutputStream());
        outputStream.flush();
        outputStream.close();
    }

    private static Boolean validExxel (String path) {
        String xls = "^.+\\.(?i)(xls)$";	// 正则表达式判断是不是以 .xls 结尾的文件
        String xlsx = "^.+\\.(?i)(xlsx)$";	// 正则表达式判断是不是以 .xlsx 结尾的文件
        if (path == null || !(path.matches(xls) || path.matches(xlsx))) {
            log.error("@!--------------------不是Excel文件！");
            return false;
        }
        return true;
    }

    /**
     *
     * @Title: readExcel
     *
     * @Description: 读取 Excel 文件的数据
     *
     * @param path 文件的路径
     * @param isSheetIndex 是否指定工作表（Sheet)
     * @param sheetIndex 指定工作表在工作簿中的位置
     *  1、如果 isSheetIndex 为 true 时，sheetIndex 为大于0的整数
     *  	1.1、如果 sheetIndex 小于等于 0，默认读取工作簿中的第一张工作表；
     *  	1.2、如果 sheetIndex 大于工作簿中工作表的总数，默认读取工作簿中最后一张工作表；
     *  2、如果 isSheetIndex 为 false 时，sheetIndex 可以是任何整数，此时读取工作簿中所有工作表的数据
     * @return Excel Excel 文件的数据集
     */
    public static List<Object> readExcel (String path, Boolean isSheetIndex, Integer sheetIndex) {
        // 指定工作表位置小于1时，默认读取第一张工作表的数据
        if (sheetIndex < 1) {
            sheetIndex = 1;
        }
        List<Object> dataList = new ArrayList<>();
        InputStream is = null;
        // 通过指定的文件路径创建文件对象
        File file = new File (path);
        if (file == null || !file.exists()) {
            log.error("@!--------------------文件不存在！");
            return null;
        }
        if (!validExxel(path)) {
            log.error("@!--------------------此文件不是 Excel 文件！");
            return null;
        }
        try {
            is = new FileInputStream(file);	// 获取文件的输入流
            dataList = readFile (is, isSheetIndex, sheetIndex-1);
        } catch (FileNotFoundException e) {
            log.error("@!--------------------找不到该文件！");
        } finally {
            if (is != null) {
                try {
                    is.close();	// 关闭文件输入流
                } catch (IOException e) {
                    log.error("@!-----------------------------文件输入流关闭失败！");
                }
            }
        }
        return dataList;
    }

    /**
     *
     * @Title: readFile
     *
     * @Description: 读取输入流中的数据
     *
     * @param is 文件输入流
     * @param isSheetIndex 是不是指定的工作表
     * @param sheetIndex 工作表在工作簿中的位置
     * @return 工作簿的数据
     */
    private static List<Object> readFile (InputStream is, Boolean isSheetIndex, Integer sheetIndex) {
        List<Object> dataList = new ArrayList<>();
        Workbook wb = null;
        try {
            // 创建工作簿（HSSFWorkbook或XSSFWorkbook），根据文件输入流自动检测创建对象
            wb = WorkbookFactory.create(is);
            Integer sheetCount = wb.getNumberOfSheets();	// 工作簿中工作表的总数
            List<Object> sheetList = new ArrayList<>();
            if (isSheetIndex) {	// 读取指定工作表
                // 判断指定的位置是否在工作簿中有对应的工作表
                if (sheetIndex < sheetCount) {
                    sheetList = readWorkBook (wb, sheetIndex);
                } else {
                    // 没有找到对应的工作表时，读取工作簿的最后一张工作表
                    sheetList = readWorkBook (wb, sheetCount-1);
                }
                dataList.add(sheetList);	// 添加工作表的数据到工作簿数据集中
            } else {
                // 读取工作簿的所有工作表
                for (int i = 0; i < sheetCount; i++) {
                    sheetList = readWorkBook (wb, i);
                    dataList.add(sheetList);	// 将工作簿中的每一张不为空的工作表添加到工作簿的数据集中
                }
            }
        } catch (IOException | InvalidFormatException e) {
            log.error("@!------------------------------创建工作簿失败！");
        }
        return dataList;
    }

    /**
     *
     * @Title: readWorkBook
     *
     * @Description: 读取工作表中的数据
     *
     * @param wb 工作簿
     * @param index 工作表在工作簿中的位置
     * @return 工作表的数据
     */
    private static List<Object> readWorkBook (Workbook wb, Integer index) {
        List<Object> sheetList = new ArrayList<>();
        Integer totalRows = 0;
        Integer totalCells = 0;
        Sheet sheet = wb.getSheetAt(index);	// 获取工作表对象
        sheetList.add(sheet.getSheetName());	// 添加工作表的名称到工作表中
        totalRows = sheet.getPhysicalNumberOfRows();	// 获取工作表的总行数
        if (totalRows > 0 && sheet.getRow(0) != null) {
            // 获取工作表的总列数，第一行或第二行的列数必须是整张工作表的总列数
            if (sheet.getRow(1) != null && sheet.getRow(0).getPhysicalNumberOfCells() < sheet.getRow(1).getPhysicalNumberOfCells()) {
                totalCells = sheet.getRow(1).getPhysicalNumberOfCells();
            } else {
                totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
            }
        }
        List<Object> rowList = new ArrayList<>();
        for (int i = 0; i < totalRows; i++) {
            Row row = sheet.getRow(i);	// 获取行对象
            // 去除空行
            if (row == null) {
                continue;
            }
            List<Object> cellList = new ArrayList<Object>();
            for (int j = 0; j < totalCells; j++) {
                Cell cell = row.getCell(j);	// 获取单元格对象
                cellList.add(cell);	// 将单元格中的数据添加到单元格数据集中
            }
            rowList.add(cellList);	// 将单元格数据集天津爱到行数据集中
        }
        sheetList.add(rowList);	// 将行数据集添加到工作表中
        return sheetList;
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
//		List<Object> dataList = readExcel ("D:\\Desktop\\test.xlsx", false, 0);
        List<Object> dataList = readExcel ("D:\\workspaceweb\\test\\test.xlsx", true, 1);
        if (dataList != null) {
            for (int i = 0; i < dataList.size(); i++) {
                // 获取工作表
                List<Object> sheetList = (List<Object>) dataList.get(i);
                String sheetName =  (String) sheetList.get(0);
                System.out.println("工作表的名称：" + sheetName);
                System.out.println("工作表的数据：");
                for (int j = 1; j < sheetList.size(); j++) {
                    // 获取工作表的每一行
                    List<Object> rowList = (List<Object>) sheetList.get(j);

                    for (int k = 0; k < rowList.size(); k++) {
                        // 获取工作表的每一列
                        List<Object> cellList = (List<Object>) rowList.get(k);
                        for (Object object : cellList) {
                            // 获取每一个单元格的数据
                            System.out.print("--"+object+" zzz");
                        }
                        System.out.println();
                        System.out.println();
                        System.out.println();
                    }
                }
            }
        }
    }
}
