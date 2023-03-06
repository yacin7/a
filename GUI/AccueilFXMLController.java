/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author YACINE
 */
public class AccueilFXMLController implements Initializable {
    
    @FXML
    private Button Btpr;
    @FXML
    private Button Btcat;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    @FXML
    private void gotoHomeProduit(ActionEvent event) throws IOException 
    {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/GUI/HomeProduitFXML.fxml"));
        Parent root= loader.load();
        Btpr.getScene().setRoot(root);
    }
    
    @FXML
    private void gotoHomeCategorie(ActionEvent event) throws IOException 
    {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/GUI/HomeCategorieFXML.fxml"));
        Parent root= loader.load();
        Btcat.getScene().setRoot(root);
    }
}
