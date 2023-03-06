/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import utils.Connetion;

/**
 * FXML Controller class
 *
 * @author YACINE
 */
public class ArchiverProduitFXMLController implements Initializable {
    
    
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
    private TableColumn<Produits, String> editCol;
      @FXML
    private TableColumn<Produits, Integer> idP;
    @FXML
    private Button btback;
      
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
    @FXML
    public void afficher(ActionEvent event) {
                show();
       
    }
    
    public void show (){
      tabProduit.getItems().clear();
      tabProduit.refresh();
        try {
            String requete ="SELECT * FROM produit where Disponible='non'";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
               Produits p =new Produits();
                            p.setId_p(rs.getInt("id_p"));
               p.setPrix(rs.getInt("prix"));
                p.setIdcat(rs.getInt("idcat"));
                p.setNomp(rs.getString("nomp"));
                p.setLibellep(rs.getString("libellep"));
                Produit.add(p);
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
idP.setCellValueFactory(new PropertyValueFactory<Produits, Integer>("id_p"));
        colNom.setCellValueFactory(new PropertyValueFactory<Produits, String>("nomp"));

        colLibelle.setCellValueFactory(new PropertyValueFactory<Produits, String>("libellep"));

        colPrix.setCellValueFactory(new PropertyValueFactory<Produits, Integer>("prix"));
        
        colId_cat.setCellValueFactory(new PropertyValueFactory<Produits, Integer>("idcat"));
        
//add cell of button edit 
         Callback<TableColumn<Produits, String>, TableCell<Produits, String>> cellFoctory = (TableColumn<Produits, String> param) -> {
            // make cell containing buttons
            final TableCell<Produits, String> cell = new TableCell<Produits, String>() {
                
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);

                        deleteIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill:#ff1744;"
                        );
                        editIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill:#00E676;"
                        );
                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                            Produits Produi = null;

                            try {
                                Produi = tabProduit.getSelectionModel().getSelectedItem();
                                query = "DELETE FROM produit WHERE id_p="+Produi.getId_p();
                                System.out.println(Produi.getId_p());
                                PreparedStatement pst = cnx.prepareStatement(query);
                                pst.execute();
                                show();
                                
                            } catch (SQLException ex) {
                                Logger.getLogger(HomeProduitFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                           

                          

                        });
                        
                         editIcon.setOnMouseClicked((MouseEvent event) -> {
                            
                            Produits Produi = null;

                            try {
                                Produi = tabProduit.getSelectionModel().getSelectedItem();
                                query = "UPDATE `produit` SET Disponible='oui' WHERE id_p ="+Produi.getId_p();
                                System.out.println(Produi.getId_p());
                                PreparedStatement pst = cnx.prepareStatement(query);
                                pst.execute();
                                show();
                                
                            } catch (SQLException ex) {
                                Logger.getLogger(HomeProduitFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        });
                        
                        

                        HBox managebtn = new HBox(editIcon, deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };
         editCol.setCellFactory(cellFoctory);
        // Ajouter les colonnes à la table view
        
        tabProduit.setItems(Produit);
                      

  }
    @FXML
    private void gotoHome(ActionEvent event) throws IOException 
    {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/GUI/HomeProduitFXML.fxml"));
        Parent root= loader.load();
        btback.getScene().setRoot(root);
    }
    
}
