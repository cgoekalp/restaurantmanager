package at.ac.tuwien.sepm.assignment.individual.universe.dao.DB;

import at.ac.tuwien.sepm.assignment.individual.universe.domain.Ordered;
import at.ac.tuwien.sepm.assignment.individual.universe.domain.Product;
import at.ac.tuwien.sepm.assignment.individual.universe.domain.dto.ProductDTO;
import at.ac.tuwien.sepm.assignment.individual.universe.exception.DAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBCProductDAO implements IProductDAO {
    private Connection connection;
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public JDBCProductDAO() {
        this.connection = JDBConnection.getInstance().getConnection();
    }

    /*
    * PreparedStatement a sql statement precompiled and store in prep.statement
    * ps.executeUpdate() run the sql statement
     */
    @Override
    public Integer create(Product product) throws DAOException{
        if(product == null) {
            throw new IllegalArgumentException("Product is null and can not be saved");
        }
        try {
            PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO Product(NAME, DESCRIPTION, BRUTTOPRICE, NETTOPRICE, CREATED,UPDATETIME,CATEGORY,TAX) Values(?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, product.getName());
            ps.setString(2,product.getDescription());
            ps.setDouble (3,product.getBruttoprice() );
            ps.setDouble (4,product.getNettoprice() );
            ps.setTimestamp (5,product.getCreated());
            ps.setTimestamp(6,product.getUpdatetime());
            ps.setString(7,product.getCategory() );
            ps.setDouble (8,product.getTax() );

            int status=ps.executeUpdate();

            if(status>0){
                ResultSet resultSet=ps.getGeneratedKeys();
                if(resultSet.next()){
                    product.setId(resultSet.getInt(1));
                }
            }
        } catch (Exception exception) {
            throw new DAOException((exception));
        }
        return product.getId();
    }

        @Override
        public void update(Product product) throws DAOException {

        if(product == null){
            LOG.error("Error in Method: " + Thread.currentThread().getStackTrace()[1].getMethodName()
                    + ", ILLEGAL ARGUMENT!");
            throw new IllegalArgumentException("Product ist null und can not take aktuell value!");
        }
        PreparedStatement ps=null;
        ResultSet rs=null;
        
        try{

            ps=connection.prepareStatement("SELECT * FROM PRODUCT WHERE ID = ?",ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ps.setInt(1,product.getId().intValue());
            rs=ps.executeQuery();
            rs.first();
            //MAP will the value of aktuel Produkte
            Map<String, Object> temp = this.mapProductValues(product);

            for(String key : temp.keySet()){
                rs.updateObject(key, temp.get(key));
            }

            //work with the updates in aktuel situation(row in tablo)and end with result
            rs.updateRow();
            rs.close();
        } catch (SQLException e) {
            LOG.error("Datenaccesserror in Method: "+ Thread.currentThread().getStackTrace()[1].getMethodName());
            //throw new ExceptionInInitializerError();
            throw new DAOException("Datenbankzugriffsfehler in Methode : " + Thread.currentThread().getStackTrace()[1].getMethodName() + ", " + e.getMessage());
        }
    }


    @Override
     public boolean removeProductById(Integer id) throws IllegalArgumentException, DAOException {
        LOG.debug("input in removeProductById Methode with such a Parameter: " + id);
        boolean deleted = false;
        Product product = findProductById(id);
        if (product == null) {
            throw new IllegalArgumentException("No product found!");
        }
        try {
            product.setDeleted(true);  // boolean bir deger atamis produktun silinip silinmedigini kontrol ediyor
            // bu sayede true dönderiyor
            update(product);
            deleted = true;
        } catch (DAOException e) {
            throw new DAOException(e);
        }

        return deleted;
    }

        @Override
    public List<Product> search(Product product) throws DAOException {
        List<Product> result = new ArrayList<Product>();

        ResultSet rs = null;
        PreparedStatement pst = null;

        try{
            String s="SELECT * FROM PRODUCT WHERE 1=1";
            int help=0;
            if(product.getName()!=null){ s+=" AND lower(NAME) LIKE ?"; help++;}
            if(product.getCategory()!=null){ s += " AND CATEGORY LIKE ? "; help++;}
            if(product.getBruttoprice()!=null){ s += " AND  BRUTTOPRICE=?" ; help++;}
            if(product.getNettoprice()!=null){ s += " AND  NETTOPRICE=?" ; help++;}
            if(product.getTax() !=null){ s += " AND  TAX=?" ; help++;}

            int filterOptionsSize = help+1;

            pst = connection.prepareStatement(s);
            if (help!=0){
                if(product.getName()!=null){pst.setString(filterOptionsSize-help, '%' + product.getName().toLowerCase() + '%'); help--;}
                if(product.getCategory()!=null){pst.setString(filterOptionsSize-help, product.getCategory()); help--;}
                if(product.getBruttoprice()!=null){pst.setDouble (filterOptionsSize-help, product.getBruttoprice()); help--;}
                if(product.getNettoprice()!=null){pst.setDouble (filterOptionsSize-help, product.getNettoprice()); help--;}
                if(product.getTax()!=null){pst.setDouble(filterOptionsSize-help, product.getTax()); help--;}
            }

            rs = pst.executeQuery();

            Product tmp = null;
            while(rs.next()){

                tmp = new Product();
                tmp.setId( rs.getInt("id"));
                tmp.setName(rs.getString ("name"));
                tmp.setDescription(rs.getString ("description"));
                tmp.setCategory(rs.getString ("category") );
                tmp.setCreated(rs.getTimestamp("created") );
                tmp.setUpdatetime(rs.getTimestamp("updatetime"));
                tmp.setBruttoprice(rs.getDouble("bruttoprice"));
                tmp.setNettoprice(rs.getDouble("nettoprice"));
                tmp.setTax(rs.getDouble("tax"));
                tmp.setDeleted(rs.getBoolean("deleted"));

                result.add(tmp);
            }

        } catch (SQLException e) {
            LOG.error("Output from searchPrice method:" + e.getMessage());
            throw new DAOException("There is no product, that we can use, we couldnt use in next one : " + e.getMessage());
        }

//        log.debug("Austritt aus der findBoxesWithFilter Methode");
        return result;
    }

    @Override
    public List<Product> search(String name, String category, Double minNetto, Double maxNetto, Double minBrutto, Double maxBrutto) throws DAOException {

        List<Product> result = new ArrayList<Product>();

        ResultSet rs = null;
        PreparedStatement pst = null;

        try{
            String s="SELECT * FROM PRODUCT WHERE 1=1";
            int help=0;
            if(name!=null){ s+=" AND lower(NAME) LIKE ?"; help++;}
            if(category!=null){ s += " AND CATEGORY LIKE ? "; help++;}
            if(maxBrutto!=null){ s += " AND BRUTTOPRICE <= ? " ; help++;}
            if(minBrutto!=null){ s += " AND BRUTTOPRICE >= ? " ; help++;}
            if(maxNetto!=null){ s += " AND NETTOPRICE <= ? " ; help++;}
            if(minNetto!=null){ s += " AND NETTOPRICE >= ? " ; help++;}

            s+=" AND DELETED=false";
            int filterOptionsSize = help+1;

            pst = connection.prepareStatement(s);
            if (help!=0){
                if(name!=null){pst.setString(filterOptionsSize-help, '%' + name.toLowerCase() + '%'); help--;}
                if(category!=null){pst.setString(filterOptionsSize-help, category); help--;}
                if(maxBrutto!=null){pst.setDouble (filterOptionsSize-help, maxBrutto); help--;}
                if(minBrutto!=null){pst.setDouble (filterOptionsSize-help, minBrutto); help--;}
                if(maxNetto!=null){pst.setDouble (filterOptionsSize-help, maxNetto); help--;}
                if(minNetto!=null){pst.setDouble (filterOptionsSize-help, minNetto); help--;}
            }

            rs = pst.executeQuery();

            Product tmp = null;
            while(rs.next()){

                tmp = new Product();
                tmp.setId( rs.getInt("id"));
                tmp.setName(rs.getString ("name"));
                tmp.setDescription(rs.getString ("description"));
                tmp.setCategory(rs.getString ("category") );
                tmp.setCreated(rs.getTimestamp("created") );
                tmp.setUpdatetime(rs.getTimestamp("updatetime"));
                tmp.setBruttoprice(rs.getDouble("bruttoprice"));
                tmp.setNettoprice(rs.getDouble("nettoprice"));
                tmp.setTax(rs.getDouble("tax"));
                tmp.setDeleted(rs.getBoolean("deleted"));

                result.add(tmp);
            }

        } catch (SQLException e) {
            LOG.error("Output from searchPrice method: " + e.getMessage());
            throw new DAOException("There is no product,that we can use, we couldn't use in next one :  " + e.getMessage());
        }

          LOG.debug("Output from the findProductwith Methode");
        return result;


    }

    @Override
    public List<Product> findAll() throws DAOException {
        List<Product> result = new ArrayList<Product>();

        ResultSet rs = null;
        PreparedStatement pst = null;

        try{
            String s="SELECT * FROM PRODUCT WHERE 1=1 AND DELETED=false";

            pst = connection.prepareStatement(s);

            rs = pst.executeQuery();

            Product tmp = null;
            while(rs.next()){

                tmp = new Product();
                tmp.setId( rs.getInt("id"));
                tmp.setName(rs.getString ("name"));
                tmp.setDescription(rs.getString ("Description"));
                tmp.setCategory(rs.getString ("Category") );
                tmp.setCreated(rs.getTimestamp("created") );
                tmp.setUpdatetime(rs.getTimestamp("updatetime"));
                tmp.setBruttoprice(rs.getDouble("bruttoprice"));
                tmp.setNettoprice(rs.getDouble("nettoprice"));
                tmp.setTax(rs.getDouble("Tax"));
                tmp.setDeleted(rs.getBoolean("deleted"));

                result.add(tmp);
            }

        } catch (SQLException e) {
            throw new DAOException("There is no product,that we can use, we couldnt use in next one : " + e.getMessage());
        }

            LOG.debug("Output from method  aus der findProductWithFilter");
        return result;
    }

    @Override
    public List<ProductDTO> findByOrdered(Ordered transformOrdered) throws DAOException {
        List<ProductDTO> result = new ArrayList<ProductDTO>();

        ResultSet rs = null;
        PreparedStatement pst = null;

        try{
            String s="SELECT p.*, op.productnumber FROM PRODUCT p JOIN ORDEREDPRODUCT op ON p.id=op.pid WHERE op.oid=?";

            pst = connection.prepareStatement(s);
            pst.setInt(1, transformOrdered.getId());
            rs = pst.executeQuery();

            ProductDTO tmp = null;
            while(rs.next()){

                tmp = new ProductDTO();
                tmp.setId( rs.getInt("id"));
                tmp.setName(rs.getString ("name"));
                tmp.setDescription(rs.getString ("Description"));
                tmp.setCategory(rs.getString ("Category") );
                tmp.setCreated(rs.getTimestamp("created").getTime() );
                tmp.setUpdatetime(rs.getTimestamp("updatetime").getTime());
                tmp.setBruttoprice(rs.getDouble("bruttoprice"));
                tmp.setNettoprice(rs.getDouble("nettoprice"));
                tmp.setTax(rs.getDouble("Tax"));
                tmp.setCount(rs.getInt("productnumber"));

                result.add(tmp);
            }

        } catch (SQLException e) {
            throw new DAOException("There is no product,that we can use, we couldnt use in next one : " + e.getMessage());
        }

        LOG.debug("Output from method  aus der findProductWithFilter");
        return result;
    }

    @Override
    public Product findProductById(Integer id) throws DAOException {
        LOG.debug("Input in Method: " + Thread.currentThread().getStackTrace()[1].getMethodName()
                  + " mit Parameter: " + (id == null ? "null" : id));

        if(id == null){
            LOG.error("Error in Method: " + Thread.currentThread().getStackTrace()[1].getMethodName() + ", ILLEGAL ARGUMENT!");
            throw new IllegalArgumentException("Invalid id");
        }

        Product product = new Product();
        ResultSet resultSet = null;
        PreparedStatement ps=null;

        try{
            ps=connection.prepareStatement("SELECT * FROM PRODUCT WHERE ID=?");
            ps.setInt(1, id);
            resultSet=ps.executeQuery();

            if (resultSet.first()){

                product.setId( resultSet.getInt("id"));
                product.setName(resultSet.getString ("name"));
                product.setDescription(resultSet.getString ("Description"));
                product.setCategory(resultSet.getString ("Category") );
                product.setCreated(resultSet.getTimestamp("created") );
                product.setUpdatetime(resultSet.getTimestamp("updatetime"));
                product.setBruttoprice(resultSet.getDouble("bruttoprice"));
                product.setNettoprice(resultSet.getDouble("nettoprice"));
                product.setTax(resultSet.getDouble("tax"));
                product.setDeleted(resultSet.getBoolean("deleted"));

            }

        } catch (SQLException e) {
            LOG.error("Data Access in Methode: " + Thread.currentThread().getStackTrace()[1].getMethodName());
            throw new DAOException("Data Access in Methode: " + Thread.currentThread()
                .getStackTrace()[1].getMethodName() + ", " + e.getMessage());
        }

        if(product.getId() == null) {
            LOG.debug("Output from findProductById Method: " + "No such Product!");
            return null;
        }

        return product;
    }


    private Map<String, Object> mapProductValues(Product product) {
        HashMap mapp = new HashMap();
        mapp.put("NAME",product.getName());
        mapp.put("DESCRIPTION",product.getDescription());
        mapp.put("CATEGORY", product.getCategory());
        mapp.put("CREATED", product.getCreated() );
        mapp.put("UPDATETIME", product.getUpdatetime() );
        mapp.put("BRUTTOPRICE", product.getBruttoprice());
        mapp.put("NETTOPRICE", product.getNettoprice() );
        mapp.put("TAX", product.getTax() );
        mapp.put("DELETED", product.getDeleted() );
        return mapp;
    }

}


