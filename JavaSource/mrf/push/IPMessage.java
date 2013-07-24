package mrf.push;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import mrf.utils.PropertiesUtils;

public abstract class IPMessage {
	public static void sendMessage(String message, String receiver){
		if(isClientAvailable(PropertiesUtils.getIPProperties().getString(receiver))){
			byte[] msg = null;
			try {
				msg = ("1:1:Maintenance:Server:32:"+message).getBytes("TIS-620");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				InetAddress address = InetAddress.getByName(PropertiesUtils.getIPProperties().getString(receiver));
				DatagramPacket packet  =new DatagramPacket(msg, msg.length, address, 2425);
				DatagramSocket dsocket = new DatagramSocket();
				dsocket.send(packet);
				dsocket.close();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}	
	
	private static boolean isClientAvailable(String ip){
		try {
			Socket sock = new Socket();
			sock.connect(new InetSocketAddress(ip, 2425), 500);
			System.out.println("IPMessage available = "+sock.isConnected());
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
