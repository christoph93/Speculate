package Speculate;

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

    public SpeculateImpl(int maxPartidas) throws RemoteException {
        this.maxPartidas = maxPartidas;
        jogadores = new ArrayList<>();
        fila = new ArrayList<>();
        partidas = new ArrayList<>(maxPartidas);
    }

    public synchronized int registraJogador(String nome) throws RemoteException {
        System.out.println("Registrando jogador " + nome);
        
       for (Jogador j : jogadores){
           if(j.getNome().equals(nome)){
               return -1;
           }
       }
       
       if (jogadores.size() >= maxPartidas * 2 ){
           return -2;
       }
       
       Jogador novoJog = new Jogador(nextID, nome);
       nextID++;
       
       return novoJog.getID();        
    }

    public synchronized int temPartida(int ID) throws RemoteException {

       // try {
          //  mutexFila.acquire();
            Jogador aux = null;
            boolean naFila = false;
            for (Jogador jf : fila) {
                if (ID == jf.getID()) {
                    naFila = true;
                    aux = jf;
                } else {
                    naFila = false;
                }
            }

            if (!naFila) {
                for (Jogador j : jogadores) {
                    if (j.getID() == ID) {
                        aux = j;
                        System.out.println("Colocando " + aux.getNome() + " na fila");
                        fila.add(aux);
                    } else {
                        System.out.println("01>Retornando -1 para " + aux.getNome());
                        return -1;
                    }
                }
            }

            if (jogadores.size() == 1) {
                System.out.println("Somente um jogador na fila, retornando 0 para " + aux.getNome());
               // mutexFila.release();
                return 0;
            } else {
                switch (jogadores.indexOf(aux)) {
                    case 0:
                        //jogador é o primeiro da fila
                        System.out.println(aux.getNome() + " é o primeiro da fila, retornando 1 para " + aux.getNome());
                       // mutexFila.release();
                        return 1;
                    case 1:
                        //jogador é o segundo da fila
                        if (partidas.size() <= maxPartidas) {
                            criaPartida(fila.get(0), fila.get(1), contID, fila.get(0));
                            System.out.println("há dois jogadores na fila e " + aux.getNome() + "é o segundo da fila, retornando 2 para " + aux.getNome());
                          //  mutexFila.release();
                            return 2;
                        } else {
                            System.out.println("02>Retornando -1 para " + aux.getNome());
                           // mutexFila.release();
                            return -1;
                        }
                    default:
                        System.out.println("03>Retornando -1 para " + aux.getNome());
                       // mutexFila.release();
                        return -1;
                }
            }
       // } catch (InterruptedException ex) {
         //   Logger.getLogger(SpeculateImpl.class.getName()).log(Level.SEVERE, null, ex);
      //  }
       // mutexFila.release();
       // return -1;
    }

    private void criaPartida(Jogador j1, Jogador j2, int ID, Jogador v) {
        contID++;
        Partida p = new Partida(j1, j2, ID, v);
        partidas.add(p);
        fila.remove(0);
        fila.remove(1);
    }

    public int ehMinhaVez() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String obtemTabuleiro() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String obtemOponente(int ID) throws RemoteException {
        return null;
    }

    public int defineJogadas() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int jogaDado() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int encerraPartida() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
