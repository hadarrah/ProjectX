package action;
import action.Msg;
import java.io.*;
import java.util.ArrayList;
import client.*;
import common.*;
import gui.Login_win;
import gui.Menu_controller;
import gui.win2_Controller;
/**
 * This class constructs the UI for a chat client.  It implements the
 * chat interface in order to activate the display() method.
 * Warning: Some of the code here is cloned in ServerConsole 
 */
public class ClientConsole implements ChatIF
{
	 public gui.Menu_controller mc;
	 public gui.win2_Controller win2;
	 // ArrayList<gui.ControllerI> conI=new ArrayList();
	 public gui.Login_win log;
	 public gui.Main_menu main;
	 public gui.Managment_Controller managment_c;
	 public gui.Create_PaymentAccount_Controller create_paymentA;

	
  //Class variables *************************************************
  /**
   * The default port to connect on.
   */
  final public static int DEFAULT_PORT = 5555;
  //Instance variables **********************************************
  
  /**
   * The instance of the client that created this ConsoleChat.
   */
  ChatClient client;

  
  //Constructors ****************************************************

  /**
   * Constructs an instance of the ClientConsole UI.
   *
   * @param host The host to connect to.
   * @param port The port to connect on.
   */
  public ClientConsole(String host, int port,gui.Login_win login_win) 
  {

	  
	this.log=login_win;
    try  
    {
        client= new ChatClient(host, port, this);
    } 
    catch(IOException exception) 
    {
      System.out.println("Error: Can't setup connection!"+ " Terminating client.");
      System.exit(1); 
    }
  }
  
  public void setMainWin(gui.Main_menu main)
  {
	  this.main=main;
  }
  
public void Set_Management_Controller(gui.Managment_Controller m)
  {
	  this.managment_c = m;  
  }

public void Set_CreatePaymentAccount_Controller(gui.Create_PaymentAccount_Controller cpa)
{
	  this.create_paymentA = cpa;  
}
  
  //public void SetWin2_Controller(gui.win2_Controller win2)
 // {
	 // this.conI.add(win2);  
 // }
  //Instance methods ************************************************
  
  /**
   * This method waits for input from the console.  Once it is 
   * received, it sends it to the client's message handler with the Msg(object).
   */
  public void accept(Object msg ) 
  {	 
		 try
		    {    	
		    	client.handleMessageFromClientUI(msg);      
		    }
		 catch (Exception ex) 
		    {
		      System.out.println
		        ("Unexpected error while reading from console!");
		    }
	}

  /**
   * This method overrides the method in the ChatIF interface.  It
   * sends the results to the Client
   * this method finds out who send the request and sends back.
 * @throws Exception 
   * 
   * @param.
   */
  public void displaytoGUI(Object message) 
  {
	 
	   if(message instanceof ArrayList<?>)
	   {
		//   ((Menu_controller) conI.get(0)).setNames(message);
	   }
	   if(message instanceof Msg)
		   
	   {
		   Msg check= (Msg)message;
		   
		 if(  check.getType().equals("SELECT"))
				 {
			 if(check.getRole().equals("verify user details"))
			 {
				
				 Person user=(Person) check.newO;
				 
				 //*send the return msg from the server to client->gui*/
					log.get_comfirmation(message);
				 
				 
		 	//((Login_win)conI.get(0)).get_comfirmation(message);
			 }
			 if(check.getRole().equals("check if ID exist and add payment account"))
			 {
				 				 
				 create_paymentA.check_if_create_success(message);
			 }
			// else{
				 //((win2_Controller) conI.get(2)).setDetails(message);
			// }
				 }
		 
	   }//else its an update query
  }   
  //Class methods ***************************************************
}
//End of ConsoleChat class
