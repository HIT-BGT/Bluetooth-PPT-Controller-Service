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
		//取回本地蓝牙设备
		LocalDevice local = null;
		StreamConnectionNotifier notifier = null;
		StreamConnection connection = null;
		 
		//设置服务器监听连接
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
		//等待链接
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
