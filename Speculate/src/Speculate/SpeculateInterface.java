package Speculate;

/*

@Author - Christoph Califice e Lucas Caltabiano
*/

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SpeculateInterface extends Remote {

    public int registraJogador(String nome) throws RemoteException;

    public int temPartida(int ID) throws RemoteException;

    public int ehMinhaVez(int ID) throws RemoteException;

    public String obtemTabuleiro(int ID) throws RemoteException;

    public String obtemOponente(int ID) throws RemoteException;

    public int defineJogadas(int ID, int numJogadas) throws RemoteException;

    public int jogaDado(int ID) throws RemoteException;

    public int encerraPartida() throws RemoteException;
    
    public int getNumBolas(int ID) throws RemoteException;

}
