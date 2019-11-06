import java.sql.Connection;
import java.sql.Statement;

import org.h2.jdbcx.JdbcConnectionPool;
import org.testng.annotations.Test;

public class H2BugTest {

    @Test
    public void testH2Bug() throws Exception {
        String sql = "CREATE TABLE test(test_id INTEGER PRIMARY KEY);\n" +
                "CREATE TABLE test2(test2_id INTEGER PRIMARY KEY);\n" +
                "CREATE USER test_user PASSWORD 'password';\n" +
                "GRANT SELECT, INSERT ON test, test2 TO test_user;\n";
        JdbcConnectionPool connectionPool = JdbcConnectionPool.create("jdbc:h2:mem:test", "test", "password");
        try (Connection conn = connectionPool.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    @Test
    public void testH2Bug2() throws Exception {
        String sql = "CREATE TABLE test(test_id INTEGER PRIMARY KEY);\n" +
                "CREATE TABLE test2(test2_id INTEGER PRIMARY KEY);\n" +
                "CREATE USER test_user PASSWORD 'password';\n" +
                "GRANT SELECT, INSERT ON test, test2 TO test_user;\n";
        JdbcConnectionPool connectionPool = JdbcConnectionPool.create("jdbc:h2:mem:test2", "test", "password");
        try (Connection conn = connectionPool.getConnection();
             Statement stmt = conn.createStatement()) {
            for (String sqlStmt : sql.split(";")) {
                stmt.execute(sqlStmt);
            }
        }
    }


}
