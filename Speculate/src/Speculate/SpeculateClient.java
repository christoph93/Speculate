package Speculate;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SpeculateClient {

    public static void main(String[] args) throws InterruptedException {

        int ID = -1;

        try {
            Scanner sc = new Scanner(System.in);
            int op = -1;

            int aux = 1;

            SpeculateInterface p = (SpeculateInterface) Naming.lookup("//localhost/Speculate");

            while (aux == 1) {

                System.out.println("Selecine uma opção: ");

                System.out.println("1 - Registrar \n2 - Procurar partida \n3 - Encerrar");
                op = sc.nextInt();

                switch (op) {
                    case 1:
                        System.out.println("Digite seu nome");
                        int resp = p.registraJogador(sc.next());
                        if (resp == -1) {
                            System.out.println("Nome já cadstrado");
                            break;
                        } else if (resp == -2) {
                            System.out.println("Numero máximo de jogadores atingido");
                            break;
                        } else if (resp >= 1) {
                            System.out.println("Registrado com sucesso. Seu ID é " + resp);
                            ID = resp;
                            aux = 2;
                            break;
                        }

                    case 2:
                    //procurar partida

                    case 3:
                        aux = -1;
                        break;

                    default:
                        break;
                }

            }

            while (aux == 2) {

                System.out.println("Selecione uma opção: ");
                System.out.println("1 - Procurar partida \n2 - Encerrar");

                op = sc.nextInt();

                switch (op) {
                    case 1: //colocar jogador na fila
                        int statusPartida = 0;

                        //fica perguntando ao servidor se tem partida
                        System.out.println("Procurando partida...");
                        while (statusPartida == 0) {
                            Thread.sleep(1000);
                            statusPartida = p.temPartida(ID);
                            System.out.println("debug: statusPartida " + statusPartida);
                        }

                        switch (statusPartida) {
                            case -1:
                                System.out.println("Ocorreu um erro!");
                                aux = -1;
                                return;

                            case 1:
                                System.out.println("Partida encontrada! Você começa jogando.");
                                aux = 3;
                                break;

                            case 2:
                                System.out.println("Partida encontrada! Você é o segundo a jogar.");
                                aux = 3;
                                break;
                        }

                        aux = 3;
                        break;

                    case 2:
                        aux = -1;
                        break;

                    default:
                        aux = -1;
                        break;
                }

            }

            int numJogadas = -1;
            while (aux == 3) {
                int vez = p.ehMinhaVez(ID);
                System.out.println("debug: ehMinhaVez = " + vez);
                if (vez == 1) {
                    System.out.println("É o seu turno. Defina o número de jogadas\n");
                    numJogadas = sc.nextInt();
                    int resposta = p.defineJogadas(ID, numJogadas);

                    switch (resposta) {
                        case 1:
                            System.out.println("Você definiu " + numJogadas + " jogadas");
                            aux = 4;
                            break;
                        case -1:
                            System.out.println("Ocorreu um erro!");
                            aux = -1;
                            break;
                        case -2:
                            System.out.println("Não existe partida.");
                            aux = 2;
                            break;
                        case -3:
                            System.out.println("Não é a sua vez.");
                            break;
                        case -4:
                            System.out.println("Não é hora de definir jogadas.");
                            break;
                        default:
                            System.out.println("Erro!");
                            break;
                    }
                }

                Thread.sleep(500);

            }
            
            
            System.out.println("\n" + p.obtemTabuleiro(ID) + "\n");
            while (aux == 4){
                
                
                
                System.out.println("Quantos dados deseja jogar?");
                int dados = sc.nextInt();
                int auxJogadas = numJogadas;
                
                if (auxJogadas <= 0){
                    System.out.println("Suas jogadas teminaram.");
                    //
                }
                
                if (dados > numJogadas || dados <= 0) System.out.println("Você deve jogar entre 1 e " + auxJogadas + " dados.");
                else {
                    for (int i = 0; i < dados; i++){
                        System.out.println("Jogando dado! Resultado: " + p.jogaDado(ID));
                        System.out.println(p.obtemTabuleiro(ID));
                        auxJogadas--;
                        Thread.sleep(100);
                    }
                }
                
                
            }

        } catch (NotBoundException ex) {
            Logger.getLogger(SpeculateClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(SpeculateClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(SpeculateClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
