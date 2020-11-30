package shop;

import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Map;

public class ShopDB {

    Connection con;
    static int nOrders = 0;
    static ShopDB singleton;

    public static void main(String[] args) throws Exception  {
        // simple method to test that ShopDB works
        System.out.println("Got this far...");
        ShopDB db = new ShopDB();
        System.out.println("created shop db");
        Basket basket = new Basket();
        System.out.println("created the basket");

        System.out.println("Testing getAllProducts");
        Collection c = db.getAllProducts();
        for (Iterator i = c.iterator(); i.hasNext() ; ) {
            Product p = (Product) i.next();
            System.out.println( p );
        }
        System.out.println("Testing getProduct(pid)");
        Product product = db.getProduct("art1");
        System.out.println( product );

        System.out.println("Testing order: ");
        basket.addItem( product );
        System.out.println("added an item "+basket.getTotalString());
        basket.addItem( product );
        System.out.println("added an item "+basket.getTotalString());
        basket.subItem( product );
        System.out.println("removed an item "+basket.getTotalString());
        db.order( basket , "Simon" );
        System.out.println("order done");

    }

    public ShopDB() {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            System.out.println("loaded class");
            con = DriverManager.getConnection("jdbc:hsqldb:file:\\tomcat\\webapps\\ass2\\shopdb", "sa", "");
            System.out.println("created con");
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    public static ShopDB getSingleton() {
        if (singleton == null) {
            singleton = new ShopDB();
        }
        return singleton;
    }

    public Collection<Product> getProducts(String artist,String title) {
        if (artist!=null)
        {
            System.out.println("artist="+artist);
            if (artist.matches("[a-zA-Z]+"))
            {
                return findArtist(artist);
            }
            return new LinkedList<>();
        }
        if (title!=null)
        {
            System.out.println("title="+title);
            if (title.matches("[a-zA-Z]+"))
            {
                return findProducts(title);
            }
            return new LinkedList<>();
        }
        return getAllProducts();
    }

    public Collection<Product> findArtist(String artist) {
        try {
            PreparedStatement ps = con.prepareStatement(
                    "Select * from Product where UCASE(ARTIST ) = ?");
            ps.setString(1, artist.toUpperCase());
            return loadProducts(ps.executeQuery());
        }
        catch(Exception e) {
            // unable to find the product matching that name
            return null;
        }
    }

    public Collection<Product> findProducts(String title)
    {
        try {
            PreparedStatement ps = con.prepareStatement(
                    "Select * from Product where UCASE(TITLE) like ?");
            ps.setString(1, "%"+title.toUpperCase()+"%");
            return loadProducts(ps.executeQuery());
        }
        catch(Exception e) {
            // unable to find the product matching that title
            return null;
        }
    }

    private Collection<Product> loadProducts(ResultSet rs) throws SQLException
    {
        LinkedList<Product> list = new LinkedList<>();
        while ( rs.next() ) {
            Product product = new Product(
                    rs.getString("PID"),
                    rs.getString("Artist"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getInt("price"),
                    rs.getString("thumbnail"),
                    rs.getString("fullimage")
            );
            list.add( product );
        }
        return list;
    }


    public Collection<Product> getAllProducts() {
        return getProductCollection("Select * from Product");
    }

    public Product getProduct(String pid) {
        try {
            // re-use the getProductCollection method
            // even though we only expect to get a single Product Object
            String query = "Select * from Product where PID = '" + pid + "'";
            Collection<Product> c = getProductCollection( query );
            Iterator<Product> i = c.iterator();
            return i.next();
        }
        catch(Exception e) {
            // unable to find the product matching that pid
            return null;
        }
    }

    public Collection<Product> getProductCollection(String query) {
        try {
            Statement s = con.createStatement();
            return loadProducts(s.executeQuery(query));
        }
        catch(Exception e) {
            System.out.println( "Exception in getProductCollection(): " + e );
            return null;
        }
    }

    public void order(Basket basket , String customer) {
        try {
            // create a unique order id
            String orderId = System.currentTimeMillis() + ":" + nOrders++;

            // iterate over the basket of contents ...

            for(Map.Entry<Product,Integer> i: basket.items.entrySet())
            {
                Product product = i.getKey();
                // and place the order for each one
                order( con, product, orderId, customer ,i.getValue());
            }
            con.commit();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }

    private void order(Connection con, Product p, String orderId, String customer,int quantity) throws Exception {
        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO ORDERS (PID,ORDERID,EMAIL,QUANTITY,PRICE) values(?,?,?,?,?)");
        ps.setString(1, p.PID);
        ps.setString(2, orderId);
        ps.setString(3, customer);
        ps.setInt(4, quantity);
        ps.setInt(5, p.price);
        ps.executeUpdate();
    }

}
