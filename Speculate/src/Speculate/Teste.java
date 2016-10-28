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
public class Teste {
    
    public static void main (String [] args){
        
        Tabuleiro tab = new Tabuleiro();
        tab.setCasaLivre(1);
        tab.setCasaLivre(3);
        tab.setCasaLivre(4);
        
        tab.setCasaOcupada(2);
        tab.setCasaOcupada(5);
        tab.setCasaOcupada(6);
        
        System.out.println(tab.getTabuleiro());
        
        
    }
    
}
