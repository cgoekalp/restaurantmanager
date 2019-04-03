package at.ac.tuwien.sepm.assignment.individual.universe.service;

import at.ac.tuwien.sepm.assignment.individual.universe.dao.DB.IProductDAO;
import at.ac.tuwien.sepm.assignment.individual.universe.dao.DB.JDBCProductDAO;
import at.ac.tuwien.sepm.assignment.individual.universe.domain.Product;
import at.ac.tuwien.sepm.assignment.individual.universe.domain.dto.OrderedDTO;
import at.ac.tuwien.sepm.assignment.individual.universe.domain.dto.ProductDTO;
import at.ac.tuwien.sepm.assignment.individual.universe.exception.DAOException;
import at.ac.tuwien.sepm.assignment.individual.universe.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.util.TransformUtility;
import at.ac.tuwien.sepm.assignment.individual.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ProductService implements IProductService{

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    private IProductDAO productDAO;

    public ProductService(){
        productDAO = new JDBCProductDAO();
    }

    @Override
    public ProductDTO create(ProductDTO productDTO) throws ServiceException {

        double brutto = 0;

        brutto = productDTO.getNettoprice() * (productDTO.getTax()/100.0) + productDTO.getNettoprice();

        productDTO.setBruttoprice(brutto);
        productDTO.setCreated(System.currentTimeMillis());
        productDTO.setUpdatetime(System.currentTimeMillis());

        try {
            int id = productDAO.create(TransformUtility.transformProductDTO(productDTO));
            return TransformUtility.transformProduct(productDAO.findProductById(id));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ProductDTO findProductById(Integer id) throws ServiceException {
        try {
            return TransformUtility.transformProduct(productDAO.findProductById(id));
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(ProductDTO productDTO) throws ServiceException {

        double brutto = 0;

        brutto = productDTO.getNettoprice() * (productDTO.getTax()/100.0) + productDTO.getNettoprice();

        productDTO.setBruttoprice(brutto);
        productDTO.setUpdatetime(System.currentTimeMillis());

        try {
            productDAO.update(TransformUtility.transformProductDTO(productDTO));
            return true;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean removeProductById(Integer id) throws ServiceException {
        try {
            return productDAO.removeProductById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ProductDTO> findAll() throws ServiceException {
        try {

            List<Product> allProducts = productDAO.findAll();
            List<ProductDTO> dtoList = TransformUtility.transformProductList(allProducts);

            return dtoList;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ProductDTO> search(ProductDTO productDTO) throws ServiceException {
        try {

            List<Product> allProducts = productDAO.search(
                Util.isEmpty(productDTO.getName()) ? null : productDTO.getName(),
                Util.isEmpty(productDTO.getCategory()) ? null : productDTO.getCategory(),
                Util.isEmpty(productDTO.getNettoMin()) ? null : Double.valueOf(productDTO.getNettoMin()),
                Util.isEmpty(productDTO.getNettoMax()) ? null : Double.valueOf(productDTO.getNettoMax()),
                Util.isEmpty(productDTO.getBruttoMin()) ? null : Double.valueOf(productDTO.getBruttoMin()),
                Util.isEmpty(productDTO.getBruttoMax()) ? null : Double.valueOf(productDTO.getBruttoMax()));

            List<ProductDTO> dtoList = TransformUtility.transformProductList(allProducts);

            return dtoList;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ProductDTO> findByOrdered(OrderedDTO orderedDTO) throws ServiceException {
        try {

            List<ProductDTO> allProducts = productDAO.findByOrdered(TransformUtility.transformOrderedDTO(orderedDTO));

            return allProducts;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void removeProductById(List<ProductDTO> productDTOList) throws ServiceException {
        for (ProductDTO productDTO : productDTOList){
            removeProductById(productDTO.getId());
        }
    }

}
