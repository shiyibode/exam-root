package com.ynsh.common.utils;

import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by syb on 2017/5/5.
 */
public class ExcelGenerateUtils {
    private static final Logger logger = LoggerFactory.getLogger(ExcelGenerateUtils.class);

    /**
     *
     * @param response 返回流
     * @param data 要写入文件的数据
     * @param fileName 文件名称
     * @param fields 文件包含的字段（中文名）
     * @param fieldsToAttributes 中文字段对应的对象属性名
     * @param additionalHeader 附加的标题（如日期，存款类型）
     * @throws Exception
     */
    public void getExcelStream(HttpServletResponse response, List data, String fileName, String fields, String fieldsToAttributes, String[] additionalHeader,Map convertValueMap) throws Exception{
        this.setExcelResponseFormat(fileName,response);
        OutputStream os = response.getOutputStream();
        WritableWorkbook book;
        int currentRow = 0;
        String[] attributes = fieldsToAttributes.split(",");
        String[] headerArr = fields.split(",");
        int fieldSize = headerArr.length;

        //校验
        if (fieldSize<=0) throw new Exception("没有需要写入的字段");

        try {
            book = Workbook.createWorkbook(os);
            WritableSheet sheet_emp_avg = book.createSheet(fileName, 0);
            Label tempLabel = null;
            // 标题
            tempLabel = new Label(0, 0, fileName, getTitleCellStyle());
            sheet_emp_avg.addCell(tempLabel);
            sheet_emp_avg.mergeCells(0, 0, fieldSize-1, 0);
            currentRow++;

            // 表头
            if (additionalHeader != null && additionalHeader.length>0){
                for (int t=0;t<additionalHeader.length;t++){
                    tempLabel = new Label(0,currentRow,additionalHeader[t],getSecondHeaderCellStyle());
                    sheet_emp_avg.addCell(tempLabel);
                    currentRow++;
                }
            }

            //字段
            for (int i = 0; i < fieldSize; i++) {
                tempLabel = new Label( i, currentRow, headerArr[i], getHeaderCellStyle());
                sheet_emp_avg.setColumnView(i,15);
                sheet_emp_avg.addCell(tempLabel);
            }
            currentRow++;

            //主体内容
            writeDataToResponse(data,attributes,sheet_emp_avg,currentRow,convertValueMap);

            book.write();
            book.close();

            os.flush();
            os.close();
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("生成Excel失败");
        }
    }

