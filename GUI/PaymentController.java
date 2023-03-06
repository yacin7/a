/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import com.google.common.collect.Lists;
import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.AmountWithBreakdown;
import com.paypal.orders.LinkDescription;
import com.paypal.orders.Order;
import com.paypal.orders.OrdersCreateRequest;
import com.paypal.orders.PurchaseUnitRequest;
import entities.Produits;
import java.io.IOException;
import java.util.Arrays;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author YACINE
 */
public class PaymentController implements Initializable {
    
    @FXML
    private TableView<Produits> tabProduit;
    

    // Variables d'environnement pour les informations d'identification PayPal
    String clientId = "CLIENT_ID_HERE";
    String clientSecret = "CLIENT_SECRET_HERE";

    @FXML
    private TextField amountField;
    @FXML
    private Text text;
    
    private double total;
    
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
//        setTotal();

    }    
    
    public void setTotal(double total) {
        this.total = total;
        text.setText(String.valueOf(total));
    }
    
   /* void Paypal() {
        // Remplacez CLIENT_ID et CLIENT_SECRET par vos propres clés d'API PayPal
        PayPalEnvironment environment = new PayPalEnvironment.Sandbox("CLIENT_ID", "CLIENT_SECRET");
        PayPalHttpClient client = new PayPalHttpClient(environment);

        // Créer l'objet PurchaseUnitRequest avec le montant de la transaction
        AmountWithBreakdown amount = new AmountWithBreakdown();
        amount.currencyCode("USD");
        amount.value("100.00"); // Remplacez par le montant de votre choix

        PurchaseUnitRequest purchaseUnit = new PurchaseUnitRequest();
        purchaseUnit.amountWithBreakdown(amount);

        // Créer l'objet OrdersCreateRequest avec l'objet PurchaseUnitRequest
        OrdersCreateRequest request = new OrdersCreateRequest();
        request.prefer("return=representation");
        //request.requestBody(new Order().purchaseUnits(Lists.newArrayList(purchaseUnit)));

        // Envoyer la requête à l'API PayPal et récupérer l'objet Order
        HttpResponse<Order> response;
        try {
            response = client.execute(request);
            Order order = response.result();

            // Récupérer le lien de paiement "approve"
            LinkDescription link = order.links().stream()
                    .filter(l -> "approve".equals(l.rel()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("approve link not found"));

            // Ouvrir le lien de paiement dans le navigateur
            Desktop.getDesktop().browse(new URI(link.href()));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }*/
    @FXML
    void processPayment(ActionEvent event) {
        // Ouvrir PayPal dans un WebView
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.load("https://www.paypal.com/signin?intent=checkout&country.x=AE&locale.x=en_US&langTgl=en");
        StackPane root = new StackPane();
        root.getChildren().add(webView);
        Scene scene = new Scene(root, 800, 600);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }


    
}
