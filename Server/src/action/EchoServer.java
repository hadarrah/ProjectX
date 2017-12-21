package action;

// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
 
import java.io.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class EchoServer extends AbstractServer 
{
  //Class variables *************************************************
  
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;
  final public static String pass ="root";
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port) 
  {
    super(port);
  } 

  
  //Instance methods ************************************************
  
  /**
   * This method handles any messages received from the client.
   * This method check which kind of query arrived from client
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient(Object msg, ConnectionToClient client)
  {	 
	  		 Msg msg1=(Msg)msg;
	       try 
			{
	          Class.forName("com.mysql.jdbc.Driver").newInstance();
	        } catch (Exception ex) {/* handle the error*/}
	        
	        try 
	        {      	 
	            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/hw2","root","hadar");
	         	         
	            System.out.println("SQL connection succeed");
	            /* Define which kind the message the server got */
	          if(msg1.getType().equals("SELECTALL"))
	        	  ViewItems(conn,client);
	          
	          else if(msg1.getType().equals("SELECT")) 
	        	 getProdectdetails(msg1,conn,client);
	         
	          else if (msg1.getType().equals("UPDATE"))	          
	        	  UpdateItem(conn,msg,client);	         
	        }  
 			
	        catch (SQLException ex) {
	     	     /* handle any errors*/
	             System.out.println("SQLException: " + ex.getMessage());
	             System.out.println("SQLState: " + ex.getSQLState());
	             System.out.println("VendorError: " + ex.getErrorCode());
	        }      
	          
}  
  						
	  
	   /**
	    * send a query to DB ->Select all
	    * returns the names of the products as an Object(ArrayList<String>)
	    * @param con
	    * @param client
	    */
	public static void ViewItems(Connection con ,ConnectionToClient client)
	{
		 Statement stmt;
		   Object temp= new ArrayList<String>() ;   
		   try {
		    
		   stmt = con.createStatement();
		   ResultSet rs = stmt.executeQuery("SELECT ProductName FROM hw2.product");
		    while(rs.next())
		    {
		     ((ArrayList<String>) temp).add(rs.getString(1));    
		    }
		    client.sendToClient(temp);
		   }
		      catch (SQLException e) {e.printStackTrace();}
		    catch(IOException x) {System.err.println("unable to send msg to client");}
	}
  
	/**
	 * this method gets the product name and change it.
	 * gets two Objects(product)
	 * @param con
	 * @param msg
	 * @param client
	 */
	public static void UpdateItem(Connection con,Object msg,ConnectionToClient client)
	{
		String ans="Update done";
		Msg msg1=(Msg)msg;
		 Product p_old=(Product) msg1.oldO;
		 Product p_new=(Product)msg1.newO;
		 try {
		PreparedStatement ps=con.prepareStatement("UPDATE hw2.product SET ProductId=?,ProductName=?,ProductType=? WHERE ProductName=?"); 
		
		/*insert the names to the query*/
		ps.setString(1,p_new.GetID());
		ps.setString(2,p_new.GetName());
		ps.setString(3,p_new.GetType());
	 	ps.setString(4,p_old.GetName());
		 
		ps.executeUpdate(); 
		
		client.sendToClient(ans);
		
		 }
		 catch (SQLException e) {
			 e.printStackTrace();
		 try {
			client.sendToClient("UPDATE faild");
		} catch (IOException e1) {
			e1.printStackTrace();
		}}
		catch(IOException x) {System.err.println("unable to send msg to client");}

	}
	/**
	 * this method gets the name of a product 
	 * sends back the products details
	 * @param msg
	 * @param con
	 * @param client
	 */
	public static void getProdectdetails(Object msg,Connection con,ConnectionToClient client)
	{		  
		Msg msg1=(Msg)msg;		
		Product p= (Product)msg1.oldO;		
		try { 
		
			/*send a query with the product name as a parameter*/
			PreparedStatement ps = con.prepareStatement("SELECT * FROM hw2.product where ProductName=?");			
			ps.setString(1,p.GetName());			
			ResultSet r=ps.executeQuery();				
			 while(r.next())
			 { 		
				 p.SetID(r.getString(1));
				 p.SetName(r.getString(2));
				 p.SetType(r.getString(3));				
				 msg1.oldO=p;				
			 } 
			 /*back to client*/
			client.sendToClient(msg1);
		 	r.close();
			}
		  catch(IOException x) {System.err.println("unable to send msg to client");}
		  catch (SQLException ex) 
			
		    {/* handle any errors*/
	    System.out.println("SQLException: " + ex.getMessage());
	    System.out.println("SQLState: " + ex.getSQLState());
	    System.out.println("VendorError: " + ex.getErrorCode());
	    }
		}
		
/**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    System.out.println
      ("Server listening for connections on port " + getPort());
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
    System.out.println
      ("Server has stopped listening for connections.");
  }
  
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of 
   * the server instance (there is no UI in this phase).
   *
   * @param args[0] The port number to listen on.  Defaults to 5555 
   *          if no argument is entered.
   */
  public static void main(String[] args) 
  {
    int port = 0; //Port to listen on
    try
    {
      port = Integer.parseInt(args[0]); //Get port from command line
    }
    catch(Throwable t)
    {
      port = DEFAULT_PORT; //Set port to 5555
    }
	
    EchoServer sv = new EchoServer(port);
    
    try  
    {
      sv.listen(); //Start listening for connections
    } 
    catch (Exception ex) 
    {
      System.out.println("ERROR - Could not listen for clients!");
    }
  }


 
}
//End of EchoServer class
