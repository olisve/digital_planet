'use strict';

angular.
module('appBasket', []).
controller('addBasketController',
    function ($scope) {
        $scope.addToBasket = function(productId) {
            localStorage.setItem(productId, productId);
            var pr =  localStorage.getItem(productId);
            alert('Товар добавлен в корзину!');
        };

        $scope.deleteFromBasket = function(productId) {
            localStorage.removeItem(productId);
            alert('Товар удален из корзины!');
        };
});

