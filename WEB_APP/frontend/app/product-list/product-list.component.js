'use strict';

angular.
module('productList').
component('productList', {
    templateUrl: 'product-list/product-list.template.html',
    controller: ['$http', function ProductListController($http) {
        var self = this;
        $http.get('products/products.json').then(function(response) {
            self.products = response.data;
        });
    }]
});