package mrf.push;

import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;

import mrf.controller.FormController;
import mrf.exeption.ControllerException;
import mrf.model.Photo;
import mrf.utils.PropertiesUtils;

import com.google.code.jgntp.Gntp;
import com.google.code.jgntp.GntpApplicationInfo;
import com.google.code.jgntp.GntpClient;
import com.google.code.jgntp.GntpNotificationInfo;

public abstract class Growl {
	
	public static void sendNotification(String header, String detail, String sender, String receiver) {	
		if(isClientAvailable(PropertiesUtils.getIPProperties().getString(receiver))){
			GntpApplicationInfo info = Gntp.appInfo("Maintenance").build();
			GntpNotificationInfo noinfo = Gntp.notificationInfo(info, "Maintenance").build();	
			GntpClient client = Gntp.client(info).forHost(PropertiesUtils.getIPProperties().getString(receiver)).secure(PropertiesUtils.getGrowlProperties().getString("password")).withoutRetry().build();		
			client.register();
			
			FormController controller = new FormController();
			Photo photo = null;
			
			try {
				photo = controller.getPhoto(sender);
			} catch (ControllerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				client.notify(Gntp.notification(noinfo, header).text(detail).icon(getImage(photo.getPhoto())).sticky(true).build());
			} catch (Exception e) {
				// TODO Auto-generated catch block		
				e.printStackTrace();
			} 
		}
	}
	
	protected static RenderedImage getImage(byte[] image) throws IOException {
        InputStream is = new ByteArrayInputStream(image);
        try {
                return ImageIO.read(is);
        } finally {
                IOUtils.closeQuietly(is);
        }
	}
	
	private static boolean isClientAvailable(String ip){
		try {
			Socket sock = new Socket();
			sock.connect(new InetSocketAddress(ip, 23053), 500);
			System.out.println("Growl available = "+sock.isConnected());
			sock.close();
			return true;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
