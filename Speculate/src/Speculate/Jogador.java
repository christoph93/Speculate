/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*

@Author - Christoph Califice e Lucas Caltabiano
*/

package Speculate;

/*
Classe para representar um jogador do jogo Speculate
*/

public class Jogador {
    
    
    private int ID;
    private String nome;   
    private int jogadas;
    private int numBolas;
    private int status;
    
    
    public Jogador(int ID, String nome){
        this.ID = ID;
        this.nome = nome;
        this.jogadas = -1;
        numBolas = 15;
        status = 1;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        //0 - não é sua vez
        //1 - definindo jogadas
        //2 - jogando dados
        this.status = status;
    }
    
    public int getNumBolas() {
        return numBolas;
    }

    public void setNumBolas(int numBolas) {
        this.numBolas = numBolas;
    }
    
    public void incrementaBolas(){
        numBolas++;
    }
    
    public void decrementaBolas(){
        numBolas--;
    }
        
    public int getJogadas() {
        return jogadas;
    }

    public void setJogadas(int jogadas) {
        this.jogadas = jogadas;
    }
    
    
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
    
    
    
}
