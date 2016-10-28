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
                            System.out.println("debug: statusPartida " +  statusPartida);
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

            while (aux == 3) {

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
