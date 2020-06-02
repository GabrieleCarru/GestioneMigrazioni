/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.borders;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.CountryAndNumber;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxNazione"
    private ComboBox<Country> boxNazione; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaConfini(ActionEvent event) {
    	txtResult.clear();
    	
    	try {
    		int year = Integer.parseInt(txtAnno.getText());
    		
    		if(year < 1816 || year > 2016) {
    			txtResult.setText("Impostare un anno valido");
    			return;
    		}
    		
    		model.creaGrafo(year);
    		List<CountryAndNumber> countriesWithNumber = model.getCountryNumber();
    		txtResult.appendText(String.format("Grafo creato: #Vertici %d - #Archi %d \n", model.numberVertex(), model.numberEdge()));
    		
    		for(CountryAndNumber cn : countriesWithNumber) {
    			txtResult.appendText(String.format("%s (%s) - paesi adiacenti: %d \n", 
    									cn.getCountry().getStateName(), 
    									cn.getCountry().getStateAbb(), 
    									cn.getNumAdiacenze()));
    		}
    		
    		boxNazione.getItems().addAll(model.getCountries());
    		
    		return;
    		
    	} catch (NumberFormatException e) {
    		e.printStackTrace();
    		txtResult.setText("Inserire un numero");
    		return;
    	}
    }

    @FXML
    void doSimula(ActionEvent event) {
    	
    	txtResult.clear();
    	
    	Country partenza = boxNazione.getValue();
    	if(partenza == null) {
    		txtResult.setText("Seleziona uno stato. \n");
    		return;
    	}
    	
    	this.model.simula(partenza);
    	
    	txtResult.appendText("SIMULAZIONE A PARTIRE DA " + partenza + "\n\n");
    	txtResult.appendText("Numero passi: " + this.model.getT() + "\n\n");
    	for(CountryAndNumber c : this.model.getStanziali()) {
    		if(c.getNumAdiacenze()>0) {
    			txtResult.appendText(c.getCountry() + " = " + c.getNumAdiacenze() + "\n");
    		}
    	}
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxNazione != null : "fx:id=\"boxNazione\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
