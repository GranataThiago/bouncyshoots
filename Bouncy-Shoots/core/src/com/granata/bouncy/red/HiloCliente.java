package com.granata.bouncy.red;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.granata.bouncy.managers.ControladorPartida;
import com.granata.bouncy.utiles.Global;
import com.granata.bouncy.utiles.Nombres;

public class HiloCliente extends Thread{

	private DatagramSocket socket;
	private InetAddress ipServer;
	private int puerto = 9696;
	private boolean fin = false;
	
	public HiloCliente() {

		try {
			ipServer = InetAddress.getByName("255.255.255.255");
			socket = new DatagramSocket();
		} catch (SocketException | UnknownHostException e) {
			e.printStackTrace();
		}
		enviarMensaje("Conexion-" + Nombres.getNombreAleatorio());
	}
	
	private void enviarMensaje(String msg) {
		byte[] data = msg.getBytes();
		DatagramPacket dp = new DatagramPacket(data, data.length, ipServer, puerto);
		
		try {
			socket.send(dp);
		} catch (IOException e) {
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
		
		String[] comando = msg.split("-");
		
		if(comando.length > 1) {
			if(comando[0].equals("crearCliente")) {
				int idCliente = Integer.valueOf(comando[1]);
				ControladorPartida.clientes[idCliente] = new Cliente(comando[2]);
			}
		}else {
			if(msg.equals("OK")) {
				ipServer = dp.getAddress();
			}else if(msg.equals("Empieza")) {
				Global.puedeIniciar = true;
			}
		}
		
		
	}
	
}
