package at.ac.tuwien.sepm.assignment.individual.universe.domain;

import java.sql.Timestamp;

public class Ordered {

    Integer id;
    Double total;
    Timestamp ordertime;
    Integer diningtable;
    String paymenttype;
    Integer orderstatus;
    Integer orderNumber;

    public Ordered(){

    }

    public Ordered(Integer id, Double total, Timestamp ordertime, Integer diningtable, String paymenttype, Integer orderstatus) {
        this.id = id;
        this.total = total;
        this.ordertime = ordertime;
        this.diningtable = diningtable;
        this.paymenttype = paymenttype;
        this.orderstatus = orderstatus;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Timestamp getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(Timestamp ordertime) {
        this.ordertime = ordertime;
    }

    public Integer getDiningtable() {
        return diningtable;
    }

    public void setDiningtable(Integer diningtable) {
        this.diningtable = diningtable;
    }

    public String getPaymenttype() {
        return paymenttype;
    }

    public void setPaymenttype(String paymenttype) {
        this.paymenttype = paymenttype;
    }

    public Integer getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(Integer orderstatus) {
        this.orderstatus = orderstatus;
    }

    @Override
    public String toString() {
        return "Ordered{" +
            "id=" + id +
            ", Total='" + total + '\'' +
            ", ordertime=" + ordertime + '\'' +
            ", Diningtable=" + diningtable + '\'' +
            ", Paymenttype=" + paymenttype + '\'' +
            ", orderstatus=" + orderstatus + '\'' +
            '}';
    }
}
