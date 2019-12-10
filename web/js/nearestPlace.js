var autocomplete;
var map;
var addresses = [];
var markers = [];
var data = [];
var placeList = [];
google.maps.event.addDomListener(window, 'load', initialize);
initMap();
var ulChoosenCategories = document.getElementById("choosen-categories");
var inputSearchCategory = document.getElementById('search-category');
var ulCategoriesList = document.getElementById("categories-container");
var isLoadAgain = false;
var currentPlaces = [];

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
    isLoadAgain = true;
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
function toggleCataegories() {
    if (ulCategoriesList.style.display === "none" || ulCategoriesList.style.display === "") {
        ulCategoriesList.style.display = "block";
    } else {
        ulCategoriesList.style.display = "none";
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
    isLoadAgain = true;
}

function clearAllAddresses() {
    addresses = [];
    updateAddressesAndMarker();
}

function findPlaces() {
    toggleLoader(true);
    if (isLoadAgain) {
        var xhr = new XMLHttpRequest();
        xhr.open("POST", '/FindNearestPlace/webresources/Places', true);
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
                    console.log(data);
                    parseToList(this.responseXML);
                    isLoadAgain = false;
                    findPlaces();
                }
            }
        }
    } else {
        var liChoosenCategories = ulChoosenCategories.childNodes;
        var choosenCategoriesList = [];
        currentPlaces = [];
        for (var i = 0; i < liChoosenCategories.length; i++) {
            var textContent = liChoosenCategories[i].textContent.trim();
            if (textContent) {
                choosenCategoriesList.push(textContent);
            }
        }
        if (choosenCategoriesList.length > 0) {
            for (var i = 0; i < placeList.length; i++) {
                var categories = placeList[i].categories;
                if (hasSameElements(categories, choosenCategoriesList)) {
                    currentPlaces.push(placeList[i]);
                }
            }
        } else {
            currentPlaces = placeList;
        }

        toggleLoader(false);
        renderCards();
    }
}
function renderCards() {
    var divContainerPlace = document.getElementsByClassName("container-places")[0];
    var bodyRender = "";
    if (currentPlaces.length === 0) {
        bodyRender = "<h3>There is no places found. Please try again</h3>";
    }
    for (var i = 0; i < currentPlaces.length; i++) {
        bodyRender += "<div class='card' onClick='showDetailCard(" + i + ")' ><img src="
                + currentPlaces[i].image
                + " alt='Avatar' class='card-image'><div class='card-contain'><h4><b>"
                + currentPlaces[i].name
                + "</b></h4><p>"
                + currentPlaces[i].fullAddress
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
    for (var i = 0; i < addresses.length; i++) {
        drawGoogleRoute(placeOrder, addresses[i]);
    }
    window.scrollTo(0, 0);
    getDistanceToAddresses(placeOrder)
}

function drawGoogleRoute(placeOrder, address) {
//    var places = data.getElementsByTagName("place");
    var place = currentPlaces[placeOrder];
    var directionsService = new google.maps.DirectionsService();
    var directionsDisplay = new google.maps.DirectionsRenderer();
    directionsDisplay.setMap(map);

    var request = {
        origin: new google.maps.LatLng(address.x, address.y),
        destination: new google.maps.LatLng(place.latitude, place.longitude),
        travelMode: google.maps.DirectionsTravelMode.DRIVING
    };
    directionsService.route(request, function (response, status) {
        if (status == google.maps.DirectionsStatus.OK) {
            directionsDisplay.setDirections(response);
        }
    });
}

function clearAllRoute() {
    map = new google.maps.Map(document.getElementById('map'), {
        zoom: 13,
        center: {lat: 10.841508, lng: 106.809628}
    });
    updateAddressesAndMarker();
}

function filterCategory() {
    // Declare variables
    var filter = inputSearchCategory.value.toUpperCase();
    var ul = document.getElementById("categories-container");
    var liList = ul.getElementsByTagName('li');

    // Loop through all list items, and hide those who don't match the search query
    for (i = 0; i < liList.length; i++) {
        var li = liList[i];
        var txtValue = li.textContent || li.innerText;
        if (txtValue.toUpperCase().indexOf(filter) > -1) {
            li.style.display = "";
        } else {
            li.style.display = "none";
        }
    }
}

function removeChoosenCategory(btnCategory) {
    var liCategory = btnCategory.parentElement;
    liCategory.parentElement.removeChild(liCategory);
}

function addCategoryToList(liCategory) {
    var categoryText = liCategory.innerHTML;
    ulChoosenCategories.innerHTML += "<li>"
            + categoryText +
            " <input onclick='removeChoosenCategory(this)' type='submit' value='Remove'/> </li>";
//    ulCategoriesList.style.display = "none";
//    inputSearchCategory.value = "";
}
function showCategoryList() {
//    ulCategoriesList.style.display = "block";
}

function parseToList(dataXML) {
    placeList = [];
    var placesXML = dataXML.getElementsByTagName("place");
    for (var i = 0; i < placesXML.length; i++) {
        var name = placesXML[i].querySelector("place > name").innerHTML;
        var latitude = placesXML[i].querySelector("place > latitude").innerHTML;
        var longitude = placesXML[i].querySelector("place > longitude").innerHTML;
        var image = placesXML[i].querySelector("place > image").innerHTML;
        var fullAddress = placesXML[i].querySelector("place > fullAddress").innerHTML;
        var fullAddressFormatted = placesXML[i].querySelector("place > fullAddressFormatted").innerHTML;
        var categories = [];
        var categoriesXML = placesXML[i].getElementsByTagName("categories");
        for (var j = 0; j < categoriesXML.length; j++) {
            categories.push(categoriesXML[j].getElementsByTagName("name")[0].innerHTML);
        }
        var place = {name: name, latitude: latitude, longitude: longitude, image: image, fullAddress: fullAddress, fullAddressFormatted: fullAddressFormatted, categories: categories};
        placeList.push(place);
//        getDistanceToAddresses(latitude, longitude, place, i === placesXML.length - 1);
    }
}

function getDistanceToAddresses(placeOrder) {
    var totalDistanceSpan = document.getElementById("totalDistance");
    var standardDeviationSpan = document.getElementById("standardDeviation");
    var place = currentPlaces[placeOrder];
    var origins = [];
    var distanceList = [];
    var totalDistance = 0;
    var standardDeviation = 0;
    for (var i = 0; i < addresses.length; i++) {
        origins.push(new google.maps.LatLng(addresses[i].x, addresses[i].y));
    }
    var destination = new google.maps.LatLng(place.latitude, place.longitude);

    var service = new google.maps.DistanceMatrixService();
    service.getDistanceMatrix(
            {
                origins: origins,
                destinations: [destination],
                travelMode: 'DRIVING',
                avoidHighways: true,
                avoidTolls: false,
            }, function (response, status) {
        if (status !== 'OK') {
            alert('Error was: ' + status);
        } else {
            console.log(response);
            for (var i = 0; i < response.rows.length; i++) {
                var element = response.rows[i].elements[0];
                console.log("element" + element);
                if (element.status === "OK") {
                    var distance = {
                        i: i, distance: element.distance.value, duration: element.duration.value
                    };
                    console.log("distance" + JSON.stringify(distance));
                    distanceList.push(distance);
                    totalDistance += element.distance.value;
                }
            }

            console.log("distances" + JSON.stringify(distanceList));
            console.log("total" + JSON.stringify(totalDistance));

            // calculate standardDeviation
            var sampleMean = totalDistance / addresses.length;
            for (var i = 0; i < distanceList.length; i++) {
                standardDeviation += (distanceList[i].distance - sampleMean) * (distanceList[i].distance - sampleMean);
            }
            standardDeviation = Math.sqrt((1 / (addresses.length - 1)) * standardDeviation);
            totalDistanceSpan.innerHTML = totalDistance / 1000 + "km";
            standardDeviationSpan.innerHTML = (standardDeviation / 1000).toFixed(2) + "km";
            console.log("standart" + JSON.stringify(standardDeviation));
        }
    });

//    return {distanceList, totalDistance, standardDeviation};
}

function hasSameElements(arr1, arr2) {
    for (var i in arr1) {
        if (arr2.indexOf(arr1[i]) > -1) {
            return true;
        }
    }
    return false;
}
//window.addEventListener("click", function (e) {
//    var clickId = e.target.id;
//    var clickClass = e.target.className;
//    if (!(clickId === "search-category") && !(clickClass.indexOf("category-content") !== -1)) {
//        ulCategoriesList.style.display = "none";
//    }
//})