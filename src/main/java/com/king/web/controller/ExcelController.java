package com.king.web.controller;

import com.king.domain.Employee;
import com.king.service.EmployeeService;
import com.king.service.ExcelExportService;
import com.king.web.view.ExcelView;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/excel")
public class ExcelController {

    private final EmployeeService employeeService;

    @Autowired
    public ExcelController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @RequestMapping(value = "/export")
    public ModelAndView exportExcelView(ModelAndView modelAndView) {
        ExcelView excelView=new ExcelView(exportService());
        excelView.setFileName("allEmployees.xls");
        List<Employee> employeeList = employeeService.findEmployees("");
        modelAndView.addObject("employeeList", employeeList);
        modelAndView.setView(excelView);
        return modelAndView;
    }

    @SuppressWarnings("unchecked")
    private ExcelExportService exportService() {
        return (Map<String,Object> model, Workbook workbook) ->{
            List<Employee> employeeList = (List<Employee>) model.get("employeeList");
            Sheet sheet=workbook.createSheet();
            Row title = sheet.createRow(0);
            title.createCell(0).setCellValue("编号");
            title.createCell(1).setCellValue("姓名");
            title.createCell(2).setCellValue("备注");
            int rowNum=0;
            for (Employee employee : employeeList) {
                rowNum++;
                Row everyEmp = sheet.createRow(rowNum);
                everyEmp.createCell(0).setCellValue(employee.getId());
                everyEmp.createCell(1).setCellValue(employee.getRealName());
                everyEmp.createCell(2).setCellValue(employee.getNote());
            }
        };
    }
}
