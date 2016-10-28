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

    private String tab;
    private Casa casa1, casa2, casa3, casa4, casa5, casa6;

    public Tabuleiro() {
        casa1 = new Casa();
        casa2 = new Casa();
        casa3 = new Casa();
        casa4 = new Casa();
        casa5 = new Casa();
        casa6 = new Casa();
        this.tab = geraTab();
    }

    private String geraTab() {
        String tab = "1 2 3 4 5 6\n";
        tab += casa1.getCasa() + " " + casa2.getCasa() + " " + casa3.getCasa() + " "
                + casa4.getCasa() + " " + casa5.getCasa() + " " + casa6.getCasa();

        this.tab = tab;
        return this.tab;
    }

    public String getTabuleiro() {
        return this.tab;
    }

    public void setCasaOcupada(int numCasa) {
        switch (numCasa) {
            case 1:
                casa1.setOcupada();
                break;
            case 2:
                casa2.setOcupada();
                break;
            case 3:
                casa3.setOcupada();
                break;
            case 4:
                casa4.setOcupada();
                break;
            case 5:
                casa5.setOcupada();
                break;
            case 6:
                casa6.setOcupada();
                break;
        }

        geraTab();
    }

    public void setCasaLivre(int numCasa) {
        switch (numCasa) {
            case 1:
                casa1.setLivre();
                break;
            case 2:
                casa2.setLivre();
                break;
            case 3:
                casa3.setLivre();
                break;
            case 4:
                casa4.setLivre();
                break;
            case 5:
                casa5.setLivre();
                break;
            case 6:
                casa6.setLivre();
                break;
        }
        
        geraTab();
    }

}
