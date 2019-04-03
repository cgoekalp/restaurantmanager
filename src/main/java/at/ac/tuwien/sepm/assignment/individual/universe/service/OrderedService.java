package at.ac.tuwien.sepm.assignment.individual.universe.service;

import at.ac.tuwien.sepm.assignment.individual.universe.dao.DB.IOrderedDAO;
import at.ac.tuwien.sepm.assignment.individual.universe.dao.DB.IOrderedProductDAO;
import at.ac.tuwien.sepm.assignment.individual.universe.dao.DB.JDBCOrderedDAO;
import at.ac.tuwien.sepm.assignment.individual.universe.dao.DB.JDBCOrderedProductDAO;
import at.ac.tuwien.sepm.assignment.individual.universe.domain.Ordered;
import at.ac.tuwien.sepm.assignment.individual.universe.domain.dto.OrderedDTO;
import at.ac.tuwien.sepm.assignment.individual.universe.domain.dto.ProductDTO;
import at.ac.tuwien.sepm.assignment.individual.universe.exception.DAOException;
import at.ac.tuwien.sepm.assignment.individual.universe.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.util.TransformUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class OrderedService implements IOrderedService {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private IOrderedDAO orderedDAO;
    private IOrderedProductDAO orderedProductDAO;

    public OrderedService(){
        orderedDAO = new JDBCOrderedDAO();
        orderedProductDAO = new JDBCOrderedProductDAO();
    }

    @Override
    public OrderedDTO create(OrderedDTO orderedDTO) throws ServiceException {

        Ordered ordered = TransformUtility.transformOrderedDTO(orderedDTO);
        ordered.setOrdertime(new Timestamp(System.currentTimeMillis()));
        ordered.setOrderstatus(0);

        try {

            int id = orderedDAO.create(ordered);
            ordered = orderedDAO.findOrderedById(id);

            //SAVE PRODUCTS
            orderedProductDAO.create(ordered, orderedDTO.getProductList());

            return TransformUtility.transformOrdered(ordered);

        } catch (DAOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void update(OrderedDTO orderedDTO) throws ServiceException {

        Ordered ordered = TransformUtility.transformOrderedDTO(orderedDTO);

        try {

            orderedDAO.update(ordered);
            orderedProductDAO.deleteByOrdered(ordered);
            //SAVE PRODUCTS
            orderedProductDAO.create(ordered, orderedDTO.getProductList());
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public void cancelOrder(OrderedDTO orderedDTO) throws ServiceException {
            Ordered ordered = TransformUtility.transformOrderedDTO(orderedDTO);

        try {
            ordered.setOrderstatus(-1);
            orderedDAO.update(ordered);
            orderedProductDAO.deleteByOrdered(ordered);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean isTableFree(Integer tableNumber) throws ServiceException {
        try {
            return orderedDAO.isTableFree(tableNumber);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Double calculateBrutto(List<ProductDTO> productDTOList) {
        double brutto = 0;

        for(ProductDTO productDTO : productDTOList){
            brutto += productDTO.getBruttoprice() * productDTO.getCount();
        }
        // round to 2 decimals
        brutto = Math.round(brutto * 100.0) / 100.0;
        return brutto;
    }

    @Override
    public List<OrderedDTO> findAllOrderedProducts() throws ServiceException {
        List<Ordered> orderedList = null;
        try {
            orderedList = orderedDAO.findAllOrderedProducts();
        } catch (DAOException e) {
            e.printStackTrace();
        }

        return TransformUtility.transformOrderedList(orderedList);
    }

    @Override
    public List<OrderedDTO> findAll(int status) throws ServiceException {

        List<Ordered> orderedList = null;
        try {
            orderedList = orderedDAO.findAll(status);
        } catch (DAOException e) {
            e.printStackTrace();
        }

        return TransformUtility.transformOrderedList(orderedList);
    }


    @Override
    public void deleteOrderedById(Integer id) throws ServiceException {
            try {
                this.orderedDAO.removeOrderedById(id);
            } catch (DAOException e) {
                throw new ServiceException(e.getMessage());
            }
    }

    @Override
    public Double calculateBrutto(OrderedDTO orderedDTO) throws ServiceException {

        double brutto = 0;

        for(ProductDTO productDTO : orderedDTO.getProductList()){
            brutto += productDTO.getBruttoprice() * productDTO.getCount();
        }
        // round to 2 decimals
        brutto = Math.round(brutto * 100.0) / 100.0;
        return brutto;
    }


}
