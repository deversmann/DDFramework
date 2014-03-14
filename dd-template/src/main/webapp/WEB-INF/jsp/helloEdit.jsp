<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>

<c:if test="${ ! empty helloNew }">
	<c:set var="page_title" value="New Hello" scope="request" />
	<c:set var="helloEntity" value="${ helloNew }" />
	<c:set var="commandName" value="helloNew" />
	<c:set var="action" value="save" />
</c:if> 
<c:if test="${ ! empty helloEdit }">
	<c:set var="page_title" value="Edit Hello" scope="request" />
	<c:set var="helloEntity" value="${ helloEdit }" />
	<c:set var="commandName" value="helloEdit" />
	<c:set var="action" value="update" />
</c:if> 

<jsp:include page="/WEB-INF/jsp/include/header.jsp" />

<table  bgcolor="lightblue" width="750" align="center" style="border-collapse: collapse;" border="1" bordercolor="#006699" >
    <tr>
        <td align="center"><h3>${ page_title } Form</h3></td>
    </tr>
    <tr valign="top" align="center">
    <td align="center">
         <form:form action="${ action }" method="post" commandName="${ commandName }">
            
                <table width="500" style="border-collapse: collapse;" border="0" bordercolor="#006699" cellspacing="2" cellpadding="2">    
                        <c:if test="${ commandName eq 'helloEdit' }">
                    <tr>
                        <td width="100" align="right">Id</td>
                        <td width="150">
                        	<form:input path="id" readonly="true"  />
                        <td align="left">
                        <form:errors path="id" cssStyle="color:red"></form:errors>
                        </td>
                    </tr>
                        </c:if>
                    <tr>
                        <td width="100" align="right">Name</td>
                        <td width="150">
                        <form:input path="name"/></td>
                        <td align="left">
                        <form:errors path="name" cssStyle="color:red"></form:errors>
                        </td>
                    </tr>
                    
                    <tr>
                        <td colspan="3" align="center">
                        <c:if test="${ commandName eq 'helloEdit' }">
                        	<input type="button" value="Delete" onclick="javascript:confirmAndGo('Are you sure you want to delete this Hello?', 'delete?id=${helloEntity.id}');" />
                        </c:if>
                        <input type="submit" name="" value="Save">
                          
                        <input type="reset" name="" value="Reset">
                          
                        <input type="button"  value="Back" onclick="javascript:go('list');">
                        </td>
                    </tr>                    
                </table>            
        </form:form>
    </td>    
  </tr>
</table>
<jsp:include page="/WEB-INF/jsp/include/footer.jsp" />
