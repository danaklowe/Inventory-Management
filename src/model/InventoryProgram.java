
package model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Dana K Lowe
 */
public class InventoryProgram extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainScreen.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //Sample info
        InHouse part1 = new InHouse(1, "Handlebars", 25.99, 5, 3, 10, 3447);
        InHouse part2 = new InHouse(2, "Bike Chain", 10.99, 25, 10, 50, 1730);
        InHouse part3 = new InHouse(3, "Tire", 19.99, 15, 10, 20, 5096);
        Outsourced part4 = new Outsourced(4, "Innertube", 7.99, 48, 20, 100, "Tires N Things");
        Outsourced part5 = new Outsourced(5, "Reflector", 4.99, 25, 10, 40, "Reflectorama");
        
        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);
        Inventory.addPart(part5);
        
        Product product1 = new Product(1, "Mountain Bike", 249.99, 5, 1, 10);
        Product product2 = new Product(2, "Road Bike", 399.99, 7, 1, 10);
        Product product3 = new Product(3, "Tandem Bike", 499.99, 2, 1, 3);
                
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);
        
                       
        launch(args);
    }
    
}
