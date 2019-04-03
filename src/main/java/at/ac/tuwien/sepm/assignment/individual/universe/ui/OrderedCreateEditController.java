package at.ac.tuwien.sepm.assignment.individual.universe.ui;

import at.ac.tuwien.sepm.assignment.individual.universe.domain.dto.OrderedDTO;
import at.ac.tuwien.sepm.assignment.individual.universe.domain.dto.ProductDTO;
import at.ac.tuwien.sepm.assignment.individual.universe.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.universe.service.IOrderedService;
import at.ac.tuwien.sepm.assignment.individual.universe.service.IProductService;
import at.ac.tuwien.sepm.assignment.individual.universe.service.OrderedService;
import at.ac.tuwien.sepm.assignment.individual.universe.service.ProductService;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class OrderedCreateEditController {

    private OrderedDTO orderedDTO;
    private Stage dialog;
    private ObservableList<ProductDTO> productData;
    private ObservableList<ProductDTO> orderedProductsData;
    private HashMap<String, ProductDTO> helperList;

    private static final Logger log = LoggerFactory.getLogger(OrderedCreateEditController.class);
    private IProductService productService = new ProductService();
    private IOrderedService orderedService = new OrderedService();

    @FXML
    private TableView<ProductDTO> orderedProductsTable;

    @FXML
    private TextField tableInput;

    @FXML
    private TableView<ProductDTO> productsTable;

    @FXML
    private TextField productNameLabel;

    @FXML
    private TextField productCountInput;

    @FXML
    private Label totalBruttoLabel;

    @FXML
    void addSelectedProduct(ActionEvent event) {

        if(productsTable.getSelectionModel().getSelectedItem() != null){

            ProductDTO selected = productsTable.getSelectionModel().getSelectedItem();

            int count = Integer.valueOf(productCountInput.getText());

            if(helperList.containsKey(selected.getName())){
                ProductDTO productDTO = helperList.get(selected.getName());
                productDTO.setCount( productDTO.getCount() + count);
            }else {
                helperList.put(selected.getName(), selected);
                ProductDTO productDTO = helperList.get(selected.getName());
                productDTO.setCount(count);
            }
            orderedProductsData.clear();
            orderedDTO.getProductList().clear();

            orderedProductsData.addAll(helperList.values());
            orderedDTO.getProductList().addAll(helperList.values());

            try {
                totalBruttoLabel.setText(String.valueOf(orderedService.calculateBrutto(orderedDTO)));
            } catch (ServiceException e) {
                e.printStackTrace();
            }

            log.info(productsTable.getSelectionModel().getSelectedItem().getName());
        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Input");
            alert.setContentText("Please select a product to add");
            alert.showAndWait();
        }

    }

    @FXML
    void saveOrder(ActionEvent event) {

        if(tableInput.getText().isEmpty()){
            disyplayAlert("Invalid Input","Table", "Please enter a table number");
            return;
        }

        if(orderedDTO.getProductList().isEmpty()){
            disyplayAlert("Invalid Input","Count", "Please add at least one product");
            return;
        }

        try {

            orderedDTO.setOrderstatus(0);
            orderedDTO.setPaymenttype("");
            orderedDTO.setTotal(Double.valueOf(totalBruttoLabel.getText()));

            int tableNumber = Integer.valueOf(tableInput.getText());

            if(orderedDTO.getId() == 0){
                if(!orderedService.isTableFree(tableNumber)){
                    disyplayAlert("Table","There is already an open order on this table", "Please choose another table number");
                    return;
                }
                orderedDTO.setDiningtable(Integer.valueOf(tableInput.getText()));
                orderedService.create(orderedDTO);
            }else {

                if(Integer.valueOf(tableInput.getText()) != orderedDTO.getDiningtable() &&
                    !orderedService.isTableFree(tableNumber)){
                    disyplayAlert("Table","There is already an open order on this table", "Please choose the current or another table number");
                    return;
                }

                orderedDTO.setDiningtable(Integer.valueOf(tableInput.getText()));
                orderedService.update(orderedDTO);
            }

            dialog.close();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void cancelOrder(ActionEvent event) {
        dialog.close();
    }

    public void setOrderedDTO(OrderedDTO item) {

        this.orderedDTO = item;
        if(orderedDTO.getId() != 0){
            try {

                List<ProductDTO> productDTOList = productService.findByOrdered(item);
                orderedProductsData.addAll(productDTOList);
                orderedDTO.getProductList().clear();
                orderedDTO.getProductList().addAll(productDTOList);
                totalBruttoLabel.setText(String.valueOf(orderedService.calculateBrutto(orderedDTO)));
                tableInput.setText(String.valueOf(orderedDTO.getDiningtable()));

                for(ProductDTO productDTO : productDTOList){
                    helperList.put(productDTO.getName(), productDTO);
                }

            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }
    }

    public void setDialog(Stage dialog) {
        this.dialog = dialog;
    }

    @FXML
    void initialize(){

        try {

            List<ProductDTO> productList = productService.findAll();
            productData = FXCollections.observableArrayList( productList );

            List<ProductDTO> orderedProductList = new ArrayList<>();
            orderedProductsData = FXCollections.observableArrayList( orderedProductList );

            helperList = new HashMap<String, ProductDTO>();

        } catch (ServiceException e) {
            e.printStackTrace();
        }

        initProductColumns(productData);
        initOrderedProductColumns(orderedProductsData);

        addTextFieldListeners();
        addOrderedProductTableContextMenu();

        log.info("Initialized create edit order");
    }

    private void addTextFieldListeners(){

        tableInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.isEmpty() && !newValue.matches("[1-9]|[1-9][0-9]+")){
                    tableInput.setText(oldValue);
                    disyplayAlert("Invalid Input","Table", "Please enter a number, ex: 5");
                }
            }
        });

        productCountInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.isEmpty() && !newValue.matches("[1-9]|[1-9][0-9]+")){
                    productCountInput.setText(oldValue);
                    disyplayAlert("Invalid Input","Count", "Please enter a number, ex: 5");
                }
            }
        });
    }

    private void addOrderedProductTableContextMenu(){
        orderedProductsTable.setRowFactory(new Callback<TableView<ProductDTO>, TableRow<ProductDTO>>() {
            @Override
            public TableRow<ProductDTO> call(TableView<ProductDTO> tableView) {
                final TableRow<ProductDTO> row = new TableRow<>();
                final ContextMenu contextMenu = new ContextMenu();
                final MenuItem deleteMenuItem = new MenuItem("Delete");
                deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        log.info("Selected order dto");
                        handleRemoveProduct(row.getItem());
                    }
                });

                contextMenu.getItems().add(deleteMenuItem);
                // Set context menu on row, but use a binding to make it only show for non-empty rows:
                row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                        .then((ContextMenu)null)
                        .otherwise(contextMenu)
                );

                return row ;
            }
        });
    }

    private void handleRemoveProduct(ProductDTO item) {
        item.setCount(0);
        orderedProductsData.remove(item);
        orderedDTO.getProductList().remove(item);
        try {
            totalBruttoLabel.setText(String.valueOf(orderedService.calculateBrutto(orderedDTO)));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    private void disyplayAlert(String title, String header, String context){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(context);
        alert.showAndWait();
    }

    private void initProductColumns(ObservableList<ProductDTO> list){
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

        FilteredList<ProductDTO> filteredData = new FilteredList<>(list, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        productNameLabel.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(product -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (product.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                }

                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<ProductDTO> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(productsTable.comparatorProperty());

        productsTable.setItems(sortedData);
        productsTable.getColumns().addAll(Arrays.asList(nameCol, bruttoCol, categoryCol));
    }

    private void initOrderedProductColumns(ObservableList<ProductDTO> list){
        TableColumn<ProductDTO, String> nameCol = new TableColumn<>("Name");
        nameCol.setMinWidth(100);
        nameCol.setCellValueFactory(
            new PropertyValueFactory<ProductDTO, String>("name"));


        TableColumn<ProductDTO, Double> bruttoCol = new TableColumn<>("Brutto Price");
        bruttoCol.setMinWidth(200);
        bruttoCol.setCellValueFactory(
            new PropertyValueFactory<ProductDTO, Double>("bruttoprice"));

        TableColumn<ProductDTO, Integer> numberCol = new TableColumn<>("Count");
        numberCol.setMinWidth(200);
        numberCol.setCellValueFactory(
            new PropertyValueFactory<ProductDTO, Integer>("count"));

        orderedProductsTable.setItems(list);
        orderedProductsTable.getColumns().addAll(Arrays.asList(nameCol, bruttoCol, numberCol));
    }
}

