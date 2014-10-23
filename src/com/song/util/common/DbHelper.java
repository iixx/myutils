package com.song.util.common;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 提供更简便的方式来查询修改数据库<br>
 * 只需要sql语句即可查询修改, 自动关闭数据库连接
 * 
 * @author Song
 * @version 1.0
 */
public class DbHelper {
    private static final Logger logger = LoggerFactory
            .getLogger(DbHelper.class);

    private static ComboPooledDataSource dataSource = null;
    private static QueryRunner queryRunner = new QueryRunner();

    private DbHelper() {
    }

    static {
        logger.info("=====init database pool...=====");
        dataSource = new ComboPooledDataSource();
        Properties configFile = Common.loadConfigFile("database.properties");
        if (configFile == null) {
            logger.info("=====config file missed, init failed=====");
        } else {
            try {
                dataSource.setDriverClass(configFile
                        .getProperty("jdbc.driverClassName"));
            } catch (PropertyVetoException e) {
                e.printStackTrace();
            }
            dataSource.setJdbcUrl(configFile.getProperty("jdbc.url"));
            dataSource.setUser(configFile.getProperty("jdbc.username"));
            dataSource.setPassword(configFile.getProperty("jdbc.password"));
            dataSource.setInitialPoolSize(10);
            dataSource.setMinPoolSize(10);
            dataSource.setMaxStatements(10);
            dataSource.setMaxIdleTime(100);
            dataSource.setMaxPoolSize(20);
            logger.info("=====init success=====");
        }
    }

    /**
     * 获得数据库连接
     * 
     * @return
     */
    public static Connection getConnection() {
        Connection con = null;
        try {
            con = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    /**
     * 查询单条记录
     * 
     * @param sql
     * @param params
     * @return
     */
    public static Map<String, Object> selectOne(String sql, Object... params) {
        Connection connection = getConnection();
        Map<String, Object> map = null;
        try {
            map = queryRunner.query(connection, sql, new MapHandler(), params);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * 查询多条记录
     * 
     * @param sql
     * @param params
     * @return
     */
    public static List<Map<String, Object>> selectAll(String sql,
            Object... params) {
        Connection connection = getConnection();
        List<Map<String, Object>> list = null;
        try {
            list = queryRunner.query(connection, sql, new MapListHandler(),
                    params);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * insert/update/delete
     * 
     * @param sql
     * @param params
     * @return
     */
    public static boolean update(String sql, Object... params) {
        Connection connection = getConnection();
        int i = -1;
        try {
            i = queryRunner.update(connection, sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return i != -1 ? true : false;
    }

}
