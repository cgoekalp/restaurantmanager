package at.ac.tuwien.sepm.assignment.individual.universe.dao.DB;

import at.ac.tuwien.sepm.assignment.individual.universe.domain.Ordered;
import at.ac.tuwien.sepm.assignment.individual.universe.domain.OrderedProduct;
import at.ac.tuwien.sepm.assignment.individual.universe.domain.Product;
import at.ac.tuwien.sepm.assignment.individual.universe.domain.dto.ProductDTO;
import at.ac.tuwien.sepm.assignment.individual.universe.exception.DAOException;

import java.util.List;

public interface IOrderedProductDAO {

    public List<OrderedProduct> findAll() throws DAOException;

    public List<OrderedProduct> findByOrdered(Ordered ordered) throws DAOException;

    public List<OrderedProduct> create(Ordered ordered, List<ProductDTO> products) throws DAOException;

    public void deleteByOrdered(Ordered ordered) throws DAOException;

}
