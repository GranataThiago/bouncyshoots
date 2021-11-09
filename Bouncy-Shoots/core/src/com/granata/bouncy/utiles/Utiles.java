package com.granata.bouncy.utiles;

import java.util.ArrayList;
import java.util.Random;

import com.granata.bouncy.managers.JuegoEventListener;
import com.granata.bouncy.managers.JugadorEventListener;
import com.granata.bouncy.modos.EstrellaEventListener;

public class Utiles {

	public static Random r = new Random();
	public static ArrayList<JugadorEventListener> jugadores = new ArrayList<JugadorEventListener>();
	public static JuegoEventListener juegoListener;
	public static EstrellaEventListener eListener;
	
}
