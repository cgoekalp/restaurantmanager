package at.ac.tuwien.sepm.assignment.individual.universe.dao.DB;

import at.ac.tuwien.sepm.assignment.individual.universe.domain.Ordered;
import at.ac.tuwien.sepm.assignment.individual.universe.exception.DAOException;

import java.util.List;

public interface IOrderedDAO {

    public Integer create(Ordered ordered) throws DAOException;

    public List<Ordered> findAll(int status) throws DAOException;

    public void update(Ordered ordered) throws DAOException;

    Ordered findOrderedById(Integer id) throws DAOException;

    void removeOrderedById(Integer id) throws DAOException;

    public List<Ordered> search(Ordered ordered) throws DAOException;

    boolean isTableFree(Integer tableNumber) throws DAOException;

    List<Ordered> findAllOrderedProducts() throws DAOException;;
}
