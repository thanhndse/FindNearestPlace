<%-- 
    Document   : index
    Created on : Nov 24, 2019, 4:29:52 PM
    Author     : thanh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<!DOCTYPE html>
<html>  
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Nearest place</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="css/nearestPlace.css">
        <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCkyK25oTRJKpEhmHiHOuaB_nPg_oUo8-Y
        &libraries=places,geometry,"></script>

    </head>
    <body>
        <div class="container">
            <nav class="navbar navbar-expand-lg navbar-light bg-light pl-5 mb-2">
                <a class="navbar-brand" href="#">Nearest place</a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <div class="collapse navbar-collapse ml-5 " id="navbarSupportedContent">
                    <ul class="navbar-nav mr-auto">
                        <li class="nav-item active">
                            <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">Find nearest place</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">Account</a>
                        </li>
                    </ul>
                </div>
            </nav>
            <div class="d-flex justify-content-between">
                <div style="width: 45%">
                    <h5>Hello, <span style="color: background">${sessionScope.user.name}</span> </h5>

                    <div class="mt-3">
                        <h3 class="mt-2">Make your friends closer</h3>
                        <div class="form-group mr-2 mb-2">
                            <input size="50" type="text" class="form-control input-address" id="pac-input" placeholder="Input address">
                        </div>
                    </div>

                    <div class="mt-3 mb-3">
                        <span>Your places: </span>
                        <ul class="list-group mb-3" id="ul-list-address" style="width: 100%">
                        </ul>
                        <button type="submit" class="btn btn-primary mb-2" onclick="clearAllAddresses()">Clear All</button>
                    </div>

                    <div class="mt-3 mb-3">
                        <span>Where do you want to go? </span><br>
                        <select class="custom-select mr-sm-2" id="select-category">
                            <option value="0">Tất cả</option>
                            <c:forEach var="category" items="${requestScope.categories}">
                                <option value="1">${category.name}</option>
                            </c:forEach>
                        </select>
                        <button type="submit" class="btn btn-primary mt-2" onclick="findPlaces()">Find places</button>
                    </div>
                    <div class="loader"></div>
                    <input id="clear-route-button" onclick="clearAllRoute()" type="submit" value="Clear route" />

                </div>
                <div id="map" style="height: 500px; background: red; width: 55%"></div>
            </div>
            <div class="container-places">

                <div class="card" onclick="showCar">
                    <img src="images/img_avatar2.png" alt="Avatar" style="width:100%" class="card-image">
                    <div class="card-contain">
                        <h4><b>Jane Doe</b></h4> 
                        <p>Interior Designer</p>
                        <p style="line-height: 0; font-style: italic">Tổng khoảng cách: <span style="color: green">15km</span></p>
                        <p style="line-height: 1; font-style: italic">Độ lệch: <span style="color: green">2km</span></p>

                    </div>
                </div>
            </div>


        </div>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
        <script src="js/nearestPlace.js"></script>
        <!--        <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCkyK25oTRJKpEhmHiHOuaB_nPg_oUo8-Y&libraries=places,geometry"
                async defer></script>-->

        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    </body>
</html>
