package at.ac.tuwien.sepm.assignment.individual.universe.dao.DB;

import at.ac.tuwien.sepm.assignment.individual.universe.domain.Ordered;
import at.ac.tuwien.sepm.assignment.individual.universe.domain.Product;
import at.ac.tuwien.sepm.assignment.individual.universe.domain.dto.ProductDTO;
import at.ac.tuwien.sepm.assignment.individual.universe.exception.DAOException;

import java.util.List;

public interface IProductDAO {

    public Integer create (Product product) throws DAOException;

    /**
     * Update Box-data
     * @param product object to update which contains the changed data
     * @return true if update was successful
     * @throws DAOException if there was an error with the data-source
     */

    public void update(Product product) throws DAOException;
    /**
     * Find Box with given Id
     * @param id the given Id
     * @return  Product Object with matching Id
     * @throws DAOException if there was an error while retrieving the data from the data-source
     */
    Product findProductById(Integer id) throws DAOException;


    /**
     * Deletes the given box id by marking it as deleted, not deleting completely.
     * @param id to mark as deleted
     * @throws DAOException if there was an error with the data-source
     */
    public boolean removeProductById(Integer id) throws DAOException;


    public List<Product> search(Product product) throws DAOException;

    public List<Product> search(String name, String category, Double minNetto, Double maxNetto, Double minBrutto, Double maxBrutto) throws DAOException;

    public List<Product> findAll() throws DAOException;

    List<ProductDTO> findByOrdered(Ordered transformOrdered)throws DAOException;
}
