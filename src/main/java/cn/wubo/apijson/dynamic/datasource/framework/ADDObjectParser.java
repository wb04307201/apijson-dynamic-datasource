package cn.wubo.apijson.dynamic.datasource.framework;

import apijson.RequestMethod;
import apijson.framework.APIJSONObjectParser;
import apijson.framework.APIJSONSQLConfig;
import apijson.orm.Join;
import apijson.orm.SQLConfig;
import cn.wubo.apijson.dynamic.datasource.SpringContextUtils;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.http.HttpSession;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * ADDObjectParser
 * 重新数据源初始化
 */
public class ADDObjectParser<T extends Object> extends APIJSONObjectParser<T> {

    public ADDObjectParser(HttpSession session, JSONObject request, String parentPath, SQLConfig arrayConfig, boolean isSubquery, boolean isTable, boolean isArrayMainTable) throws Exception {
        super(session, request, parentPath, arrayConfig, isSubquery, isTable, isArrayMainTable);
    }

    @Override
    public SQLConfig<T> newSQLConfig(RequestMethod method, String table, String alias, JSONObject request, List<Join> joinList, boolean isProcedure) throws Exception {
        ADDSQLConfig ADDSQLConfig = (ADDSQLConfig) APIJSONSQLConfig.newSQLConfig(method, table, alias, request, joinList, isProcedure);
        Map<String, Object> map = getCustomMap();
        String dsUrl = "";
        String dsUserName = "";
        String dsPassword = "";
        if (map != null && map.size() > 0 && map.containsKey("@dsUrl") && map.containsKey("@dsUserName") && map.containsKey("@dsPassword")) {
            dsUrl = String.valueOf(map.get("@dsUrl"));
            dsUserName = String.valueOf(map.get("@dsUserName"));
            dsPassword = String.valueOf(map.get("@dsPassword"));
        } else {
            DruidDataSource druidDataSource = (DruidDataSource) SpringContextUtils.getBean(DataSource.class);
            dsUrl = druidDataSource.getUrl();
            dsUserName = druidDataSource.getUsername();
            dsPassword = druidDataSource.getPassword();
        }
        int i = dsUrl.lastIndexOf("/");
        int j = dsUrl.lastIndexOf("?");
        String start = dsUrl.substring(0, i);
        String end = dsUrl.substring(j);
        String db = dsUrl.substring(i + 1, j);
        ADDSQLConfig.setDb(start + end, dsUserName, dsPassword, db);
        return ADDSQLConfig;
    }
}
