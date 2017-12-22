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

        self.searchData = function(formSearch) {
            self.hellot = formSearch.textSearch;
            $http.get('filters/mobile.json').then(function(response) {
                self.products = response.data;
            });
        };

    }]
});