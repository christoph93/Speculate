
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SpeculateClient {

    public static void main(String[] args) {

        int ID;

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
                            System.out.println("Nome já cadstrado"); break;    
                        } else if (resp == -2) {
                            System.out.println("Numero máximo de jogadores atingido");  break;                          
                        } else if (resp >= 1){
                            System.out.println("Registrado com sucesso");                            
                            ID = resp;                            
                            System.out.println(ID); break;                                                        
                        }
                        

                        
                    case 2:
                        //procurar partida
                        
                    case 3: aux = -1; break;
                    
                    default: break;
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
