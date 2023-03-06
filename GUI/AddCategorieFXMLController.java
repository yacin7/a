/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import entities.Categories;
import entities.Produits;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import service.Categorie;
import service.Produit;
import utils.Connetion;

/**
 * FXML Controller class
 *
 * @author YACINE
 */
public class AddCategorieFXMLController implements Initializable {
    
    
    @FXML
    private TextField CategorieId;

    @FXML
    private TextField nomC;

    @FXML
    private TextField tfDescription;
    
    java.sql.Connection cnx = Connetion.getInstance().getCnx();
    public ObservableList<Categories> Categorie = FXCollections.observableArrayList();
    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    @FXML
    private boolean verif() {
    // Add your verification logic here, for example:
    if (nomC.getText().isEmpty() || !nomC.getText().matches("[a-zA-Z]+")) {
        JOptionPane.showMessageDialog(null, "Le champ nom est obligatoire et doit contenir uniquement des lettres de l'alphabet.");
        return false;
    }
    
    
  return true;
}
    
    
    @FXML
    void Ajouter(ActionEvent event) {
         Categorie s= new Categorie();
               

         if (verif()) {
        Categories p = new Categories(nomC.getText(), tfDescription.getText());

        s.ajouter(p);
        JOptionPane.showMessageDialog(null, "Ajoue avec succe");
        }
         

    }
    
}
