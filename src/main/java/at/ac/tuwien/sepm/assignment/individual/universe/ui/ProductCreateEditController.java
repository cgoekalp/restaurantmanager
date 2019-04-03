package at.ac.tuwien.sepm.assignment.individual.universe.ui;

import at.ac.tuwien.sepm.assignment.individual.universe.domain.dto.ProductDTO;
import at.ac.tuwien.sepm.assignment.individual.universe.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.universe.service.IProductService;
import at.ac.tuwien.sepm.assignment.individual.universe.service.ProductService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductCreateEditController {

    private ProductDTO productDTO;
    private IProductService productService;
    private static final Logger log = LoggerFactory.getLogger(ProductCreateEditController.class);


    @FXML
    private ComboBox<String> taxCategoryComboBox;

    @FXML
    private ComboBox<String> productCategoryComboBox;

    @FXML
    private Label titleLabel;

    @FXML
    private TextField nettoPriceInput;

    @FXML
    private TextField descriptionInput;

    @FXML
    private TextField productNameInput;
    private Stage dialog;

    @FXML
    void initialize(){
        productService = new ProductService();

        taxCategoryComboBox.getItems().add("");
        taxCategoryComboBox.getItems().add("10.0");
        taxCategoryComboBox.getItems().add("20.0");

        productCategoryComboBox.getItems().add("");
        productCategoryComboBox.getItems().add("Starter");
        productCategoryComboBox.getItems().add("MainCourse");
        productCategoryComboBox.getItems().add("Dessert");

        taxCategoryComboBox.getSelectionModel().selectFirst();
        productCategoryComboBox.getSelectionModel().selectFirst();

        nettoPriceInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.isEmpty() && !newValue.matches("\\d+\\.?|\\d+\\.?\\d+")){
                    nettoPriceInput.setText(oldValue);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Invalid Input");
                    alert.setHeaderText("Invalid Input for " + "Netto");
                    alert.setContentText("Please use a valid positive number, ex:5, 5.5 or 5.");
                    alert.showAndWait();
                }
            }
        });
    }

    @FXML
    void saveProduct(ActionEvent event) {

        boolean taxNotSelected = taxCategoryComboBox.getSelectionModel().getSelectedIndex() == 0;
        boolean categoryNotSelected = productCategoryComboBox.getSelectionModel().getSelectedIndex() == 0;
        boolean nettoIsEmpty = nettoPriceInput.getText().isEmpty();
        boolean nameIsEmpty = productNameInput.getText().isEmpty();

        if(taxNotSelected || categoryNotSelected || nameIsEmpty || nettoIsEmpty){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Missing Input");
            alert.setHeaderText("All fields with * are required!");
            alert.showAndWait();
            return;
        }

        setDTOValues();
        
        if(productDTO.getId() == 0){
            handleCreate(productDTO);
        }else {
            handleUpdate(productDTO);
        }

        dialog.close();
    }

    private void setDTOValues() {
        productDTO.setName(productNameInput.getText());
        productDTO.setNettoprice(Double.valueOf(nettoPriceInput.getText()));
        productDTO.setTax(Double.valueOf(taxCategoryComboBox.getSelectionModel().getSelectedItem()));
        productDTO.setCategory(productCategoryComboBox.getSelectionModel().getSelectedItem());
        productDTO.setDescription(descriptionInput.getText());
    }

    @FXML
    void cancelProduct(ActionEvent event) {
        dialog.close();
    }

    private void handleCreate(ProductDTO productDTO){
        try {
            productService.create(productDTO);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    private void handleUpdate(ProductDTO productDTO){
        try {
            productService.update(productDTO);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    public void setProductDTO(ProductDTO productDTO){
        log.info("Set new Product for create/edit");
        titleLabel.setText("Create");
        if(productDTO.getId() != 0){
            titleLabel.setText("Update");
            productNameInput.setText(productDTO.getName());
            productCategoryComboBox.getSelectionModel().select(productDTO.getCategory());
            taxCategoryComboBox.getSelectionModel().select(String.valueOf(productDTO.getTax()));
            descriptionInput.setText(productDTO.getDescription());
            nettoPriceInput.setText(String.valueOf(productDTO.getNettoprice()));
        }

        this.productDTO = productDTO;
    }

    public void setDialog(Stage dialog) {
        this.dialog = dialog;
    }
}
