package cn.wubo.apijson.dynamic.datasource.framework;

import apijson.Log;
import apijson.framework.APIJSONSQLExecutor;
import apijson.orm.SQLConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * ZeroCodeSQLExecutor
 * 零代码SQL执行器
 *
 * @author 吴博
 * @version 1.0
 * @date 2022.08.20
 */
public class ADDSQLExecutor extends APIJSONSQLExecutor {

    @Override
    public void rollback() throws SQLException {
        String id = MDC.get(ADDConstant.APIJSON_DYNAMIC_DATASOURCE_TRACE_ID);
        if (StringUtils.isEmpty(id)) super.rollback();
    }

    @Override
    public void commit() throws SQLException {
        String id = MDC.get(ADDConstant.APIJSON_DYNAMIC_DATASOURCE_TRACE_ID);
        if (StringUtils.isEmpty(id)) super.commit();
    }

    @Override
    public void close() {
        String id = MDC.get(ADDConstant.APIJSON_DYNAMIC_DATASOURCE_TRACE_ID);
        if (StringUtils.isEmpty(id)) super.close();
    }

    @Override
    public Connection getConnection(SQLConfig config) throws Exception {
        String id = MDC.get(ADDConstant.APIJSON_DYNAMIC_DATASOURCE_TRACE_ID);
        if (StringUtils.isNotEmpty(id)) {
            String info = config.getDBUri();
            this.connection = ADDConnectionPool.getConnect(id, info);
            if (this.connection == null || this.connection.isClosed()) {
                Log.i("AbstractSQLExecutor", "select  connection " + (this.connection == null ? " = null" : "isClosed = " + this.connection.isClosed()));
                this.connection = DriverManager.getConnection(config.getDBUri(), config.getDBAccount(), config.getDBPassword());
                ADDConnectionPool.addConnect(id, info, this.connection);
            }
            int ti = this.getTransactionIsolation();
            if (ti != 0) {
                this.begin(ti);
            }
            return this.connection;
        } else
            return super.getConnection(config);
    }
}
