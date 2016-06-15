'use strict';

angular.module('findyourhashtags.services', [])
    .factory('TwitterService', function (Restangular) {
        var service = {};

        service.getTweets = function() {
            return Restangular.all('tweets').getList();
        };

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

        service.undoRetweet= function(tweetId) {
            return Restangular.all('tweets').customPOST({}, 'undoretweet', {tweetId: tweetId}, {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            });
        };

        service.reply = function(tweetId, screenName, statusText) {
            return Restangular.all('tweets').one('comment').put({tweetId: tweetId, screenName: screenName, text: statusText}, {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            });
        };

        return service;
    });