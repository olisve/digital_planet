'use strict';

angular.
module('productFilter').
component('productFilter', {
    templateUrl: 'product-filter/product-filter.template.html',
    controller: ['$http', function ProductListController($http) {
        var self = this;
        $http.get('filters/mobile.json').then(function(response) {
            self.filters = response.data;
        });
    }]
});