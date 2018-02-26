package com.king.web.view;

import com.king.service.ExcelExportService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class ExcelView extends AbstractXlsView{
    private String fileName;
    private ExcelExportService excelExportService;

    public ExcelView(String viewName,ExcelExportService excelExportService) {
        this.setBeanName(viewName);
        this.excelExportService = excelExportService;
    }

    public ExcelView(ExcelExportService excelExportService) {
        this.excelExportService=excelExportService;
    }

    @Override
    protected void buildExcelDocument(Map<String, Object> map, Workbook workbook,
                                      HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        if (excelExportService == null) {
            throw new RuntimeException("导出服务接口不能为空");
        }

        if(!StringUtils.isEmpty(fileName)){
            String reqCharset = httpServletRequest.getCharacterEncoding();
            reqCharset = reqCharset == null ? "utf-8" : reqCharset;
            fileName = new String(fileName.getBytes(reqCharset), "ISO8859-1");
            httpServletResponse.setHeader("Content-disposition","attachment;filename="+fileName);
            excelExportService.makeWorkBook(map,workbook);
        }

    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
