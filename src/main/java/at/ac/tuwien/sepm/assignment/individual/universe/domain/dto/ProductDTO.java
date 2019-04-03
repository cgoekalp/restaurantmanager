package at.ac.tuwien.sepm.assignment.individual.universe.domain.dto;

import javafx.beans.property.*;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProductDTO implements Serializable {

    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty description;
    private SimpleStringProperty category;
    private SimpleLongProperty created;
    private SimpleLongProperty updatetime;
    private SimpleDoubleProperty bruttoprice;
    private SimpleDoubleProperty nettoprice;
    private SimpleDoubleProperty tax;
    private SimpleBooleanProperty is_deleted;

    private SimpleStringProperty nettoMin;
    private SimpleStringProperty nettoMax;
    private SimpleStringProperty bruttoMin;
    private SimpleStringProperty bruttoMax;
    private SimpleIntegerProperty count;

    public ProductDTO() {

        this.name = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
        this.category = new SimpleStringProperty();
        this.id = new SimpleIntegerProperty();
        this.created = new SimpleLongProperty();
        this.updatetime = new SimpleLongProperty();
        this.bruttoprice = new SimpleDoubleProperty();
        this.nettoprice = new SimpleDoubleProperty();
        this.tax = new SimpleDoubleProperty();
        this.is_deleted = new SimpleBooleanProperty();

        this.nettoMin = new SimpleStringProperty();
        this.nettoMax = new SimpleStringProperty();
        this.bruttoMin = new SimpleStringProperty();
        this.bruttoMax = new SimpleStringProperty();
        this.count = new SimpleIntegerProperty();
    }


    public int getCount() {
        return count.get();
    }

    public SimpleIntegerProperty countProperty() {
        return count;
    }

    public void setCount(int count) {
        this.count.set(count);
    }

    public String getNettoMin() {
        return nettoMin.get();
    }

    public SimpleStringProperty nettoMinProperty() {
        return nettoMin;
    }

    public void setNettoMin(String nettoMin) {
        this.nettoMin.set(nettoMin);
    }

    public String getNettoMax() {
        return nettoMax.get();
    }

    public SimpleStringProperty nettoMaxProperty() {
        return nettoMax;
    }

    public void setNettoMax(String nettoMax) {
        this.nettoMax.set(nettoMax);
    }

    public String getBruttoMin() {
        return bruttoMin.get();
    }

    public SimpleStringProperty bruttoMinProperty() {
        return bruttoMin;
    }

    public void setBruttoMin(String bruttoMin) {
        this.bruttoMin.set(bruttoMin);
    }

    public String getBruttoMax() {
        return bruttoMax.get();
    }

    public SimpleStringProperty bruttoMaxProperty() {
        return bruttoMax;
    }

    public void setBruttoMax(String bruttoMax) {
        this.bruttoMax.set(bruttoMax);
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

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
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

    public long getCreated() {
        return created.get();
    }

    public SimpleLongProperty
    createdProperty() {
        return created;
    }

    public void setCreated(long created) {
        this.created.set(created);
    }

    public long getUpdatetime() {
        return updatetime.get();
    }

    public SimpleLongProperty updatetimeProperty() {
        return updatetime;
    }

    public void setUpdatetime(long updatetime) {
        this.updatetime.set(updatetime);
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

    public boolean isIs_deleted() {
        return is_deleted.get();
    }

    public SimpleBooleanProperty is_deletedProperty() {
        return is_deleted;
    }

    public void setIs_deleted(boolean is_deleted) {
        this.is_deleted.set(is_deleted);
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

    public String getCreatedString(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(getCreated());
        return sdf.format(date);
    }

    public String getUpdatedString(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(getUpdatetime());
        return sdf.format(date);
    }

}
