package at.ac.tuwien.sepm.assignment.individual.universe.ui;


import at.ac.tuwien.sepm.assignment.individual.application.MainApplication;
import at.ac.tuwien.sepm.assignment.individual.universe.domain.dto.ProductDTO;
import at.ac.tuwien.sepm.assignment.individual.universe.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.universe.service.IProductService;
import at.ac.tuwien.sepm.assignment.individual.universe.service.ProductService;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProductController {

    private ObservableList<ProductDTO> data;
    private final IProductService productService = new ProductService();
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @FXML
    private TextField nettoMaxInput;

    @FXML
    private ComboBox<String> categoryComboBox;

    @FXML
    private TextField bruttoMaxInput;

    @FXML
    private TextField bruttoMinInput;

    @FXML
    private TableView<ProductDTO> productsTable;

    @FXML
    private TextField productNameInput;

    @FXML
    private TextField nettoMinInput;

    @FXML
    private Label bruttoPriceLabel;

    @FXML
    private Label updatedLabel;

    @FXML
    private Label productNameLabel;

    @FXML
    private Label productCategoryLabel;

    @FXML
    private Label taxLabel;

    @FXML
    private Label productDescriptionLabel;

    @FXML
    private Label nettoPriceLabel;

    @FXML
    private Label createdLabel;

    @FXML
    void initialize() {
        try {

            data = FXCollections.observableArrayList( productService.findAll() );
            createColumns(data);
            addTableContextMenu();
            fillCategoryComboBox();
            addTextFieldListeners();
            setWrapText();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void createNewProduct(ActionEvent event) {
        handleNewEditProduct(new ProductDTO());
    }

    @FXML
    void searchProducts(ActionEvent event) {

        ProductDTO productDTO = new ProductDTO();
        productDTO.setCategory(categoryComboBox.getValue());
        productDTO.setNettoMin(nettoMinInput.getText());
        productDTO.setNettoMax(nettoMaxInput.getText());
        productDTO.setBruttoMin(bruttoMinInput.getText());
        productDTO.setBruttoMax(bruttoMaxInput.getText());
        productDTO.setName(productNameInput.getText());

        try {
            data.clear();
            data.addAll(productService.search(productDTO));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void clearFields(ActionEvent event) {
        categoryComboBox.getSelectionModel().select(0);
        nettoMinInput.setText("");
        nettoMaxInput.setText("");
        bruttoMinInput.setText("");
        bruttoMaxInput.setText("");
        productNameInput.setText("");

        log.info("Cleared fields");
    }

    private void fillCategoryComboBox(){
        categoryComboBox.getItems().add("");
        categoryComboBox.getItems().add("Starter");
        categoryComboBox.getItems().add("MainCourse");
        categoryComboBox.getItems().add("Dessert");
    }

    private void addTextFieldListeners(){

        nettoMinInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.isEmpty() && !newValue.matches("\\d+\\.?|\\d+\\.?\\d+")){
                    nettoMinInput.setText(oldValue);
                    disyplayAlert("NettoMin");
                }
            }
        });

        nettoMaxInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.isEmpty() && !newValue.matches("\\d+\\.?|\\d+\\.?\\d+")){
                    nettoMaxInput.setText(oldValue);
                    disyplayAlert("NettoMax");
                }
            }
        });

        bruttoMaxInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.isEmpty() && !newValue.matches("\\d+\\.?|\\d+\\.?\\d+")){
                    bruttoMaxInput.setText(oldValue);
                    disyplayAlert("BruttoMax");
                }
            }
        });

        bruttoMinInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.isEmpty() && !newValue.matches("\\d+\\.?|\\d+\\.?\\d+")){
                    bruttoMinInput.setText(oldValue);
                    disyplayAlert("BruttoMin");
                }
            }
        });
    }

    private void setWrapText(){
        productDescriptionLabel.setWrapText(true);
        productDescriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
    }

    private void disyplayAlert(String fieldName){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Invalid Input");
        alert.setHeaderText("Invalid Input for " + fieldName);
        alert.setContentText("Please use a valid positive number, ex:5, 5.5 or 5.");
        alert.showAndWait();
    }

    private void createColumns(ObservableList<ProductDTO> tableData){
        TableColumn<ProductDTO, String> nameCol = new TableColumn<>("Name");
        nameCol.setMinWidth(100);
        nameCol.setCellValueFactory(
            new PropertyValueFactory<ProductDTO, String>("name"));

        TableColumn<ProductDTO, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setMinWidth(100);
        categoryCol.setCellValueFactory(
            new PropertyValueFactory<ProductDTO, String>("category"));

        TableColumn<ProductDTO, Double> bruttoCol = new TableColumn<>("Brutto Price");
        bruttoCol.setMinWidth(200);
        bruttoCol.setCellValueFactory(
            new PropertyValueFactory<ProductDTO, Double>("bruttoprice"));

        TableColumn<ProductDTO, Double> nettoCol = new TableColumn<>("Netto Price");
        nettoCol.setMinWidth(200);
        nettoCol.setCellValueFactory(
            new PropertyValueFactory<ProductDTO, Double>("nettoprice"));

        productsTable.setItems(tableData);
        productsTable.getColumns().addAll(Arrays.asList(nameCol, categoryCol, bruttoCol, nettoCol));

        productsTable.getSelectionModel().setSelectionMode(
            SelectionMode.MULTIPLE
        );
    }

    private void addTableContextMenu(){
        productsTable.setRowFactory(new Callback<TableView<ProductDTO>, TableRow<ProductDTO>>() {
            @Override
            public TableRow<ProductDTO> call(TableView<ProductDTO> tableView) {
                final TableRow<ProductDTO> row = new TableRow<>();
                final ContextMenu contextMenu = new ContextMenu();
                final MenuItem deleteMenuItem = new MenuItem("Delete");
                deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        List<ProductDTO> items =  new ArrayList(productsTable.getSelectionModel().getSelectedItems());
                        log.info("Number of selected rows " + items.size());
                        handleRemoveProduct(items);
                    }
                });

                final MenuItem editMenuItem = new MenuItem("Edit ");
                editMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        handleNewEditProduct(row.getItem());
                    }
                });

                contextMenu.getItems().add(editMenuItem);
                contextMenu.getItems().add(deleteMenuItem);
                // Set context menu on row, but use a binding to make it only show for non-empty rows:
                row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                        .then((ContextMenu)null)
                        .otherwise(contextMenu)
                );

                row.setOnMouseClicked(event -> {
                    handleProductDetails(row.getItem());
                });

                return row ;
            }
        });
    }

    private void handleNewEditProduct(ProductDTO item) {

        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(MainApplication.getPrimaryStage());

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/productCreateEdit.fxml"));

        ProductCreateEditController productCreateEditController = new ProductCreateEditController();
        loader.setController(productCreateEditController);

        try {

            AnchorPane products = (AnchorPane) loader.load();
            AnchorPane.setTopAnchor(products, 0.0);
            AnchorPane.setLeftAnchor(products, 0.0);
            AnchorPane.setBottomAnchor(products, 0.0);
            AnchorPane.setRightAnchor(products, 0.0);

            productCreateEditController.setProductDTO(item);

            Scene dialogScene = new Scene(products, 800, 600);
            dialog.setScene(dialogScene);
            dialog.setResizable(false);
            dialog.show();

            dialog.setOnHiding(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    log.info("Stage Dialog is closing");
                    refreshData();
                }
            });

            productCreateEditController.setDialog(dialog);

        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("Selected product new/edit");
    }

    private boolean handleRemoveProduct(List<ProductDTO> productDTOList){

        String productList = "\n";

        for(ProductDTO productDTO : productDTOList){
            productList+= productDTO.getName() + "\n";
            if(productDTOList.indexOf(productDTO) == 10){
                productList+="...";
                break;
            }
        }

        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.WARNING,
            "Are sure you wan to delete the selected product(s)?" + productList,
            yes,
            no);

        alert.setTitle("Delete Product");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.orElse(no) == yes) {
            try {
                log.info("Selected product delete");
                productService.removeProductById(productDTOList);
                refreshData();
                return true;
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    private void handleProductDetails(ProductDTO productDTO){

        if (productDTO == null) return;

        productNameLabel.setText(productDTO.getName());
        productCategoryLabel.setText(productDTO.getCategory());
        bruttoPriceLabel.setText(String.valueOf(productDTO.getBruttoprice()));
        nettoPriceLabel.setText(String.valueOf(productDTO.getNettoprice()));
        taxLabel.setText(String.valueOf(productDTO.getTax()));
        createdLabel.setText(productDTO.getCreatedString());
        updatedLabel.setText(productDTO.getUpdatedString());
        productDescriptionLabel.setText(productDTO.getDescription());

        log.info("Selected product details");
    }

    public void refreshData(){
        searchProducts(null);
    }

}
