'use strict';

angular.
module('productFilter').
component('productFilter', {
    templateUrl: 'product-filter/product-filter.template.html',
    controller: ['$http', function ProductListController($http) {
        var self = this;
        $http.get('products/products.json').then(function(response) {
            self.products = response.data;
        });
    }]
});