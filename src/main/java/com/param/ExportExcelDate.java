package com.param;

import com.alibaba.excel.annotation.ExcelProperty;

public class ExportExcelDate {
    @ExcelProperty(value = "流量主名称")
    private String trafficName;

    public String getTrafficName() {
        return trafficName;
    }

    public void setTrafficName(String trafficName) {
        this.trafficName = trafficName;
    }
}
