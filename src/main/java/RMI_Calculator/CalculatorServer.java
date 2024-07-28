package src.main.java.RMI_Calculator;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

/**
 * The CalculatorServer class is responsible for creating and registering
 * the Calculator implementation with the RMI registry.
 */
public class CalculatorServer {
    public static void main(String[] args) {
        try {
            Calculator calculator = new CalculatorImplementation();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("Calculator", calculator);
            System.out.println("Calculator Server is running.");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}