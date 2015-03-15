/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.hdd.debata.util.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.flyway.core.Flyway;

/**
 *
 * @author Marc
 */
public class DBUtil {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(DBUtil.class);

    private static final DBUtil INSTANCE = new DBUtil();
    private BasicDataSource ds;

    private DBUtil() {

    }

    public static DBUtil getInstance() {
        return DBUtil.INSTANCE;
    }

    public DataSource getDatasource() {
        return ds;
    }

    public void init() {

        LOGGER.debug("Creating datasource");
        ds = new BasicDataSource();
        ds.setDriverClassName("org.h2.Driver");
        ds.setUrl("jdbc:h2:~/h2DB/debataDB");
        //ds.setUrl("jdbc:h2:file:C:/data/test");
        ds.setUsername("sa");
        ds.setPassword("");

        LOGGER.debug("Executing Flyway (database migration)");
        Flyway flyway = new Flyway();
        flyway.setDataSource(ds);
        flyway.migrate();

    }

    public void close() {
        if (ds != null) {
            LOGGER.debug("Closing datasource");
            try {
                ds.close();
            } catch (SQLException sqle) {
                LOGGER.error("Failed to close datasource", sqle);

            }
        }
    }

    public static Connection getConnection() throws SQLException {
        // TODO Auto-generated method stub
        LOGGER.debug("getInstance(): " + getInstance());
        LOGGER.debug("getInstance().getDatasource(): " + getInstance().getDatasource());
        LOGGER.debug("getInstance().getDatasource().getConnection(): " + getInstance().getDatasource().getConnection());

        return getInstance().getDatasource().getConnection();
    }

}
