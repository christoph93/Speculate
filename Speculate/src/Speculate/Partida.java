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
Classe representando a lógica do jogo Speculate
*/
public class Partida {

    private Jogador jogador1, jogador2;
    private int ID;
    private Jogador vez;
    private Tabuleiro tabuleiro;
    private Dado dado;
    private int auxDados;

    /* Aloca dois jogadores e da inicio a uma nova partida de Speculate */
    public Partida(Jogador jogador1, Jogador jogador2, int ID) {
        this.jogador1 = jogador1;
        this.jogador2 = jogador2;
        this.tabuleiro = new Tabuleiro();
        this.dado = new Dado();
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public void setTabuleiro(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
    }

    public Dado getDado() {
        return dado;
    }

    public void setDado(Dado dado) {
        this.dado = dado;
    }

    public Jogador getJogador1() {
        return jogador1;
    }

    private Jogador getJogadorByID(int ID) {
        //Retorna o Jogador com o ID fornecido por parâmetro ou nulo caso o ID não pertença a essa partida
        if (jogador1.getID() == ID) {
            return jogador1;
        } else if (jogador2.getID() == ID) {
            return jogador2;
        } else {
            return null;
        }
    }
// Define quantas vezes o jogador vai jogar na mesma rodada
    public int defineJogadas(int ID, int jogadas) {
       //Acha o Jogador através do ID fornecido 
        Jogador jog = getJogadorByID(ID);
        if (vez != jog) {
            return -3; // Retorna -3 caso não seja a vez do jogador
        } else if (jog.getStatus() != 1) {
            return -4; //Retorna -4 se o status do jogador não for "Definindo Jogadas"
        } else if (jogadas > jog.getNumBolas() || jogadas <= 0) {
            return -1; // Retorna -1 caso esteja tentando jogar mais vezes do que o número de bolas que possui
        } else {
            //Define o número de jogadas
            jog.setJogadas(jogadas);
            auxDados = jogadas;
            return 1;
        }
    }
//Método para realizar o sorteio dos dados
    public int jogaDado(int ID) {
                
        //verifica se é a vez do Jogador com o ID fornecido, retorna -3 caso não seja
        if (vez.getID() != ID) {
            return -3;
        }

        //realiza o sorteio dos dados e acessa a respectiva casa do tabuleiro
        int num = dado.jogaDado();
        Casa aux = tabuleiro.getCasas()[num-1];
        
        //verifica se a casa está ocupada e aplica as regras de acordo
        if("X".equals(aux.getCasa())){
            vez.incrementaBolas();
            aux.setLivre();            
        } else if ("O".equals(aux.getCasa())){
            vez.decrementaBolas();
            aux.setOcupada();
        }
        //decrementa o número de jogadas que faltam para o jogador atual
        auxDados--;
        
        //Passa a vez para o próximo jogador quando o número de jogadas for 0
        if (auxDados == 0){
            if(vez == jogador1) vez = jogador2;
            else if (vez == jogador2) vez = jogador1;
        }
        
        return num;
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

    public Jogador getVez() {
        return vez;
    }

    public void setVez(Jogador vez) {
        this.vez = vez;
    }

}
