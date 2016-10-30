/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Speculate;

/**
 *
 * @author ccalifi
 */
public class Partida {

    private Jogador jogador1, jogador2;
    private int ID;
    private Jogador vez;
    private Tabuleiro tabuleiro;
    private Dado dado;

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
        if (jogador1.getID() == ID) {
            return jogador1;
        } else if (jogador2.getID() == ID) {
            return jogador2;
        } else {
            return null;
        }
    }

    public int defineJogadas(int ID, int jogadas) {
        Jogador jog = getJogadorByID(ID);
        if (vez != jog) {
            return -3;
        } else if (jog.getStatus() != 1) {
            return -4;
        } else if (jogadas > jog.getNumBolas() || jogadas <= 0) {
            return -1;
        } else {
            jog.setJogadas(jogadas);
            return 1;
        }
    }

    public int jogaDado(int ID) {

        if (vez.getID() != ID) {
            return -3;
        }

        int num = dado.jogaDado();

        //verifica se a casa está ocupada
        switch (num) {
            case 1:
                if (tabuleiro.getCasa1().getCasa().equals("X")) {
                    tabuleiro.getCasa1().setLivre();
                    vez.incrementaBolas();
                } else {
                    tabuleiro.getCasa1().setOcupada();
                    vez.decrementaBolas();
                }
                break;
            case 2:
                if (tabuleiro.getCasa2().getCasa().equals("X")) {
                    tabuleiro.getCasa2().setLivre();
                    vez.incrementaBolas();
                } else {
                    tabuleiro.getCasa2().setOcupada();
                    vez.decrementaBolas();
                }
                break;
            case 3:
                if (tabuleiro.getCasa3().getCasa().equals("X")) {
                    tabuleiro.getCasa3().setLivre();
                    vez.incrementaBolas();
                } else {
                    tabuleiro.getCasa3().setOcupada();
                    vez.decrementaBolas();
                }
                break;
            case 4:

                if (tabuleiro.getCasa4().getCasa().equals("X")) {
                    tabuleiro.getCasa4().setLivre();
                    vez.incrementaBolas();
                } else {
                    tabuleiro.getCasa4().setOcupada();
                    vez.decrementaBolas();
                }
                break;
            case 5:
                if (tabuleiro.getCasa5().getCasa().equals("X")) {
                    tabuleiro.getCasa1().setLivre();
                    vez.incrementaBolas();
                } else {
                    tabuleiro.getCasa5().setOcupada();
                    vez.decrementaBolas();
                }
                break;
            case 6:
                vez.decrementaBolas();
                break;

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
