/*
 * @author: Ginger Nix
 * 
 * This servlet creates a form with all the room's data pre-populated
 * in it for the admin to edit.
 * 
*/

package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import helpers.RoomsSelectQuery;
import model.Admin;
import model.DbConnect;

/**
 * Servlet implementation class RoomEditServlet
 */
@WebServlet("/RoomEditServlet")
public class RoomEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session; 
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RoomEditServlet() {
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
		String message = "";
			
		// get the current session
		session = request.getSession(false);
	
		
		// check to see if there is a valid session
		if (session != null){ // there is an active session

			// get admin user object from session
			Admin loggedInAdminUser = (Admin) session.getAttribute("loggedInAdminUser"); 
			if (loggedInAdminUser != null){
				
				// get info for the currently logged in admin user.
				String role = loggedInAdminUser.getRole();
				int status = loggedInAdminUser.getAdminStatus();
				
				// push content based off role
				if((role.equalsIgnoreCase("A") || role.equalsIgnoreCase("S")) && status == 1){
				
					message = (String) request.getAttribute("message"); 
				
					// blank the message if nothing gotten in message attribute
					if (message == null || message.isEmpty()) {
						 message = "";
					}				
	
					// hidden parameters to use and to pass on
					int roomID = Integer.parseInt(request.getParameter("roomID")); 
					int buildingID = Integer.parseInt(request.getParameter("buildingID")); 
					String cancelAction = request.getParameter("cancelAction"); 
					
					System.out.println("RoomEditServlet: roomID = " + roomID);
					
					// create a pre-populated form for the admin to edit a room
					RoomsSelectQuery rsq = new RoomsSelectQuery();	
					table = rsq.createEditRoomForm(roomID, cancelAction);					
					
					//forward our request along
					request.setAttribute("table", table);
					request.setAttribute("message", message);
					request.setAttribute("loggedInAdminUser", loggedInAdminUser);
			
					url = "admin/roomEdit.jsp";	
						
						
				}  else if (role.equalsIgnoreCase("C") && status == 1){ 
					//------------------------------------------------//
					/*                VIEW FOR CLERK                  */
					//------------------------------------------------//
					
					// forwarding URL
					url = "AdminViewReservations";
					
				} else {
					//------------------------------------------------//
					/*              NOT A VALID ROLE                  */
					//------------------------------------------------//
					// if a new session is created with no user object passed
					// user will need to login again
					session.invalidate();
					
					response.sendRedirect(DbConnect.urlRedirect());
					return;
				}
			} else {
				//------------------------------------------------//
				/*            ADMIN USER INFO EXPIRED             */
				//------------------------------------------------//
				// if a new session is created with no user object passed
				// user will need to login again
				session.invalidate();
				
				response.sendRedirect(DbConnect.urlRedirect());
				return;
			}
		
		} else { // there isn't an active session (session == null).
			//------------------------------------------------//
			/*        INVALID SESSION (SESSION == NULL)       */
			//------------------------------------------------//
			// if session has timed out, go to home page
			// the site should log them out.
			
			response.sendRedirect(DbConnect.urlRedirect());
			return;
		}
					
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	
	}

}
