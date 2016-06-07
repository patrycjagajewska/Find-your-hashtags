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

    .controller('TwitterController', function ($scope, $state, TwitterService) {

        TwitterService.getTweets().then(function(resp){
            $scope.tweets = resp;
        });

        $scope.searchForHashtag = function(hashtag) {
            TwitterService.searchForHashtag(hashtag).then(function(resp){
                $scope.tweets = resp;
            });
        }

    });


