package com.granata.bouncy.red;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.granata.bouncy.managers.ControladorBalas;
import com.granata.bouncy.utiles.Global;
import com.granata.bouncy.utiles.Nombres;
import com.granata.bouncy.utiles.Render;
import com.granata.bouncy.utiles.Utiles;

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
				if(Utiles.jugadores != null) {
					Utiles.jugadores.get(Integer.valueOf(comando[1])).actualizarPosicion(Float.parseFloat(comando[2]), Float.parseFloat(comando[3]));
				}
			}else if(comando[0].equals("Disparo")) {
				if(Utiles.jugadores != null) {
					System.out.println("Disparó");
					String[] posDisparo = comando[2].substring(1, comando[2].length()-1).split(",");
					String[] target = comando[3].substring(1, comando[3].length()-1).split(",");
					Utiles.jugadores.get(Integer.valueOf(comando[1])).disparar(new Vector2(Float.parseFloat(posDisparo[0]), Float.parseFloat(posDisparo[1])), new Vector3(Float.parseFloat(target[0]), Float.parseFloat(target[1]), 0));
				}
			}else if(comando[0].equals("ModificarPosBala")) {
				ControladorBalas.balasActivas.get(Integer.valueOf(comando[1])).actualizarPosicion(Float.parseFloat(comando[2]), Float.parseFloat(comando[3]));
			}else if(comando[0].equals("BorrarBala")) {
				ControladorBalas.balasActivas.get(Integer.valueOf(comando[1])).destruir();
			}else if(comando[0].equals("BorrarJugador")) {
				Render.app.getCliente().getClientes().get(Integer.valueOf(comando[1])).getPj().destruir();
			}else if(comando[0].equals("SpawnPowerup")) {
				System.out.println("Se recibió un powerup");
				Utiles.juegoListener.spawnPickup(Integer.valueOf(comando[1]), Integer.valueOf(comando[2]));
			}else if(comando[0].equals("BorrarPowerup")) {
				Utiles.juegoListener.borrarPickup(Integer.valueOf(comando[1]));
			}else if(comando[0].equals("CambiarMapa")) {
				Utiles.juegoListener.cambiarMapa(Integer.valueOf(comando[1]));
			}else if(comando[0].equals("comenzar")) {
				Global.partidaIniciada = true;
				Global.nroMapaInicial = Integer.valueOf(comando[1]);
			}
		}else {
			if(msg.equals("puedeComenzar")) {
				Global.puedeIniciar = true;
			}
		}
		
		
	}
	
}
