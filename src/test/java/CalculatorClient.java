package src.test.java;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import org.junit.Before;
import org.junit.Test;

import src.main.java.RMI_Calculator.Calculator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * The CalculatorClient class is responsible for testing the Calculator RMI service.
 * It includes unit tests for both single-client and multi-client scenarios.
 */
public class CalculatorClient {
    private Calculator calculator;

    /**
     * Sets up the client by looking up the Calculator service in the RMI registry.
     *
     * @throws Exception if an error occurs during setup.
     */
    @Before
    public void setUp() throws Exception {
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        calculator = (Calculator) registry.lookup("Calculator");
    }

    /**
     * Tests pushing a value onto the stack for a single client.
     *
     * @throws Exception if an error occurs during the test.
     */
    @Test
    public void testPushValueSingleClient() throws Exception {
        calculator.pushValue(10);
        assertFalse(calculator.isEmpty());
    }

    /**
     * Tests the "min" operation for a single client.
     *
     * @throws Exception if an error occurs during the test.
     */
    @Test
    public void testPushOperationSingleClientMin() throws Exception {
        calculator.pushValue(10);
        calculator.pushValue(20);
        calculator.pushOperation("min");
        assertEquals(10, calculator.pop());
    }

    /**
     * Tests the "max" operation for a single client.
     *
     * @throws Exception if an error occurs during the test.
     */
    @Test
    public void testPushOperationSingleClientMax() throws Exception {
        calculator.pushValue(10);
        calculator.pushValue(20);
        calculator.pushOperation("max");
        assertEquals(20, calculator.pop());
    }

    /**
     * Tests the "gcd" operation for a single client.
     *
     * @throws Exception if an error occurs during the test.
     */
    @Test
    public void testPushOperationSingleClientGcd() throws Exception {
        calculator.pushValue(15);
        calculator.pushValue(20);
        calculator.pushOperation("gcd");
        assertEquals(5, calculator.pop());
    }

    /**
     * Tests the "lcm" operation for a single client.
     *
     * @throws Exception if an error occurs during the test.
     */
    @Test
    public void testPushOperationSingleClientLcm() throws Exception {
        calculator.pushValue(4);
        calculator.pushValue(5);
        calculator.pushOperation("lcm");
        assertEquals(20, calculator.pop());
    }

    /**
     * Tests popping a value from the stack for a single client.
     *
     * @throws Exception if an error occurs during the test.
     */
    @Test
    public void testPopSingleClient() throws Exception {
        calculator.pushValue(10);
        assertEquals(10, calculator.pop());
    }

    /**
     * Tests the delayPop method for a single client.
     *
     * @throws Exception if an error occurs during the test.
     */
    @Test
    public void testDelayPopSingleClient() throws Exception {
        calculator.pushValue(10);
        assertEquals(10, calculator.delayPop(1000));
    }

    /**
     * Helper method to test multiple clients performing the same task.
     *
     * @param numberOfClients the number of clients to simulate.
     * @param clientTask the task each client will perform.
     * @throws Exception if an error occurs during the test.
     */
    private void testMultipleClients(int numberOfClients, Runnable clientTask) throws Exception {
        Thread[] clients = new Thread[numberOfClients];
        for (int i = 0; i < numberOfClients; i++) {
            clients[i] = new Thread(clientTask);
            clients[i].start();
        }
        for (Thread client : clients) {
            client.join();
        }
    }

    /**
     * Tests pushing values onto the stack for multiple clients.
     *
     * @throws Exception if an error occurs during the test.
     */
    @Test
    public void testPushValueMultipleClients() throws Exception {
        testMultipleClients(3, () -> {
            try {
                calculator.pushValue((int) (Math.random() * 100));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        assertFalse(calculator.isEmpty());
    }

    /**
     * Tests the "min" operation for multiple clients.
     *
     * @throws Exception if an error occurs during the test.
     */
    @Test
    public void testPushOperationMultipleClientsMin() throws Exception {
        testMultipleClients(3, () -> {
            try {
                calculator.pushValue((int) (Math.random() * 100));
                calculator.pushOperation("min");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Verify the minimum value, assuming stack is not empty
        assertFalse(calculator.isEmpty());
    }

    /**
     * Tests the "max" operation for multiple clients.
     *
     * @throws Exception if an error occurs during the test.
     */
    @Test
    public void testPushOperationMultipleClientsMax() throws Exception {
        testMultipleClients(3, () -> {
            try {
                calculator.pushValue((int) (Math.random() * 100));
                calculator.pushOperation("max");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Verify the maximum value, assuming stack is not empty
        assertFalse(calculator.isEmpty());
    }

    /**
     * Tests the "gcd" operation for multiple clients.
     *
     * @throws Exception if an error occurs during the test.
     */
    @Test
    public void testPushOperationMultipleClientsGcd() throws Exception {
        testMultipleClients(3, () -> {
            try {
                calculator.pushValue(15);
                calculator.pushValue(20);
                calculator.pushOperation("gcd");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Verify the GCD value, assuming stack is not empty
        assertFalse(calculator.isEmpty());
    }

    /**
     * Tests the "lcm" operation for multiple clients.
     *
     * @throws Exception if an error occurs during the test.
     */
    @Test
    public void testPushOperationMultipleClientsLcm() throws Exception {
        testMultipleClients(3, () -> {
            try {
                calculator.pushValue(4);
                calculator.pushValue(5);
                calculator.pushOperation("lcm");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Verify the LCM value, assuming stack is not empty
        assertFalse(calculator.isEmpty());
    }

    /**
     * Tests popping values from the stack for multiple clients.
     *
     * @throws Exception if an error occurs during the test.
     */
    @Test
    public void testPopMultipleClients() throws Exception {
        testMultipleClients(3, () -> {
            try {
                calculator.pushValue((int) (Math.random() * 100));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        assertFalse(calculator.isEmpty());
        calculator.pop();
    }

    /**
     * Tests the delayPop method for multiple clients.
     *
     * @throws Exception if an error occurs during the test.
     */
    @Test
    public void testDelayPopMultipleClients() throws Exception {
        testMultipleClients(3, () -> {
            try {
                calculator.pushValue((int) (Math.random() * 100));
                calculator.delayPop(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        assertFalse(calculator.isEmpty());
    }
}