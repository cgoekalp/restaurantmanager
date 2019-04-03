package at.ac.tuwien.sepm.assignment.individual.universe.dao.DB;
import at.ac.tuwien.sepm.assignment.individual.universe.domain.Ordered;
import at.ac.tuwien.sepm.assignment.individual.universe.exception.DAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.LogManager;

public class JDBCOrderedDAO implements IOrderedDAO {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    JDBConnection con = JDBConnection.getInstance();
    Connection connection;

    public JDBCOrderedDAO() {
        this.connection = con.getConnection();
    }

    @Override
    public Integer create(Ordered ordered) throws DAOException {

           LOG.debug("Input in Method: " + Thread.currentThread().getStackTrace()[1].getMethodName()
                + " with Parameter: " + (ordered == null ? "null" : ordered));
        if (ordered == null) {
            LOG.error("Error in Method: " + Thread.currentThread().getStackTrace()[1].getMethodName()
                + ", ILLEGAL ARGUMENT!");
            throw new IllegalArgumentException("Ordered is null and can not be saved");
        }
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Ordered (TOTAL, ORDERTIME, DININGTABLE, PAYMENTTYPE, ORDERSTATUS, ORDERNUMBER) " +
                "Values(?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            ps.setDouble(1, ordered.getTotal());
            ps.setTimestamp(2, ordered.getOrdertime());
            ps.setInt(3, ordered.getDiningtable());
            ps.setString(4, ordered.getPaymenttype());
            ps.setInt(5, ordered.getOrderstatus());
            ps.setInt(6, getNextOrderNumber());
            int status=ps.executeUpdate();

            if(status>0){
                ResultSet resultSet=ps.getGeneratedKeys();
                if(resultSet.next()){
                    ordered.setId(resultSet.getInt(1));
                }
            }
        } catch (Exception exception) {
       LOG.debug ("Datenaccesserror in Methode: " + Thread.currentThread().getStackTrace()[1].getMethodName());
            exception.printStackTrace();
        }
       LOG.debug("Output aus Methode: " + Thread.currentThread().getStackTrace()[1].getMethodName()
           + " with Parameter: " + ordered);
        return ordered.getId();
    }


    @Override
    public List<Ordered> findAll(int status) throws DAOException {
        List<Ordered> result = new ArrayList<Ordered>();

        ResultSet rs = null;
        PreparedStatement pst = null;

        try{
            String s="SELECT * FROM ORDERED WHERE orderstatus=?";

            pst = connection.prepareStatement(s);
            pst.setInt(1, status);
            rs = pst.executeQuery();

            Ordered tmp = null;
            while(rs.next()){

                tmp = new Ordered();
                tmp.setId( rs.getInt("id"));
                tmp.setTotal(rs.getDouble("total"));
                tmp.setOrdertime(rs.getTimestamp("ordertime"));
                tmp.setDiningtable(rs.getInt("diningtable"));
                tmp.setPaymenttype(rs.getString("paymenttype"));
                tmp.setOrderstatus(rs.getInt("orderstatus"));
                tmp.setOrderNumber(rs.getInt("ordernumber"));

                result.add(tmp);
            }

        } catch (SQLException e) {
            LOG.error("Output from findOrderedWithFilter Method: " + e.getMessage());
            throw new DAOException("There is no product,that we can use, we couldn't use in next one : " + e.getMessage());
        }

        LOG.debug("Output in findAll Method");
        return result;
    }

    @Override
    public void update(Ordered ordered) throws DAOException{

        if(ordered == null){
            LOG.error("Error in Method: " + Thread.currentThread().getStackTrace()[1].getMethodName()
                    + ", ILLEGAL ARGUMENT!");
            throw new IllegalArgumentException("Ordered ist null und can not take a aktuel value!");
        }

        PreparedStatement ps=null;
        ResultSet rs=null;

        try{

            ps=connection.prepareStatement("SELECT * FROM ORDERED WHERE ID = ?",ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ps.setInt(1,ordered.getId().intValue());
            rs=ps.executeQuery();
            rs.first();
            //mappe die werte des zu aktualisierenden Boxes
            Map<String, Object> temp = this.mapOrderedValues(ordered);
            //i = 1, da wir die ID Ueberspringen wollen!
            for(String key : temp.keySet()){
                rs.updateObject(key, temp.get(key));
            }
            //fuehre die updates im aktuellen Datensatz (Zeile in der Tabelle) durch und beende das resultset
            rs.updateRow();
            rs.close();
        } catch (SQLException e) {
            LOG.error("Datenaccesserror in Method: "+ Thread.currentThread().getStackTrace()[1].getMethodName());
            throw new DAOException(e);
        }
    }


    @Override
    public void removeOrderedById (Integer id) throws DAOException {
       LOG.debug("Eintritt in removeBoxById Methode mit folgendem Parameter: " + id);

        Ordered ordered = findOrderedById(id);
        if(ordered ==null){
            throw new IllegalArgumentException("Es könnte nicht Ordered finden!");
        }
    }


    @Override
    public List<Ordered> search(Ordered ordered) throws DAOException {
        List<Ordered> result = new ArrayList<Ordered>();

        ResultSet rs = null;
        PreparedStatement pst = null;

        try {
            String ord="SELECT * FROM ORDERED WHERE 1=1";
            int help=0;
            if(ordered.getTotal() != null){ ord +=" AND TOTAL =?"; help++;}
            if(ordered.getDiningtable() != null){ord += " AND DININGTABLE LIKE ? "; help++;}
            if(ordered.getPaymenttype() !=null){ ord += " AND PAYMENTTYPE LIKE ?" ; help++;}
            if(ordered.getOrderstatus() !=null){ ord += " AND ORDERSTATUS =?" ; help++;}

            int filterOptionsSize = help+1;
            pst = connection.prepareStatement(ord);
            if (help!=0){
                if(ordered.getTotal()!=0.0){pst.setDouble (filterOptionsSize-help, ordered.getTotal()); help--;}
                //if(ordered.getOrdertime()!=null){pst.setTimestamp (filterOptionsSize-help, ordered.getOrdertime()); help--;}
                if(ordered.getDiningtable()!=null){pst.setInt (filterOptionsSize-help, ordered.getDiningtable()); help--;}
                if(ordered.getPaymenttype()!=null){pst.setString (filterOptionsSize-help, ordered.getPaymenttype()); help--;}
                if(ordered.getOrderstatus()!=null){pst.setInt(filterOptionsSize-help, ordered.getOrderstatus()); help--;}
            }

            rs = pst.executeQuery();

            Ordered tmp = null;
            while(rs.next()){

                tmp = new Ordered();
                tmp.setId( rs.getInt("id"));
                tmp.setTotal(rs.getDouble ("total"));
                tmp.setDiningtable(rs.getInt ("diningtable"));
                tmp.setOrdertime(rs.getTimestamp("ordertime"));
                tmp.setPaymenttype(rs.getString("paymenttype"));
                tmp.setOrderstatus(rs.getInt("orderstatus"));
                result.add(tmp);
            }

        } catch (SQLException e) {
            LOG.error("Output from search method: " + e.getMessage());
            throw new DAOException("There is no product,that we can use, we couldn't use in next one : " + e.getMessage());
        }
        //Keine order gefunden, die in Benutzung sind bzw. in Zukï¿½nftigen Reservationen enthalten sind:
        return result;
    }

    @Override
    public boolean isTableFree(Integer tableNumber) throws DAOException {

        List<Ordered> result = new ArrayList<Ordered>();

        ResultSet rs = null;
        PreparedStatement pst = null;

        try{
            String s="SELECT * FROM ORDERED WHERE orderstatus=0 AND diningtable=?";

            pst = connection.prepareStatement(s);
            pst.setInt(1,tableNumber);

            rs = pst.executeQuery();

            Ordered tmp = null;
            while(rs.next()){
                tmp = new Ordered();
                tmp.setId( rs.getInt("id"));
                result.add(tmp);
            }

        } catch (SQLException e) {
            LOG.error("Output from findOrderedWithFilter Method: " + e.getMessage());
            throw new DAOException("There is no order,that we can use, we couldn't use in next one : " + e.getMessage());
        }

        LOG.debug("Output in findAll Method");

        return result.isEmpty();
    }

    @Override
    public List<Ordered> findAllOrderedProducts() throws DAOException {
        List<Ordered> result = new ArrayList<Ordered>();

        ResultSet rs = null;
        PreparedStatement pst = null;

        try{
            String s="select o.id, o.total, op.invoice as ordertime, o.diningtable, o.paymenttype, o.orderstatus, o.ordernumber from ORDERED o join ORDEREDPRODUCT op on o.id=op.oid " +
                "where o.orderstatus=1 " +
                "group by o.id, o.total, op.invoice, o.diningtable, o.paymenttype, o.orderstatus, o.ordernumber";

            pst = connection.prepareStatement(s);
            rs = pst.executeQuery();

            Ordered tmp = null;
            while(rs.next()){

                tmp = new Ordered();
                tmp.setId( rs.getInt("id"));
                tmp.setTotal(rs.getDouble("total"));
                tmp.setOrdertime(rs.getTimestamp("ordertime"));
                tmp.setDiningtable(rs.getInt("diningtable"));
                tmp.setPaymenttype(rs.getString("paymenttype"));
                tmp.setOrderstatus(rs.getInt("orderstatus"));
                tmp.setOrderNumber(rs.getInt("ordernumber"));

                result.add(tmp);
            }

        } catch (SQLException e) {
            LOG.error("Output from findOrderedWithFilter Method: " + e.getMessage());
            throw new DAOException("There is no product,that we can use, we couldn't use in next one : " + e.getMessage());
        }

        LOG.debug("Output in findAll Method");
        return result;
    }


    @Override
    public Ordered findOrderedById(Integer id) throws DAOException {
        LOG.debug("Eintritt in Methode: " + Thread.currentThread().getStackTrace()[1].getMethodName()
                 + " mit Parameter: " + (id == null ? "null" : id));


        if(id == null){
            LOG.error("Error in Method: " + Thread.currentThread().getStackTrace()[1].getMethodName() + ", ILLEGAL ARGUMENT!");
            throw new IllegalArgumentException("Invalid id");
        }

        Ordered ordered = new Ordered();
        ResultSet rs = null;
        PreparedStatement ps=null;

        try{
            ps=connection.prepareStatement("SELECT * FROM ORDERED WHERE ID=?");
            ps.setInt(1, id);
            rs=ps.executeQuery();

            if (rs.first()){

                ordered = new Ordered();
                ordered.setId( rs.getInt("id"));
                ordered.setTotal(rs.getDouble("total"));
                ordered.setOrdertime(rs.getTimestamp("ordertime"));
                ordered.setDiningtable(rs.getInt("diningtable"));
                ordered.setPaymenttype(rs.getString("paymenttype"));
                ordered.setOrderstatus(rs.getInt("orderstatus"));
                ordered.setOrderNumber(rs.getInt("ordernumber"));

            }

        } catch (SQLException e) {
            LOG.error("Datenaccesserror in Method: " + Thread.currentThread().getStackTrace()[1].getMethodName());
            throw new DAOException("Datenaccesserror in Method : " + Thread.currentThread()
                .getStackTrace()[1].getMethodName() + ", " + e.getMessage());
        }


        if(ordered.getId() == null) {
            LOG.debug("Output from findOrderedById Method: " + "No such Order!");
            return null;
        }

        return ordered;
    }

    private int getNextOrderNumber() throws DAOException{
        int number = 0;
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO OrderNumber () VALUES ()", Statement.RETURN_GENERATED_KEYS);
            int status = ps.executeUpdate();
            if(status>0){
                ResultSet resultSet=ps.getGeneratedKeys();
                if(resultSet.next()){
                    number = resultSet.getInt(1);
                }
            }
        }catch (SQLException e){
            throw  new DAOException(e);
        }
        return number;
    }

    private Map<String, Object> mapOrderedValues(Ordered ordered ) {
        HashMap mapp = new HashMap();
        mapp.put("TOTAL",ordered.getTotal());
        mapp.put("ORDERTIME",ordered.getOrdertime());
        mapp.put("DININGTABLE", ordered.getDiningtable());
        mapp.put("PAYMENTTYPE", ordered.getPaymenttype() );
        mapp.put("ORDERSTATUS", ordered.getOrderstatus() );
        mapp.put("ORDERNUMBER", ordered.getOrderNumber() );

        return mapp;
    }
}