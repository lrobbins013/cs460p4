<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@page import="java.util.*,java.lang.StringBuffer,
    java.sql.Connection,java.sql.DriverManager, 
    java.sql.SQLException,java.sql.Statement,
    java.sql.ResultSet"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Procedure List</title>
</head>
<body>
<div id="searchresult">
<%
	/**************************************************************
	 * Returns a table representation of the available procedures *
	 * and the supplies required for each.                        *
         **************************************************************/

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
	ResultSet rs = statement.executeQuery("select PRONAME, COST, SUPNAME, AMOUNT, QNTY " + 
					      "from   levihill.Procedure, levihill.supplies, levihill.proSup " + 
					      "where  procedure.proID=proSup.proID and proSup.supID=supplies.supID " + 
					      "order by procedure.proname");

	out.write("<table><tr><th>COST</th><th>Procedure</th>" +
   		  "<th>Supplies</th><th>Amount</th><th>Supply Qnty</th></tr>");

	String qFName=null, qLName=null, qBalance=null;
	int i = 0;	

	while(rs.next()) {
		out.write("<tr id=\"tablerow_" + i + "\"> "+
			  "<td>$" + rs.getString("COST") + "</td> "+
			  "<td>" + rs.getString("PRONAME") + "</td> "+
			  "<td>" + rs.getString("SUPNAME") + "</td> "+
			  "<td>" + rs.getString("AMOUNT") + "</td> "+
			  "<td>" + rs.getString("QNTY") + "</td> "+
			  "</tr>");
		i++;
	}

	statement.close();
	connection.close();

	out.write("</table><form action=\"/service.jsp\">" +
		     "<input type=\"hidden\" name=\"patID\" value=\"" + request.getParameter("patID") + "\"/> " +
		     "<input type=\"submit\" value=\"Return to service info\"/> " + 
		     "</form>");
%>
</div>
</body>
</html>