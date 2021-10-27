package com.granata.bouncy.red;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.badlogic.gdx.math.Vector2;
import com.granata.bouncy.utiles.Global;
import com.granata.bouncy.utiles.Nombres;
import com.granata.bouncy.utiles.Render;

public class HiloCliente extends Thread{

	private DatagramSocket socket;
	private InetAddress ipServer;
	private int puerto = 6767;
	private boolean fin = false;
	
	public HiloCliente() {

		try {
			ipServer = InetAddress.getByName("255.255.255.255");
			socket = new DatagramSocket();
		} catch (SocketException | UnknownHostException e) {
			e.printStackTrace();
		}
		enviarMensaje("Conexion!" + Nombres.getNombreAleatorio());
	}
	
	public void enviarMensaje(String msg) {
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
		
		String[] comando = msg.split("!");
		
		if(comando.length > 1) {
			if(comando[0].equals("crearCliente")) {
				boolean esCliente = (Render.app.getCliente().getId() == Integer.valueOf(comando[1]) ? true : false);
				Render.app.getCliente().getClientes().add(new Jugador(comando[2], Integer.valueOf(comando[3]), esCliente));
			}else if(comando[0].equals("OK")) {
				ipServer = dp.getAddress();
				Render.app.getCliente().setId(Integer.valueOf(comando[1]));
			}else if(comando[0].equals("ModificarPosicion")) {
				String[] pos = comando[2].substring(1, comando[2].length()-1).split(",");
				Render.app.getCliente().getClientes().get(Integer.valueOf(comando[1])).getPj().setPosition(new Vector2(Float.parseFloat(pos[0]), Float.parseFloat(pos[1])));
			}
		}else {
			if(msg.equals("puedeComenzar")) {
				Global.puedeIniciar = true;
			}else if(msg.equals("comenzar")) {
				Global.partidaIniciada = true;
			}
		}
		
		
	}
	
}
