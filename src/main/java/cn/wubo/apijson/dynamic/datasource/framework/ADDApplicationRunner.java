package cn.wubo.apijson.dynamic.datasource.framework;

import apijson.framework.APIJSONApplication;
import apijson.framework.APIJSONCreator;
import apijson.orm.Parser;
import apijson.orm.SQLConfig;
import apijson.orm.SQLExecutor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

/**
 * ADDApplicationRunner
 * 项目启动时初始化paijson
 */
@Configuration
public class ADDApplicationRunner implements ApplicationRunner {

    static {
        // 使用本项目的自定义处理类,自定义的数据库配置
        APIJSONApplication.DEFAULT_APIJSON_CREATOR = new APIJSONCreator<Long>() {
            @Override
            public SQLConfig createSQLConfig() {
                return new ADDSQLConfig();
            }
            @Override
            public Parser<Long> createParser() {
                return new ADDParser<>();
            }
            @Override
            public SQLExecutor createSQLExecutor() {
                return new ADDSQLExecutor();
            }
        };
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ADDApplication.initAPIJSON();
    }
}
