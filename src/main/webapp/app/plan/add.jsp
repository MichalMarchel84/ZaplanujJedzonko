<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Add plan</title>
</head>
<body>
<div class="m-4 p-3 width-medium">
    <div class="dashboard-content border-dashed p-3 m-4 view-height">

        <form action="/app/plan/add" method="post">
            <div class="row border-bottom border-3 p-1 m-1">
                <div class="col noPadding">
                    <h3 class="color-header text-uppercase">NOWY PLAN</h3>
                </div>
                <div class="col d-flex justify-content-end mb-2 noPadding">
                    <button type="submit" class="btn btn-color rounded-0 pt-0 pb-0 pr-4 pl-4">Zapisz</button>
                </div>
            </div>

            <div class="schedules-content">

                <div class="form-group row">
                    <label for="planName" class="col-sm-2 label-size col-form-label">
                        Nazwa planu
                    </label>
                    <div class="col-sm-10">
                        <input name="name" class="form-control" id="planName" placeholder="Nazwa planu">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="planDescription" class="col-sm-2 label-size col-form-label">
                        Opis planu
                    </label>
                    <div class="col-sm-10">
                                <textarea name="description" class="form-control" rows="5" id="planDescription"
                                          placeholder="Opis planu"></textarea>
                    </div>
                </div>
            </div>

            <section>
                <div>
                    <label>Utwórz z szablonu
                        <input type="checkbox" name="useTemplate" class="checkbox" checked>
                    </label>
                </div>
                <div class="d-flex flex-row align-items-center">
                    <div class="list-margin d-flex flex-column align-items-center">
                        <h3>Dni w planie</h3>
                        <ul class="list-unstyled">
                            <c:forEach items="${days}" var="day">
                                <li>
                                    <input class="m-1 checkbox" type="checkbox" name="d${day.id}" checked>
                                    <label class="m-1">${day.name}</label>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                    <div class="list-margin d-flex flex-column align-items-center">
                        <h3>Posiłki każdego dnia</h3>
                        <ul class="list-unstyled">
                            <c:forEach begin="1" end="6" step="1" var="no">
                                <li>
                                    <c:choose>
                                        <c:when test="${no eq 1}">
                                            <input class="m-1 checkbox" type="checkbox" name="m${no}" checked>
                                            <input class="m-1" type="text" name="mn${no}"
                                                   value="Śniadanie">
                                        </c:when>
                                        <c:when test="${no eq 2}">
                                            <input class="m-1 checkbox" type="checkbox" name="m${no}" checked>
                                            <input class="m-1" type="text" name="mn${no}"
                                                   value="Obiad">
                                        </c:when>
                                        <c:when test="${no eq 3}">
                                            <input class="m-1 checkbox" type="checkbox" name="m${no}" checked>
                                            <input class="m-1" type="text" name="mn${no}"
                                                   value="Kolacja">
                                        </c:when>
                                        <c:otherwise>
                                            <input class="m-1 checkbox" type="checkbox" name="m${no}">
                                            <input class="m-1" type="text" name="mn${no}"
                                                   placeholder="Posiłek ${no}">
                                        </c:otherwise>
                                    </c:choose>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </section>
        </form>
    </div>
</div>
</body>
</html>
