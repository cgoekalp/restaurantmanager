package at.ac.tuwien.sepm.assignment.individual.dao;

import at.ac.tuwien.sepm.assignment.individual.universe.dao.DB.IOrderedDAO;
import at.ac.tuwien.sepm.assignment.individual.universe.dao.DB.JDBCOrderedDAO;
import at.ac.tuwien.sepm.assignment.individual.universe.domain.Ordered;
import at.ac.tuwien.sepm.assignment.individual.universe.exception.DAOException;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class JDBCOrderedDAOTest {

    private IOrderedDAO orderedDAO = new JDBCOrderedDAO();

    /*
     *test bir obje olusturuyor, onu test ediyor..
     */

    @Before
    public void init() throws DAOException {

    }


    /**
     * 2.version
     * Dieser Test versucht einen ungueltigen Wert (NULL - Wert ) in die
     * Datenbank zu speichern. Das DAO sollte eine Exception werfen.
     * @throws DAOException
     **/
    @Test(expected = IllegalArgumentException.class)
    public void createWithNullShouldThrowException() throws DAOException{
        this.orderedDAO.create(null);
    }


        /**
         * Dieser Test sucht nach einem Objekt des Typs Ordered mit
         * einer ungueltigen ID.
         *
         * **/
    @Test
    public void findWithNotExistingIdToNull() throws DAOException {
        int i = 999999;
        Ordered ordered = this.orderedDAO.findOrderedById(Integer.valueOf(i));
        assertTrue(ordered ==null);

    }

    @Test
    public void testCreate() throws DAOException{
        Ordered or = new Ordered();
        or.setTotal(2.0);
        or.setOrdertime(new Timestamp(System.currentTimeMillis()));
        or.setDiningtable(1);
        or.setPaymenttype("fer");
        or.setOrderstatus(1);
        or.setOrderNumber(1000);

        int id = orderedDAO.create(or);

        assertTrue(orderedDAO.findOrderedById(id)!= null);
    }

    /*
     *Search the Product with the id,
     *first I give every Parameter then i create id
     * make a list
     *boolean exist at begin false if there is an another product with same id
     * then return exist true
     * */

    @Test
    public void findOrderedById() throws DAOException {
        Ordered or = new Ordered();

        or.setTotal(2.0);
        or.setOrdertime(new Timestamp(System.currentTimeMillis()));
        or.setDiningtable(1);
        or.setPaymenttype("fer");
        or.setOrderstatus(1);
        or.setOrderNumber(1001);

        int id = orderedDAO.create(or);
        List<Ordered> result = orderedDAO.findAll(1);
        boolean exists = false;

        for (Ordered ordered : result) {
            if (ordered.getId() == id) exists = true;
        }

        assertTrue(exists);
    }


}

