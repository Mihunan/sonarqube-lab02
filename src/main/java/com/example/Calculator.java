import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class Calculator {
    
    // Use enum for operation types instead of magic strings
    public enum Operation {
        ADD, SUBTRACT, MULTIPLY, DIVIDE, MODULUS, POWER
    }
    
    // Use map to avoid long if-else chain
    private static final Map<Operation, BiFunction<Integer, Integer, Integer>> OPERATIONS = new HashMap<>();
    
    static {
        OPERATIONS.put(Operation.ADD, (a, b) -> a + b);
        OPERATIONS.put(Operation.SUBTRACT, (a, b) -> a - b);
        OPERATIONS.put(Operation.MULTIPLY, (a, b) -> a * b);
        OPERATIONS.put(Operation.DIVIDE, (a, b) -> b != 0 ? a / b : 0);
        OPERATIONS.put(Operation.MODULUS, (a, b) -> b != 0 ? a % b : 0);
        OPERATIONS.put(Operation.POWER, (a, b) -> (int) Math.pow(a, b));
    }
    
    // Refactored method - much shorter and cleaner
    public int calculate(int a, int b, Operation op) {
        BiFunction<Integer, Integer, Integer> operation = OPERATIONS.get(op);
        if (operation != null) {
            return operation.apply(a, b);
        }
        throw new IllegalArgumentException("Unsupported operation: " + op);
    }
    
    // Remove duplicate method
    
    
    // Helper method for string to enum conversion
    public Operation parseOperation(String opStr) {
        try {
            return Operation.valueOf(opStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid operation: " + opStr);
        }
    }
}
