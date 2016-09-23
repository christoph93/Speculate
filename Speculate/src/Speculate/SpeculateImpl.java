
import Speculate.Jogador;
import Speculate.Partida;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SpeculateImpl extends UnicastRemoteObject implements SpeculateInterface {

    private static final long serialVersionUID = 1234L;
    static private Integer nextID = 1;
    static private Integer contID = 1;
    private ArrayList<Partida> partidas;
    private ArrayList<Jogador> jogadores;
    private ArrayList<Jogador> fila;
    private int maxPartidas;
    static Semaphore mutexID = new Semaphore(1);
    static Semaphore mutexFila = new Semaphore(1);

    protected SpeculateImpl(int maxPartidas) throws RemoteException {
        this.maxPartidas = maxPartidas;
        jogadores = new ArrayList<>();
        fila = new ArrayList<>();
        partidas = new ArrayList<>(maxPartidas);
    }


    @Override
    public int registraJogador(String nome) throws RemoteException {
        System.out.println("Registrando jogador " + nome);

        if (jogadores.size() > maxPartidas*2){
            System.out.println("retornou -2");
            return -2;
        }
        
        try {
            mutexID.acquire();

            for (Jogador k : jogadores) {
                if (k.getNome().equals(nome)) {
                    System.out.println("Retornou -1");
                    return -1;
                }
            }
            Jogador j = new Jogador(nextID, nome);
            nextID++;
            jogadores.add(j);
            mutexID.release();
            return j.getID();

        } catch (InterruptedException ex) {
            Logger.getLogger(SpeculateImpl.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Server: registraJogador locked");
            return 0;
        }
    }

    @Override
    public int temPartida(int ID) throws RemoteException {
        try {
            mutexFila.acquire();
            Jogador aux = null;
            for (Jogador j : jogadores){
                if (j.getID() == ID){
                    aux = j;
                } else return -1;
            }
            fila.add(aux);
            System.out.println("Colocando " + aux.getNome() + " na fila");
            if (jogadores.size() == 1){
                System.out.println("Retornando 0 para " + aux.getNome());
                mutexFila.release();
                return 0;
            } else {
                switch (jogadores.indexOf(aux)) {
                    case 0:
                        System.out.println("Retornando 1 para " + aux.getNome());
                        mutexFila.release();
                        return 1;
                    case 1:
                        if(partidas.size() <= maxPartidas){
                            criaPartida(fila.get(0), fila.get(1), contID, fila.get(0));
                            System.out.println("Retornando 2 para " + aux.getNome());
                            mutexFila.release();
                            return 2;
                        } else{
                            System.out.println("Retornando -1 para " + aux.getNome());
                            mutexFila.release();
                            return -1;
                        }
                    default:
                        mutexFila.release();
                        return -1;
                }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(SpeculateImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
        mutexFila.release();
        return -1;
    }

    
    private void criaPartida(Jogador j1, Jogador j2, int ID, Jogador v){
        contID++;
        Partida p = new Partida(j1, j2, ID, v);
        partidas.add(p);
        fila.remove(0);
        fila.remove(1);
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
