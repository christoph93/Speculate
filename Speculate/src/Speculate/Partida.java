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
    
    public Partida(Jogador jogador1, Jogador jogador2, int ID){
        this.jogador1 = jogador1;
        this.jogador2 = jogador2;        
    }

    public Jogador getJogador1() {
        return jogador1;
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
    
}
