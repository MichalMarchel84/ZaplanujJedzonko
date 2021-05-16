<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>Add plan</title>
</head>
<body>
<div class="m-4 p-3 width-medium">
    <div class="dashboard-content border-dashed p-3 m-4 view-height">

        <form action="/app/plan/edit" method="post">
            <input type="hidden" name="id" value="${plan.id}">
            <div class="row border-bottom border-3 p-1 m-1">
                <div class="col noPadding">
                    <h3 class="color-header text-uppercase">EDYCJA PLANU</h3>
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
                        <input name="name" class="form-control" id="planName" value="${fn:escapeXml(plan.name)}">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="planDescription" class="col-sm-2 label-size col-form-label">
                        Opis planu
                    </label>
                    <div class="col-sm-10">
                        <textarea name="description" class="form-control" rows="5"
                                  id="planDescription"><c:out value="${plan.description}"/></textarea>
                    </div>
                </div>
                <div class="schedules-content">
                    <c:forEach items="${details}" var="entry">
                        <table class="table">
                            <thead>
                            <tr class="d-flex">
                                <th class="col"><c:out value="${entry.key.name}"/></th>
                            </tr>
                            </thead>
                            <tbody class="text-color-lighter">
                            <c:forEach items="${entry.value}" var="meal">
                                <tr class="d-flex">
                                    <td class="col-1 align-center"><input class="meal-order center" name="${meal.id}-displayOrder" type="number"
                                               value="${fn:escapeXml(meal.displayOrder)}">
                                    </td>
                                    <td class="col-md-auto"><input class="center" name="${meal.id}-mealName" type="text"
                                                             value="${fn:escapeXml(meal.mealName)}"></td>
                                    <td class="col-md-auto">
                                        <select name="${meal.id}-recipeId">
                                            <c:forEach items="${recipes}" var="recipe">
                                                <option value="${recipe.id}"
                                                        <c:if test="${recipe.id eq meal.recipeId}">
                                                            selected
                                                        </c:if>>
                                                    <c:out value="${recipe.name}"/>
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td class="col"></td>
                                    <td class="col-1">
                                        <input class="checkbox" name="${meal.id}-delete" type="checkbox">
                                        <label>Usuń</label>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </c:forEach>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>
