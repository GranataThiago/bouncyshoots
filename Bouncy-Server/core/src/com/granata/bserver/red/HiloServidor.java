package com.granata.bserver.red;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import com.badlogic.gdx.math.Vector2;
import com.granata.bserver.elementos.Personajes;
import com.granata.bserver.managers.ControladorNiveles;
import com.granata.bserver.utiles.Render;
import com.granata.bserver.utiles.Utiles;

public class HiloServidor extends Thread{

	private DatagramSocket socket;
	private boolean fin = false;
	private int cantClientes = 0;
	private Servidor sv;
	
	public HiloServidor() {

		try {
			socket = new DatagramSocket(6767);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
	}
	
	private void enviarMensaje(String msg, InetAddress ip, int puerto) {
		if(!Utiles.fin) {
			byte[] data = msg.getBytes();
			DatagramPacket dp = new DatagramPacket(data, data.length, ip, puerto);
			
			try {
				socket.send(dp);
			} catch (IOException e) {	
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void run() {
		do {
			byte[] data = new byte[1024];
			DatagramPacket dp = new DatagramPacket(data, data.length);
			try {
				System.out.println("es");
				socket.receive(dp);
			} catch (IOException e) {
				e.printStackTrace();
			}
			procesarMensaje(dp);
		}while(!fin);
	}
	
	private void procesarMensaje(DatagramPacket dp) {
		sv = Render.app.getSv();
		String msg = (new String(dp.getData())).trim();
		
		String[] comando = msg.split("!");
		
		if(comando.length>1) {
			if(comando[0].equals("Conexion")) {
				// Si todavia no esta "llena" la lobby, permitimos que se sigan conectando clientes
				if(cantClientes < 4) {
					sv.getClientes().add(new Cliente(dp.getAddress(), dp.getPort(), comando[1], Personajes.values()[cantClientes].getPosicion()));
					enviarMensaje("OK!" + cantClientes, sv.getClientes().get(cantClientes).getIp(), sv.getClientes().get(cantClientes).getPuerto());
					actualizarClientes(cantClientes);
					enviarMensajeGeneral("crearCliente!" + (cantClientes) + "!" + comando[1] + "!" + Personajes.values()[cantClientes++].getPosicion());
				}
				/* Si ya hay suficientes jugadores para comenzar la partida
				enviamos un mensaje a todos los conectados para activar esta opción */
				if(cantClientes > 1) {
					enviarMensajeGeneral("puedeComenzar");
				}
			}else if(comando[0].equals("Ejecutar")) {
				if(comando[1].equals("Salto")) {
					Utiles.jugadores.get(Integer.valueOf(comando[2])).ejecutarMovimiento(comando[1]);
				}else if(comando[1].equals("Derecha")) {
					Utiles.jugadores.get(Integer.valueOf(comando[2])).ejecutarMovimiento(comando[1]);
				}else if(comando[1].equals("Izquierda")) {
					Utiles.jugadores.get(Integer.valueOf(comando[2])).ejecutarMovimiento(comando[1]);
				}else if(comando[1].equals("Disparo")) {
					Utiles.jugadores.get(Integer.valueOf(comando[2])).ejecutarDisparo(new Vector2(Float.parseFloat(comando[3]), Float.parseFloat(comando[4])));
				}
				

			}else if(comando[0].equals("DejarEjecutar")) {
				if(comando[1].equals("Salto")) {
					Utiles.jugadores.get(Integer.valueOf(comando[2])).dejarEjecutarMovimiento(comando[1]);
				}else if(comando[1].equals("Derecha")) {
					// Tiene que dejar de ejecutar el movimiento hacia la derecha para el jugador que recibió
					Utiles.jugadores.get(Integer.valueOf(comando[2])).dejarEjecutarMovimiento(comando[1]);
				}else if(comando[1].equals("Izquierda")) {
					// Tiene que dejar de ejecutar el movimiento hacia la izquierda para el jugador que recibió
					Utiles.jugadores.get(Integer.valueOf(comando[2])).dejarEjecutarMovimiento(comando[1]);
				}else if(comando[1].equals("Disparo")) {
					Utiles.jugadores.get(Integer.valueOf(comando[2])).dejarDisparar();
				}
			}else if(comando[0].equals("DesconectarCliente")) {
				enviarMensaje("terminarPartida", sv.getClientes().get(Integer.valueOf(comando[1])).getIp(), sv.getClientes().get(Integer.valueOf(comando[1])).getPuerto());
				cantClientes--;
			}
		}else {
			
			/* Si se inició la partida, le envio un mensaje a todos los clientes para que tmb comiencen la partida */
			if(msg.equals("iniciarPartida")) {
				enviarMensajeGeneral("comenzar!" + ControladorNiveles.niveles.get(0));
				Utiles.partidaIniciada = true;
			}
			
		}
		

	}
	
	private void actualizarClientes(int nroCliente) {
		for(int i = 0; i < cantClientes; i++) {
			enviarMensaje("crearCliente!" + (i) + "!" + sv.getClientes().get(i).getNombre() + "!" + sv.getClientes().get(i).getPosTxt(), sv.getClientes().get(nroCliente).getIp(), sv.getClientes().get(nroCliente).getPuerto());
		}
		
	}

	public void enviarMensajeGeneral(String msj) {
		if(!Utiles.fin) {
			for(int i = 0; i < cantClientes; i++) {
				enviarMensaje(msj, sv.getClientes().get(i).getIp(), sv.getClientes().get(i).getPuerto());
			}
		}
	}
	
	public void cerrarHilo() {
		fin = true;
	}
	
}
