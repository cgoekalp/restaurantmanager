package at.ac.tuwien.sepm.assignment.individual.universe.service;

import at.ac.tuwien.sepm.assignment.individual.universe.domain.Ordered;
import at.ac.tuwien.sepm.assignment.individual.universe.domain.OrderedProduct;
import at.ac.tuwien.sepm.assignment.individual.universe.domain.dto.OrderedDTO;
import at.ac.tuwien.sepm.assignment.individual.universe.domain.dto.OrderedProductDTO;
import at.ac.tuwien.sepm.assignment.individual.universe.domain.dto.ProductDTO;
import at.ac.tuwien.sepm.assignment.individual.universe.exception.ServiceException;

import java.util.List;

public interface IOrderedProductService {

    public List<OrderedProductDTO> findByOrdered(OrderedDTO item) throws ServiceException;

    public void update(Ordered ordered, List<ProductDTO> productDTOList) throws ServiceException;
}
