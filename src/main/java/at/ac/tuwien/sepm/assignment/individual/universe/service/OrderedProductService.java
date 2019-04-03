package at.ac.tuwien.sepm.assignment.individual.universe.service;

import at.ac.tuwien.sepm.assignment.individual.universe.dao.DB.IOrderedProductDAO;
import at.ac.tuwien.sepm.assignment.individual.universe.dao.DB.JDBCOrderedProductDAO;
import at.ac.tuwien.sepm.assignment.individual.universe.domain.Ordered;
import at.ac.tuwien.sepm.assignment.individual.universe.domain.OrderedProduct;
import at.ac.tuwien.sepm.assignment.individual.universe.domain.dto.OrderedDTO;
import at.ac.tuwien.sepm.assignment.individual.universe.domain.dto.OrderedProductDTO;
import at.ac.tuwien.sepm.assignment.individual.universe.domain.dto.ProductDTO;
import at.ac.tuwien.sepm.assignment.individual.universe.exception.DAOException;
import at.ac.tuwien.sepm.assignment.individual.universe.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.util.TransformUtility;

import java.util.List;

public class OrderedProductService implements IOrderedProductService {

    private IOrderedProductDAO orderedProductDAO;

    public OrderedProductService(){
        orderedProductDAO = new JDBCOrderedProductDAO();
    }

    @Override
    public List<OrderedProductDTO> findByOrdered(OrderedDTO item) throws ServiceException {

        try {
            List<OrderedProduct> result = orderedProductDAO.findByOrdered(TransformUtility.transformOrderedDTO(item));
            return TransformUtility.transformOrderedProduct(result);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(Ordered ordered, List<ProductDTO> productDTOList) throws ServiceException {

    }
}
