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
    private Partida[] partidas;
    private Map jogadores;
    private int maxPartidas;
    private Jogador jog1Espera;
    private Jogador jog2Espera;

    public SpeculateImpl(int maxPartidas) throws RemoteException {
        this.maxPartidas = maxPartidas;
        jogadores = new HashMap<Integer, Jogador>(maxPartidas * 2);
        partidas = new Partida[maxPartidas];
        jog1Espera = null;
        jog2Espera = null;
        criaPartidas(0);
    }

    public int registraJogador(String nome) throws RemoteException {
        System.out.println("Registrando jogador " + nome);

        Jogador[] aux;
        aux = (Jogador[]) jogadores.values().toArray();

        for (Jogador j : aux) {
            if (j.getNome().equals(nome)) {
                System.out.println("retornando -1");
                return -1;
            }
        }

        if (jogadores.size() >= maxPartidas * 2) {
            System.out.println("retornando -2");
            return -2;
        }
        
        Jogador novoJog = new Jogador(nextID, nome);
        nextID++;

        System.out.println("retornando " + novoJog.getID());
        return novoJog.getID();
    }

    private void criaPartidas(int maxPartidas) {
        for (int i = 0; i <= maxPartidas; i++) {
            partidas[i] = new Partida(null, null, i);
        }
    }

    public int temPartida(int ID) throws RemoteException {       
        
        //busca o ID do jogador no map de jogadores, se este não existir, retorna -1
        Jogador jog = (Jogador) jogadores.get(ID);
        if (jog == null){
            System.out.println("retornando -1");
            return -1;
        }
        
        //verifica se o jogador já está em uma partida
        for (Partida p : partidas){            
            //esperando outro jogador
            if (p.getJogador1() == jog && p.getJogador2() == null){
                return 0;
            }else if (p.getJogador1() == jog && p.getJogador2() != null){ //é o primeiro a jogar
                return 1;
            } else if (p.getJogador1() != jog && p.getJogador1() != null && p.getJogador2() == jog){//é o segundo a jogar
            return 2;
            } else return -1;
        }
        
        return -1;
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
