'use strict';

angular.
module('productDetail').
component('productDetail', {
    templateUrl: 'product-detail/product-detail.template.html',
    controller: ['$http', '$routeParams',
        function ProductListController($http, $routeParams) {
            var self = this;
            $http.get('https://digital-app.herokuapp.com/product?id=' + $routeParams.productId).then(function(response) {
                self.product = response.data;
            });
        }
    ]
});
