package at.ac.tuwien.sepm.assignment.individual.universe.domain.dto;

import javafx.beans.property.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderedDTO {

    private SimpleIntegerProperty id;
    private SimpleDoubleProperty total;
    private SimpleLongProperty ordertime;
    private SimpleIntegerProperty diningtable;
    private SimpleDoubleProperty nettoprice;
    private SimpleStringProperty paymenttype;
    private SimpleIntegerProperty orderstatus;
    private SimpleStringProperty createdStr;
    private SimpleIntegerProperty orderNumber;

    private List<ProductDTO> productList;

    public OrderedDTO() {
        this.id = new SimpleIntegerProperty();
        this.total = new SimpleDoubleProperty();
        this.ordertime = new SimpleLongProperty();
        this.diningtable = new SimpleIntegerProperty();
        this.nettoprice = new SimpleDoubleProperty();
        this.paymenttype = new SimpleStringProperty();
        this.orderstatus = new SimpleIntegerProperty();
        this.createdStr = new SimpleStringProperty();
        this.productList = new ArrayList<ProductDTO>();
        this.orderNumber = new SimpleIntegerProperty();
    }

    public int getOrderNumber() {
        return orderNumber.get();
    }

    public SimpleIntegerProperty orderNumberProperty() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber.set(orderNumber);
    }

    public List<ProductDTO> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductDTO> productList) {
        this.productList = productList;
    }

    public String getCreatedStr() {
        return createdStr.get();
    }

    public SimpleStringProperty createdStrProperty() {
        return createdStr;
    }

    public void setCreatedStr(String createdStr) {
        this.createdStr.set(createdStr);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public double getTotal() {
        return total.get();
    }

    public SimpleDoubleProperty totalProperty() {
        return total;
    }

    public void setTotal(double total) {
        this.total.set(total);
    }

    public long getOrdertime() {
        return ordertime.get();
    }

    public SimpleLongProperty ordertimeProperty() {
        return ordertime;
    }

    public void setOrdertime(long ordertime) {
        this.ordertime.set(ordertime);
    }

    public int getDiningtable() {
        return diningtable.get();
    }

    public SimpleIntegerProperty diningtableProperty() {
        return diningtable;
    }

    public void setDiningtable(int diningtable) {
        this.diningtable.set(diningtable);
    }

    public double getNettoprice() {
        return nettoprice.get();
    }

    public SimpleDoubleProperty nettopriceProperty() {
        return nettoprice;
    }

    public void setNettoprice(double nettoprice) {
        this.nettoprice.set(nettoprice);
    }

    public String getPaymenttype() {
        return paymenttype.get();
    }

    public SimpleStringProperty paymenttypeProperty() {
        return paymenttype;
    }

    public void setPaymenttype(String paymenttype) {
        this.paymenttype.set(paymenttype);
    }

    public int getOrderstatus() {
        return orderstatus.get();
    }

    public SimpleIntegerProperty orderstatusProperty() {
        return orderstatus;
    }

    public void setOrderstatus(int orderstatus) {
        this.orderstatus.set(orderstatus);
    }

}
