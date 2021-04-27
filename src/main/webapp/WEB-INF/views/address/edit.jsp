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
        <h3>Adres: <p id="message"></p></h3>
        <form:form id="address" method="POST" action="" modelAttribute="address">
            <table>
                <tr>
                    <td><form:label path="country">Państwo:</form:label></td>
                    <td><form:input path="country" autocomplete="off" />
                        <form:input path="addressId" type="hidden" autocomplete="off"/>
                    </td>
                </tr>
                <tr>
                    <td><form:label path="zip">Kod pocztowy:</form:label></td>
                    <td><form:input path="zip" autocomplete="off"/></td>
                </tr>
                <tr>
                    <td><form:label path="state">Województwo</form:label></td>
                    <td><form:input path="state" autocomplete="off"/></td>
                </tr>
                <tr>
                    <td><form:label path="city">Miasto</form:label></td>
                    <td><form:input path="city" autocomplete="off"/></td>
                </tr>
                <tr>
                    <td><form:label path="streetName">Ulica:</form:label></td>
                    <td><form:input path="streetName" autocomplete="off"/></td>
                </tr>
                <tr>
                    <td><form:label path="houseNumber">Nr domu/mieszkania:</form:label></td>
                    <td><form:input path="houseNumber" autocomplete="off"/></td>
                </tr>
            </table>
        </form:form>
            <table>
                <tr>
                    <td><input type="button" id="edit" value="Edytuj"/></td>
                    <td><input type="button" id="save" value="Zapisz"/></td>
                    <td><input type="button" id="delete" value="Usuń"/></td>
                    <td><input type="button" id="find" value="Szukaj"/></td>
                </tr>
            </table>
    </div>
    <div id="actions"><p></p></div>
</div>

</body>
</html>