    /**
     * 设置response返回头格式
     * @param fileName 文件名
     * @param response 返回流
     */
    protected void setExcelResponseFormat(String fileName, HttpServletResponse response){
        String name = null;
        try {
            name=new String(fileName.getBytes("UTF-8"),"iso-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.reset();
        response.setCharacterEncoding("gb2312");
        response.setContentType("application/OCTET-STREAM;charset=gb2312");
        response.setHeader("pragma", "no-cache");
        response.addHeader("Content-Disposition", "attachment;filename=\""
                + name + ".xls\"");
    }

    /**
     * 正中标题格式
     * @return
     */
    protected WritableCellFormat getTitleCellStyle(){
        WritableFont wf_merge = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD,
                false, UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
        WritableCellFormat wff_merge = new WritableCellFormat(wf_merge);
        try {
            wff_merge.setAlignment(jxl.format.Alignment.CENTRE);//把水平对齐方式指定为居中
            wff_merge.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//把垂直对齐方式指定为居中
        } catch (WriteException e) {
            e.printStackTrace();
        }
        return wff_merge;
    }

    /**
     * 字段格式
     * @return
     */
    protected WritableCellFormat getHeaderCellStyle() {
        WritableFont wf_head = new WritableFont(WritableFont.ARIAL,10,WritableFont.NO_BOLD,
                false,UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.BLACK);
        WritableCellFormat wff_head = new WritableCellFormat(wf_head);
        try {
            wff_head.setAlignment(jxl.format.Alignment.CENTRE);
            wff_head.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
        } catch (WriteException e) {
            e.printStackTrace();
        }
        return wff_head;
    }

    /**
     * 附加表头格式
     * @return
     */
    protected WritableCellFormat getSecondHeaderCellStyle(){
        WritableFont wf_head = new WritableFont(WritableFont.ARIAL,10,WritableFont.NO_BOLD,
                false,UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.BLACK);
        WritableCellFormat wff_head = new WritableCellFormat(wf_head);
        try {
            wff_head.setAlignment(jxl.format.Alignment.LEFT);
            wff_head.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
        } catch (WriteException e) {
            e.printStackTrace();
        }
        return wff_head;
    }

    /**
     * 数据内容格式
     * @return
     */
    protected WritableCellFormat getBodyCellStyle() {
        WritableFont font = new WritableFont(WritableFont.ARIAL, 10,
                WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE);
        WritableCellFormat bodyFormat = new WritableCellFormat(font);
        try {
            // 设置单元格背景色：表体为白色
            bodyFormat.setBackground(Colour.WHITE);
            // 设置表头表格边框样式
            // 整个表格线为细线、黑色
            bodyFormat.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
        } catch (WriteException e) {
            System.out.println("表体单元格样式设置失败！");
        }
        return bodyFormat;
    }

    /**
     * 写数据到文件中
     * @param entityList 需写入的数据
     * @param attributes 属性数组
     * @param sheet 要写入的表格
     * @param currentRow 当前行
     * @throws Exception
     */
    protected void writeDataToResponse(List entityList, String[] attributes, WritableSheet sheet, int currentRow,Map convertValueMap) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Reflection reflection = new Reflection();
        //内容
        for(int i=0;i<entityList.size();i++){

            Label tempLabel = null;
            for (int j=0;j<attributes.length;j++){
                Object o = reflection.getFieldValueByName(attributes[j],entityList.get(i));
                //值转换，如将“1”转换为“保本浮动收益类型”；“2”转换为“非保本浮动收益类型”
                String value = null;
                if (convertValueMap != null && convertValueMap.containsKey(attributes[j])){
                    Map valueMap = (Map)convertValueMap.get(attributes[j]);
                    value = (String)valueMap.get(o);
                    o = value;
                }

                //类型格式化，如将时间格式化为 yyyy-mm-dd
                String attributeType = reflection.getFieldTypeByName(attributes[j],entityList.get(i));
                if (attributeType != null && o != null){
                    if (attributeType.equals("class java.util.Date")){
                        tempLabel = new Label(j,currentRow,sdf.format(o),getBodyCellStyle());
                    }
                    else if (attributeType.equals("class java.lang.Double")){
                        tempLabel = new Label(j,currentRow,new BigDecimal(o.toString()).setScale(2,BigDecimal.ROUND_HALF_UP).toString(),getBodyCellStyle());
                    }
                    else if (attributeType.equals("class java.math.BigDecimal")){
                        tempLabel = new Label(j,currentRow,new BigDecimal(o.toString()).setScale(2,BigDecimal.ROUND_HALF_UP).toString(),getBodyCellStyle());
                    }
                    else if (attributeType.equals("class java.lang.Boolean")){
                        if (o.toString().equals("true")) tempLabel = new Label(j,currentRow,"完成",getBodyCellStyle());
                        else tempLabel = new Label(j,currentRow,"未完成",getBodyCellStyle());
                    }
                    else {
                        tempLabel = new Label(j,currentRow,o.toString(),getBodyCellStyle());
                    }
                }
                else tempLabel = new Label(j,currentRow,"",getBodyCellStyle());


                sheet.addCell(tempLabel);
            }
            currentRow++;
        }

    }

    /**
     * 根据需要聚合的属性，判断这些属性值是否相同，聚合原始数据
     * @param conditionAttribute 需要用来判断聚合的属性
     * @param aggregateAttribute 需要累加的属性，此属性必须为BigDecimal类型
     * @param originalDataList 原始数据
     * @param className 原始类的名称，用来将map中的数据还原
     * @return 经过聚合的数据
     * @throws Exception
     */
    public List aggregateData(String conditionAttribute,String aggregateAttribute,List originalDataList,String className) throws Exception{
        if (StringUtils.isBlank(conditionAttribute)|| StringUtils.isBlank(aggregateAttribute)) return null;
        Map<List<Object>,List<BigDecimal>> data = new LinkedHashMap<>();
        String[] attributeArray = conditionAttribute.split(",");
        String[] aggregateAttributeArray = aggregateAttribute.split(",");
        Reflection reflection = new Reflection();
        List finalData = new ArrayList<>();

        //遍历原始数据一次，将每一条原始数据分组
        for (int i=0;i<originalDataList.size();i++){
            Object originalDataUnit = originalDataList.get(i);
            List<Object> key = new ArrayList<>();
            //根据给定的多个聚合属性，判断这些属性的值是否相同，相同则为一组，累加aggregateAttribute的值；否则将记录添加到map中
            for (int j=0;j<attributeArray.length;j++){
                Object value = reflection.getFieldValueByName(attributeArray[j],originalDataUnit);
                key.add(value);
            }

            List<BigDecimal> valueList = new ArrayList<>();
            for (int t=0;t<aggregateAttributeArray.length;t++){
                Object valueObject = reflection.getFieldValueByName(aggregateAttributeArray[t],originalDataUnit);
                BigDecimal value = BigDecimal.valueOf(Double.parseDouble(valueObject.toString()));
                valueList.add(value);
            }

            if (data.containsKey(key)){
                List<BigDecimal> origValueList = data.get(key);
                for (int temp=0;temp<valueList.size();temp++){
                    BigDecimal value = valueList.get(temp).add(origValueList.get(temp));
                    valueList.set(temp,value);
                }
                data.put(key,valueList);
            }else {
                data.put(key,valueList);
            }
        }
        //将map中的数据还原为原始对象
        for (Iterator it = data.keySet().iterator(); it.hasNext();){
            Object k = it.next();
            List<BigDecimal> finalValueList = data.get(k);
            List<Object> lo = (ArrayList<Object>)k;

            Class objectClass = Class.forName(className);
            Object o = objectClass.newInstance();
//            Class<T> entityClass = Reflections.getClassGenricType(getClass(), 1);
//            T o = (T)entityClass.newInstance();
            Field f = null;
            //用来判断的属性
            for (int t=0;t<attributeArray.length;t++){
                f = o.getClass().getDeclaredField(attributeArray[t]);
                f.setAccessible(true);
                f.set(o, lo.get(t));
            }
            //用来累加的属性，如金额
            for (int t=0;t<aggregateAttributeArray.length;t++){
                f = o.getClass().getDeclaredField(aggregateAttributeArray[t]);
                f.setAccessible(true);
                f.set(o,finalValueList.get(t));
            }

            finalData.add(o);
        }

        return finalData;
    }

}
