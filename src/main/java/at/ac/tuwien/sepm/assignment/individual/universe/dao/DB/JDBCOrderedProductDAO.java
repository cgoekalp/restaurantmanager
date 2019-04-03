package at.ac.tuwien.sepm.assignment.individual.universe.dao.DB;

import at.ac.tuwien.sepm.assignment.individual.universe.domain.Ordered;
import at.ac.tuwien.sepm.assignment.individual.universe.domain.OrderedProduct;
import at.ac.tuwien.sepm.assignment.individual.universe.domain.Product;
import at.ac.tuwien.sepm.assignment.individual.universe.domain.dto.ProductDTO;
import at.ac.tuwien.sepm.assignment.individual.universe.exception.DAOException;
import org.h2.jdbc.JdbcConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCOrderedProductDAO implements IOrderedProductDAO {

    private Connection connection;
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public JDBCOrderedProductDAO(){
        this.connection = JDBConnection.getInstance().getConnection();
    }

    @Override
    public List<OrderedProduct> findAll() throws DAOException {

        List<OrderedProduct> result = new ArrayList<OrderedProduct>();

        ResultSet rs = null;
        PreparedStatement pst = null;

        try{

            String s="SELECT id, oid, invoice, diningtable FROM ORDEREDPRODUCT op join ORDERED o on op.oid=o.id  WHERE o.orderstatus=1 group by invoice, oid ";

            pst = connection.prepareStatement(s);

            rs = pst.executeQuery();

            OrderedProduct tmp = null;
            while(rs.next()){

                tmp = new OrderedProduct();
                tmp.setId( rs.getInt("id"));
                tmp.setCategory(rs.getString ("category") );
                tmp.setBruttoprice(rs.getDouble("bruttoprice"));
                tmp.setNettoprice(rs.getDouble("nettoprice"));
                tmp.setTax(rs.getDouble("tax"));
                tmp.setInvoice(rs.getTimestamp("invoice"));
                tmp.setOid(rs.getInt("oid"));
                tmp.setPid(rs.getInt("pid"));
                tmp.setProductnumber(rs.getInt("productnumber"));

                result.add(tmp);
            }

        } catch (SQLException e) {
            throw new DAOException("There is no product,that we can use, we couldnt use in next one : " + e.getMessage());
        }

        LOG.debug("Output from find all ordered products");
        return result;
    }

    @Override
    public List<OrderedProduct> findByOrdered(Ordered ordered) throws DAOException {
        List<OrderedProduct> result = new ArrayList<OrderedProduct>();

        ResultSet rs = null;
        PreparedStatement pst = null;

        try{

            String s = "SELECT * FROM ORDEREDPRODUCT WHERE oid=? ";

            pst = connection.prepareStatement(s);
            pst.setInt(1, ordered.getId());

            rs = pst.executeQuery();

            OrderedProduct tmp = null;
            while(rs.next()){

                tmp = new OrderedProduct();
                tmp.setId( rs.getInt("id"));
                tmp.setCategory(rs.getString ("category") );
                tmp.setBruttoprice(rs.getDouble("bruttoprice"));
                tmp.setNettoprice(rs.getDouble("nettoprice"));
                tmp.setTax(rs.getDouble("tax"));
                tmp.setInvoice(rs.getTimestamp("invoice"));
                tmp.setOid(rs.getInt("oid"));
                tmp.setPid(rs.getInt("pid"));
                tmp.setProductnumber(rs.getInt("productnumber"));
                tmp.setName(rs.getString("name"));

                result.add(tmp);
            }

        } catch (SQLException e) {
            throw new DAOException("There is no product,that we can use, we couldnt use in next one : " + e.getMessage());
        }

        LOG.debug("Output from find all ordered products");
        return result;
    }

    @Override
    public List<OrderedProduct> create(Ordered ordered, List<ProductDTO> products) throws DAOException{

        try {

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO ORDEREDPRODUCT(PID,INVOICE,NAME,BRUTTOPRICE,NETTOPRICE,CATEGORY,TAX,OID,PRODUCTNUMBER) VALUES(?,?,?,?,?,?,?,?,?)");

            Timestamp timeStamp = new Timestamp(System.currentTimeMillis());

            for(ProductDTO product : products){
                preparedStatement.setInt(1, product.getId());
                preparedStatement.setTimestamp(2, timeStamp);
                preparedStatement.setString(3, product.getName());
                preparedStatement.setDouble(4, product.getBruttoprice());
                preparedStatement.setDouble(5,product.getNettoprice());
                preparedStatement.setString(6, product.getCategory());
                preparedStatement.setDouble(7, product.getTax());
                preparedStatement.setInt(8, ordered.getId());
                preparedStatement.setInt(9, product.getCount());

                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new DAOException("Could not create product error: " + e.getMessage());
        }

        return findByOrdered(ordered);
    }

    @Override
    public void deleteByOrdered(Ordered ordered) throws DAOException {
        try {

            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM ORDEREDPRODUCT WHERE oid=?");
            preparedStatement.setInt(1, ordered.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();

        }catch(SQLException ex){
            throw new DAOException(ex);
        }
    }
}
