
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

/**
 * FXML Controller class
 *
 * @author Dana K Lowe
 */
public class AddPartScreenController implements Initializable {
    
    Stage stage;
    Parent scene;

    @FXML
    private RadioButton inHouseRBtn;
    @FXML
    private RadioButton outsourcedRBtn;
    @FXML
    private Label coNameMachineIDLbl;
    @FXML
    private TextField idTxt;
    @FXML
    private TextField nameTxt;
    @FXML
    private TextField invTxt;
    @FXML
    private TextField priceTxt;
    @FXML
    private TextField coNameMachineIDTxt;
    @FXML
    private TextField maxTxt;
    @FXML
    private TextField minTxt;
    @FXML
    private ToggleGroup rBtnGroup;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idTxt.setText(String.valueOf(Inventory.findPartId()));
        invTxt.setText("0");
    }    

    @FXML
    private void inHouseRBtnClicked(ActionEvent event) {
        coNameMachineIDLbl.setText("Machine ID");
        coNameMachineIDTxt.setPromptText("Mach ID");
    }

    @FXML
    private void outsourcedRBtnClicked(ActionEvent event) {
        coNameMachineIDLbl.setText("Company Name");
        coNameMachineIDTxt.setPromptText("Comp Nm");
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
            if (inHouseRBtn.isSelected()){
                InHouse newPart = new InHouse(
                    Integer.parseInt(idTxt.getText()),
                    nameTxt.getText(), 
                    Double.parseDouble(priceTxt.getText()), 
                    Integer.parseInt(invTxt.getText()), 
                    Integer.parseInt(minTxt.getText()), 
                    Integer.parseInt(maxTxt.getText()), 
                    Integer.parseInt(coNameMachineIDTxt.getText()));
                Inventory.getAllParts().add(newPart);
            }
            if (outsourcedRBtn.isSelected()){
                Outsourced newPart = new Outsourced(
                    Integer.parseInt(idTxt.getText()),
                    nameTxt.getText(), 
                    Double.parseDouble(priceTxt.getText()), 
                    Integer.parseInt(invTxt.getText()), 
                    Integer.parseInt(minTxt.getText()), 
                    Integer.parseInt(maxTxt.getText()), 
                    coNameMachineIDTxt.getText());
                Inventory.getAllParts().add(newPart);
            }
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
