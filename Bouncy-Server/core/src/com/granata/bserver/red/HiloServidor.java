package com.granata.bserver.red;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class HiloServidor extends Thread{

	private DatagramSocket socket;
	private boolean fin = false;
	private DireccionRed[] clientes = new DireccionRed[2];
	private int cantClientes = 0;
	
	
	public HiloServidor() {

		try {
			socket = new DatagramSocket(9696);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
	}
	
	private void enviarMensaje(String msg, InetAddress ip, int puerto) {
		byte[] data = msg.getBytes();
		DatagramPacket dp = new DatagramPacket(data, data.length, ip, puerto);
		
		try {
			socket.send(dp);
		} catch (IOException e) {	
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		do {
			byte[] data = new byte[1024];
			DatagramPacket dp = new DatagramPacket(data, data.length);
			try {
				socket.receive(dp);
			} catch (IOException e) {
				e.printStackTrace();
			}
			procesarMensaje(dp);
		}while(!fin);
	}
	
	private void procesarMensaje(DatagramPacket dp) {
		String msg = (new String(dp.getData())).trim();
		if(msg.equals("Conexion")) {
			if(cantClientes <= 1) {
				clientes[cantClientes] = new DireccionRed(dp.getAddress(), dp.getPort());
				enviarMensaje("OK", clientes[cantClientes].getIp(), clientes[cantClientes].getPuerto());
				cantClientes++;
			}
			if(cantClientes > 1) {
				for(int i = 0; i < clientes.length; i++) {
					enviarMensaje("Empieza", clientes[i].getIp(), clientes[i].getPuerto());
				}
			}
		}
	}
	
}
