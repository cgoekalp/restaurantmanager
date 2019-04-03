package at.ac.tuwien.sepm.assignment.individual.universe.domain;

import java.sql.Timestamp;

public class OrderedProduct {

    private Integer id;
    private Integer pid;
    private Integer oid;
    private Timestamp invoice;
    private String name;
    private Double bruttoprice;
    private Double nettoprice;
    private String category;
    private Integer productnumber;
    private Double tax;

    public OrderedProduct(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public Timestamp getInvoice() {
        return invoice;
    }

    public void setInvoice(Timestamp invoice) {
        this.invoice = invoice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getBruttoprice() {
        return bruttoprice;
    }

    public void setBruttoprice(Double bruttoprice) {
        this.bruttoprice = bruttoprice;
    }

    public Double getNettoprice() {
        return nettoprice;
    }

    public void setNettoprice(Double nettoprice) {
        this.nettoprice = nettoprice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getProductnumber() {
        return productnumber;
    }

    public void setProductnumber(Integer productnumber) {
        this.productnumber = productnumber;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public String toString() {
        return "OrderedProduct{" +
            "id=" + id +
            "pid=" + pid +
            "oid=" + oid +
            ", productnr=" + productnumber + '\'' +
            '}';
    }

}
