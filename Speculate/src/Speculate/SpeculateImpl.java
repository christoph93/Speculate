package Speculate;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SpeculateImpl extends UnicastRemoteObject implements SpeculateInterface {

    private static final long serialVersionUID = 1234L;
    static private Integer nextID = 1;
    static private Integer contID = 1;
    private Partida[] partidas;
    private ArrayList<Jogador> jogadores;
    private int maxPartidas;
    private Jogador jog1Espera;
    private Jogador jog2Espera;

    public SpeculateImpl(int maxPartidas) throws RemoteException {
        this.maxPartidas = maxPartidas;
        jogadores = new ArrayList<>(maxPartidas * 2);
        partidas = new Partida[maxPartidas];
        jog1Espera = null;
        jog2Espera = null;
        criaPartidas(maxPartidas - 1);
    }

    public int registraJogador(String nome) throws RemoteException {
        System.out.println("Registrando jogador " + nome);

        for (Jogador j : jogadores) {
            if (j.getNome().equals(nome)) {
                System.out.println("retornando -1");
                return -1;
            }
        }

        //verifica se já atingiu o máximo de jogadores
        if (jogadores.size() >= maxPartidas * 2) {
            System.out.println("retornando -2");
            return -2;
        }

        Jogador novoJog = new Jogador(nextID, nome);
        nextID++;
        jogadores.add(novoJog);
        System.out.println("retornando " + novoJog.getID());
        return novoJog.getID();
    }

    private void criaPartidas(int maxPartidas) {
        for (int i = 0; i <= maxPartidas; i++) {
            partidas[i] = new Partida(null, null, i);
        }
    }

    public int temPartida(int ID) throws RemoteException {
        Jogador jAux = null;
        //busca o ID do jogador no array de jogadores, se este não existir, retorna -1
        for (Jogador jog : jogadores) {
            if (jog.getID() == ID) {
                jAux = jog;
            }
        }

        if (jAux == null) { //jogador não encontrado
            System.out.println("jogador não encontrado, retornando -1");
            return -1;
        }

        //verifica se o jogador já está em uma partida
        for (Partida p : partidas) {
            //esperando outro jogador
            System.out.println(p.getID());
            System.out.println(p.getJogador1() + " " + p.getJogador2());
            if (p.getJogador1() == jAux && p.getJogador2() == null) {
                return 0;
            } else if (p.getJogador1() == jAux && p.getJogador2() != null) { //é o primeiro a jogar
                return 1;
            } else if (p.getJogador1() != jAux && p.getJogador1() != null && p.getJogador2() == jAux) {//é o segundo a jogar
                return 2;
            }
        }

        //não está em nunhuma partida
        for (Partida p : partidas) {
            if (p.getJogador1() != null && p.getJogador2() == null) {
                System.out.println("colocando jogador " + jAux.getNome() + " na partida " + p.getID() + " e retornando 2");
                p.setJogador2(jAux);
                return 2;
            } else if (p.getJogador1() == null && p.getJogador2() == null) {
                System.out.println("colocando jogador " + jAux.getNome() + " na partida " + p.getID() + " e retornando 0");
                p.setJogador1(jAux);
                return 0;
            }
        }

        return -1;
    }

    private Partida getPartidaByIdJogador(int idJogador) {
        for (Partida p : partidas) {
            if (p.getJogador1() != null) {
                if (p.getJogador1().getID() == idJogador) {
                    return p;
                }
            }
            if (p.getJogador2() != null) {
                if (p.getJogador2().getID() == idJogador) {
                    return p;
                }
            }

        }

        return null;
    }

    public int ehMinhaVez(int idJogador) throws RemoteException {
        boolean jogadorExiste = false;

        //verifica se o jogador está registrado
        for (Jogador j : jogadores) {
            if (j.getID() == idJogador) {
                jogadorExiste = true;
            }
        }

        if (!jogadorExiste) {
            return -1;
        }

        //busca a partida do jogador
        Partida p = getPartidaByIdJogador(idJogador);

        if (p == null) {
            return -2;
        }

        //verifica se é a vez do jogador
        if (p.getVez().getID()
                == idJogador) {
            return 1;
        }
        if (p.getVez().getID()
                != idJogador) {
            return 0;
        }

        //implementar tipos de vitória
        return 0;
    }

    public String obtemTabuleiro() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String obtemOponente(int ID) throws RemoteException {
        Partida p = getPartidaByIdJogador(ID);
        if (p.getJogador1().getID() == ID) {
            return p.getJogador2().getNome();
        }
        if (p.getJogador2().getID() == ID) {
            return p.getJogador1().getNome();
        }

        return "";
    }

    public int defineJogadas(int ID, int numJogadas) throws RemoteException {
        Partida p = getPartidaByIdJogador(ID);
        return p.defineJogadas(ID, numJogadas);
    }

    public int jogaDado(int ID) throws RemoteException {
        Partida p = getPartidaByIdJogador(ID);
        return p.jogaDado(ID);
    }

    public int encerraPartida() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
