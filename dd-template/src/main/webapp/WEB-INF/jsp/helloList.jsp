<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>

<c:set var="page_title" value="Hello List" scope="request" /> 
<jsp:include page="/WEB-INF/jsp/include/header.jsp" />

<center>
	<form action="search" method="post">
		<table border="0" bordercolor="#006699"
			style="border-collapse: collapse; width: 600px;">
			<tbody>
				<tr>
					<td><input onclick="javascript:go('new');" type="button" value="New Hello"></td>
					<td align="right">Enter Hello Name:<input name="name" type="text"><input type="submit" value="Search"></td>
				</tr>
			</tbody>
		</table>
	</form>
	<table border="1" bordercolor="#006699" style="border-collapse: collapse; width: 600px;">
		<tbody>
			<tr bgcolor="lightblue">
				<th>Id</th>
				<th>Name</th>
				<th>Created</th>
				<th>Modified</th>
				<th>Ver</th>
				<th></th>
			</tr>
			<c:if test="${empty helloList}">
				<tr>
					<td colspan="6">No Results found</td>
				</tr>
			</c:if>
			<c:if test="${! empty helloList}">
				<c:forEach items="${helloList}" var="hello">
					<tr>
						<td>${hello.id}</td>
						<td>${hello.name}</td>
						<td><fmt:formatDate value="${hello.created}" type="both" /></td>
						<td><fmt:formatDate value="${hello.modified}" type="both" /></td>
						<td>${hello.version}</td>
						<td><a href="edit?id=${hello.id}">Edit</a>
							<a href="javascript:confirmAndGo('Are you sure you want to delete this Hello?', 'delete?id=${hello.id}');">Delete</a>
						</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
</center>
<jsp:include page="/WEB-INF/jsp/include/footer.jsp" />
