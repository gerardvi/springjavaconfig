package commons.config;

public class DatabaseConfigInfo {
    private String automaticTestTable;
    private Integer checkoutTimeout;
    private String databaseId;
    private Integer idleConnectionTestPeriod;
    private String jdbcUrl;
    private Integer maxIdleTime;
    private Integer maxPoolSize;
    private Integer minPoolSize;
    private String password;
    private String sqlInitFile;
    private String user;

    public String getAutomaticTestTable () {
        return automaticTestTable != null ? automaticTestTable : "jdbc_pool_check";
    }

    public int getCheckoutTimeout () {
        return checkoutTimeout != null ? checkoutTimeout.intValue () : 20000;
    }

    public String getDatabaseId () {
        return databaseId;
    }

    public int getIdleConnectionTestPeriod () {
        return idleConnectionTestPeriod != null ? idleConnectionTestPeriod.intValue () : 300;
    }

    public String getJdbcUrl () {
        return jdbcUrl;
    }

    public int getMaxIdleTime () {
        return maxIdleTime != null ? maxIdleTime.intValue () : 3600;
    }

    public int getMaxPoolSize () {
        return maxPoolSize != null ? maxPoolSize.intValue () : 20;
    }

    public int getMinPoolSize () {
        return minPoolSize != null ? minPoolSize.intValue () : 2;
    }

    public String getPassword () {
        return password;
    }

    public String getSqlInitFile () {
        return sqlInitFile;
    }

    public String getUser () {
        return user;
    }

    public void setAutomaticTestTable (String val) {
        automaticTestTable = val;
    }

    public void setCheckoutTimeout (int val) {
        checkoutTimeout = Integer.valueOf (val);
    }

    public void setDatabaseId (String val) {
        databaseId = val;
    }

    public void setIdleConnectionTestPeriod (int val) {
        idleConnectionTestPeriod = Integer.valueOf (val);
    }

    public void setJdbcUrl (String val) {
        jdbcUrl = val;
    }

    public void setMaxIdleTime (int val) {
        maxIdleTime = Integer.valueOf (val);
    }

    public void setMaxPoolSize (int val) {
        maxPoolSize = Integer.valueOf (val);
    }

    public void setMinPoolSize (int val) {
        minPoolSize = Integer.valueOf (val);
    }

    public void setPassword (String val) {
        password = val;
    }

    public void setSqlInitFile (String val) {
        sqlInitFile = val;
    }

    public void setUser (String val) {
        user = val;
    }
}
