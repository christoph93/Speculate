package Speculate;

/*

@Author - Christoph Califice e Lucas Caltabiano
*/

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;


/* 

Classe que implementa os métodos remotos disponíveis 

*/

public class SpeculateImpl extends UnicastRemoteObject implements SpeculateInterface {

    private static final long serialVersionUID = 1234L;
    static private Integer nextID = 1;
    static private Integer contID = 1;
    private Partida[] partidas;
    private ArrayList<Jogador> jogadores;
    private int maxPartidas;
    private Jogador jog1Espera;
    private Jogador jog2Espera;
    private Semaphore semaph = new Semaphore(1);

    public SpeculateImpl(int maxPartidas) throws RemoteException {
        this.maxPartidas = maxPartidas; //Ajusta o número máximo de partidas baseado no paramêtro passado
        jogadores = new ArrayList<>(maxPartidas * 2); //cria um array para armazenar todos os jogadores
        partidas = new Partida[maxPartidas]; //Cria uma lista de partidas com o limite igual ao máximo possível
        jog1Espera = null;
        jog2Espera = null;
        criaPartidas(maxPartidas - 1); //inicializa o número máximo de partidas       
    }

    public int registraJogador(String nome) throws RemoteException {
        System.out.println("Registrando jogador " + nome);

        //Verifica se já existe um jogador registrado com o mesmo nome.
        //Retorna -1 caso já exista
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

        //registra o novo jogador
        try {
            semaph.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(SpeculateImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        Jogador novoJog = new Jogador(nextID, nome);
        nextID++;
        jogadores.add(novoJog);
        System.out.println("retornando " + novoJog.getID());
        semaph.release();
        return novoJog.getID();
    }

//Método que inicializa previamente todas as partidas dentro do limite estipulado
    private void criaPartidas(int maxPartidas) {
        try {
            semaph.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(SpeculateImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int i = 0; i <= maxPartidas; i++) {
            partidas[i] = new Partida(null, null, i);
        }
        semaph.release();
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
                        
        try {
            semaph.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(SpeculateImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for (Partida p : partidas) {
            if (p.getJogador1() != null && p.getJogador2() == null) { //Coloca o jogador em uma partida como Jogador 2
                System.out.println("colocando jogador " + jAux.getNome() + " na partida " + p.getID() + " e retornando 2");
                
                p.setJogador2(jAux);
                p.setVez(p.getJogador1());
                
                semaph.release();
                return 2;
            } else if (p.getJogador1() == null && p.getJogador2() == null) {
                System.out.println("colocando jogador " + jAux.getNome() + " na partida " + p.getID() + " e retornando 0");
                
//                try {
//                    semaph.acquire();
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(SpeculateImpl.class.getName()).log(Level.SEVERE, null, ex);
//                }
                
                p.setJogador1(jAux);
                p.setVez(p.getJogador1());
                
                semaph.release();
                
                return 0;
            }
        }
        semaph.release();
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
            return -1; //retorna -1 caso não encontre o jogador
        }

        //busca a partida do jogador
        Partida p = getPartidaByIdJogador(idJogador);

        if (p == null) {
            return -2; //retorna -2 caso não encontre a partida a qual o jogador pertence.
        }

        //verifica se é a vez do jogador
        if (p.getVez().getID() == idJogador) { //é a vez do jogador
            //verifica se já ganhou ou se o oponente ganhou            
                     
            if (p.getJogador1() == p.getVez()) { //é a vez do jogador1
                if (p.getJogador1().getNumBolas() == 0) { //jogador1 venceu
                    liberaPartida(p);
                    return 2;
                } else if (p.getJogador2().getNumBolas() == 0) { //jogador2 venceu e jogador1 perdeu
                    liberaPartida(p);
                    return 3;
                }
            } else if (p.getJogador2() == p.getVez()) { //é a vez do jogador2
                if (p.getJogador2().getNumBolas() == 0) { //jogador2 venceu
                    liberaPartida(p);
                    return 2;
                } else if (p.getJogador1().getNumBolas() == 0) { //jogador1 venceu e jogador2 perdeu
                    liberaPartida(p);
                    return 3;
                }
            }
            
            

            return 1;
        }
        if (p.getVez().getID() != idJogador) {
            return 0;
        }

        return 0;
    }

    public String obtemTabuleiro(int ID) throws RemoteException {
        
        System.out.println("obtemTabuleiro(" + ID + ")");
        Partida p = getPartidaByIdJogador(ID);
       
        return p.getTabuleiro().getTabuleiro();

    }
    
    
    private void liberaPartida(Partida p){
        try {
                    semaph.acquire();
                } catch (InterruptedException ex) {
                    Logger.getLogger(SpeculateImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
        
        int tmpID = p.getID();
        p = new Partida(null, null, tmpID);
        semaph.release();
    }
    

    public String obtemOponente(int ID) throws RemoteException {
        Partida p = getPartidaByIdJogador(ID); //acha a partida do jogador
        if (p.getJogador1().getID() == ID) { 
            return p.getJogador2().getNome(); //Retorna o nome do Jogador 2, caso o ID passado seja do Jogador 1
        }
        if (p.getJogador2().getID() == ID) {
            return p.getJogador1().getNome(); //Retorna o nome do Jogador 1, caso o ID passado seja do Jogador 2
        }

        return "";
    }

    public int getNumBolas(int ID) {
        Partida p = getPartidaByIdJogador(ID); //acha a partida do jogador

        if (ID == p.getJogador1().getID()) { //acha o jogador dentro da partida e retorna o número de bolas que possue
            return p.getJogador1().getNumBolas();
        } else if (ID == p.getJogador2().getID()) {
            return p.getJogador2().getNumBolas();
        } else {
            return -1; //retorna -1 caso não ache o jogador
        }

    }

    public int defineJogadas(int ID, int numJogadas) throws RemoteException {
        Partida p = getPartidaByIdJogador(ID); //Acha a partida do jogador
        return p.defineJogadas(ID, numJogadas); //Define quantas vezes o jogador vai jogar o dado na rodada
    }

    public int jogaDado(int ID) throws RemoteException {
        System.out.println("jogaDado(" + ID + ")");
        Partida p = getPartidaByIdJogador(ID); //acha a partida do jogador
        int a = p.jogaDado(ID);
        System.out.println("a: " + a);
        return a;
    }

    public int encerraPartida() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
