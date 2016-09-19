
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SpeculateInterface extends Remote {

    public int getPID() throws RemoteException;

    public int registraJogador() throws RemoteException;

    public int temPartida() throws RemoteException;

    public int ehMinhaVez() throws RemoteException;

    public String obtemTabuleiro() throws RemoteException;

    public String obtemOponente() throws RemoteException;

    public int defineJogadas() throws RemoteException;

    public int jogaDado() throws RemoteException;

    public int encerraPartida() throws RemoteException;

}
