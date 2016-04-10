/*
 *  @author: Ginger Nix
 *  
 *  This servlet gets the building record id from the RoomsServlet -> rooms.jsp which
 *  links to this servlet.
 *  
 */


package controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import helpers.RoomsSelectQuery;
import model.Admin;

/**
 * Servlet implementation class RoomsListServlet
 */
@WebServlet("/RoomsListServlet")
public class RoomsListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session; 
  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RoomsListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = "";
		String table = "";

		//get our current session
		session = request.getSession();

		// create admin user object w/ session data on the logged in user's info
		Admin loggedInAdminUser = (Admin) session.getAttribute("loggedInAdminUser");

		System.out.println("RoomsListServlet: logged in admin user adminMyID = " + loggedInAdminUser.getAdminMyID());
		int buildingID;

		// get building id from form - it's the id of the building they selected		
		//buildingID = Integer.parseInt(request.getParameter("buildingList")); 
		buildingID = Integer.parseInt(request.getParameter("buildingID")); //from list of buildings - user clicked on view rooms

		
		// NEED THIS BLOCK??
/*		if (buildingID == 0) {
			System.out.println("RoomListServlet: buildingID = 0");
			buildingID = Integer.parseInt(request.getParameter("buildingID"));
			System.out.println("RoomListServlet: NEW buildingID = " + buildingID);
		}*/
		
		
		System.out.println("Rooms List Servlet: building id = " + buildingID);
		
		// using building id, create a table of a list of all the rooms in that building
		RoomsSelectQuery rsq = new RoomsSelectQuery();
		table = rsq.ListRoomsInBuilding(buildingID);
		
		
		//forward our request along
		request.setAttribute("table", table);
		request.setAttribute("loggedInAdminUser", loggedInAdminUser);

		url = "admin/roomsList.jsp";	
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);

	
		
	}

}
