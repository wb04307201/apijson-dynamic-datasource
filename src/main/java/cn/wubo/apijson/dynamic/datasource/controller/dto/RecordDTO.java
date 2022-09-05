package cn.wubo.apijson.dynamic.datasource.controller.dto;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class RecordDTO {
    private String method;
    private JSONObject data;
}
