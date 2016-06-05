'use strict';

angular.module('findyourhashtags.services', [])
    .factory('HashtagService', function ($resource, serverUrl) {
        var service = {};

        service.getTweets = function() {
            Restangular.all('tweets').getList().then(function(resp){
                console.log(resp);
            });
        };

        service.getTweet = function(tweetId){
            Restangular.all('tweets').one(tweetId).get().then(function(resp){
                console.log(resp);
            });
        };

        service.searchHashtag = function(hashtag) {
            Restangular.all('tweets').one(hashtag).get().then(function(resp){
                console.log(resp);
            });
        };

        service.retweet = function(){
          Restangular.one(tweetId).get().then(function(data){

            });
        };

        //service.getTweets() = function() {
        //    return $resource(serverUrl + "/tweets").query();
        //};
        //
        //service.searchHashtag = function(hashtag) {
        //    return $resource(serverUrl + "/tweets/" + hashtag).get();
        //};
        //
        //service.retweet = function(tweetId) {
        //    return $resource(serverUrl + "/tweets/retweet/" + tweetId).get();
        //};
        //
        //service.favourite = function(tweetId) {
        //    return $resource(serverUrl + "/tweets/favourite/" + tweetId).get();
        //};
        //
        //service.getComments = function(tweetId) {
        //    return $resource(serverUrl + "/tweets/comments/" + tweetId).get();
        //};
        //
        //service.comment = function(tweetId) {
        //    return $resource(serverUrl + "/tweets/comment/" + tweetId).get();
        //};

        return service;
    });