'use strict';

angular.module('findyourhashtags.controllers', [])


    .controller('LoginController', function ($scope, $rootScope, $state, $location, Restangular) {

        $scope.login = function(){
            Restangular.all('login').post('username=' + $scope.credentials.username
                + '&password=' + $scope.credentials.password).then(function(resp){
                console.log(resp);
                $location.path("/gifts");

            }, function(resp){
                console.log(resp);
            });
        };

        $scope.logout = function(){
            Restangular.all('logout').post().then(function(resp){
                console.log(resp);
            }, function(resp){
                console.log(resp);
            });
            $location.path('login');

        };
    })

    .controller('HashtagController', function ($scope, $state, HashtagService) {
        var tweets = HashtagService.getTweets();

        $scope.tweets = tweets;
    });



