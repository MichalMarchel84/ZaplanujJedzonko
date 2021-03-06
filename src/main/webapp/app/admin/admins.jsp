<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Użytkownicy</title>
</head>
<body>

<div class="m-4 p-3 width-medium">
    <div class="m-4 p-3 border-dashed view-height">

        <div class="row border-bottom border-3 p-1 m-1">
            <div class="col noPadding">
                <h3 class="color-header text-uppercase">LISTA UŻYTKOWNIKÓW</h3>
            </div>
            <form action="/app/super-admin-users" method="get" class="flex-row align-baseline">
                <label class="m-3">Szukaj w:</label>
                <input type="checkbox" name="columns" value="first_name" class="checkbox" ${first_name}>
                <label>imię</label>
                <input type="checkbox" name="columns" value="last_name" class="checkbox" ${last_name}>
                <label>nazwisko</label>
                <input type="checkbox" name="columns" value="email" class="checkbox" ${email}>
                <label>e-mail</label>
                <div class="flex-row align-center m-2">
                    <input type="text" name="searchTxt" value="<c:out value="${queryTxt}"/>" class="search-height">
                    <button type="submit" class="btn btn-color rounded-0 pt-0 pb-0 pr-4 pl-4 search-height"><i
                            class="fa fa-search"></i></button>
                </div>
            </form>
        </div>

        <div class="schedules-content">
            <table class="table">
                <thead>
                <tr class="d-flex">
                    <th class="col-1">ID</th>
                    <th class="col-3">IMIĘ</th>
                    <th class="col-6">NAZWISKO</th>
                    <th class="col-2 center">AKCJE</th>
                </tr>
                </thead>
                <tbody class="text-color-lighter">

                <c:forEach var="admin" items="${adminMap}">
                    <tr class="d-flex">
                        <td class="col-1">${admin.key.id}</td>
                        <td class="col-3"><c:out value="${admin.key.firstName}"/></td>
                        <td class="col-6"><c:out value="${admin.key.lastName}"/></td>
                        <c:choose>
                            <c:when test="${admin.value==1}">
                                <td class="col-2 center">
                                    <a href="/app/modifyadmin?action=block&id=${admin.key.id}"
                                       class="btn btn-danger rounded-0 text-light m-1">Blokuj</a>
                                </td>
                            </c:when>
                            <c:when test="${admin.value==0}">
                                <td class="col-2 center">
                                    <a href="/app/modifyadmin?action=unblock&id=${admin.key.id}"
                                       class="btn btn-success rounded-0 pt-0 pb-0 pr-4 pl-4">Odblokuj</a>
                                </td>
                            </c:when>
                            <c:when test="${admin.value==3}">
                            <td class="col-2 center">
                                Administrator
                            </td>
                            </c:when>
                        </c:choose>
                    </tr>
                </c:forEach>

                </tbody>
            </table>

        </div>
    </div>
</div>

</body>
</html>
