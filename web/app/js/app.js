'use strict';

// main application module definition
angular.module('wishlist', [
        'ui.router',
        'ngResource',
        'wishlist.services',
        'wishlist.directives',
        'wishlist.controllers'
    ])

    .constant("serverUrl", "http://192.168.50.123:8080")

    .config(function ($stateProvider, $urlRouterProvider) {

        $urlRouterProvider.when('', '/login');

        $urlRouterProvider.otherwise(function ($injector, $location) {
            $injector.invoke(['$state', function ($state) {
                $state.go('404');
            }]);
        });

        $stateProvider
            .state('404', {
                templateUrl: 'partials/404.html'
            })
            .state('greeting', {
                url: '/greeting',
                templateUrl: 'partials/views/greeting.html',
                controller: 'GreetingController',
                service: 'GreetingService'
            })
            .state('login', {
                url: '/login',
                templateUrl: 'partials/views/login.html'
            })
            .state('register', {
                url: '/register',
                templateUrl: 'partials/views/register.html'
            })
            .state('navbar', {
                templateUrl: 'partials/views/navbar.html'
            })
            .state('navbar.friends', {
                url: '/friends',
                templateUrl: 'partials/views/friends.html',
                controller: 'FriendController',
                service: 'UserService'
            })
            .state('navbar.gifts', {
                url: '/gifts',
                templateUrl: 'partials/views/gifts.html',
                controller: 'GiftController',
                service: 'UserService'
            });

    })

    .run(function ($state) {
        $state.go('login');
    });