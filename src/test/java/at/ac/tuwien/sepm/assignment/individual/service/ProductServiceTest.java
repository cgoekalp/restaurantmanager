package at.ac.tuwien.sepm.assignment.individual.service;

import at.ac.tuwien.sepm.assignment.individual.universe.domain.Product;
import at.ac.tuwien.sepm.assignment.individual.universe.domain.dto.ProductDTO;
import at.ac.tuwien.sepm.assignment.individual.universe.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.universe.service.IProductService;
import at.ac.tuwien.sepm.assignment.individual.universe.service.ProductService;
import at.ac.tuwien.sepm.assignment.individual.util.TransformUtility;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;

import static junit.framework.TestCase.assertTrue;

public class ProductServiceTest {

    private IProductService iProductService;

 /*   @Before
    public void init() throws ServiceException {
        iProductService = new ProductService();

        Product pr = new Product();
        pr.setName("productone");
        pr.setDescription("schoko");
        pr.setCategory("Drink");
        pr.setCreated(new Timestamp(System.currentTimeMillis()));
        pr.setBruttoprice(14.3);
        pr.setNettoprice(12.10);
        pr.setUpdatetime(new Timestamp(System.currentTimeMillis()));
        pr.setTax(20.0);
        // TODO insert products
        iProductService.create(TransformUtility.transformProduct(pr));
    }

    @Test
    public void testPrice() throws ServiceException {
        iProductService = new ProductService();

        double netto = 10;
        String category = "Drink";

        double brutto = netto * 0.2 + netto;

        Product pr = new Product();
        pr.setName("sert");
        pr.setDescription("drinking");
        pr.setCategory(category);
        pr.setCreated(new Timestamp(System.currentTimeMillis()));
        pr.setNettoprice(netto);
        pr.setUpdatetime(new Timestamp(System.currentTimeMillis()));

        ProductDTO result = iProductService.create(TransformUtility.transformProduct(pr));

        assertTrue(result.getTax() == 20.0);
        assertTrue(result.getBruttoprice() == 12.0);

    }

*/
}
