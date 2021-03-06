'use strict';

angular.
module('productList').
component('productList', {
    templateUrl: 'product-list/product-list.template.html',
    controller: ['$http', '$routeParams',
        function ProductListController($http, $routeParams) {
        var self = this;
        $http.get('https://digital-app.herokuapp.com/products').then(function(response) {
            self.products = response.data;
        });
    }]
});