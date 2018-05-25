package com.codegen.jet.dolphins.util;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * 用于提供生成器的数据源
 * 
 * @author badqiu
 *
 */
public class DataSourceProvider {
	private static Connection connection;
	private static DataSource dataSource;

    private static Connection schema_connection;
    private static DataSource schema_dataSource;

	public synchronized static Connection getConnection() {
		try {
			if(connection == null || connection.isClosed()) {
				connection = getDataSource().getConnection();
			}
			return connection;
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void setDataSource(DataSource dataSource) {
		DataSourceProvider.dataSource = dataSource;
	}

	public synchronized static DataSource getDataSource() {
		if(dataSource == null) {
			dataSource = new DriverManagerDataSource(ReadProperties.getRequiredProperty("jdbc.url"),
					ReadProperties.getRequiredProperty("jdbc.username"),
					ReadProperties.getProperty("jdbc.password"),
					ReadProperties.getRequiredProperty("jdbc.driver"));
		}
		return dataSource;
	}

    public synchronized static Connection getSchemaConnection() {
        try {
            if(schema_connection == null || schema_connection.isClosed()) {
                schema_connection = getSchemaDataSource().getConnection();
            }
            return schema_connection;
        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized static DataSource getSchemaDataSource() {
        if(schema_dataSource == null) {
            String url = ReadProperties.getRequiredProperty("jdbc.url");
            url = url.replaceAll(ReadProperties.getRequiredProperty("jdbc.databasename"), "information_schema");
            schema_dataSource = new DriverManagerDataSource(url,
                    ReadProperties.getRequiredProperty("jdbc.username"),
                    ReadProperties.getProperty("jdbc.password"),
                    ReadProperties.getRequiredProperty("jdbc.driver"));
        }
        return schema_dataSource;
    }
	
	public static class DriverManagerDataSource implements DataSource {
		private String url;
		private String username;
		private String password;
		private String driverClass;
		
		private static void loadJdbcDriver(String driver) {
			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("not found jdbc driver class:["+driver+"]",e);
			}
		}
		
		public DriverManagerDataSource(String url, String username,String password, String driverClass) {
			this.url = url;
			this.username = username;
			this.password = password;
			this.driverClass = driverClass;
			loadJdbcDriver(driverClass);
		}

		public Connection getConnection() throws SQLException {
			return DriverManager.getConnection(url,username,password);
		}

		public Connection getConnection(String username, String password) throws SQLException {
			return DriverManager.getConnection(url,username,password);
		}

		public PrintWriter getLogWriter() throws SQLException {
			throw new UnsupportedOperationException("getLogWriter");
		}

		public int getLoginTimeout() throws SQLException {
			throw new UnsupportedOperationException("getLoginTimeout");
		}

		public void setLogWriter(PrintWriter out) throws SQLException {
			throw new UnsupportedOperationException("setLogWriter");
		}

		public void setLoginTimeout(int seconds) throws SQLException {
			throw new UnsupportedOperationException("setLoginTimeout");
		}

		public <T> T  unwrap(Class<T> iface) throws SQLException {
			if(iface == null) throw new IllegalArgumentException("Interface argument must not be null");
			if (!DataSource.class.equals(iface)) {
				throw new SQLException("DataSource of type [" + getClass().getName() +
						"] can only be unwrapped as [javax.sql.DataSource], not as [" + iface.getName());
			}
			return (T) this;
		}

		public boolean isWrapperFor(Class<?> iface) throws SQLException {
			return DataSource.class.equals(iface);
		}

        /**
         * TODO 简单描述该方法的实现功能（可选）.
         * @see javax.sql.CommonDataSource#getParentLogger()
         */
        public Logger getParentLogger() throws SQLFeatureNotSupportedException {
            
            // TODO Auto-generated method stub
            return null;
        }

	}
}
