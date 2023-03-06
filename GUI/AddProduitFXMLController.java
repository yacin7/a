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
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
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
public class AddProduitFXMLController implements Initializable {
    
    
    
     @FXML
    private TextField tflibelle;

    @FXML
    private TextField tfPrix;

    @FXML
    private TextField nom;

    @FXML
    private ComboBox comb;

java.sql.Connection cnx = Connetion.getInstance().getCnx();
    public ObservableList<Produits> Produit = FXCollections.observableArrayList();
     String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    Produits Pr = null;
    private boolean update;
    int PoduitId;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
           Categorie c = new Categorie();
           List<Categories> a = c.afficherCat();
           ObservableList<String> list = FXCollections.observableArrayList();
           for (Categories cat : a) {
               list.add(cat.toStringG());
           }
           comb.setItems(list);
        
    }    
    @FXML
    private boolean verif() {
    // Add your verification logic here, for example:
    if (nom.getText().isEmpty() || !nom.getText().matches("[a-zA-Z]+")) {
        JOptionPane.showMessageDialog(null, "Le champ nom est obligatoire et doit contenir uniquement des lettres de l'alphabet.");
        return false;
    }
    if (tflibelle.getText().isEmpty() ) {
        JOptionPane.showMessageDialog(null, "Le champ Description est obligatoire et et doit contenir uniquement des lettres de l'alphabet.");
   return false;
    }
    
    if (tfPrix.getText().isEmpty() || !tfPrix.getText().matches("\\d+")) {
        JOptionPane.showMessageDialog(null, "Le champ PRIX est obligatoire et doit contenire des chiffres.");
        return false;
    }
    
    
  return true;
}
    
    
    @FXML
    void Ajouter(ActionEvent event) {
         Produit s= new Produit();
               

         if (verif()) {
        Produits p = new Produits(Integer.parseInt(tfPrix.getText()), Integer.parseInt(comb.getSelectionModel().getSelectedItem().toString()), nom.getText(), tflibelle.getText());

        s.ajouter(p);
        JOptionPane.showMessageDialog(null, "Ajoue avec succe");
        }
         

    }
    
     

    

}
