'use strict';

angular.
module('userPage').
component('userPage', {
    templateUrl: 'user-page/user-page.template.html',
    controller: ['$http', '$routeParams',
        function UserPageController($http, $routeParams) {
            var self = this;

            self.userInSystem=false;
            self.userRegister=false;
            self.userPageIs=false;
            if(localStorage.getItem('user')!=null){
                self.userInSystem=true;
                self.userRegister=false;
                self.userPageIs=true;
            }

            self.logIn = function(loginInfo){
                alert(loginInfo.email + ' ' + loginInfo.password);
                $http.post('https://digital-app.herokuapp.com/login', 'email='+loginInfo.email+'&password='+loginInfo.password).then(function(response) {
                    if(response.data == 'NOT FOUND'){
                        alert('Пользователь не найден. Введите корректные данные либо зарегестрируйтесь.');
                    }
                    else{
                        self.userInSystem = true;
                        self.userRegister=false;
                        self.userPageIs=true;
                        self.user = response.data;
                        localStorage.setItem('user', self.user.email);
                    }
                });
            }

            self.registIn = function(){
                self.userRegister=true;
                self.userInSystem=false;
            }

            self.registR = function(registerInfo){
                var postStr = 'email='+registerInfo.email+'&password='+registerInfo.password+'&firstname='+registerInfo.firstName+'&lastname='+registerInfo.lastName+'+&address='+registerInfo.address+'&phone='+registerInfo.phone;
                $http.post('https://digital-app.herokuapp.com/signup', postStr).then(function(response) {

                });
                self.userRegister=false;
                self.userInSystem=false;
            }

            self.logOut = function(){
                localStorage.removeItem('user');
                self.userInSystem=false;
            }

        }]
});