<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Geolocation</title>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
    <meta charset="utf-8">
    <link rel="stylesheet" href="../../assets/css/index.css">
    <style>
        /* Always set the map height explicitly to define the size of the div
         * element that contains the map. */
        #map {
            height: 80%;
        }
        /* Optional: Makes the sample page fill the window. */
        html, body {
            height: 95%;
            margin-left: 0;
            margin-right: 0;
            margin-bottom: 0;
            padding: 0;
        }
    </style>
</head>
<body>

<h1>Kindness Around the World</h1>

<nav>
    <ul>
        <li><a href="index.jsp">Home</a></li>
        <li><a href="map.jsp">Map</a></li>
    </ul>
</nav>

<div id="map"></div>

<script src="https://www.gstatic.com/firebasejs/4.12.1/firebase.js"></script>
<script>
    // Initialize Firebase
    var config = {
        apiKey: "AIzaSyAlpfPcXOXycuVGAKq7osrN7ffsxIJdsr0",
        authDomain: "kindness-836db.firebaseapp.com",
        databaseURL: "https://kindness-836db.firebaseio.com",
        projectId: "kindness-836db",
        storageBucket: "kindness-836db.appspot.com",
        messagingSenderId: "933634843909"
    };
    firebase.initializeApp(config);
</script>
<script>
    var map, infoWindow;
    function initMap() {
        map = new google.maps.Map(document.getElementById('map'), {
            center: {lat: 43.814848399999995, lng: -111.7848428},
            zoom: 13
        });
        infoWindow = new google.maps.InfoWindow;

        // Try HTML5 geolocation.
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(function(position) {
                var pos = {
                    lat: position.coords.latitude,
                    lng: position.coords.longitude
                };

                infoWindow.setPosition(pos);
                // infoWindow.setContent('Location found.');
                // infoWindow.open(map);
                map.setCenter(pos);
            }, function() {
                handleLocationError(true, infoWindow, map.getCenter());
            });
        } else {
            // Browser doesn't support Geolocation
            handleLocationError(false, infoWindow, map.getCenter());
        }
    }

    function handleLocationError(browserHasGeolocation, infoWindow, pos) {
        infoWindow.setPosition(pos);
        infoWindow.setContent(browserHasGeolocation ?
            'Error: The Geolocation service failed.' :
            'Error: Your browser doesn\'t support geolocation.');
        infoWindow.open(map);
    }
</script>
<script async defer
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAlpfPcXOXycuVGAKq7osrN7ffsxIJdsr0&callback=initMap">
</script>
</body>
</html>