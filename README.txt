# ProjectX
Collage project - Semester 5 - "Zer-Li" application
/********************************************************************************************************/
qustion to mohi !!
1. are there any survey qustions that i can use   in the client survey ?
2. According to the user story the number of answers should be 10( radio button from 1 to 10)
3. If someone know the answers to ^^^thos^^^^ qustion please reply !!!-->netanel8884@gmail.com

4. good  night!
5.יצאתי קצת רומן 
/*****************************************************************************************************/






Design Conventions:

-Font:  *Default: System 12px
	*Special font: Arial Bold Italic

-Font Colors:   *Green [#25a829]
		*Red   [#ab0909]


- Window Size:  *Default - 471x402 (width x height) [pixels].
		*Bigger  - 


- Acceptance Buttons (√ ,yes, ok, order, add, etc..) will be on the LEFT.
- Denial Buttons (Ꭓ, not, cancel, back, etc..) on RIGHT.


-back button position: x=35 , y=366




Usability Principles: (Lecture 7)

1. Base UI designs on users’ tasks.
2. Do not rely only on usability guidelines – always test with users.
3. Ensure that the sequences of actions to achieve a task are as simple as possible.
4. Ensure that the user always knows what he or she can and should do next.
5. Provide good feedback including effective error messages.
6. Ensure that the user can always get out, go back or undo an action.
7. Ensure that response time is adequate.
8. Use understandable encoding techniques.
9. Ensure that the UI’s appearance is uncluttered.
10. Consider the needs of different groups of users.
11. Provide all necessary help.
12. Be consistent.


Common Method:

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

if neeeded	ControllerI prevPage, nextPage;
   ""		prevPage = Login_win.to_Client.mc;		//save prev page controller (where we came from)
   ""		nextPage = (IController)MyNextPage;		//next page controller if needed.
Do Always:	Login_win.to_Client.setController(this);	//Initialize current controller.
