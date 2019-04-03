package at.ac.tuwien.sepm.assignment.individual.universe.ui;

import at.ac.tuwien.sepm.assignment.individual.universe.domain.dto.OrderedDTO;
import at.ac.tuwien.sepm.assignment.individual.universe.domain.dto.OrderedProductDTO;
import at.ac.tuwien.sepm.assignment.individual.universe.domain.dto.ProductDTO;
import at.ac.tuwien.sepm.assignment.individual.universe.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.universe.service.*;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.stream.Collectors;

public class InvoicesController {

    private ObservableList<OrderedDTO> orderedData;
    private ObservableList<OrderedProductDTO> orderedProductsData;
    private IOrderedService orderedService;
    private IOrderedProductService orderedProductService;


    @FXML
    private Label netto20Label;

    @FXML
    private Label brutto10Label;

    @FXML
    private TableView<OrderedProductDTO> orderedProductsTable;

    @FXML
    private Label orderNumberLabel;

    @FXML
    private Label paidOnLabel;

    @FXML
    private Label netto10Label;

    @FXML
    private Label tableNumberLabel;

    @FXML
    private Label tax10Label;

    @FXML
    private Label tax20Label;

    @FXML
    private Label bruttoTotalLabel;

    @FXML
    private TableView<OrderedDTO> orderedTable;

    @FXML
    private Label createdOnLabel;

    @FXML
    private Label brutto20Label;

    @FXML Label paymentTypeLabel;

    private static final Logger log = LoggerFactory.getLogger(InvoicesController.class);

    @FXML
    void initialize() {

        orderedService = new OrderedService();
        orderedProductService = new OrderedProductService();

        try {
            orderedData = FXCollections.observableArrayList( orderedService.findAllOrderedProducts() );
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        orderedProductsData = FXCollections.observableArrayList();
        createColumns(orderedData);
        addOrderedTableContextMenu();
        initOrderedProductColumns(orderedProductsData);
    }

    private void initOrderedProductColumns(ObservableList<OrderedProductDTO> list){
        TableColumn<OrderedProductDTO, String> nameCol = new TableColumn<>("Name");
        nameCol.setMinWidth(100);
        nameCol.setCellValueFactory(
            new PropertyValueFactory<OrderedProductDTO, String>("name"));


        TableColumn<OrderedProductDTO, Double> bruttoCol = new TableColumn<>("Brutto Price");
        bruttoCol.setMinWidth(200);
        bruttoCol.setCellValueFactory(
            new PropertyValueFactory<OrderedProductDTO, Double>("bruttoprice"));

        TableColumn<OrderedProductDTO, Integer> numberCol = new TableColumn<>("Count");
        numberCol.setMinWidth(200);
        numberCol.setCellValueFactory(
            new PropertyValueFactory<OrderedProductDTO, Integer>("productnumber"));

        orderedProductsTable.setItems(list);
        orderedProductsTable.getColumns().addAll(Arrays.asList(nameCol, bruttoCol, numberCol));
    }

    private void addOrderedTableContextMenu(){
        orderedTable.setRowFactory(new Callback<TableView<OrderedDTO>, TableRow<OrderedDTO>>() {
            @Override
            public TableRow<OrderedDTO> call(TableView<OrderedDTO> tableView) {
                final TableRow<OrderedDTO> row = new TableRow<>();
       
                row.setOnMouseClicked(event -> {
                    handleOrderedDetails(row.getItem());
                });

                return row ;
            }
        });
    }


    @FXML
    void refresh(ActionEvent event) {
        try {
            orderedProductsData.clear();
            orderedData.clear();
            orderedData.addAll(orderedService.findAllOrderedProducts());
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }


    private void handleOrderedDetails(OrderedDTO item) {

        if(item == null){
            return;
        }
        try {
            log.info("loded order products");

            orderedProductsData.clear();
            orderedProductsData.addAll(orderedProductService.findByOrdered(item));

            orderNumberLabel.setText(String.valueOf(item.getOrderNumber()));
            tableNumberLabel.setText(String.valueOf(item.getDiningtable()));
            paidOnLabel.setText(item.getCreatedStr());
            bruttoTotalLabel.setText(String.valueOf(item.getTotal()));
            paymentTypeLabel.setText(item.getPaymenttype());

            OrderedDTO tmp = orderedService.findAll(1).stream().filter(order -> order.getId() == item.getId()).findFirst().get();    // we dont like mkyong

            createdOnLabel.setText(tmp.getCreatedStr());

            double netto10 = 0;
            double netto20 = 0;
            double brutto10 = 0;
            double brutto20 = 0;

            for(OrderedProductDTO orderedProductDTO : orderedProductService.findByOrdered(item)){
                if(orderedProductDTO.getTax() == 10.0){
                    netto10+= orderedProductDTO.getNettoprice() * orderedProductDTO.getProductnumber();
                    brutto10+= orderedProductDTO.getBruttoprice() * orderedProductDTO.getProductnumber();
                }

                if(orderedProductDTO.getTax() == 20.0){
                    netto20+= orderedProductDTO.getNettoprice() * orderedProductDTO.getProductnumber();
                    brutto20+= orderedProductDTO.getBruttoprice() * orderedProductDTO.getProductnumber();
                }
            }

            netto10Label.setText(String.valueOf(netto10));
            netto20Label.setText(String.valueOf(netto20));

            brutto10Label.setText(String.valueOf(brutto10));
            brutto20Label.setText(String.valueOf(brutto20));

            tax10Label.setText(String.valueOf(brutto10 - netto10));
            tax20Label.setText(String.valueOf(brutto20 - netto20));

        } catch (ServiceException e) {
            e.printStackTrace();
        }

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

}
