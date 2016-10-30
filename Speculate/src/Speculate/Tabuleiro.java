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
        casa1.setOcupada();
        casa2 = new Casa();
        casa3 = new Casa();
        casa3.setOcupada();
        casa4 = new Casa();
        casa5 = new Casa();
        casa5.setOcupada();
        casa6 = new Casa();
        this.tab = geraTab();
    }

    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }

    public Casa getCasa1() {
        return casa1;
    }

    public void setCasa1(Casa casa1) {
        this.casa1 = casa1;
    }

    public Casa getCasa2() {
        return casa2;
    }

    public void setCasa2(Casa casa2) {
        this.casa2 = casa2;
    }

    public Casa getCasa3() {
        return casa3;
    }

    public void setCasa3(Casa casa3) {
        this.casa3 = casa3;
    }

    public Casa getCasa4() {
        return casa4;
    }

    public void setCasa4(Casa casa4) {
        this.casa4 = casa4;
    }

    public Casa getCasa5() {
        return casa5;
    }

    public void setCasa5(Casa casa5) {
        this.casa5 = casa5;
    }

    public Casa getCasa6() {
        return casa6;
    }

    public void setCasa6(Casa casa6) {
        this.casa6 = casa6;
    }
    
    
    

    private String geraTab() {
        String tab = "1 2 3 4 5 6\n";
        tab += casa1.getCasa() + " " + casa2.getCasa() + " " + casa3.getCasa() + " "
                + casa4.getCasa() + " " + casa5.getCasa() + " " + casa6.getCasa();

        this.tab = tab;
        return this.tab;
    }

    public String getTabuleiro() {
        geraTab();
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
