import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class SpeculateClient {

	public static void main(String[] args) {
		try {
			SpeculateInterface p = (SpeculateInterface)Naming.lookup("//localhost/PID");
			System.out.println("PID="+p.getPID());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

}
