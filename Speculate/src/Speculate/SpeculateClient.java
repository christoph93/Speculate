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

            //menu para se registrar
            while (aux == 1) {

                System.out.println("Selecine uma opção: ");

                System.out.println("1 - Registrar \n2 - Procurar partida \n3 - Encerrar");
                op = sc.nextInt();

                switch (op) {
                    case 1:
                        System.out.println("Digite seu nome");
                        int resp = p.registraJogador(sc.next());
                        if (resp == -1) {
                            System.out.println("Nome já cadastrado");
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

            String oponente = "";

            
            //após registrado é possível procurar uma partida
            while (aux == 2) {

                System.out.println("Selecione uma opção: ");
                System.out.println("1 - Procurar partida \n2 - Encerrar");

                op = sc.nextInt();

                switch (op) {
                    case 1: //colocar jogador na fila
                        int statusPartida = 0;

                        //fica perguntando ao servidor se tem partida
                        System.out.println("Procurando partida...");
                        while (statusPartida == 0) { //enquanto não há partida formada, veriifca se já existe partida
                            Thread.sleep(1000);
                            statusPartida = p.temPartida(ID);
                        }

                        switch (statusPartida) { //encontrou partida ou erro
                            case -1:
                                System.out.println("Ocorreu um erro!");
                                aux = -1;
                                return;

                            case 1: //é o primeiro a jogar
                                oponente = p.obtemOponente(ID);
                                System.out.println("Partida encontrada! Você começa jogando. Seu oponente é " + oponente);
                                aux = 3;
                                break;

                            case 2: //é o segundo a jogar
                                oponente = p.obtemOponente(ID);
                                System.out.println("Partida encontrada! Você é o segundo a jogar. Seu oponente é " + oponente);
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

            int quantidadeBolas;
            boolean vezAux = true;

            int numJogadas = -1;
            while (aux == 3) {
                int vez = p.ehMinhaVez(ID);

                /* 
                ­2 (erro: ainda não há partida), 
                ­1 (erro: jogador não encontrado), 
                0 (não), 
                1 (sim), 
                2 (é o vencedor), 
                3 (é o perdedor), 
                4 (houve empate), 
                5 (vencedor por WO), 
                6 (perdedor por WO)
                 */
                switch (vez) {
                    case 0:
                        if (vezAux) {
                            System.out.println(oponente + " está jogando. Por favor aguarde.");
                            vezAux = false;
                        }
                        break;
                    case 1:
                        quantidadeBolas = p.getNumBolas(ID); //recebe a quantidade de bolas disponíveis para este jogador
                        System.out.println("\nTabuleiro atual: \n" + p.obtemTabuleiro(ID) + "\n");
                        System.out.println("É o seu turno! Você tem " + quantidadeBolas + " bolas. Defina o número de jogadas\n"); //define quantos dados vai jogar
                        numJogadas = sc.nextInt();
                        
                        if(numJogadas > quantidadeBolas){ //quantidade de dados inválida
                            System.out.println("Você deve jogar entre 1 e " + quantidadeBolas + " dados!\n");
                            break;
                        }
                        
                        int resposta = p.defineJogadas(ID, numJogadas);
                        switch (resposta) {
                            case 1:
                                System.out.println("Você definiu " + numJogadas + " jogadas.");
                                System.out.print("Jogando dados... ");                                
                                Thread.sleep(1000);

                                System.out.print("\n\n> "); 
                                for (int i = 1; i <= numJogadas; i++) { //chama o metodo jogaDado() para a quantidade de dados escolhidos
                                    System.out.print(p.jogaDado(ID) + " ");                                    
                                    Thread.sleep(150);
                                }

                                System.out.print("<\n");

                                System.out.println("\nTabuleiro atual: \n" + p.obtemTabuleiro(ID));

                                if (p.getNumBolas(ID) == 0) { //verifica se o jogador venceu
                                    System.out.println("Parabéns, suas bolas terminaram! Você venceu!");
                                    aux = 2;
                                    break;
                                }

                                System.out.println("\nVocê ficou com " + p.getNumBolas(ID) + " bolas!\n");
                                vezAux = true;
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
                        break;
                    case 2:
                        System.out.println("Parabéns, suas bolas terminaram! Você venceu!");
                        aux = 2;
                        break;
                    case 3:
                        System.out.println("As bolas de " + oponente + " acabaram! Você perdeu!");
                        aux = 2;
                        break;
                    case 5:
                        System.out.println(oponente + " não respondeu após 30s. Você venceu!");
                        aux = 2;
                        break;
                    case 6:
                        System.out.println("Você ficou inativo por 30s. " + oponente + " venceu!");
                        aux = 2;
                        break;
                    default:
                        break;
                }

                Thread.sleep(500);

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
