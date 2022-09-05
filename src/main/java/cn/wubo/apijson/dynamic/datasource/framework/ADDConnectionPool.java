package cn.wubo.apijson.dynamic.datasource.framework;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * ConnectionPool
 * 管理连接池，进行统一提交
 *
 * @author 吴博
 * @version 1.0
 * @date 2022.09.05
 */
public class ADDConnectionPool {

    private ADDConnectionPool() {
    }

    private static Map<String, Map<String, Connection>> pools = new HashMap<>();

    public static synchronized Connection getConnect(String id, String info) {
        if (pools.containsKey(id) && pools.get(id).containsKey(info)) return pools.get(id).get(info);
        else return null;
    }

    public static synchronized void addConnect(String id, String info, Connection conn) {
        Map<String, Connection> map = new HashMap<>();
        if (pools.containsKey(id) && !pools.get(id).containsKey(info))
            map = pools.get("id");
        map.put(info, conn);
        pools.put(id, map);
    }

    public static synchronized void onRollback(String id) {
        if (pools.containsKey(id)) {
            Map<String, Connection> map = pools.get(id);
            try {
                try {
                    rollback(map);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } finally {
                try {
                    close(map);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                pools.remove(id);
            }
        }
    }

    public static synchronized void onCommit(String id) {
        if (pools.containsKey(id)) {
            Map<String, Connection> map = pools.get(id);
            try {
                commit(map);
            } catch (Exception e) {
                try {
                    rollback(map);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } finally {
                try {
                    close(map);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                pools.remove(id);
            }
        }
    }

    private static void commit(Map<String, Connection> map) throws SQLException {
        for (Map.Entry<String, Connection> m : map.entrySet()) {
            m.getValue().commit();
        }
    }

    private static void rollback(Map<String, Connection> map) throws SQLException {
        for (Map.Entry<String, Connection> m : map.entrySet()) {
            m.getValue().rollback();
        }
    }

    private static void close(Map<String, Connection> map) throws SQLException {
        for (Map.Entry<String, Connection> m : map.entrySet()) {
            m.getValue().close();
        }
    }
}
