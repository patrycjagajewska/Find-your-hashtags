'use strict';

angular.module('wishlist.services', [])

    .factory('GreetingService', function ($resource) {
        return $resource('http://rest-service.guides.spring.io/greeting');
    })

    .factory('UserService', function ($resource, serverUrl) {
        var service = {};

        service.getUsers = function() {
            return $resource(serverUrl + "/users").query();
        };

        service.getUser = function(username) {
            return $resource(serverUrl + "/users/" + username).get();
        };

        return service;
    })

    .factory('GiftService', function ($resource, serverUrl){
        var service = {};

        service.getGifts = function(){
            return $resource(serverUrl + "/gifts").query();
        };

        service.getGift = function(id){
            return $resource(serverUrl + "/gifts/" + id).get();
        }
    })
;