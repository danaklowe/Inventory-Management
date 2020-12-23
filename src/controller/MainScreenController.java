
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
public class MainScreenController implements Initializable {
    
    private Stage stage;
    private Parent scene;

    @FXML
    private Button searchBtn;
    @FXML
    private TextField searchTxt;
    @FXML
    private TableView<Part> tableView;
    @FXML
    private TableColumn<Part, Integer> idCol;
    @FXML
    private TableColumn<Part, String> nameCol;
    @FXML
    private TableColumn<Part, Integer> inventoryCol;
    @FXML
    private TableColumn<Part, Double> priceCol;
    @FXML
    private Button searchBtn1;
    @FXML
    private TextField searchTxt1;
    @FXML
    private TableView<Product> tableView1;
    @FXML
    private TableColumn<Product, Integer> idCol1;
    @FXML
    private TableColumn<Product, String> nameCol1;
    @FXML
    private TableColumn<Product, Integer> inventoryCol1;
    @FXML
    private TableColumn<Product, Double> priceCol1;

    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //Parts table
        tableView.setItems(Inventory.getAllParts());
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        
        //Products table
        idCol1.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol1.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryCol1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceCol1.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableView1.setItems(Inventory.getAllProducts());
    }    

    @FXML //Searches for Part by ID or partial Name
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
    private void addBtnClicked(ActionEvent event) throws IOException {
       changeScreen(event, "/view/addPartScreen.fxml");
    }

    @FXML
    private void modifyBtnClicked(ActionEvent event) throws IOException {
      
       if(tableView.getSelectionModel().getSelectedItem() == null){
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Part not selected");
           alert.setContentText("Please select a part to modify.");
           alert.showAndWait();
       }else{
           stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        
           FXMLLoader loader = new FXMLLoader();
           loader.setLocation(getClass().getResource("/view/modifyPartScreen.fxml"));
           scene = loader.load();
        
           stage.setScene(new Scene(scene));
           stage.show();
                
           ModifyPartScreenController controller = loader.getController();
           controller.initData(tableView.getSelectionModel().getSelectedItem()); 
       }
    }

    @FXML
    private void deleteBtnClicked(ActionEvent event) {
            
        if(tableView.getSelectionModel().getSelectedItem() == null){
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Part not selected");
            alert2.setContentText("Please select a part to delete.");
            alert2.showAndWait();
       }else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "The selected part will be deleted. Do you want to continue?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                Inventory.deletePart(tableView.getSelectionModel().getSelectedItem());
            }
       }
    }

    @FXML //Searches for Product by ID or partial Name 
    private void searchBtn1Clicked(ActionEvent event) {
        ObservableList<Product> searchResultsProducts = FXCollections.observableArrayList();
        
        if(!(searchResultsProducts.isEmpty()))
            searchResultsProducts.clear();        
        String searchString = searchTxt1.getText();

        try{
            int searchInt = Integer.parseInt(searchString);
            searchResultsProducts.add(Inventory.lookupProduct(searchInt));
            tableView1.setItems(searchResultsProducts);
            
        }catch(NumberFormatException e){
            tableView1.setItems(Inventory.lookupProduct(searchString));
          
        }
    }

    @FXML
    private void addBtn1Clicked(ActionEvent event) throws IOException {
        changeScreen(event, "/view/addProductScreen.fxml");
    }

    @FXML
    private void modifyBtn1Clicked(ActionEvent event) throws IOException {
        
        if(tableView1.getSelectionModel().getSelectedItem() == null){
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Product not selected");
           alert.setContentText("Please select a product to modify.");
           alert.showAndWait();
        }else{
           stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        
           FXMLLoader loader = new FXMLLoader();
           loader.setLocation(getClass().getResource("/view/modifyProductScreen.fxml"));
           scene = loader.load();
        
           stage.setScene(new Scene(scene));
           stage.show();
                
           ModifyProductScreenController controller = loader.getController();
           controller.initData(tableView1.getSelectionModel().getSelectedItem()); 
       }
        
    }

    @FXML 
    private void deleteBtn1Clicked(ActionEvent event) {
        if(tableView1.getSelectionModel().getSelectedItem() == null){
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Product not selected");
            alert2.setContentText("Please select a product to delete.");
            alert2.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "The selected product will be deleted. Do you want to continue?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                Inventory.deleteProduct(tableView1.getSelectionModel().getSelectedItem());
            }
       }
    }

    @FXML
    private void exitBtnClicked(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit the program?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            System.exit(0);
        }
        
    }
    
    public void changeScreen(ActionEvent event, String targetScreen) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource(targetScreen));
        stage.setScene(new Scene(scene));
        stage.show();
        
    }
}
