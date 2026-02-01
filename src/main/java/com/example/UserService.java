import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.sql.DataSource;
import java.util.Properties;

public class UserService {
    
    // Use dependency injection for connection
    private DataSource dataSource;
    private static final String FIND_USER_QUERY = "SELECT * FROM users WHERE name = ?";
    private static final String DELETE_USER_QUERY = "DELETE FROM users WHERE name = ?";
    private static final String UPDATE_USER_QUERY = "UPDATE users SET name = ? WHERE name = ?";
    
    public UserService(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    // Use PreparedStatement to prevent SQL injection
    public void findUser(String username) throws Exception {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(FIND_USER_QUERY)) {
            
            pstmt.setString(1, username); // Safe parameter binding
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println("User found: " + rs.getString("name"));
                }
            }
        }
    }
    
    // Fixed delete method
    public void deleteUser(String username) throws Exception {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(DELETE_USER_QUERY)) {
            
            pstmt.setString(1, username); // Safe parameter binding
            int rowsAffected = pstmt.executeUpdate();
            
            System.out.println("Deleted " + rowsAffected + " user(s)");
        }
    }
    
    // Fixed update method
    public void updateUser(String oldUsername, String newUsername) throws Exception {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(UPDATE_USER_QUERY)) {
            
            pstmt.setString(1, newUsername);
            pstmt.setString(2, oldUsername);
            
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Updated " + rowsAffected + " user(s)");
        }
    }
    
    // Factory method for creating DataSource
    public static DataSource createDataSource() throws Exception {
        // In real application, use connection pool like HikariCP
        // This is simplified for the lab
        Properties props = new Properties();
        props.setProperty("user", System.getenv("DB_USER"));
        props.setProperty("password", System.getenv("DB_PASSWORD"));
        
        // Return a simple DataSource wrapper
        return new SimpleDataSource("jdbc:mysql://localhost/db", props);
    }
    
    // Simple DataSource implementation
    static class SimpleDataSource implements DataSource {
        private final String url;
        private final Properties props;
        
        SimpleDataSource(String url, Properties props) {
            this.url = url;
            this.props = props;
        }
        
        @Override
        public Connection getConnection() throws java.sql.SQLException {
            return DriverManager.getConnection(url, props);
        }
        
        // Other DataSource methods would be implemented here
        // (simplified for example)
    }
}
