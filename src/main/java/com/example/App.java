import javax.sql.DataSource;

public class App {
    
    private static final int MAX_ITERATIONS = 10; // Replace magic number
    
    public static void main(String[] args) {
        try {
            runApplication(args);
        } catch (Exception e) {
            System.err.println("Application error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void runApplication(String[] args) throws Exception {
        Calculator calc = new Calculator();
        
        System.out.println("Calculator operations:");
        
        // Use enum instead of magic strings
        Calculator.Operation addOp = Calculator.Operation.ADD;
        System.out.println("10 + 5 = " + calc.calculate(10, 5, addOp));
        
        // Create DataSource properly
        DataSource dataSource = UserService.createDataSource();
        UserService service = new UserService(dataSource);
        
        // Validate input before using
        String username = "admin";
        if (isValidUsername(username)) {
            service.findUser(username);
            
            // Only delete if explicitly requested
            if (args.length > 0 && "delete".equals(args[0])) {
                System.out.println("Deleting user after confirmation...");
                // service.deleteUser(username); // Commented for safety
            }
        }
        
        // Meaningful loop
        for(int i = 0; i < MAX_ITERATIONS; i++) {
            System.out.println("Processing item " + (i + 1) + " of " + MAX_ITERATIONS);
        }
        
        // Use extracted method
        printWelcomeMessage();
    }
    
    private static boolean isValidUsername(String username) {
        return username != null && !username.trim().isEmpty() && username.length() <= 50;
    }
    
    private static void printWelcomeMessage() {
        System.out.println("Application started successfully!");
    }
    
    // Actually used method
    public void processData(String data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        System.out.println("Processing: " + data);
    }
}