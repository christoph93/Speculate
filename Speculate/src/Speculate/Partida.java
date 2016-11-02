/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Speculate;

/**
 *
 * @author ccalifi
 */
public class Partida {

    private Jogador jogador1, jogador2;
    private int ID;
    private Jogador vez;
    private Tabuleiro tabuleiro;
    private Dado dado;
    private int auxDados;

    public Partida(Jogador jogador1, Jogador jogador2, int ID) {
        this.jogador1 = jogador1;
        this.jogador2 = jogador2;
        this.tabuleiro = new Tabuleiro();
        this.dado = new Dado();
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public void setTabuleiro(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
    }

    public Dado getDado() {
        return dado;
    }

    public void setDado(Dado dado) {
        this.dado = dado;
    }

    public Jogador getJogador1() {
        return jogador1;
    }

    private Jogador getJogadorByID(int ID) {
        if (jogador1.getID() == ID) {
            return jogador1;
        } else if (jogador2.getID() == ID) {
            return jogador2;
        } else {
            return null;
        }
    }

    public int defineJogadas(int ID, int jogadas) {
        Jogador jog = getJogadorByID(ID);
        if (vez != jog) {
            return -3;
        } else if (jog.getStatus() != 1) {
            return -4;
        } else if (jogadas > jog.getNumBolas() || jogadas <= 0) {
            return -1;
        } else {
            jog.setJogadas(jogadas);
            auxDados = jogadas;
            return 1;
        }
    }

    public int jogaDado(int ID) {
        
        
        //verifica se é a vez deste ID
        if (vez.getID() != ID) {
            return -3;
        }

        int num = dado.jogaDado();
        Casa aux = tabuleiro.getCasas()[num-1];
        
        //verifica se a casa está ocupada e aplica as regras de acordo
        if("X".equals(aux.getCasa())){
            vez.incrementaBolas();
            aux.setLivre();            
        } else if ("O".equals(aux.getCasa())){
            vez.decrementaBolas();
            aux.setOcupada();
        }
        
        auxDados--;
        
        
        if (auxDados == 0){
            if(vez == jogador1) vez = jogador2;
            else if (vez == jogador2) vez = jogador1;
        }
        
        return num;
    }

    public void setJogador1(Jogador jogador1) {
        this.jogador1 = jogador1;
    }

    public Jogador getJogador2() {
        return jogador2;
    }

    public void setJogador2(Jogador jogador2) {
        this.jogador2 = jogador2;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Jogador getVez() {
        return vez;
    }

    public void setVez(Jogador vez) {
        this.vez = vez;
    }

}
