'use strict';

angular.
module('productFilter').
component('productFilter', {
    templateUrl: 'product-filter/product-filter.template.html',
    controller: ['$http', '$routeParams',
        function ProductFilterController($http, $routeParams) {
            var self = this;
            $http.get('https://digital-app.herokuapp.com/products_by_category?category=' + $routeParams.filterCategory).then(function(response) {
                self.filters = response.data;
            });
        }
    ]
});

