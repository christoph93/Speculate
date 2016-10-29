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
public class Casa {

    private boolean ocupada;

    public Casa() {
        ocupada = false;
    }

    public void setOcupada() {
        this.ocupada = true;
    }

    public void setLivre() {
        this.ocupada = false;
    }

    public String getCasa() {
        if(ocupada) return "X";
        else return "O";
    }

}
