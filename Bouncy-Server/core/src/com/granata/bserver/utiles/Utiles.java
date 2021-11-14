package com.granata.bserver.utiles;

import java.util.ArrayList;
import java.util.Random;

import com.granata.bserver.managers.JugadorEventListener;
import com.granata.bserver.modos.CarreraEventListener;
import com.granata.bserver.modos.EstrellaEventListener;

public class Utiles {

	public static Random r = new Random();
	public static EstrellaEventListener eListener;
	public static CarreraEventListener cListener;
	public static ArrayList<JugadorEventListener> jugadores = new ArrayList<JugadorEventListener>();
	public static boolean partidaIniciada = false;
	public static boolean fin = false;
}


