<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<% request.setCharacterEncoding("UTF-8"); %>
<% response.setCharacterEncoding("UTF-8");%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="../../../js/address.js" type="application/javascript"></script>
    <style type="text/css">
       #container {
           display: flex;
           background-color: #ddf3fa;
           border: darkred 1px solid;
       }
       #main {
           padding: 12px;
           flex: auto;
           border-right: darkred 1px solid;
       }
       #message {
           display: inline;
       }
       #actions {
           padding: 12px;
           flex: 40%;
       }
    </style>
    <title>Address</title>
</head>
<body>
<div id="container">
    <div id="main">
        <h2>Znalezione adresy:</h2>
        <c:forEach items="${addressList}" var="address">
            <form:form id="address${address.addressId}" method="POST" action="" modelAttribute="address">
            <table>
                <tr>
                    <label for="country">Państwo:</label><br>
                    <input type="text" id="country" name="country" value="${address.country}"><br>
                    <input type="hidden" id="addressId" name="addressId" value="${address.addressId}">
                </tr>
                <tr>
                    <label for="state">Wewództwo:</label><br>
                    <input type="text" id="state" name="state" value="${address.state}"><br>
                </tr>
                <tr>
                    <label for="zip">Kod pocztowy:</label><br>
                    <input type="text" id="zip" name="zip" value="${address.zip}"><br>
                </tr>
                <tr>
                    <label for="city">Miasto:</label><br>
                    <input type="text" id="city" name="city" value="${address.city}"><br>
                </tr>
                <tr>
                    <label for="streetName">Ulica:</label><br>
                    <input type="text" id="streetName" name="streetName" value="${address.streetName}"><br>
                </tr>
                <tr>
                    <label for="houseNumber">Nr domu/mieszkania:</label><br>
                    <input type="text" id="houseNumber" name="houseNumber" value="${address.houseNumber}"><br>
                </tr>
                <tr>
                    <td><input type="button" class="goToEdit" value="Edytuj"/></td>
                </tr>
            </table>
            </form:form>
        </c:forEach>
    </div>
    <div id="actions"><p></p></div>
</div>
</body>
</html>
