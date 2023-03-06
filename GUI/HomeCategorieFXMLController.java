/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entities.Categories;
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
public class HomeCategorieFXMLController implements Initializable {
    
    @FXML
    private TableView<Categories> tabCategorie;
    @FXML
    private TableColumn<Categories, Integer> idC;
    @FXML
    private TableColumn<Categories, String> colNomC;
    @FXML
    private TableColumn<Categories, String> colDescription;
    @FXML
    private TableColumn<Categories, String> editCol;

    @FXML
    private Button btArch;
    @FXML
    private Button btback;
    
    
    java.sql.Connection cnx = Connetion.getInstance().getCnx();
    public ObservableList<Categories> Categorie = FXCollections.observableArrayList();
    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;

    /* @FXML
    private ComboBox USER;
    
    
   /* Categorie p= new Categorie();
    @FXML
    private final ObservableList   idd =FXCollections.observableArrayList(p.afficherID()); */
    /**
     *
     * @param event
     */
    @FXML
    public void afficher(ActionEvent event) {
        show();

    }
    @FXML
    private void gotoHome(ActionEvent event) throws IOException 
    {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/GUI/AccueilFXML.fxml"));
        Parent root= loader.load();
        btback.getScene().setRoot(root);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        show();
    }    
    
    public void show() {
        tabCategorie.getItems().clear();
        tabCategorie.refresh();
        try {
            String requete = "SELECT * FROM categorie ";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Categories c = new Categories();
                c.setId_cat(rs.getInt("id_cat"));
                c.setNom_cat(rs.getString("nom_cat"));
                c.setDescription(rs.getString("Description"));
                Categorie.add(c);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        idC.setCellValueFactory(new PropertyValueFactory<Categories, Integer>("id_cat"));
        colNomC.setCellValueFactory(new PropertyValueFactory<Categories, String>("nom_cat"));
        colDescription.setCellValueFactory(new PropertyValueFactory<Categories, String>("Description"));

       
//add cell of button edit 
        Callback<TableColumn<Categories, String>, TableCell<Categories, String>> cellFoctory = (TableColumn<Categories, String> param) -> {
            // make cell containing buttons
            final TableCell<Categories, String> cell = new TableCell<Categories, String>() {

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
                            Categories cat = null;

                            try {
                                cat = tabCategorie.getSelectionModel().getSelectedItem();
                                query = "DELETE FROM categorie WHERE id_cat=" + cat.getId_cat();
                                System.out.println(cat.getId_cat());
                                PreparedStatement pst = cnx.prepareStatement(query);
                                pst.execute();
                                show();

                            } catch (SQLException ex) {
                                Logger.getLogger(HomeCategorieFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        });
                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                            Categories cat = null;
                            cat = tabCategorie.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("/GUI/EditeCategorieFXML.fxml"));
                            try {
                                loader.load();

                                EditeCategorieFXMLController Edd = loader.getController();
                                //    Ed.setUpdate(true);
                                Edd.setTextField(cat.getNom_cat(), cat.getDescription(), cat.getId_cat());
                                Parent parent = loader.getRoot();
                                Stage stage = new Stage();
                                stage.setScene(new Scene(parent));
                                stage.initStyle(StageStyle.UTILITY);
                                stage.show();
                                show();
                            } catch (IOException ex) {
                                Logger.getLogger(HomeCategorieFXMLController.class.getName()).log(Level.SEVERE, null, ex);
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

        tabCategorie.setItems(Categorie);

    }

    @FXML
    private void getAddView(ActionEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/GUI/AddCategorieFXML.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AddCategorieFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    
    
}
