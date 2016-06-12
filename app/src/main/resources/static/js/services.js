'use strict';

angular.module('findyourhashtags.services', [])
    .factory('TwitterService', function (Restangular) {
        var service = {};

        service.getTweets = function() {
            return Restangular.all('tweets').getList();
        };

        //service.getTweetId = function(tweetId){
        //    Restangular.all('tweets').one(tweetId).get().then(function(resp){
        //        console.log(resp);
        //    });
        //};

        service.searchForHashtag = function(hashtag) {
             return Restangular.all('tweets').all(hashtag).getList();
        };

        service.favourite = function(tweetId) {
            return Restangular.all('tweets').customPOST({}, 'favourite', {tweetId: tweetId}, {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            });
        };

        service.unfavourite = function(tweetId) {
            return Restangular.all('tweets').customPOST({}, 'unfavourite', {tweetId: tweetId}, {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            });
        };

        service.retweet = function(tweetId) {
            return Restangular.all('tweets').customPOST({}, 'retweet', {tweetId: tweetId}, {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            });
        };

        return service;
    });