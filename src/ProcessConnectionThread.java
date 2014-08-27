import java.awt.Robot; 
import java.awt.event.KeyEvent;
import java.io.InputStream;

import javax.microedition.io.StreamConnection;



public class ProcessConnectionThread implements Runnable {
	private StreamConnection mConnection;
	
	//用于识别来自设备命令的常量
	private static final int EXIT_CMD = -1; 
	private static final int KEY_RIGHT = 1;
	private static final int KEY_LEFT = 2;
	private static final int KEY_ESC = 3;
	private static final int CMD_START = 4;
	
	//构造方法
	public ProcessConnectionThread(StreamConnection connection){
		this.mConnection = connection;
	}
	@Override
	public void run() {
		try{
			//准备接收数据
			InputStream inputStream = mConnection.openInputStream();
			System.out.println("waiting for input...");
			while(true){
				int command = inputStream.read();
				command -= 48;
				if (command == EXIT_CMD)
				{
					System.out.println("finish process");
					break;
				}
				processCommand(command);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private void processCommand(int command){
		try{
			Robot robot = new Robot();
			switch(command){
			case KEY_RIGHT:
				robot.keyPress(KeyEvent.VK_RIGHT);
				robot.keyRelease(KeyEvent.VK_RIGHT);
				System.out.println("Right");
				break;
			case KEY_LEFT:
				robot.keyPress(KeyEvent.VK_LEFT);
				robot.keyRelease(KeyEvent.VK_LEFT);
				System.out.println("Left");
				break;
			case KEY_ESC:
				robot.keyPress(KeyEvent.VK_ESCAPE);
				robot.keyRelease(KeyEvent.VK_ESCAPE);
				System.out.println("Esc");
				break;
			case CMD_START:
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_F5);
				robot.keyRelease(KeyEvent.VK_F5);
				robot.keyRelease(KeyEvent.VK_SHIFT);
				System.out.println("Start");
				break;
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
