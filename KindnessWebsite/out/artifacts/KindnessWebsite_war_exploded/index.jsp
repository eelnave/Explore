<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Kindness Around the World</title>
    <link rel="stylesheet" href="../../assets/css/index.css">
</head>
<body>

<h1>Kindness Around the World</h1>

<nav>
    <ul>
        <li><a href="index.jsp">Home</a></li>
        <li><a href="map.jsp">Map</a></li>
    </ul>
</nav>

<div class="see-kindness">
    <p>
    <li>Lorem ipsum dolor sit amet, consectetuer adipiscing elit.</li>
    <li>Aliquam tincidunt mauris eu risus.</li>
    <li>Vestibulum auctor dapibus neque.</li>
    <li>Nunc dignissim risus id metus.</li>
    <li>Cras ornare tristique elit.</li>
    <li>Vivamus vestibulum ntulla nec ante.</li>
    <li>Praesent placerat risus quis eros.</li>
    <li>Fusce pellentesque suscipit nibh.</li>
    <li>Integer vitae libero ac risus egestas placerat.</li>
    <li>Vestibulum commodo felis quis tortor.</li>
    <li>Ut aliquam sollicitudin leo.</li>
    <li>Cras iaculis ultricies nulla.</li>
    <li>Donec quis dui at dolor tempor interdum.</li></p>
</div>

<div class="input">

<form method="post" action="http://localhost:8080/kindness">
    <b>Send Kindness Act</b><br>
    <label id="category">Category:</label><input type="text" name="category"><br>
    <label id="date">Date:</label><input type="text" name="date"><br>
    <input type="submit" value="Send">
</form>

<form method="get" action="http://localhost:8080/world">
    <input type="submit" value="World">
</form>
<button onclick="getLocation()">Get Location</button>

</div>

<div class="android">
    <p>Get our Android App Here. (Coming Soon)</p>
</div>

<div class="calc">
    <p>Get our Universal Calculator <a href="https://play.google.com/store/apps/details?id=edu.byui.cit.calc360">Here</a>.</p>
</div>

<p id="demo"></p>
<script src="../../assets/js/index.js"></script>
</body>
</html>
