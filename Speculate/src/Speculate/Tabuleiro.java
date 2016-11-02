/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Speculate;

/**
 *
 * @author 12104806
 */
public class Tabuleiro {

    private String tabuleiro;
    private Casa[] casas;

    public Tabuleiro() {
        tabuleiro = "";
        criaCasas();
    }

    private void criaCasas() {
        casas = new Casa[6];

        for (int i = 0; i < 6; i++) {
            casas[i] = new Casa();
        }

        casas[0].setOcupada();
        casas[2].setOcupada();
        casas[4].setOcupada();
    }
    
    public Casa[] getCasas(){
        return casas;
    }
    
    
    public void ocupaCasa(int casa){
        casas[casa-1].setOcupada();
    }
    
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
