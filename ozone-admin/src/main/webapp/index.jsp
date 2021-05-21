<html>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
	<meta http-equiv="Pragma" content="no-cache" />
	<!-- <meta http-equiv="Expires" content="0" /> -->
	
	<c:choose>
	    <c:when test="${param.type == 'timeout'}">
	        <p>Your session has been timed out. Redirecting to the login page.</p>
	        <jsp:forward page="login.faces"/> 
	    </c:when>
    	<c:otherwise>
        	<jsp:forward page="login.faces"/>
    </c:otherwise>
	</c:choose>
</html>