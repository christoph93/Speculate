/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*

@Author - Christoph Califice e Lucas Caltabiano
*/

package Speculate;

import java.util.Random;


public class Dado {
    
    //retorna numero aleatório de 1 a 6
    public  int jogaDado(){
        Random r = new Random();        
        return r.nextInt(6) + 1;       
    }    
}
