# ProjectX
Collage project - Semester 5 - "Zer-Li" application

Design Conventions:

-Font:  *Default: System 12px
	*Special font: Arial Bold Italic

-Font Colors:   *Green [#29940c]
		*Red   [#eb0a0a]


- Window Size:  *Default - 500x600 (width x height) [pixels].

- Acceptance Buttons (√ ,yes, ok, order, add, etc..) will be on the RIGHT.
- Denial Buttons (Ꭓ, not, cancel, back, etc..) on LEFT.


-back button position: x=35 , y=366


-Common Method:

move to other window (implementation):

/**
     * General function for the movement between the different windows
     * @param event
     * @param next_fxml = string of the specific fxml
     * @throws IOException
     */
    public void move(ActionEvent event, String next_fxml)throws IOException 
	{
		  Parent menu;
		  menu = FXMLLoader.load(getClass().getResource(next_fxml));
		 Scene win1= new Scene(menu);
		 Stage win_1= (Stage) ((Node) (event.getSource())).getScene().getWindow();
		 win_1.setScene(win1);
		 win_1.show();
	}
  
Example of calling:
  move(event,main.fxmlDir+ "Managment_F.fxml");


-Main, Prev and Next controllers:

		ControllerI prevPage, nextPage;
   		prevPage = Login_win.to_Client.mc;		//(where we came from)
   		nextPage = (IController)MyNextPage;		//next page controller if needed.
Do Always:	Login_win.to_Client.setController(this);	//Initialize current controller.


toAdd:
a log file ,that recod the server action.
the system manger can see this file
