/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;


import entities.Produits;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import service.Produit;
import utils.Connetion;

/**
 * FXML Controller class
 *
 * @author YACINE
 */
public class ProduitFrantController implements Initializable {
    
    
    @FXML
    private TableView<Produits> tabProduit;
    @FXML
    private TableColumn<Produits, String> colNom;
    @FXML
    private TableColumn<Produits, String> colLibelle;
    @FXML
    private TableColumn<Produits, Integer> colPrix;
    @FXML
    private TableColumn<Produits, Integer> colId_cat;
    @FXML
    private TableColumn<Produits, Integer> idP;
    @FXML
    private Button btA;
    @FXML
    private TextField searchfield;

    @FXML
    private TextField searchfield1;
    
    private double total;
    
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
        show();
        
    }  
    
    


    
    public void show() {
        tabProduit.getItems().clear();
        tabProduit.refresh();
        try {
            String requete = "SELECT * FROM produit where Disponible='oui'";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Produits p = new Produits();
                p.setId_p(rs.getInt("id_p"));
                p.setPrix(rs.getInt("prix"));
               // p.setIdcat(rs.getInt("idcat"));
                p.setNomp(rs.getString("nomp"));
                p.setLibellep(rs.getString("libellep"));
                Produit.add(p);
                //total += p.getPrix();
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        idP.setCellValueFactory(new PropertyValueFactory<Produits, Integer>("id_p"));
        colNom.setCellValueFactory(new PropertyValueFactory<Produits, String>("nomp"));

        colLibelle.setCellValueFactory(new PropertyValueFactory<Produits, String>("libellep"));

        colPrix.setCellValueFactory(new PropertyValueFactory<Produits, Integer>("prix"));

       // colId_cat.setCellValueFactory(new PropertyValueFactory<Produits, Integer>("idcat"));
        // Ajouter les colonnes à la table view

        //tabProduit.setItems(Produit);
        searchRec();

    }
    
 

    
    
    @FXML
   private void searchRec() {
       Produit por = new Produit();
         FilteredList<Produits> filteredData = new FilteredList<>(FXCollections.observableArrayList(por.afficher()),b->true);
         searchfield.textProperty().addListener((observable,oldValue,newValue)-> {
             filteredData.setPredicate(rec-> {
                 if (newValue == null || newValue.isEmpty()){
                     return true;
                 }
                 String lowerCaseFilter = newValue.toLowerCase();
                 if (rec.getNomp().toLowerCase().indexOf(lowerCaseFilter)!= -1){
                 return true;
                 }else if (String.valueOf(rec.getPrix()).toLowerCase().indexOf(lowerCaseFilter)!= -1){
                 return true;
                 }else
                 return false ;
                 
             });
         });
         SortedList<Produits> sortedData = new SortedList<>(filteredData);
         sortedData.comparatorProperty().bind(tabProduit.comparatorProperty());
       //  getTotal();
         tabProduit.setItems(sortedData); 
         
    }
   
     public double getTotal() {
    for (Produits produit : tabProduit.getItems()) {
        total += produit.getPrix();
    }
    return total;
}
     
     @FXML
private void gotoPayment(ActionEvent event) throws IOException {
    try {
        // Obtenir une référence à la scène et au contrôleur actuels
        Node source = (Node) event.getSource();
        Scene currentScene = source.getScene();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/ProduitFrant.fxml"));
        Parent parent = loader.load();
        ProduitFrantController currentController = loader.getController();

        // Obtenir le total en appelant une méthode sur le contrôleur actuel
        double total = currentController.getTotal();

        // Charger la prochaine scène
        FXMLLoader paymentLoader = new FXMLLoader(getClass().getResource("/GUI/Payment.fxml"));
        Parent paymentParent = paymentLoader.load();
        PaymentController paymentController = paymentLoader.getController();

        // Passer le total à la prochaine scène en utilisant une propriété de FXMLLoader
        paymentController.setTotal(total);

        Scene scene = new Scene(paymentParent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    } catch (IOException ex) {
        Logger.getLogger(AddProduitFXMLController.class.getName()).log(Level.SEVERE, null, ex);
    }
}

@FXML
    private void getstatistic(ActionEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/GUI/statistic.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(StatisticController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    
}
