'use strict';

angular.
module('productBasket').
component('productBasket', {
    templateUrl: 'product-basket/product-basket.template.html',
    controller: ['$q', '$http', '$routeParams',
        function ProductBasketController($q, $http, $routeParams) {
        var self = this;
        self.totalSum = 0;

           /*$http.get('https://digital-app.herokuapp.com/products').then(function(response) {
                self.pfb = response.data;
            });
            */

            var all = [];
            var i=0;
            var products_promise = [];
            for(i=0; i<localStorage.length; i++) {
                var productId = localStorage.key(i);
                if (productId != 'user' && productId != null) {
                    products_promise.push($http.get('https://digital-app.herokuapp.com/product?id=' + productId));
                }
            }
            if(products_promise.length>0) {
                $q.all(products_promise).then(function (response) {
                    for (i = 0; i < response.length; i++) {
                        all.push(response[i].data || response[i]);
                        self.totalSum += (response[i].data || response).price;
                    }
                    self.pfb = all;
                });
            }

            self.placeAnOrder = function () {
                var user = localStorage.getItem('user');
                if(user == null)
                    alert('Для оформления заказа необходимо войти в систему.');
                else{
                    alert('Заказ оформлен! ' + user);
                    localStorage.clear();
                    localStorage.setItem('user', user);
                }
            }
    }]
});