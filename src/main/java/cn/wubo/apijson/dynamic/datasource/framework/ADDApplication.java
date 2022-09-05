package cn.wubo.apijson.dynamic.datasource.framework;

import apijson.Log;
import apijson.framework.APIJSONApplication;
import apijson.framework.APIJSONController;
import apijson.framework.APIJSONParser;
import apijson.framework.APIJSONSQLConfig;
import apijson.orm.AbstractParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ADDApplication
 * apijson启动入口
 *
 * @author 吴博
 * @version 1.0
 * @date 2022.09.05
 */
public class ADDApplication extends APIJSONApplication {

    private static final Logger logger = LoggerFactory.getLogger(ADDApplication.class);

    /**
     * 简化初始化
     */
    public static void initAPIJSON() {
        logger.info("\n\n\n\n\n<<<<<<<<<<<<<<<<<<<<<<<<< APIJSON 开始启动 >>>>>>>>>>>>>>>>>>>>>>>>\n");


        //开启关闭DEBUG
        Log.DEBUG = false;
        //调整批量处理参数
        AbstractParser.MAX_OBJECT_COUNT = 5000;
        AbstractParser.MAX_SQL_COUNT = 5000;
        AbstractParser.MAX_QUERY_COUNT = 5000;

        // 统一用同一个 creator
        APIJSONSQLConfig.APIJSON_CREATOR = DEFAULT_APIJSON_CREATOR;
        APIJSONParser.APIJSON_CREATOR = DEFAULT_APIJSON_CREATOR;
        APIJSONController.APIJSON_CREATOR = DEFAULT_APIJSON_CREATOR;

        logger.info("\n\n<<<<<<<<<<<<<<<<<<<<<<<<< APIJSON 启动完成 >>>>>>>>>>>>>>>>>>>>>>>>\n");
    }
}
