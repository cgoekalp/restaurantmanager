package at.ac.tuwien.sepm.assignment.individual.universe.service;

import at.ac.tuwien.sepm.assignment.individual.universe.domain.Ordered;
import at.ac.tuwien.sepm.assignment.individual.universe.domain.dto.OrderedDTO;
import at.ac.tuwien.sepm.assignment.individual.universe.domain.dto.ProductDTO;
import at.ac.tuwien.sepm.assignment.individual.universe.exception.ServiceException;

import java.util.Date;
import java.util.List;

public interface IOrderedService {

    OrderedDTO create(OrderedDTO var1) throws ServiceException;

    List<OrderedDTO> findAll(int status) throws ServiceException;

    void deleteOrderedById(Integer id) throws ServiceException;

    public Double calculateBrutto(OrderedDTO orderedDTO)throws ServiceException;

    void update(OrderedDTO orderedDTO) throws ServiceException;

    void cancelOrder(OrderedDTO orderedDTO) throws ServiceException;

    public boolean isTableFree(Integer tableNumber) throws ServiceException;

    public Double calculateBrutto(List<ProductDTO> productDTOList) throws ServiceException;

    public List<OrderedDTO> findAllOrderedProducts() throws ServiceException;
}
