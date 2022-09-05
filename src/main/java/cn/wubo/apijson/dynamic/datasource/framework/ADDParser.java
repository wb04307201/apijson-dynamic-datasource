package cn.wubo.apijson.dynamic.datasource.framework;

import apijson.framework.APIJSONObjectParser;
import apijson.framework.APIJSONParser;
import apijson.orm.SQLConfig;
import com.alibaba.fastjson.JSONObject;

/**
 * ADDParser
 * 调用ADDObjectParser进行数据源初始化
 *
 * @author 吴博
 * @version 1.0
 * @date 2022.09.05
 */
public class ADDParser<T extends Object> extends APIJSONParser<T> {

    // 创建对象解析器
    @Override
    public APIJSONObjectParser createObjectParser(JSONObject request, String parentPath, SQLConfig arrayConfig
            , boolean isSubquery, boolean isTable, boolean isArrayMainTable) throws Exception {
        return new ADDObjectParser(getSession(), request, parentPath, arrayConfig
                , isSubquery, isTable, isArrayMainTable).setMethod(getMethod()).setParser(this);
    }

    @Override
    //解析请求并回复
    public JSONObject parseResponse(JSONObject request) {
        return super.parseResponse(request);
    }
}
