package it.polito.tdp.alien;

import java.util.*;

public class Dizionario {

	private LinkedList<String> traduzione;
	
	public Dizionario(String s) {
		traduzione = new LinkedList<String>();
		traduzione.add(s);
	}

	public LinkedList<String> getTraduzione(){
		return traduzione;
	}
	
	public void aggiungiTraduzione(String s) {
		traduzione.add(s);
	}
}