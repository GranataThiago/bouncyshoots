package com.granata.bserver.red;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class HiloServidor extends Thread{

	private DatagramSocket socket;
	private boolean fin = false;
	private DireccionRed[] clientes = new DireccionRed[4];
	private int cantClientes = 0;
	
	
	public HiloServidor() {

		try {
			socket = new DatagramSocket(9898);
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
		
		if(comando[0].equals("Conexion")) {
			if(cantClientes <= 4) {
				clientes[cantClientes] = new DireccionRed(dp.getAddress(), dp.getPort(), comando[1]);
				enviarMensaje("OK", clientes[cantClientes].getIp(), clientes[cantClientes].getPuerto());
				actualizarClientes(cantClientes);
				enviarMensajeGeneral("crearCliente-" + (cantClientes++) + "-" + comando[1]);
			}
			if(cantClientes > 1) {
				enviarMensajeGeneral("puedeComenzar");
			}
		}else {
			
			if(msg.equals("iniciarPartida")) {
				enviarMensajeGeneral("comenzar");
			}
			
		}
		

	}
	
	private void actualizarClientes(int nroCliente) {
		for(int i = 0; i < cantClientes; i++) {
			enviarMensaje("crearCliente-" + (i) + "-" + clientes[i].getNombre(), clientes[nroCliente].getIp(), clientes[nroCliente].getPuerto());
		}
		
	}

	private void enviarMensajeGeneral(String msj) {
		for(int i = 0; i < cantClientes; i++) {
			enviarMensaje(msj, clientes[i].getIp(), clientes[i].getPuerto());
		}
	}
	
}
