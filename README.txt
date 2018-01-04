# ProjectX
Collage project - Semester 5 - "Zer-Li" application

Design Conventions:

-Font:  *Default: System 12px
	*Special font: Arial Bold Italic

-Font Colors:   *Green [#29940c]
		*Red   [#eb0a0a]
		
-Titles:	Font: Arial Black
		Size: 28px
		Color: #0d0d0c


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
		 
		  //close window by X button
		 win_1.setOnCloseRequest(new EventHandler<WindowEvent>() {
	          public void handle(WindowEvent we) {
	        	  Msg  msg=new Msg();
	      		Person user_logout=current_user;
	      		msg.setRole("user logout");
	      		msg.setTableName("person");
	      		msg.setUpdate();
	      		msg.oldO=user_logout;
	      		Login_win.to_Client.accept(msg);
	          }
	      });        
	}
  
Example of calling:
  move(event,main.fxmlDir+ "Managment_F.fxml");

showPopUpMessage:
Optional<ButtonType> option = Login_win.showPopUp(type="CONFIRMATION", title="LogOut", header="Are you sure...", content="blabla");

if you don't need one of the field => insert empty string ""


-Main, Prev and Next controllers:

		ControllerI prevPage, nextPage;
   		prevPage = Login_win.to_Client.mc;		//(where we came from)
   		nextPage = (IController)MyNextPage;		//next page controller if needed.
Do Always:	Login_win.to_Client.setController(this);	//Initialize current controller.


toAdd:
a log file ,that recod the server action.
the system manger can see this file
to view profile -> purcahse  history
