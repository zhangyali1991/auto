package com.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.models.TrafficInfo;
import com.param.ExportExcelDate;
import com.service.TrafficInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class ExportExcel {
    @Resource
    TrafficInfoService trafficInfoService;
    @RequestMapping(value = "export")
    public String export(){
        return "export.html";
    }
    @RequestMapping(value = "exportExcel" )
    @ResponseBody
    public void exportExcel()throws Exception{
        String fileName="D://a.xlsx";
        List<ExportExcelDate> list = trafficInfoService.selectTrafficName();
        EasyExcel.write(fileName, ExportExcelDate.class).sheet("sheet1").doWrite(list);
    }

}
