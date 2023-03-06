/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import com.itextpdf.text.Anchor;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entities.Categories;
import entities.Produits;
import java.io.IOException;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import service.Categorie;
import service.Produit;
import utils.Connetion;
import java.io.File;
import java.io.IOException;

import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.function.Function;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.AcroFields.Item;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfDate;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.text.TextAlignment;
import javax.swing.text.StyleConstants.FontConstants;

/**
 * FXML Controller class
 *
 * @author YACINE
 */
public class HomeProduitFXMLController implements Initializable {

    /**
     *
     * @param event
     */

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
    private Button pdf;

    @FXML
    private Button btArch;
    @FXML
    private Button btback;
    @FXML
    private TextField searchfield;
    @FXML
    private TextField searchfield1;

    java.sql.Connection cnx = Connetion.getInstance().getCnx();
    public ObservableList<Produits> Produit = FXCollections.observableArrayList();
    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    Produits Pr = null;
    private boolean update;
    int PoduitId;

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        /*
         USER.setValue("Select id");
         USER.setItems(idd);*/
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
                                query = "UPDATE `produit` SET Disponible='non' WHERE id_p =" + Produi.getId_p();
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
                            Produi = tabProduit.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("/GUI/EditeProduitFXML.fxml"));
                            try {
                                loader.load();

                                EditeProduitFXMLController Ed = loader.getController();
                                //    Ed.setUpdate(true);
                                Ed.setTextField(Produi.getPrix(), Produi.getNomp(), Produi.getLibellep(), Produi.getId_p());
                                Parent parent = loader.getRoot();
                                Stage stage = new Stage();
                                stage.setScene(new Scene(parent));
                                stage.initStyle(StageStyle.UTILITY);
                                stage.show();
                                show();
                            } catch (IOException ex) {
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

        //tabProduit.setItems(Produit);
        searchRec();

    }

    @FXML
    private void getAddView(ActionEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/GUI/AddProduitFXML.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AddProduitFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void gotoArchiver(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/ArchiverProduitFXML.fxml"));
        Parent root = loader.load();
        btArch.getScene().setRoot(root);
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
         tabProduit.setItems(sortedData); 
         
    }

   
   @FXML
   private void btPDF(ActionEvent event) {
      FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(pdf.getScene().getWindow());
        if (file != null) {
        ToPDFFacture(tabProduit, file.getAbsolutePath());
    }
   }
   
   public void ToPDFFacture(TableView<Produits> tabProduit, String filename) {
    try {
        Document document = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(document, new FileOutputStream(filename));
        document.open();
        
        
        Image image = Image.getInstance("background.jpg");
        image.setAbsolutePosition(0, 0);
        image.setAlignment(1);
        document.add(image);
        

    Font fontTitle = new Font(BaseFont.createFont(), 20, Font.BOLD);
    Font fontSubtitle = new Font(BaseFont.createFont(), 16, Font.BOLD);
    Font fontNormal = new Font(BaseFont.createFont(), 12);

    Paragraph title = new Paragraph("Facture", fontTitle);
    title.setAlignment(Element.ALIGN_CENTER);
    document.add(title);

    Paragraph subtitle = new Paragraph("Date: 04/03/2023", fontSubtitle);
    subtitle.setAlignment(Element.ALIGN_CENTER);
    document.add(subtitle);
    
    Image imagea = Image.getInstance("logo.png");
    imagea.scaleAbsolute(80, 80);
    imagea.setAlignment(1);
    document.add(imagea) ;
    
    
    // Numéro de facture
    Paragraph numFacture = new Paragraph("N° Facture: 123456", fontNormal);
    numFacture.setAlignment(Element.ALIGN_LEFT);
    document.add(numFacture);

    document.add(Chunk.NEWLINE);
    
    // Tableau de produits
    PdfPTable table = new PdfPTable(new float[] { 2, 5, 2 });
    table.setWidthPercentage(100);
    table.setSpacingBefore(10f);
    table.setSpacingAfter(10f);
    
    // Entête du tableau
    PdfPCell cell1 = new PdfPCell(new Paragraph("Nom", fontNormal));
    cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
    cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
    PdfPCell cell2 = new PdfPCell(new Paragraph("Libellé", fontNormal));
    cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
    cell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
    PdfPCell cell3 = new PdfPCell(new Paragraph("Prix", fontNormal));
    cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
    cell3.setBackgroundColor(BaseColor.LIGHT_GRAY);
    
    table.addCell(cell1);
    table.addCell(cell2);
    table.addCell(cell3);
    
    // Lignes du tableau
    ObservableList<Produits> items = tabProduit.getItems();
    for (Produits item : items) {
        cell1 = new PdfPCell(new Paragraph(item.getNomp().toString(), fontNormal));
        cell2 = new PdfPCell(new Paragraph(item.getLibellep().toString(), fontNormal));
        cell3 = new PdfPCell(new Paragraph(Integer.toString(item.getPrix()), fontNormal));
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
    }
    
    document.add(table);
    
    // Total de la facture
    float total = 0;
    for (Produits item : items) {
        total += item.getPrix();
    }
    
    Paragraph totalPar = new Paragraph("Total: " + total, fontNormal);
    totalPar.setAlignment(Element.ALIGN_RIGHT);
    document.add(totalPar);
    
    document.close();

} catch (Exception e) {
    e.printStackTrace();
}

}


}
