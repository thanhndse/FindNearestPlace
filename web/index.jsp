<%-- 
    Document   : index
    Created on : Nov 24, 2019, 4:29:52 PM
    Author     : thanh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Nearest place</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="css/nearestPlace.css">
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
                <div>
                    <h5>Hello, <span style="color: background">${sessionScope.user.name}</span> </h5>

                    <div class="mt-3">
                        <h3 class="mt-2">Make your friends closer</h3>
                        <div class="form-group mr-2 mb-2">
                            <label for="inputPassword2" class="sr-only">Address</label>
                            <input size="50" type="text" class="form-control input-address" id="inputAddress" placeholder="Input address">
                        </div>
                        <button type="submit" class="btn btn-primary">Add</button>
                    </div>

                    <div class="mt-3">
                        <span>Your places: </span><br>
                        <div class="list-address mb-2">
                            <ul class="list-group">
                                <li class="list-group-item py-1">Chung cư Thới An, Lê Thị Riêng, Quận 12</li>
                                <li class="list-group-item py-1">Trường đại học ngoại thương</li>
                            </ul>
                        </div>
                        <button type="submit" class="btn btn-primary mb-2">Remove</button>
                        <button type="submit" class="btn btn-primary mb-2">Clear All</button>
                    </div>

                    <div class="mt-3">
                        <span>Where do you want to go? </span><br>
                        <select class="custom-select mr-sm-2" id="inlineFormCustomSelect">
                            <option selected>Choose your category</option>
                            <option value="1">Coffee</option>
                            <option value="2">Food</option>
                            <option value="3">Milk tea</option>
                        </select>
                        <button type="submit" class="btn btn-primary mt-2">Find places</button>
                    </div>

                </div>
                <div>
                    <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3918.6056895740307!2d106.80763811450237!3d10.84145749227741!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x31752731176b07b1%3A0xb752b24b379bae5e!2zxJDhuqFpIGjhu41jIEZQVCBUUCBIQ00!5e0!3m2!1sen!2s!4v1574607107358!5m2!1sen!2s" width="600" height="450" frameborder="0" style="border:0;" allowfullscreen=""></iframe>
                </div>
            </div>

        </div>

        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    </body>
</html>
