'use strict';

angular.
module('productDetail').
component('productDetail', {
    template: 'TBD: Detail view for <span>{{$ctrl.productId}}</span>',
    controller: ['$routeParams',
        function ProductDetailController($routeParams) {
            this.productId = $routeParams.productId;
        }
    ]
});