package cn.wubo.apijson.dynamic.datasource.framework;

import apijson.RequestMethod;
import apijson.framework.APIJSONObjectParser;
import apijson.framework.APIJSONSQLConfig;
import apijson.orm.Join;
import apijson.orm.SQLConfig;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * ADDObjectParser
 * 重新数据源初始化
 *
 * @author 吴博
 * @version 1.0
 * @date 2022.09.05
 */
public class ADDObjectParser extends APIJSONObjectParser {

    public ADDObjectParser(HttpSession session, JSONObject request, String parentPath, SQLConfig arrayConfig, boolean isSubquery, boolean isTable, boolean isArrayMainTable) throws Exception {
        super(session, request, parentPath, arrayConfig, isSubquery, isTable, isArrayMainTable);
    }

    @Override
    public SQLConfig newSQLConfig(RequestMethod method, String table, String alias, JSONObject request, List<Join> joinList, boolean isProcedure) throws Exception {
        ADDSQLConfig ADDSQLConfig = (ADDSQLConfig) APIJSONSQLConfig.newSQLConfig(method, table, alias, request, joinList, isProcedure);
        Map<String, Object> map = getCustomMap();
        if (map != null && map.size() > 0 && map.containsKey("@dsUrl") && map.containsKey("@dsUserName") && map.containsKey("@dsPassword")) {
            String dsUrl = String.valueOf(map.get("@dsUrl"));
            String dsUserName = String.valueOf(map.get("@dsUserName"));
            String dsPassword = String.valueOf(map.get("@dsPassword"));
            int i = dsUrl.lastIndexOf("/");
            int j = dsUrl.lastIndexOf("?");
            String start = dsUrl.substring(0, i);
            String end = dsUrl.substring(j);
            String db = dsUrl.substring(i + 1, j);
            ADDSQLConfig.setDb(start + end, dsUserName, dsPassword, db, "5.7.22");
        } else {
            ADDSQLConfig.setDb("jdbc:mysql://10.133.92.80:3306?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=UTF-8&rewriteBatchedStatements=true", "root", "Mysql@2020", "erp_dev_demo", "5.7.22");
        }
        return ADDSQLConfig;
    }
}
