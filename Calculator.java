import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The Calculator interface defines the remote methods that can be invoked
 * by a client on the Calculator server.
 */
public interface Calculator extends Remote {
    void pushValue(int val) throws RemoteException;
    void pushOperation(String operator) throws RemoteException;
    int pop() throws RemoteException;
    boolean isEmpty() throws RemoteException;
    int delayPop(int millis) throws RemoteException;
}