'use strict';

angular.
module('productDetail').
component('productDetail', {
    templateUrl: 'product-detail/product-detail.template.html',
    controller: ['$http', function ProductListController($http) {
        var self = this;
        $http.get('products/5a1f36e04a7dd271c42ec0c6.json').then(function(response) {
            self.product = response.data;
        });
    }]
});

