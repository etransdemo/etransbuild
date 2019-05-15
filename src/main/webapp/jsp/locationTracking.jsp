<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <script src="../jsp/js/angular.min.js"></script>
 <!--    <script src="../bower_components/leaflet/dist/leaflet.js"></script>
    <script src="../bower_components/angular-simple-logger/dist/angular-simple-logger.js"></script>
    <script src="../dist/ui-leaflet.min.js"></script>
    <link rel="stylesheet" href="../bower_components/leaflet/dist/leaflet.css" /> -->
    <script>
        var app = angular.module("demoapp", ['ngRoute','ui-leaflet']);
        app.controller("trackController", [ "$scope", function($scope) {
            // Nothing here!
            console.log(trackInfo);
        }]);
    </script>
</head>
<body ng-controller="trackController">
    <leaflet width="100%" height="480px"></leaflet>
    <h1>First steps, basic example</h1>
  </body>
</html>
