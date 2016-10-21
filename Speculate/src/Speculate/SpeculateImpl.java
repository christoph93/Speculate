package Speculate;

import Speculate.Jogador;
import Speculate.Partida;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SpeculateImpl extends UnicastRemoteObject implements SpeculateInterface {

    private static final long serialVersionUID = 1234L;
    static private Integer nextID = 1;
    static private Integer contID = 1;
    private Map partidas;
    private Map jogadores;
    private int maxPartidas;

    public SpeculateImpl(int maxPartidas) throws RemoteException {
        this.maxPartidas = maxPartidas;
        jogadores = new HashMap <Integer, Jogador>(maxPartidas*2);
        partidas = new HashMap <Integer, Partida>(maxPartidas);
        criaPartidas(0);
    }

    public int registraJogador(String nome) throws RemoteException {
        System.out.println("Registrando jogador " + nome);

        
        
        Jogador[] aux;        
        aux = (Jogador[]) jogadores.values().toArray();
        
        for(Jogador j : aux){
            if(j.getNome().equals(nome)){
                return -1;
            }
        }
        
        
        

        if (jogadores.size() >= maxPartidas * 2) {
            return -2;
        }

        Jogador novoJog = new Jogador(nextID, nome);
        nextID++;

        return novoJog.getID();
    }

    private void criaPartidas(int maxPartidas) {
        for (int i = 0; i <= maxPartidas; i++) {
            partidas.add(new Partida(null, null, i));
        }
    }

    public int temPartida(int ID) throws RemoteException {
        Jogador jog = null;
        for (Jogador j : jogadores) {
            if (j.getID() == ID) {
                jog = j;
                break;
            }
        }
        
        for (Partida p : partidas) {
            if (p.getJogador1() != null && p.getJogador2() == null) {
                p.setJogador2(jog);
                return p.getID();
            }
            
        }
        
        return 0;

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
