'use strict';

angular.
module('productFilter').
component('productFilter', {
    templateUrl: 'product-filter/product-filter.template.html',
    controller: ['$http', '$routeParams',
        function ProductFilterController($http, $routeParams) {
            var self = this;
            var ctg =  $routeParams.filterCategory;
            $http.get('https://digital-app.herokuapp.com/products_by_category?category='+ctg).then(function(response) {
                self.filterproducts = response.data;
            });
            self.formInfo = {};
            self.filterData = function(formInfo) {
                var priceFrom = formInfo.priceFrom;
                var priceTo = formInfo.priceTo;
                var manufacturer = formInfo.manufacturer;
                if(priceFrom == undefined){
                    priceFrom = '';
                }if(priceTo == undefined){
                    priceTo = '';
                }if(manufacturer == undefined){
                    manufacturer = '';
                }

                $http.get('https://digital-app.herokuapp.com/filter?category='+ctg+'&brand='+manufacturer+'&from='+priceFrom+'&to='+priceTo).then(function(response) {
                    self.filterproducts = response.data;
                });
            };
            self.resetData = function() {
                $http.get('https://digital-app.herokuapp.com/products_by_category?category='+ctg).then(function(response) {
                    self.filterproducts = response.data;
                });
            };
        }
    ]
});

