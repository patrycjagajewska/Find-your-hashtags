'use strict';

angular.module('wishlist.controllers', [])

    .controller('GreetingController', function ($scope, $state, GreetingService) {
        $scope.greeting = GreetingService.get();
    })
    .controller('FriendController', function ($scope, $state, UserService) {
        var users = UserService.getUsers();

        $scope.friends = users;
    })
    .controller('GiftController', function($scope, $state, UserService){
        var user = UserService.getUser("siatek25");
        console.log(user);
        var gifts = UserService.getUser("siatek25").gifts;

        $scope.gifts = gifts;
    });

