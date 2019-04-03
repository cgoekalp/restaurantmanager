package at.ac.tuwien.sepm.assignment.individual.universe.domain.dto;

import javafx.beans.property.*;

public class OrderedProductDTO {

    private SimpleIntegerProperty id;
    private SimpleIntegerProperty pid;
    private SimpleIntegerProperty oid;
    private SimpleStringProperty name;
    private SimpleStringProperty category;
    private SimpleLongProperty invoice;
    private SimpleDoubleProperty bruttoprice;
    private SimpleDoubleProperty nettoprice;
    private SimpleDoubleProperty tax;
    private SimpleIntegerProperty productnumber;

    public OrderedProductDTO(){

        this.name = new SimpleStringProperty();
        this.category = new SimpleStringProperty();
        this.id = new SimpleIntegerProperty();
        this.bruttoprice = new SimpleDoubleProperty();
        this.nettoprice = new SimpleDoubleProperty();
        this.tax = new SimpleDoubleProperty();

        this.pid = new SimpleIntegerProperty();
        this.oid = new SimpleIntegerProperty();
        this.invoice = new SimpleLongProperty();
        this.productnumber = new SimpleIntegerProperty();

    }

    public int getPid() {
        return pid.get();
    }

    public SimpleIntegerProperty pidProperty() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid.set(pid);
    }

    public int getOid() {
        return oid.get();
    }

    public SimpleIntegerProperty oidProperty() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid.set(oid);
    }

    public long getInvoice() {
        return invoice.get();
    }

    public SimpleLongProperty invoiceProperty() {
        return invoice;
    }

    public void setInvoice(long invoice) {
        this.invoice.set(invoice);
    }

    public int getProductnumber() {
        return productnumber.get();
    }

    public SimpleIntegerProperty productnumberProperty() {
        return productnumber;
    }

    public void setProductnumber(int productnumber) {
        this.productnumber.set(productnumber);
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

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getCategory() {
        return category.get();
    }

    public SimpleStringProperty categoryProperty() {
        return category;
    }

    public void setCategory(String category) {
        this.category.set(category);
    }

    public double getBruttoprice() {
        return bruttoprice.get();
    }

    public SimpleDoubleProperty bruttopriceProperty() {
        return bruttoprice;
    }

    public void setBruttoprice(double bruttoprice) {
        this.bruttoprice.set(bruttoprice);
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

    public double getTax() {
        return tax.get();
    }

    public SimpleDoubleProperty taxProperty() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax.set(tax);
    }

}
