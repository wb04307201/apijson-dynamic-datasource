package cn.wubo.apijson.dynamic.datasource.framework;

import apijson.framework.APIJSONSQLConfig;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * ZeroCodeSQLConfig
 * 零代码数据源配置
 *
 * @author 吴博
 * @version 1.0
 * @date 2022.08.20
 */
public class ADDSQLConfig extends APIJSONSQLConfig {

    private String url;
    private String username;
    private String password;
    private String version;
    private String schema;

    @Override
    public String getSchema() {
        return schema;
    }

    public void setDb(String url, String username, String password, String sechema, String version) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.schema = sechema;
        this.version = version;
    }

    @Override
    public String getDBVersion() {
        return version;
    }

    @JSONField(serialize = false)  // 不在日志打印 账号/密码 等敏感信息
    @Override
    public String getDBUri() {
        return url;
    }

    @JSONField(serialize = false)  // 不在日志打印 账号/密码 等敏感信息
    @Override
    public String getDBAccount() {
        return username;
    }

    @JSONField(serialize = false)  // 不在日志打印 账号/密码 等敏感信息
    @Override
    public String getDBPassword() {
        return password;
    }

}
