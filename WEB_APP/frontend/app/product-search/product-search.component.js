'use strict';

angular.
module('productSearch').
component('productSearch', {
    templateUrl: 'product-search/product-search.template.html',
    controller: ['$http', '$routeParams',
        function ProductSearchController($http, $routeParams) {
            var self = this;
            var searhString = $routeParams.searchParam;
            searhString.replace(/ /ig, '%20');

            self.reqst = searhString;

            $http.get('https://digital-app.herokuapp.com/search?word=' + searhString).then(function (response) {
                self.searchproduct = response.data;
            });
    }]
});