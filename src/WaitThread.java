import javax.bluetooth.*;
import javax.bluetooth.UUID;

import java.util.*;

import javax.microedition.io.*;

public class WaitThread implements Runnable{

	
	public WaitThread(){
	}
	@Override
	public void run() {
		waitForConnection();
	}
	public void waitForConnection(){
		//ȡ�ر��������豸
		LocalDevice local = null;
		StreamConnectionNotifier notifier = null;
		StreamConnection connection = null;
		 
		//���÷�������������
		try
		{
			local = LocalDevice.getLocalDevice();
			local.setDiscoverable(DiscoveryAgent.GIAC);
			UUID uuid = new UUID(80087355);//"04c6093b-0000-1000-8000-00805f9b34fb"
			String url = "btspp://localhost:" + uuid.toString() + ";name=RemoteBluetooth";
			notifier = (StreamConnectionNotifier)Connector.open(url);
		}catch(Exception e)
		{
			e.printStackTrace();
			return;
		}
		//�ȴ�����
		while (true){
			try{
				System.out.println("Waiting for connection...");
				connection = notifier.acceptAndOpen();
				Thread processThread = new Thread(new ProcessConnectionThread(connection));
				processThread.start();
			}catch(Exception e){
				e.printStackTrace();
				return;
			}
		}
		
	}
	
}
