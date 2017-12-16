'use strict';
// Define the `digitalPlanet` module
var digitalPlanet = angular.module('digitalPlanet', []);

// Define the `mainListController` controller on the `digitalPlanet` module
digitalPlanet.controller('MainListController', function MainListController($scope) {
    $scope.phones = [
        {
            name: 'Nexus S',
            price: '110.9'
        }, {
            name: 'Motorola XOOM™ with Wi-Fi',
            price: '255.0'
        }, {
            name: 'MOTOROLA XOOM™',
            price: '116.8'
        },{
            name: 'Nexus S444',
            price: '116.8'
        },{
            name: 'Телефон Samsung',
            price: '216.8'
        },{
            name: 'Телефон Samsung X',
            price: '222.2'
        },{
            name: 'MOTOROLA XOOM™',
            price: '116.8'
        },{
            name: 'Nexus S',
            price: '110.9'
        }
    ];
});