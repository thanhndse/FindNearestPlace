var autocomplete;
var map;
var addresses = [];
var markers = [];
var data = [];
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
    var addressInfo = {name: place.formatted_address, x: location.lat(), y: location.lng()};
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
        createMarker(address.x, address.y);
    }
}

function addNewLiAddress(ulAddresses, name) {
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
function removeAddress(e) {
    var button = e.target;
    var address = button.parentElement.childNodes[0].innerHTML;
    for (var i = 0; i < addresses.length; i++) {
        if (addresses[i].name === address) {
            addresses.splice(i, 1);
        }
    }
    updateAddressesAndMarker();
}

function clearAllAddresses() {
    addresses = [];
    updateAddressesAndMarker();
}

function findPlaces() {
    toggleLoader(true);
    var xhr = new XMLHttpRequest();
    console.log(addresses);
    var selectCategory = document.getElementById("select-category");
    var categoryString = selectCategory.options[selectCategory.selectedIndex].text;
    xhr.open("POST", '/FindNearestPlace/webresources/Places?category=' + categoryString, true);
    //Send the proper header information along with the request
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(JSON.stringify(addresses));
    xhr.onreadystatechange = function () { // Call a function wh
        //en the state changes.
        if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
            // Request finished. Do processing here.
            data = this.responseXML;
            toggleLoader(false);
            if (data) {
                renderCards(data);
            }
        }
    }
}
function renderCards(data) {
    var divContainerPlace = document.getElementsByClassName("container-places")[0];
    var bodyRender = "";
    var places = data.getElementsByTagName("place");
    if (places.length == 0) {
        bodyRender = "<h3>There is no places found. Please try again</h3>";
    }
    for (var i = 0; i < places.length; i++) {
        bodyRender += "<div class='card' onClick='showDetailCard(" + i + ")' ><img src="
                + places[i].querySelector("place > image").innerHTML
                + " alt='Avatar' class='card-image'><div class='card-contain'><h4><b>"
                + places[i].querySelector("place > name").innerHTML
                + "</b></h4><p>"
                + places[i].querySelector("place > fullAddress").innerHTML
                + "</p></div></div>"
    }

    //add to body table
    divContainerPlace.innerHTML = bodyRender;
}

function toggleLoader(isShow) {
    var loader = document.getElementsByClassName("loader")[0];
    if (isShow) {
        loader.style.display = "block";
    } else {
        loader.style.display = "none";
    }
}

function showDetailCard(placeOrder) {
    map = new google.maps.Map(document.getElementById('map'), {
        zoom: 13,
        center: {lat: 10.841508, lng: 106.809628}
    });
    for (var i = 0; i < addresses.length; i++){
        drawGoogleRoute(placeOrder, addresses[i]);
    }
    window.scrollTo(0, 0);
}

function drawGoogleRoute(placeOrder, address) {
    var places = data.getElementsByTagName("place");
    var place = places[placeOrder];
    var directionsService = new google.maps.DirectionsService();
    var directionsDisplay = new google.maps.DirectionsRenderer();
    directionsDisplay.setMap(map);

    var request = {
        origin: new google.maps.LatLng(address.x, address.y),
        destination: new google.maps.LatLng(place.querySelector("place > latitude").innerHTML, place.querySelector("place > longitude").innerHTML),
        travelMode: google.maps.DirectionsTravelMode.DRIVING
    };
    directionsService.route(request, function (response, status) {
        if (status == google.maps.DirectionsStatus.OK) {
            directionsDisplay.setDirections(response);
        }
    });
}

function clearAllRoute(){
    map = new google.maps.Map(document.getElementById('map'), {
        zoom: 13,
        center: {lat: 10.841508, lng: 106.809628}
    });
    updateAddressesAndMarker();
}