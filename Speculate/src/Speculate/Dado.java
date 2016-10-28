/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Speculate;

import java.util.Random;


public class Dado {
    
    static int jogaDado(){
        Random r = new Random();        
        return r.nextInt(5) + 1;       
    }    
}
