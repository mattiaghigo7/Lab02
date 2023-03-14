package it.polito.tdp.alien;

import java.net.URL;
import java.util.*;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class FXMLController {

	Map<String,Dizionario> diz = new HashMap<String,Dizionario>();
	
	@FXML
	private ImageView immagineBella;
	  
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnTranslate;

    @FXML
    private TextField txtInsert;

    @FXML
    private TextArea txtResult;
    
    //controllo per verificare che nella stringa ci siano solo lettere, spazi o ?
    public boolean controlloLettere(String s) {
		boolean b = true;
		for (int i=0;i<s.length();i++) {
    		char c = s.charAt(i);
    		if ((c<'a' || c>'z') && c!=' ' && c!='?') {
    			b=false;
    		}
    	}
		return b;
	}
    
    //pulisce l'area con i risultati
    @FXML
    void pushClear(ActionEvent event) {
    	this.txtResult.clear();
    }

    @FXML
    void pushTranslate(ActionEvent event) {
    	String totale = this.txtInsert.getText();
    	String t = totale.toLowerCase();
    	//controllo la presenza di lettere strane
    	if (controlloLettere(t)==true) {
    		//controllo se è presente lo spazio che divide più parole
    		if (t.contains(" ")) {
        		String array[];
            	array = t.split(" ");
            	//controllo che ci siano solo 2 parole e non di più
            	if (array.length<=2) {
            		String parolaAliena = array[0];
                	String traduzione = array[1];
                	//se la parola da tradurre esiste già aggiungo solamente la traduzione alla lista
                	if (diz.containsKey(parolaAliena)) {
                		diz.get(parolaAliena).aggiungiTraduzione(traduzione);
                	}
                	//se la parola da tradurre non esiste ancora creo un oggetto nuovo
                	else {
                		diz.put(parolaAliena, new Dizionario(traduzione));
                	}
            	}
        	}
    		//controllo se nella parola c'è ?
    		else if(t.contains("?")){
    			//lista contenente tutte le chiavi
    			LinkedList<String> temp = new LinkedList<String>();
    			temp.addAll(diz.keySet());
    			String array[];
    			array = t.split("\\?");
    			//controllo se il ? non è in fondo alla parola
    			if (array.length==2) {
    				//lista contenente tutte le chiavi che assomigliano alla parola cercata
    				LinkedList<String> parolaSimile = new LinkedList<String>();
        			for (int i=0;i<temp.size();i++) {
        				if (temp.get(i).contains(array[0]) && temp.get(i).contains(array[1]) && t.length()==temp.get(i).length()) {
        					parolaSimile.add(temp.get(i));
        				}
        			}
        			parolaSimile.sort(null);
        			for (int i=0;i<parolaSimile.size();i++) {
        				this.txtResult.appendText(parolaSimile.get(i)+":"+"\n");
        				diz.get(parolaSimile.get(i)).getTraduzione().sort(null);
        				for (int j=0;j<diz.get(parolaSimile.get(i)).getTraduzione().size();j++) {
        					this.txtResult.appendText(diz.get(parolaSimile.get(i)).getTraduzione().get(j)+"\n");
        				}
        			}
    			}
    			//se sono qua il ? è in fondo alla parola
    			else if (array.length==1) {
    				LinkedList<String> parolaSimile = new LinkedList<String>();
        			for (int i=0;i<temp.size();i++) {
        				if (temp.get(i).contains(array[0]) && t.length()==temp.get(i).length()) {
        					parolaSimile.add(temp.get(i));
        				}
        			}
        			parolaSimile.sort(null);
        			for (int i=0;i<parolaSimile.size();i++) {
        				this.txtResult.appendText(parolaSimile.get(i)+":"+"\n");
        				diz.get(parolaSimile.get(i)).getTraduzione().sort(null);
        				for (int j=0;j<diz.get(parolaSimile.get(i)).getTraduzione().size();j++) {
        					this.txtResult.appendText(diz.get(parolaSimile.get(i)).getTraduzione().get(j)+"\n");
        				}
        			}
    			}
    		}
    		//se sono qui c'è solo una parola senza ?
        	else {
        		String parolaAliena = t;
        		//controllo se la parola esiste
        			if (diz.containsKey(parolaAliena)) {
        				//stampo la parola cercata
        				this.txtResult.appendText(t+":"+"\n");
        				diz.get(parolaAliena).getTraduzione().sort(null);
        				//stampo in ordine alfabetico tutte le traduzioni della parola cercata
        				for (int i=0;i<diz.get(parolaAliena).getTraduzione().size();i++) {
        					this.txtResult.appendText(diz.get(parolaAliena).getTraduzione().get(i)+"\n");
        				}
        			}
        	}
    	}
    	this.txtInsert.clear();
    }

    @FXML
    void initialize() {
        assert btnClear != null : "fx:id=\"btnClear\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnTranslate != null : "fx:id=\"btnTranslate\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtInsert != null : "fx:id=\"txtInsert\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert immagineBella != null : "fx:id=\"immagineBella\" was not injected: check your FXML file 'Scene.fxml'.";
    }

}
