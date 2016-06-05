'use strict';

// main application module definition
angular.module('findyourhashtags', [
        'ui.router',
        'ngResource',
        'findyourhashtags.services',
        'findyourhashtags.directives',
        'findyourhashtags.controllers',
        'restangular'
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
            .state('home', {
                url: '/',
                templateUrl: 'partials/views/home.html',
                controller: 'TwitterController',
                service: 'TwitterService'
            })
            .state('login', {
                url: '/login',
                templateUrl: 'partials/views/login.html'
            });

    })

    .run(function ($state) {
        $state.go('login');
    });