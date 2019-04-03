package at.ac.tuwien.sepm.assignment.individual.util;

import at.ac.tuwien.sepm.assignment.individual.universe.domain.Ordered;
import at.ac.tuwien.sepm.assignment.individual.universe.domain.OrderedProduct;
import at.ac.tuwien.sepm.assignment.individual.universe.domain.Product;
import at.ac.tuwien.sepm.assignment.individual.universe.domain.dto.OrderedDTO;
import at.ac.tuwien.sepm.assignment.individual.universe.domain.dto.OrderedProductDTO;
import at.ac.tuwien.sepm.assignment.individual.universe.domain.dto.ProductDTO;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransformUtility {

    //Transper fron Prdouct to DTO Variable


    public static Product transformProductDTO(ProductDTO productDTO) {

        Product product = new Product();

        product.setName(Util.isEmpty(productDTO.getName()) ? null : productDTO.getName());
        product.setBruttoprice(Util.isEmpty(productDTO.getBruttoprice()) ? null : productDTO.getBruttoprice());
        product.setCategory(Util.isEmpty(productDTO.getCategory()) ? null : productDTO.getCategory());
        product.setCreated(new Timestamp(productDTO.getCreated()));
        product.setDescription(productDTO.getDescription());
        product.setUpdatetime(new Timestamp(productDTO.getUpdatetime()));
        product.setNettoprice(Util.isEmpty(productDTO.getNettoprice()) ? null : productDTO.getNettoprice());
        product.setId(productDTO.getId());
        product.setTax(Util.isEmpty(productDTO.getTax()) ? null : productDTO.getTax());
        product.setDeleted(productDTO.isIs_deleted());

        return product;
    }

    public static ProductDTO transformProduct(Product product) {
        ProductDTO productDTO = new ProductDTO();

        productDTO.setName(product.getName());
        productDTO.setBruttoprice(product.getBruttoprice());
        productDTO.setCategory(product.getCategory());
        productDTO.setCreated(product.getCreated().getTime());
        productDTO.setDescription(product.getDescription());
        productDTO.setUpdatetime(product.getUpdatetime().getTime());
        productDTO.setNettoprice(product.getNettoprice());
        productDTO.setId(product.getId());
        productDTO.setTax(product.getTax());
        productDTO.setIs_deleted(product.getDeleted());

        return productDTO;
    }

    public static List<ProductDTO> transformProductList(List<Product> products) {

        List<ProductDTO> dtoList = new ArrayList<>();
        for (Product product : products) {
            dtoList.add(transformProduct(product));
        }
        return dtoList;
    }

    public static List<Product> transformProductDTOList(List<ProductDTO> productDTOs) {
        List<Product> productList = new ArrayList<>();
        for (ProductDTO productDTO : productDTOs) {
            productList.add(transformProductDTO(productDTO));
        }
        return productList;
    }

    public static Ordered transformOrderedDTO(OrderedDTO orderedDTO) {

        Ordered ordered = new Ordered();

        ordered.setId(orderedDTO.getId());
        ordered.setTotal(orderedDTO.getTotal());
        ordered.setDiningtable(orderedDTO.getDiningtable());
        ordered.setOrderstatus(orderedDTO.getOrderstatus());
        ordered.setPaymenttype(orderedDTO.getPaymenttype());
        ordered.setOrdertime(new Timestamp(orderedDTO.getOrdertime()));
        ordered.setOrderNumber(orderedDTO.getOrderNumber());

        return ordered;
    }

    public static OrderedDTO transformOrdered(Ordered ordered) {

        OrderedDTO orderedDTO = new OrderedDTO();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        orderedDTO.setId(ordered.getId());
        orderedDTO.setTotal(ordered.getTotal());
        orderedDTO.setDiningtable(ordered.getDiningtable());
        orderedDTO.setOrderstatus(ordered.getOrderstatus());
        orderedDTO.setPaymenttype(ordered.getPaymenttype());
        orderedDTO.setOrdertime(ordered.getOrdertime().getTime());
        orderedDTO.setCreatedStr(sdf.format(new Date(ordered.getOrdertime().getTime())));
        orderedDTO.setOrderNumber(ordered.getOrderNumber());

        return orderedDTO;
    }

    public static List<OrderedDTO> transformOrderedList(List<Ordered> orderedList) {

        List<OrderedDTO> dtoList = new ArrayList<>();
        for (Ordered ordered : orderedList) {
            dtoList.add(transformOrdered(ordered));
        }
        return dtoList;
    }

    public static List<Ordered> transformOrderedDTOList(List<OrderedDTO> orderedDTOs) {
        List<Ordered> orderedList = new ArrayList<>();
        for (OrderedDTO orderedDTO : orderedDTOs) {
            orderedList.add(transformOrderedDTO(orderedDTO));
        }
        return orderedList;
    }

    public static OrderedProductDTO transformOrderedProduct(OrderedProduct orderedProduct){

        OrderedProductDTO productDTO = new OrderedProductDTO();

        productDTO.setName(orderedProduct.getName());
        productDTO.setBruttoprice(orderedProduct.getBruttoprice());
        productDTO.setCategory(orderedProduct.getCategory());
        productDTO.setNettoprice(orderedProduct.getNettoprice());
        productDTO.setId(orderedProduct.getId());
        productDTO.setTax(orderedProduct.getTax());
        productDTO.setOid(orderedProduct.getOid());
        productDTO.setPid(orderedProduct.getPid());
        productDTO.setInvoice(orderedProduct.getInvoice().getTime());
        productDTO.setProductnumber(orderedProduct.getProductnumber());

        return productDTO;
    }

    public static List<OrderedProductDTO> transformOrderedProduct(List<OrderedProduct> result) {
        List<OrderedProductDTO> orderedList = new ArrayList<>();
        for (OrderedProduct orderedProduct : result) {
            orderedList.add(transformOrderedProduct(orderedProduct));
        }
        return orderedList;
    }
}
