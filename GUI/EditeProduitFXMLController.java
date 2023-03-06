/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import entities.Produits;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import utils.Connetion;

/**
 * FXML Controller class
 *
 * @author YACINE
 */
public class EditeProduitFXMLController implements Initializable {
    
    
    
    @FXML
    private TextField nomM;

    @FXML
    private TextField libelleM;

    @FXML
    private TextField prixM;
    @FXML
    private TextField PoduitId;
    
   
     
    
    
    java.sql.Connection cnx = Connetion.getInstance().getCnx();
    public ObservableList<Produits> Produit = FXCollections.observableArrayList();
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
    if (nomM.getText().isEmpty() || !nomM.getText().matches("[a-zA-Z]+")) {
        JOptionPane.showMessageDialog(null, "Le champ nom est obligatoire et doit contenir uniquement des lettres de l'alphabet.");
        return false;
    }
    if (libelleM.getText().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Le champ Description est obligatoire et et doit contenir uniquement des lettres de l'alphabet.");
   return false;
    }
    
    if (prixM.getText().isEmpty() || !prixM.getText().matches("\\d+")) {
        JOptionPane.showMessageDialog(null, "Le champ PRIX est obligatoire et doit contenire des chiffres.");
        return false;
    }
    
    
  return true;
}

    //            String requete="UPDATE produit SET  nomp='"+nomM.getText()+"', libellep='"+libelleM.getText()+"', prix='"+prixM.getText()+"'  WHERE id_p='"+PoduitId.getText()+"'";

    @FXML
    private void modifierp(ActionEvent event) {
        //Produit s= new Produit();
               
        if (verifM()) {
        try {
            String requete = "UPDATE `produit` SET nomp=?, libellep=?, prix=? WHERE id_p= ?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(4, Integer.parseInt(PoduitId.getText()));
            pst.setString(1, nomM.getText());
            pst.setString(2, libelleM.getText());
            pst.setInt(3, Integer.parseInt(prixM.getText()));

            pst.executeUpdate();
            System.out.println("Produit modifié !");

           JOptionPane.showMessageDialog(null, "Modifié avec succe");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }


        }
    }
    
    
     void setTextField(int prix, String nomp, String libellep,int id_p) {

        nomM.setText(nomp);
        libelleM.setText(libellep);
        prixM.setText(String.valueOf(prix));
        PoduitId.setText(String.valueOf(id_p));

    }

  
    
}
