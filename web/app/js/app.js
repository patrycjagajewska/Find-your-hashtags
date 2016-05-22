'use strict';

// main application module definition
angular.module('findyourhashtags', [
        'ui.router',
        'ngResource',
        'wishlist.services',
        'wishlist.directives',
        'wishlist.controllers'
    ])

    .constant("serverUrl", "localhost:8080")

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
            .state('main', {
                url: '/',
                templateUrl: 'partials/views/main.html',
                //controller: 'GreetingController',
                //service: 'GreetingService'
            })
            .state('login', {
                url: '/login',
                templateUrl: 'partials/views/login.html'
            });

    })

    .run(function ($state) {
        $state.go('login');
    });