<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<% response.setCharacterEncoding("UTF-8");%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="../../../js/breeder.js" type="application/javascript"></script>
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
        <form:form id="breeder" method="POST" action="" modelAttribute="breeder">
            <table>
                <tr>
                    <td><form:label path="name">Hodowca:</form:label></td>
                    <td><form:input path="name" autocomplete="off" />
                        <form:input path="breederId" type="hidden" autocomplete="off"/>
                    </td>
                </tr>
            </table>
            <table>
                <tr> Znane adresy:
                    <td><c:forEach items="${addressList}" var="address">
                        <div>
                                ${address.country}, ${address.state}, ${address.zip}, ${address.city}, ${address.streetName}, ${address.houseNumber}
                        </div>
                    </c:forEach></td>
                </tr>
            </table>
        </form:form>


            <table>
                <tr>
                    <td><input type="button" id="edit" value="Edytuj"/></td>
                    <td><input type="button" id="save" value="Zapisz"/></td>
                    <td><input type="button" id="delete" value="Usuń"/></td>
                    <td><input type="button" id="find" value="Szukaj"/></td>
                    <td><input type="button" id="addAddress" value="Dodaj adres"/></td>
                </tr>
            </table>
    </div>
    <div id="actions"><p></p></div>
</div>

</body>
</html>