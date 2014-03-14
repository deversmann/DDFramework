<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>

<c:set var="page_title" value="Hello World" scope="request" /> 
<jsp:include page="/WEB-INF/jsp/include/header.jsp" />
${page_title}
<jsp:include page="/WEB-INF/jsp/include/footer.jsp" />
