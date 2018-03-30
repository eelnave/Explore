<?php
$servername = "157.201.228.200";
$username = "Administrator";
$password = "cit360kindness!";

$conn = new mysqli($servername, $username, $password);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
echo "Connected successfully";
?>