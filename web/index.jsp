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
        <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCkyK25oTRJKpEhmHiHOuaB_nPg_oUo8-Y
        &libraries=places,geometry"></script>

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

                    <div class="mt-3">
                        <span>Where do you want to go? </span><br>
                        <select class="custom-select mr-sm-2" id="inlineFormCustomSelect">
                            <option selected value="0">Tất cả</option>
                            <c:forEach var="category" items="${requestScope.categories}">
                                <option value="1">${category.name}</option>
                            </c:forEach>
                        </select>
                        <button type="submit" class="btn btn-primary mt-2">Find places</button>
                    </div>

                </div>
                <div id="map" style="height: 500px; background: red; width: 55%"></div>
            </div>

        </div>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>

        <script>
            var autocomplete;
            var map;
            var addresses = [];
            var markers = [];

            google.maps.event.addDomListener(window, 'load', initialize);
            initMap();

            function initialize() {
                var input = document.getElementById('pac-input');
                autocomplete = new google.maps.places.Autocomplete(input);
                autocomplete.addListener('place_changed', changePlaceListener)
            }

            function initMap() {
                map = new google.maps.Map(document.getElementById('map'), {
                    zoom: 13,
                    center: {lat: 10.841508, lng: 106.809628}
                });
//                marker.addListener('click', toggleBounce);
            }

            
            function changePlaceListener() {
                var input = document.getElementById('pac-input');
                // get info
                var place = autocomplete.getPlace();
                var location = place.geometry.location;

                // add location
                var addressInfo = {name: place.formatted_address, lat: location.lat(), lng: location.lng()};
                addresses.push(addressInfo);

                //clear input
                input.value = "";

                //load address and marker
                updateAddressesAndMarker();

            }

            function updateAddressesAndMarker() {
                
                //revove all addresses
                var ulAddresses = document.getElementById("ul-list-address");
                ulAddresses.innerHTML = "";
                
                //remove all markers
                for (var i = 0; i < markers.length; i++) {
                    markers[i].setMap(null);
                }
                markers.length = 0;
                
                // add new address and marker
                for (var i = 0; i < addresses.length; i++) {
                    var address = addresses[i];
                    addNewLiAddress(ulAddresses, address.name);
                    createMarker(address.lat, address.lng);
                }
            }
            
            function addNewLiAddress(ulAddresses, name){
                var liAddress = document.createElement("li");
                liAddress.setAttribute("class", "list-group-item py-1");

                // add address content
                var spanAddress = document.createElement("span");
                spanAddress.innerHTML = name;
                liAddress.appendChild(spanAddress);

                // add space
                var spanSpace = document.createElement("span");
                spanSpace.innerHTML = " ";
                liAddress.appendChild(spanSpace);

                // add remove button
                var removeButtion = document.createElement("button");
                removeButtion.innerHTML = "Remove";
                liAddress.appendChild(removeButtion);
                removeButtion.addEventListener("click", removeAddress);

                ulAddresses.appendChild(liAddress);
            }
            
            function createMarker(lat, lng) {
                var marker = new google.maps.Marker({
                    map: map,
                    draggable: true,
                    animation: google.maps.Animation.DROP,
                    position: {lat: lat, lng: lng}
                });
                map.setCenter(new google.maps.LatLng(lat, lng));
                markers.push(marker);
            }
            function removeAddress(e){
                var button = e.target;
                var address = button.parentElement.childNodes[0].innerHTML;
                for (var i = 0; i < addresses.length; i++){
                    if (addresses[i].name === address){
                        addresses.splice(i,1);
                    }
                }
                updateAddressesAndMarker();
            }
            
            function clearAllAddresses(){
                addresses = [];
                updateAddressesAndMarker();
            }

        </script>
        <!--        <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCkyK25oTRJKpEhmHiHOuaB_nPg_oUo8-Y&libraries=places,geometry"
                async defer></script>-->

        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    </body>
</html>
