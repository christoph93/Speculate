/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*

@Author - Christoph Califice e Lucas Caltabiano
*/
package Speculate;

/* Classe criada para representar o tabuleiro */

public class Tabuleiro {

    private String tabuleiro;
    private Casa[] casas;

    public Tabuleiro() {
        tabuleiro = "";
        criaCasas();
    }

/* Método para criar as casas do tabuleiro e definir inicialmente a casa 1, 3 e 5 como ocupadas */    
    private void criaCasas() {
        casas = new Casa[6];

        for (int i = 0; i < 6; i++) {
            casas[i] = new Casa();
        }
//define as casas inicias como ocupadas
        casas[0].setOcupada();
        casas[2].setOcupada();
        casas[4].setOcupada();
    }
    
    public Casa[] getCasas(){
        return casas;
    }
    
 //marca uma casa como ocupada a partir do número da casa que representa a posição dela no array -1   
    public void ocupaCasa(int casa){
        casas[casa-1].setOcupada();
    }
//marca uma casa como livre a partir do número da casa que representa a posição dela no array -1     
    public void liberaCasa(int casa){
        casas[casa-1].setLivre();
    }

    public String getTabuleiro() {
        tabuleiro = "1 2 3 4 5 6\n";

        for (Casa c : casas) {
            tabuleiro += c.getCasa() + " ";
        }

        return tabuleiro;
    }

}
