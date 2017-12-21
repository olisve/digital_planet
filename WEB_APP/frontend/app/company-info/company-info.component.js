'use strict';

angular.
module('companyInfo').
component('companyInfo', {
    templateUrl: 'company-info/company-info.template.html',
    controller: ['$http', function ProductListController($http) {
        var self = this;
        $http.get('products/products.json').then(function(response) {
            self.products = response.data;
        });
    }]
});