/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import entities.Categories;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import utils.Connetion;

/**
 * FXML Controller class
 *
 * @author YACINE
 */
public class EditeCategorieFXMLController implements Initializable {
    
    
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
    
    private boolean verifM() {
    // Add your verification logic here, for example:
    if (nomC.getText().isEmpty() || !nomC.getText().matches("[a-zA-Z]+")) {
        JOptionPane.showMessageDialog(null, "Le champ nom est obligatoire et doit contenir uniquement des lettres de l'alphabet.");
        return false;
    }
    if (tfDescription.getText().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Le champ Description est obligatoire et et doit contenir uniquement des lettres de l'alphabet.");
   return false;
    }
    
    
    
    
  return true;
}

    //            String requete="UPDATE produit SET  nomp='"+nomM.getText()+"', libellep='"+libelleM.getText()+"', prix='"+prixM.getText()+"'  WHERE id_p='"+PoduitId.getText()+"'";

    @FXML
    private void modifierp(ActionEvent event) {
               
        if (verifM()) {
        try {
            String requete = "UPDATE `categorie` SET nom_cat=?, Description=? WHERE id_cat= ?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(3, Integer.parseInt(CategorieId.getText()));
            pst.setString(1, nomC.getText());
            pst.setString(2, tfDescription.getText());

            pst.executeUpdate();
            System.out.println("categorie modifié !");

           JOptionPane.showMessageDialog(null, "Modifié avec succe");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }


        }
    }
    
    
     void setTextField(String nom_cat, String Description,int id_cat) {

        nomC.setText(nom_cat);
        tfDescription.setText(Description);
        CategorieId.setText(String.valueOf(id_cat));

    }
    
}
