
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

/**
 * FXML Controller class
 *
 * @author Dana K Lowe
 */
public class ModifyProductScreenController implements Initializable {
    
    private Product selectedProduct;
    Stage stage;
    Parent scene;

    @FXML
    private TextField idTxt;
    @FXML
    private TextField nameTxt;
    @FXML
    private TextField invTxt;
    @FXML
    private TextField priceTxt;
    @FXML
    private TextField maxTxt;
    @FXML
    private TextField minTxt;
    @FXML
    private TextField searchTxt;
    @FXML
    private TableView<Part> tableView;
    @FXML
    private TableColumn<Part, Integer> idCol;
    @FXML
    private TableColumn<Part, String> nameCol;
    @FXML
    private TableColumn<Part, Integer> invCol;
    @FXML
    private TableColumn<Part, Double> priceCol;
    @FXML
    private TableView<Part> tableView1;
    @FXML
    private TableColumn<Part, Integer> idCol1;
    @FXML
    private TableColumn<Part, String> nameCol1;
    @FXML
    private TableColumn<Part, Integer> invCol1;
    @FXML
    private TableColumn<Part, Double> priceCol1;
    
    public void initData(Product product){
        selectedProduct = product;
        
        idTxt.setText(String.valueOf(selectedProduct.getId()));
        nameTxt.setText(selectedProduct.getName());
        invTxt.setText(String.valueOf(selectedProduct.getStock()));
        priceTxt.setText(String.valueOf(selectedProduct.getPrice()));
        minTxt.setText(String.valueOf(selectedProduct.getMin()));
        maxTxt.setText(String.valueOf(selectedProduct.getMax()));       
        
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableView1.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
        tableView.setItems(Inventory.getAllParts());
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        invCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        tableView1.setItems(selectedProduct.getAllAssociatedParts());
        idCol1.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol1.setCellValueFactory(new PropertyValueFactory<>("name"));
        invCol1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceCol1.setCellValueFactory(new PropertyValueFactory<>("price"));
        
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void searchBtnClicked(ActionEvent event) {
        ObservableList<Part> searchResultsParts = FXCollections.observableArrayList();
        
        if(!(searchResultsParts.isEmpty()))
            searchResultsParts.clear();        
        String searchString = searchTxt.getText();

        try{
            int searchInt = Integer.parseInt(searchString);
            searchResultsParts.add(Inventory.lookupPart(searchInt));
            tableView.setItems(searchResultsParts);
            
        }catch(NumberFormatException e){
            tableView.setItems(Inventory.lookupPart(searchString));
        }        
    }

    @FXML
    private void addBtnClicked(ActionEvent event) {
        selectedProduct.addAssociatedPart(tableView.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void deleteBtnClicked(ActionEvent event) {
        if(tableView1.getSelectionModel().getSelectedItem() == null){
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Part not selected");
            alert2.setContentText("Please select a part to delete.");
            alert2.showAndWait();
       }else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "The selected part will be deleted. Do you want to continue?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                ObservableList<Part> selectedRows;
                selectedRows = tableView1.getSelectionModel().getSelectedItems();
                for (Part part: selectedRows)
                    selectedProduct.deleteAssociatedPart(part);
            }
       }
    }

    @FXML
    private void saveBtnClicked(ActionEvent event) throws IOException {
        
        //alert box for Inv out of bounds
        try{
            if (Integer.parseInt(invTxt.getText()) > Integer.parseInt(maxTxt.getText()) ||
            Integer.parseInt(invTxt.getText()) < Integer.parseInt(minTxt.getText()))
                throw new Exception();
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Inventory value out of bounds");
            alert.setContentText("Please enter an Inventory value that is between Min and Max values.");
            alert.showAndWait();
            return;
        }
        
        try{
            selectedProduct.setId(Integer.parseInt(idTxt.getText()));
            selectedProduct.setName(nameTxt.getText());
            selectedProduct.setPrice(Double.parseDouble(priceTxt.getText()));
            selectedProduct.setStock(Integer.parseInt(invTxt.getText()));
            selectedProduct.setMin(Integer.parseInt(minTxt.getText()));
            selectedProduct.setMax(Integer.parseInt(maxTxt.getText()));
            Inventory.updateProduct(selectedProduct.getId() -1, selectedProduct);
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/mainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Form error");
            alert.setContentText("Please ensure all fields are entered with their proper format.");
            alert.showAndWait();
            return;
        }
    }

    @FXML
    private void cancelBtnClicked(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Return to the main screen without saving?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/mainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }
    
    
}
