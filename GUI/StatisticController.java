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
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import utils.Connetion;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author YACINE
 */
public class StatisticController implements Initializable {

    @FXML
    private PieChart pieChart;
    
  @FXML
    private Button back;
//Calcul des statistiques des types des evenements

 java.sql.Connection cnx = Connetion.getInstance().getCnx();
    public ObservableList<Produits> Produit = FXCollections.observableArrayList();
    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
   private ObservableList data;
    @Override
public void initialize(URL url, ResourceBundle rb) {
    data = FXCollections.observableArrayList();
    try {
       String SQL = "SELECT DISTINCT prix AS 'Unique Prices', AVG(prix) AS 'Average Price', MIN(prix) AS 'Minimum Price', MAX(prix) AS 'Maximum Price', COUNT(*) AS 'Number of Products', nomp AS 'Product Name' FROM produit GROUP BY prix, nomp";
        PreparedStatement ste = (PreparedStatement) cnx.prepareStatement(SQL);
        ResultSet rs = ste.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getString(1));
            // adding data on area chart data
            data.add(new PieChart.Data(rs.getString(1), rs.getDouble(2)));
            pieChart.setData(data);
        }

        
    } catch (Exception e) {
        System.out.println("Error on DB connection");
        return;
    }
}

//Bouton de retour
       /*     @FXML
    void back(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AccEv.fxml"));
        Parent root = loader.load(); 
        back.getScene().setRoot(root);
    }*/

    
    
}
