'use strict';

angular.
module('productBasket').
component('productBasket', {
    templateUrl: 'product-basket/product-basket.template.html',
    controller: ['$http', '$routeParams',
        function ProductBasketController($http, $routeParams) {
        var self = this;
        self.totalSum = 0;

        $http.get('https://digital-app.herokuapp.com/products').then(function(response) {
            self.pfb = response.data;
        });

        var i=0;
        for(i=0; i<=localStorage.length; i++){
            var productId =  localStorage.key(i);
            if(productId!='user'&&productId!=null){
                $http.get('https://digital-app.herokuapp.com/product?id='+productId).then(function(response) {
                    self.totalSum = response.data.price;
                });

            }
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