
import Speculate.Jogador;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SpeculateImpl extends UnicastRemoteObject implements SpeculateInterface {

    private static final long serialVersionUID = 1234L;
    static private Integer nextID = 1;
    private ArrayList<Jogador> jogadores;
    private ArrayList<Jogador> fila;
    private int maxPartidas;
    static Semaphore mutex = new Semaphore(1);

    protected SpeculateImpl(int maxPartidas) throws RemoteException {
        this.maxPartidas = maxPartidas;
        jogadores = new ArrayList<>();
        fila = new ArrayList<>();
    }


    @Override
    public int registraJogador(String nome) throws RemoteException {

        if (jogadores.size() > maxPartidas*2){
            return -2;
        }
        
        try {
            mutex.acquire();

            for (Jogador k : jogadores) {
                if (k.getNome().equals(nome)) {
                    return -1;
                }
            }
            Jogador j = new Jogador(nextID, nome);
            nextID++;
            jogadores.add(j);
            mutex.release();
            return j.getID();

        } catch (InterruptedException ex) {
            Logger.getLogger(SpeculateImpl.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Server: registraJogador locked");
            return 0;
        }
    }

    @Override
    public int temPartida(int ID) throws RemoteException {
        Jogador aux = null;
        for (Jogador j : jogadores){
            if (j.getID() == ID){
                aux = j;
            } else return -1;
        }
        fila.add(aux);
        if (jogadores.size() == 1){
            return 0;
        } else {
            switch (jogadores.indexOf(aux)) {
                case 0:
                    return 1;
                case 1:
                    return 2;
                default:
                    return -1;
            }
        }
    }

    @Override
    public int ehMinhaVez() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String obtemTabuleiro() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String obtemOponente(int ID) throws RemoteException {
        return null;
    }

    @Override
    public int defineJogadas() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int jogaDado() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int encerraPartida() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
