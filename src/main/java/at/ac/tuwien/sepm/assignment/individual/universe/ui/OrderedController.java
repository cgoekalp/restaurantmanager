package at.ac.tuwien.sepm.assignment.individual.universe.ui;

import at.ac.tuwien.sepm.assignment.individual.application.MainApplication;
import at.ac.tuwien.sepm.assignment.individual.universe.domain.OrderedProduct;
import at.ac.tuwien.sepm.assignment.individual.universe.domain.dto.OrderedDTO;
import at.ac.tuwien.sepm.assignment.individual.universe.domain.dto.ProductDTO;
import at.ac.tuwien.sepm.assignment.individual.universe.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.universe.service.*;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

public class OrderedController {

    private ObservableList<OrderedDTO> orderedData;
    private ObservableList<ProductDTO> orderedProductsData;
    private IOrderedService orderedService;
    private IProductService productService;

    private static final Logger log = LoggerFactory.getLogger(OrderedController.class);

    @FXML
    private Label totalBruttoLabel;

    @FXML
    private DatePicker datePickerFrom;

    @FXML
    private Button searchButton;

    @FXML
    private DatePicker datePickerTo;

    @FXML
    private TableView<ProductDTO> orderedProductsTable;

    @FXML
    private TableView<OrderedDTO> orderedTable;

    @FXML
    private ComboBox<String> paymentMethodCombobox;

    @FXML
    void initialize() {

        orderedService = new OrderedService();
        productService = new ProductService();

        initPaymentComboBox();
        try {
            orderedData = FXCollections.observableArrayList( orderedService.findAll(0) );
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        orderedProductsData = FXCollections.observableArrayList();
        createColumns(orderedData);
        initOrderedProductColumns(orderedProductsData);

        paymentMethodCombobox.getSelectionModel().select(0);

        addOrderedTableContextMenu();
    }

    private void initPaymentComboBox(){
        paymentMethodCombobox.getItems().add("");
        paymentMethodCombobox.getItems().add("Card");
        paymentMethodCombobox.getItems().add("Cash");
    }

    private void createColumns(ObservableList<OrderedDTO> tableData){

        TableColumn<OrderedDTO, String> createdCol = new TableColumn<>("Created");
        createdCol.setMinWidth(100);
        createdCol.setCellValueFactory(
            new PropertyValueFactory<OrderedDTO, String>("createdStr"));

        TableColumn<OrderedDTO, Integer> tableCol = new TableColumn<>("Table");
        tableCol.setMinWidth(100);
        tableCol.setCellValueFactory(
            new PropertyValueFactory<OrderedDTO, Integer>("diningtable"));

        orderedTable.setItems(tableData);
        orderedTable.getColumns().addAll(Arrays.asList(createdCol, tableCol));
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


    @FXML
    void createOrder(ActionEvent event) {
        handleNewEditOrdered(new OrderedDTO());
    }

    private void handleNewEditOrdered(OrderedDTO item) {

        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(MainApplication.getPrimaryStage());

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/orderedCreateEdit.fxml"));

        OrderedCreateEditController orderedCreateEditController = new OrderedCreateEditController();
        loader.setController(orderedCreateEditController);

        try {

            AnchorPane orders = (AnchorPane) loader.load();
            AnchorPane.setTopAnchor(orders, 0.0);
            AnchorPane.setLeftAnchor(orders, 0.0);
            AnchorPane.setBottomAnchor(orders, 0.0);
            AnchorPane.setRightAnchor(orders, 0.0);

            orderedCreateEditController.setOrderedDTO(item);

            Scene dialogScene = new Scene(orders, 1100, 600);
            dialog.setScene(dialogScene);
            dialog.setResizable(false);
            dialog.show();

            dialog.setOnHiding(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    log.info("Stage Dialog is closing");
                    refreshData();
                }
            });

            orderedCreateEditController.setDialog(dialog);

        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("Selected product new/edit");
    }

    private void addOrderedTableContextMenu(){
        orderedTable.setRowFactory(new Callback<TableView<OrderedDTO>, TableRow<OrderedDTO>>() {
            @Override
            public TableRow<OrderedDTO> call(TableView<OrderedDTO> tableView) {
                final TableRow<OrderedDTO> row = new TableRow<>();
                final ContextMenu contextMenu = new ContextMenu();
                final MenuItem deleteMenuItem = new MenuItem("Cancel");
                deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        log.info("Selected order dto");
                        handleRemoveOrdered(row.getItem());
                    }
                });

                final MenuItem editMenuItem = new MenuItem("Edit ");
                editMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        handleNewEditOrdered(row.getItem());
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
                    handleOrderedDetails(row.getItem());
                });

                return row ;
            }
        });
    }

    private void handleOrderedDetails(OrderedDTO item) {
        if(item == null){
            return;
        }
        orderedProductsData.clear();
        try {
            List<ProductDTO> productDTOList = productService.findByOrdered(item);
            orderedProductsData.addAll(productDTOList);
            totalBruttoLabel.setText(String.valueOf(orderedService.calculateBrutto(productDTOList)));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    private void handleRemoveOrdered(OrderedDTO item) {

        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.WARNING,
            "Are you sure you want to cancel the selected order? " + paymentMethodCombobox.getSelectionModel().getSelectedItem(),
            yes,
            no);

        alert.setTitle("Confirm Cancel");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.orElse(no) == yes) {
            try {
                orderedService.cancelOrder(item);
                refreshData();
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void refreshAll(ActionEvent event) {
        refreshData();
    }

    private void refreshData() {
        orderedData.clear();
        orderedProductsData.clear();
        paymentMethodCombobox.getSelectionModel().select(0);
        totalBruttoLabel.setText("");
        try {
            orderedData.addAll(orderedService.findAll(0));
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

    @FXML
    void payOrder(ActionEvent event) {
        if(orderedTable.getSelectionModel().getSelectedItem() != null){
            OrderedDTO selected = orderedTable.getSelectionModel().getSelectedItem();

            if(paymentMethodCombobox.getSelectionModel().getSelectedIndex() == 0){
                disyplayAlert("Payment Type", "Payment Type is missing", "Please select Cash or Card");
                return;
            }

            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.WARNING,
                "Proceed with payment? Payment type " + paymentMethodCombobox.getSelectionModel().getSelectedItem(),
                yes,
                no);

            alert.setTitle("Confirm Order");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.orElse(no) == yes) {
                try {
                    selected.setOrderstatus(1);
                    selected.setPaymenttype(paymentMethodCombobox.getSelectionModel().getSelectedItem());
                    selected.getProductList().clear();
                    selected.getProductList().addAll(productService.findByOrdered(selected));
                    orderedService.update(selected);
                    refreshData();
                } catch (ServiceException e) {
                    e.printStackTrace();
                }
            }
        }else {
            disyplayAlert("Order Confirm", "No Order selected", "Please select an Order from the table");
            return;
        }
    }
}
