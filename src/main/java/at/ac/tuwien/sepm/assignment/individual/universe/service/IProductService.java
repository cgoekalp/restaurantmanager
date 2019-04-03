package at.ac.tuwien.sepm.assignment.individual.universe.service;

import at.ac.tuwien.sepm.assignment.individual.universe.domain.Product;
import at.ac.tuwien.sepm.assignment.individual.universe.domain.dto.OrderedDTO;
import at.ac.tuwien.sepm.assignment.individual.universe.domain.dto.ProductDTO;
import at.ac.tuwien.sepm.assignment.individual.universe.exception.DAOException;
import at.ac.tuwien.sepm.assignment.individual.universe.exception.ServiceException;

import java.util.Date;
import java.util.List;

public interface IProductService {

    ProductDTO create(ProductDTO product) throws ServiceException;
    ProductDTO findProductById(Integer id) throws ServiceException;
    boolean update(ProductDTO productDTO) throws ServiceException;
    boolean removeProductById(Integer id) throws ServiceException;

    public List<ProductDTO> findAll() throws ServiceException;

    public List<ProductDTO> search(ProductDTO productDTO)  throws ServiceException;

    public List<ProductDTO> findByOrdered(OrderedDTO orderedDTO)  throws ServiceException;

    void removeProductById(List<ProductDTO> productDTOList)throws ServiceException;

}
