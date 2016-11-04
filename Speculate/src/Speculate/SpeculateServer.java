package Speculate;

/*

@Author - Christoph Califice e Lucas Caltabiano
*/

import Speculate.SpeculateImpl;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class SpeculateServer {

	public static void main(String[] args) {
		try {
			java.rmi.registry.LocateRegistry.createRegistry(1099);
			System.out.println("RMI registry ready.");
		} catch (RemoteException e) {
			System.out.println("RMI registry already running.");
		}
		try {
			Naming.rebind ("Speculate", new SpeculateImpl(5)); //cria o servidor com a capacidade de partidas indicada
			System.out.println ("Speculate server pronto.");
		} catch (Exception e) {
			System.out.println ("Speculate server falhou:");
			e.printStackTrace();
		}
	}

}
