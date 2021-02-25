package com.jspcrud.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jspcrud.dao.User_DAO;
import com.jspcrud.modal.User;

/**
 * Servlet implementation class addUser
 */
public class addUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		User user = new User();
		int sr_no = 0;
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		long number = Long.parseLong(request.getParameter("number"));
		
		
		try {
//			method to get the max sr_no
//			this method is to get the max value of the sr_no from the table and increase it by 1 so that new record will store with unique id
					Connection con = User_DAO.gerConnection();
					PreparedStatement ps = con.prepareStatement("select max(sr_no) from crud_user");
					ResultSet rs = ps.executeQuery();
					if (rs.next()) {
						sr_no = rs.getInt(1);
						sr_no++;
						
						user.setSr_no(sr_no);
						user.setName(name);
						user.setEmail(email);
						user.setPassword(password);
						user.setNumber(number);
						
						int i = User_DAO.insert(user);
						
						if (i == 1) {
							out.print("User Added");
							request.getRequestDispatcher("/index.jsp").include(request, response);
						} else {
							out.print("Failed");
							request.getRequestDispatcher("/index.jsp").include(request, response);
						}
					}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
