package at.ac.tuwien.sepm.assignment.individual.dao;

import at.ac.tuwien.sepm.assignment.individual.universe.dao.DB.JDBCProductDAO;
import at.ac.tuwien.sepm.assignment.individual.universe.dao.DB.IProductDAO;
import at.ac.tuwien.sepm.assignment.individual.universe.domain.Product;
import at.ac.tuwien.sepm.assignment.individual.universe.exception.DAOException;
import org.junit.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class JDBCProductDAOTest {

    private IProductDAO IProductDAO;

    /*
    *test bir obje olusturuyor, onu test ediyor..
    *
     */
    @Before
    public void init() {
        IProductDAO = new JDBCProductDAO();
        Product pr = new Product();
        pr.setName("productone");
        pr.setDescription("schoko");
        pr.setCategory("Drink");
        pr.setCreated(new Timestamp(System.currentTimeMillis()));
        pr.setBruttoprice(14.3);
        pr.setNettoprice(12.10);
        pr.setUpdatetime(new Timestamp(System.currentTimeMillis()));
        pr.setTax(20.0);

        try {
            IProductDAO.create(pr);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }


    private Product generateProductObject() {     // RETURN DEGER DÖNDÜREN BIR INITe ihtiyacim vardi
        // erstelle neues Box - Objekt
        Product p=new Product();
        p.setName("productone");
        p.setDescription("schoko");
        p.setCategory("Drink");
        p.setCreated(new Timestamp(System.currentTimeMillis()));
        p.setBruttoprice(14.3);
        p.setNettoprice(12.10);
        p.setUpdatetime(new Timestamp(System.currentTimeMillis()));
        p.setTax(20.0);

        return p;
    }
    /*
    test objesini siliyor

     */

    /*@After
    public void tearDown() {
        /*try {

            List<Product> productList = IProductDAO.search(new Product());

            for(Product product : productList){
                IProductDAO.removeProductById(product.getId());
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }*/


    /**
     * 2.version
     * Dieser Test versucht einen ungueltigen Wert (NULL - Wert ) in die
     * Datenbank zu speichern. Das DAO sollte eine Exception werfen.
     * @throws DAOException
     **/
    @Test(expected = IllegalArgumentException.class)
    public void createWithNullShouldThrowException() throws DAOException{
        this.IProductDAO.create(null);
    }


    /*@Test
    public void createNewProduct(){
        IProductDAO.create(null);
    }*/

    /*
    id yanlis verildiginde

     */
    /**
     * Dieser Test sucht nach einem Objekt des Typs Box mit
     * einer ungueltigen ID.
     *
     * **/
    @Test
    public void findWithNotExistingIdToNull() throws DAOException {
        int i = 999999;
        Product product = this.IProductDAO.findProductById(Integer.valueOf(i));
        assertTrue(product ==null);

    }

    @Test
    public void testCreate() throws DAOException{
        Product pr = new Product();
        pr.setName("pro");
        pr.setDescription("banana");
        pr.setCategory("Food");
        pr.setCreated(new Timestamp(System.currentTimeMillis()));
        pr.setBruttoprice(2.3);
        pr.setNettoprice(1.10);
        pr.setUpdatetime(new Timestamp(System.currentTimeMillis()));
        pr.setTax(15.0);
        // TODO insert products
        int id = IProductDAO.create(pr);

        assertTrue(IProductDAO.findProductById(id)!= null );
    }

    /*
    *create a Product without paramater
    * to test, output is that it muss be >0
     *Product{id=null, name='null', bruttoprice=null', description=null', created=null', updatetime=null', category=null', tax=null'}
     */

    @Test
    public void testSearch() throws DAOException{

        Product searchProduct = new Product();
        List<Product> result = IProductDAO.search(searchProduct);
        assertTrue(result.size() > 0);

    }

    /*
    *Search the Product with the id,
    *first I give every Parameter then i create id
    * make a list
    *boolean exist at begin false if there is an another product with same id
    * then return exist true
    * */

    @Test
    public void findProductById() throws DAOException {
        Product pr = new Product();
        pr.setName("pro");
        pr.setDescription("banana");
        pr.setCategory("Food");
        pr.setCreated(new Timestamp(System.currentTimeMillis()));
        pr.setBruttoprice(2.3);
        pr.setNettoprice(1.10);
        pr.setUpdatetime(new Timestamp(System.currentTimeMillis()));
        pr.setTax(15.0);

        int id = IProductDAO.create(pr);
        List<Product> result = IProductDAO.findAll();
        boolean exists = false;

        for(Product product : result){
            if(product.getId() == id) exists = true;
        }

        assertTrue(exists);
    }

    /*
    * Create a result with product which is already in tabella before
    *I create a similar one.
    *
    *
     */

    @Test
    public void testSearchTwo() throws DAOException{

        Product searchProduct = new Product();

        searchProduct.setName("productone");
        searchProduct.setDescription("schoko");
        searchProduct.setCategory("Drink");
        searchProduct.setCreated(new Timestamp(System.currentTimeMillis()));
        searchProduct.setBruttoprice(14.3);
        searchProduct.setUpdatetime(new Timestamp(System.currentTimeMillis()));
        searchProduct.setTax(20.0);

        List<Product> result = IProductDAO.search(searchProduct);
        assertTrue(result.size() > 0);
    }

    /*
    * creat a random productname
    * fill other parameter after then create a new product
    *
    *
     */
    @Test
    public void testSearchNameLowercaseWildcard() throws DAOException{

        String productname = UUID.randomUUID().toString();

        Product pr = new Product();
        pr.setName(productname);
        pr.setDescription("banana");
        pr.setCategory("Food");
        pr.setCreated(new Timestamp(System.currentTimeMillis()));
        pr.setBruttoprice(2.3);
        pr.setNettoprice(1.10);
        pr.setUpdatetime(new Timestamp(System.currentTimeMillis()));
        pr.setTax(15.0);

        IProductDAO.create(pr);

        pr.setName(productname.substring(10));
        List<Product> result = IProductDAO.search(pr);

        assertTrue(result.size() > 0);

    }

    /*
     * Min Max Netto
     * Min Max Brutto
     */

    @Test
    public void testMinMax() throws DAOException{
        Product pr = new Product();

        pr.setName("damla");
        pr.setDescription("banana");
        pr.setCategory("Food");
        pr.setCreated(new Timestamp(System.currentTimeMillis()));
        pr.setBruttoprice(2.3);
        pr.setNettoprice(1.10);
        pr.setUpdatetime(new Timestamp(System.currentTimeMillis()));
        pr.setTax(15.0);

        IProductDAO.create(pr);
        List<Product> result = IProductDAO.search(pr.getName(), pr.getCategory(), 1.0, 2.0, 1.0, 3.0);
        assertTrue(result.size() > 0);
    }

    /*
    * test Min Max Two With Null
    *
     */

    @Test
    public void testMinMaxTwoWithNull() throws DAOException{

        Product pr = new Product();
        pr.setName("damla");
        pr.setDescription("banana");
        pr.setCategory("Food");
        pr.setCreated(new Timestamp(System.currentTimeMillis()));
        pr.setBruttoprice(2.3);
        pr.setNettoprice(1.10);
        pr.setUpdatetime(new Timestamp(System.currentTimeMillis()));
        pr.setTax(15.0);

        IProductDAO.create(pr);

        List<Product> result = IProductDAO.search(pr.getName(), pr.getCategory(), null, null, 1.0, 3.0);

        assertTrue(result.size() > 0);

    }
}
