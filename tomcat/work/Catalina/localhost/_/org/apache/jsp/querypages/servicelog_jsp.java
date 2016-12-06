package org.apache.jsp.querypages;

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

public final class servicelog_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<title>Appointment Scheduling</title>\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("<div id=\"searchresult\">\n");
      out.write("<h4>\n");

	String patID = request.getParameter("patID");
	String empID = request.getParameter("empID");
	String proID = request.getParameter("proID");

	//A handle to the connection to the DBMS.

	Connection connection;

	//A handle to the statement.

	Statement statement;

	String username = "levihill";
	String password = "a3012";
	String connectString = "jdbc:oracle:thin:@aloe.cs.arizona.edu:1521:oracle";


	Class.forName("oracle.jdbc.OracleDriver");
	connection = DriverManager.getConnection(connectString, username, password);
	statement = connection.createStatement();
	ResultSet rs = null;

		//Insert new serviceLog entry
	statement.executeQuery("INSERT INTO levihill.ServiceLog (SERVID, PATID, PROID, EMPID) " +
				"VALUES ((Select MAX(SERVID)+1  FROM Levihill.ServiceLog), \'" + patID + "\', " +
					"\'" + proID + "\', \'" + empID + "\')");

		//Update Patient Balance
	rs = statement.executeQuery("select BALANCE from levihill.patient where PATID=\'" + patID +"\'");
	rs.next();
	int tempBalance = Integer.parseInt(rs.getString("BALANCE"));

	rs = statement.executeQuery("select COST from levihill.procedure where PROID=\'" + proID +"\'");
	rs.next();
	tempBalance += Integer.parseInt(rs.getString("COST"));

	statement.executeQuery("update patient set BALANCE = \'" + tempBalance + "\' where PATID=\'" + patID + "\'");

	out.write("Service event successfully logged. <br/> " + 
			"<form action=\"/login.jsp\">" +
			"<input type=\"hidden\" name=\"patID\" value=\"" + patID +"\"/>" +
			"<input type=\"submit\" value=\"Return to patient home\"/>"+
			"</form>");


	statement.close();
	connection.close();
	

      out.write(" \n");
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
