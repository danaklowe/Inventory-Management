
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Inventory {
    
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }
    
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }
    
    public static Part lookupPart(int partId){
        for (Part part : allParts)
            if(part.getId() == partId)
                return part;
        return null;
    }
    
    public static Product lookupProduct(int productId){
        for (Product product : allProducts)
            if(product.getId() == productId)
                return product;
        return null;
    }
    
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> partResults = FXCollections.observableArrayList();
        if(!(partResults.isEmpty()))
            partResults.clear();
        for (Part part : allParts)
            if((part.getName().contains(partName)))
                partResults.add(part);
        return partResults;
    }
    
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> productResults = FXCollections.observableArrayList();
        if(!(productResults.isEmpty()))
            productResults.clear();
        for (Product product : allProducts)
            if((product.getName().contains(productName)))
                productResults.add(product);
        return productResults;
    }
    
    public static void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }
    
    public static void updateProduct(int index, Product selectedProduct){
        allProducts.set(index, selectedProduct);
    }
    
    public static void deletePart(Part selectedPart){
        allParts.remove(selectedPart);
    }
    
    public static void deleteProduct(Product selectedProduct){
        allProducts.remove(selectedProduct);
    }
  
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
    
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }

    public static int findPartId(){
        int newPartId = 0;
        for(int i = 1; i<=100; i++){
            if(Inventory.lookupPart(i) == null){
                newPartId = i;
                break;
            }
        }
        return newPartId;
    }
    
    public static int findProductId(){
        int newProductId = 0;
        for(int i = 1; i<=100; i++){
            if(Inventory.lookupProduct(i) == null){
                newProductId = i;
                break;
            }
        }
        return newProductId;
    }
}
