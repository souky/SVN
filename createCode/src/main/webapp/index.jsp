<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String url = request.getContextPath() + "/code/initCreateCode.do";
	System.out.println(url);
	response.sendRedirect(url);
%>
