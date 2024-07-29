package RMI_Calculator;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Stack;
import java.util.Map;
import java.util.HashMap;

/**
 * Implementation of the Calculator interface for RMI.
 * This class provides methods to perform stack operations and basic arithmetic operations.
 */
public class CalculatorImplementation extends UnicastRemoteObject implements Calculator {
    
    // Map to store stacks for each client identified by thread ID.
    private Map<String, Stack<Integer>> clientStacks;

    /**
     * Constructor for CalculatorImplementation.
     * Initializes the clientStacks map.
     *
     * @throws RemoteException if an RMI error occurs.
     */
    public CalculatorImplementation() throws RemoteException {
        super();
        clientStacks = new HashMap<>();
    }

    /**
     * Retrieves the stack for the current client based on thread ID.
     * If no stack exists for the client, a new stack is created.
     *
     * @return the stack for the current client.
     */
    private Stack<Integer> getStack() {
        String clientId = String.valueOf(Thread.currentThread().threadId());
        clientStacks.putIfAbsent(clientId, new Stack<>());
        return clientStacks.get(clientId);
    }

    /**
     * Pushes a value onto the client's stack.
     *
     * @param val the value to push.
     * @throws RemoteException if an RMI error occurs.
     */
    @Override
    public void pushValue(int val) throws RemoteException {
        Stack<Integer> stack = getStack();
        stack.push(val);
    }

    /**
     * Applies an operation to the client's stack.
     * Supported operations: min, max, lcm, gcd.
     *
     * @param operator the operation to apply.
     * @throws RemoteException if an RMI error occurs or if the stack is empty.
     */
    @Override
    public void pushOperation(String operator) throws RemoteException {
        Stack<Integer> stack = getStack();
        if (stack.isEmpty()) {
            throw new RemoteException("Stack is empty.");
        }

        int result = 0;
        switch (operator) {
            case "min":
                result = stack.stream().min(Integer::compare).orElseThrow();
                break;
            case "max":
                result = stack.stream().max(Integer::compare).orElseThrow();
                break;
            case "lcm":
                result = stack.stream().reduce(this::lcm).orElseThrow();
                break;
            case "gcd":
                result = stack.stream().reduce(this::gcd).orElseThrow();
                break;
            default:
                throw new RemoteException("Invalid operator: " + operator);
        }

        stack.clear();
        stack.push(result);
    }

    /**
     * Pops a value from the client's stack.
     *
     * @return the value popped from the stack.
     * @throws RemoteException if an RMI error occurs or if the stack is empty.
     */
    @Override
    public int pop() throws RemoteException {
        Stack<Integer> stack = getStack();
        if (stack.isEmpty()) {
            throw new RemoteException("Stack is empty");
        }
        return stack.pop();
    }

    /**
     * Checks if the client's stack is empty.
     *
     * @return true if the stack is empty, false otherwise.
     * @throws RemoteException if an RMI error occurs.
     */
    @Override
    public boolean isEmpty() throws RemoteException {
        return getStack().isEmpty();
    }

    /**
     * Pops a value from the client's stack after a delay.
     *
     * @param millis the delay in milliseconds.
     * @return the value popped from the stack.
     * @throws RemoteException if an RMI error occurs or if interrupted.
     */
    @Override
    public int delayPop(int millis) throws RemoteException {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return pop();
    }

    /**
     * Calculates the least common multiple (LCM) of two integers.
     *
     * @param a the first integer.
     * @param b the second integer.
     * @return the LCM of a and b.
     */
    private int lcm(int a, int b) {
        return Math.abs(a * b) / gcd(a, b);
    }

    /**
     * Calculates the greatest common divisor (GCD) of two integers.
     *
     * @param a the first integer.
     * @param b the second integer.
     * @return the GCD of a and b.
     */
    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}