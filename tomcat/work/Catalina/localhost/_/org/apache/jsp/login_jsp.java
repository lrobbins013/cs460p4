package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import java.lang.StringBuffer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public final class login_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = JspFactory.getDefaultFactory().getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    JspFactory _jspxFactory = null;
    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      _jspxFactory = JspFactory.getDefaultFactory();
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\n");
      out.write("\n");
      out.write("<html>\n");
      out.write("<head>\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("<title>Patient Homepage</title>\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("<div id=\"searchresult\">\n");
      out.write("<h4>\n");

	String firstName = request.getParameter("firstName");
	String lastName = request.getParameter("lastName");

	//Parameter given if returning from appointment scheduling
	String patID = request.getParameter("patID");

	//out.write("Patient: " + firstName + " " + lastName);

	//A handle to the connection to the DBMS.

	Connection connection;

	//A handle to the statement.

	Statement statement;

	String username = "lrobbins013";
	String password = "a1106";
	String connectString = "jdbc:oracle:thin:@aloe.cs.arizona.edu:1521:oracle";


	Class.forName("oracle.jdbc.OracleDriver");
	connection = DriverManager.getConnection(connectString, username, password);
	statement = connection.createStatement();
	ResultSet rs = null;

	if (firstName != null) {
		rs = statement.executeQuery("select * from levihill.Patient where  FIRSTNAME=\'" + firstName + "\' " + 
                                                      "and  LASTNAME=\'" + lastName + "\'");
	}
	else {
		rs = statement.executeQuery("select * from levihill.Patient where  PATID=\'" + patID + "\'");
	}

	String qPatID=null, qFName=null, qLName=null, qBalance=null;
	
	while(rs.next()) {
		qPatID = rs.getString("PATID");
		qFName = rs.getString("FIRSTNAME");
		qLName = rs.getString("LASTNAME");
		qBalance = rs.getString("BALANCE");
	}

	if (qFName == null) {
		out.write("Patient \"" + firstName + " " + lastName + "\" not found.");
	}
	else {
		out.write("Patient: " + qFName + " " + qLName + " <br/> Balance: $" + qBalance + " <br/> ");
	}

	statement.close();
	connection.close();
	

      out.write(" \n");
      out.write("<br>\n");

	if (qFName != null) {
		out.write("<form action=\"appointment.jsp\">"+
				"<input type=\"hidden\" name=\"patID\" value=\"" + qPatID + "\"/>" +
			   	"<input type=\"submit\" value=\"Schedule an appointment\"/>"+
			   "</form>");
		out.write("<form action=\"/querypages/current_appointments.jsp\">"+
				"<input type=\"hidden\" name=\"patID\" value=\"" + qPatID + "\"/>" +
			   	"<input type=\"submit\" value=\"Check current appointments\"/>"+
			   "</form>");
		out.write("<form action=\"/querypages/cancellation.jsp\">"+
				"<input type=\"hidden\" name=\"patID\" value=\"" + qPatID + "\"/>" +
			   	"<input type=\"submit\" value=\"Cancel an appointment\"/>"+
			   "</form>");
		out.write("<form action=\"/querypages/payment.jsp\">"+
				"<input type=\"hidden\" name=\"patID\" value=\"" + qPatID + "\"/>" +
			   	"<input type=\"submit\" value=\"Make a payment\"/>"+
			   "</form>");
		out.write("<form action=\"/querypages/transaction_history.jsp\">"+
				"<input type=\"hidden\" name=\"patID\" value=\"" + qPatID + "\"/>" +
			   	"<input type=\"submit\" value=\"View Transaction History\"/>"+
			   "</form>");
		out.write("<form action=\"service.jsp\">"+
				"<input type=\"hidden\" name=\"patID\" value=\"" + qPatID + "\"/>" +
			   	"<input type=\"submit\" value=\"Service information\"/>"+
			   "</form>");
		out.write("<form action=\"index.html\">"+
			   	"<input type=\"submit\" value=\"Logout\"/>"+
			   "</form>");
	}

      out.write("\n");
      out.write("</h4>\n");
      out.write("</div>\n");
      out.write("</body>\n");
      out.write("</html>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      if (_jspxFactory != null) _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
