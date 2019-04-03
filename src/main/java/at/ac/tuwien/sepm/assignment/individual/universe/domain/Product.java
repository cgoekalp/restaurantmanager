package at.ac.tuwien.sepm.assignment.individual.universe.domain;

import java.sql.Timestamp;

public class Product {

        private Integer id;
        private String name;
        private String description;
        private String category;
        private Timestamp created;
        private Timestamp updatetime;
        private Double bruttoprice;
        private Double nettoprice;
        private Double tax;
        private Boolean deleted;

        public Product() {
        }

        public Product(Integer id, String name, String description, String category, Timestamp created, Timestamp updatetime, Double bruttoprice, Double tax) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.created = created;
        this.updatetime = updatetime;
        this.bruttoprice = bruttoprice;
        this.tax = tax;
    }

    public Double getNettoprice() {
        return nettoprice;
    }

    public void setNettoprice(Double nettoprice) {
        this.nettoprice = nettoprice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Timestamp updatetime) {
        this.updatetime = updatetime;
    }

    public Double getBruttoprice() {
        return bruttoprice;
    }

    public void setBruttoprice(Double bruttoprice) {
        this.bruttoprice = bruttoprice;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public void setDeleted(Boolean deleted){ this.deleted = deleted; }

    public Boolean getDeleted() { return deleted; }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", bruttoprice=" + bruttoprice + '\'' +
            ", description=" + description + '\'' +
            ", created=" + created + '\'' +
            ", updatetime=" + updatetime + '\'' +
            ", category=" + category + '\'' +
            ", tax=" + tax + '\'' +
            '}';
    }


}
