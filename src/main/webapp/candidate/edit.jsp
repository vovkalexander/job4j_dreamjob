<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="ru.job4j.dream.model.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>

    <title>Работа мечты</title>
</head>
<body>
<script src="https://code.jquery.com/jquery-3.4.1.min.js" ></script>
<script>
    function  validate() {
        var value = "";
        if ($('#name').val() == '') {
            value = "name ";
        }
        if ($('#photoId').val() == '') {
            value = value + "photoId ";
        }
        if ($('#city').val() == '') {
            value = value + "cityId ";
        }
        if (value!= "") {
            alert("Fill up field: " + value);
        }
        return  value == "" ;
    }
</script>

<script>
    function chooseCity() {
        var $select = $('#city');
        $.ajax({
            type: 'GET',
            crossdomain: true,
            url: 'http://localhost:8080/job4j_dreamjob_war_exploded/city.do',
            dataType: 'json'
        }).done(function (data) {
            var dataCity = JSON.stringify(data);
            var listCity = JSON.parse(dataCity );
            $select.html('');
            $select.append('<option value="" selected ></option>')
            for (var i = 0; i < listCity.length; i++) {
                $select.append('<option value ="' + listCity[i].id + '">'
                    + listCity[i].city + '</option>')
            }
        }).fail(function (err) {
            alert(err);
        });
    }
    window.onload = chooseCity;
</script>
<%
    String id = request.getParameter("id");
    Candidate candidate = new Candidate(0, "", "", 0);
    if (id != null) {
        candidate = PsqlStore.instOf().findCandidateById(Integer.valueOf(id));
    }
%>
<div class="container pt-3">
    <div class="row">
        <ul class="nav">
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/login.jsp"> <c:out value="${user.name}"/> | Выйти</a>
            </li>
        </ul>
        <div class="card" style="width: 100%">
            <div class="card-header">
                Новый кандидат.
            </div>
            <div class="card-body">
                <form action="<%=request.getContextPath()%>/candidates.do?id=<%=candidate.getId()%>" enctype="multipart/form-data" method="post">
                    <div class="form-group">
                        <label>Имя</label>
                        <label>
                            <input type="text" class="form-control" id = "name"  name="name" value="<%=candidate.getName()%>">
                        </label>
                        <div class = "checkbox">
                            <input type = "file" id="photoId" name ="photoId">
                        </div>
                        <div class = "form-group">
                            <label for="city">Город:</label>
                            <select id="city" name="cityId" value="<%=candidate.getCityId()%>">
                                <option value=""></option>
                            </select>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-primary" onclick="return validate()" >Сохранить</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
