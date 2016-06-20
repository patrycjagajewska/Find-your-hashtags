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
            $scope.statuses = resp;
            for(var i = 0; i < $scope.statuses.length; i++){
                $scope.statuses[i].favourited = $scope.statuses[i].tweet.favorited;
                $scope.statuses[i].retweeted = $scope.statuses[i].tweet.retweeted;
            }
        });

        //TwitterService.getScreenName().then(function(resp){
        //    $scope.screenName = resp;
        //});

        $scope.searchForHashtag = function(hashtag) {
            TwitterService.searchForHashtag(hashtag).then(function(resp){
                $scope.statuses = resp;
                for(var i = 0; i < $scope.statuses.length; i++){
                    $scope.statuses[i].favourited = $scope.statuses[i].tweet.favorited;
                    $scope.statuses[i].retweeted = $scope.statuses[i].tweet.retweeted;
                }
            });
        };

        $scope.favourite = function(status) {

            if(!status.favourited){
                TwitterService.favourite(status.id).then(function(resp){
                    console.log("Tweet " + status.id + " marked as favourite");
                });
            } else {
                TwitterService.unfavourite(status.id).then(function(resp){
                    console.log("Tweet " + status.id + " unmarked favourite");
                });
            }

            status.favourited = !status.favourited;
            console.log(status.favourited);
        };

        $scope.unfavourite = function(status) {

            TwitterService.unfavourite(status.id).then(function(resp){
                console.log("Tweet " + status.id + " unmarked favourite");
            });
        };

        $scope.retweet = function(status) {

            if(!status.retweeted) {
                TwitterService.retweet(status.id).then(function (resp) {
                    console.log("Tweet " + status.id + " retweeted");
                });
            } else {
                TwitterService.undoRetweet(status.id).then(function(resp){
                    console.log("Tweet " + status.id + " removed from retweeted");
                });
            }

            status.retweeted = !status.retweeted;
            console.log(status.retweeted);
        };

        //$scope.undoRetweet = function(status) {
        //
        //    TwitterService.undoRetweet(status.id).then(function(resp){
        //        console.log("Tweet " + status.id + " removed from retweeted");
        //    });
        //};

        $scope.reply = function(status, statusText) {

            TwitterService.reply(status.id, status.tweet.user.screenName, statusText).then(function(resp){
                console.log("Replied to a tweet " + status.id + " of user " + status.tweet.user.screenName);
                console.log("Reply text - " + statusText);
            });
        };

        $scope.showComment = function(){
            $(function () {
                $('.panel-google-plus > .panel-footer > .input-placeholder, .panel-google-plus > .panel-google-plus-comment > .panel-google-plus-textarea > button[type="reset"]').mousedown(function(event) {
                    var $panel = $(this).closest('.panel-google-plus');
                    var $comment;
                    $comment = $panel.find('.panel-google-plus-comment');

                    $comment.find('.btn:first-child').addClass('disabled');
                    $comment.find('textarea').val('');

                    $panel.toggleClass('panel-google-plus-show-comment');

                    if ($panel.hasClass('panel-google-plus-show-comment')) {
                        $comment.find('textarea').focus();
                    }
                });
                $('.panel-google-plus-comment > .panel-google-plus-textarea > textarea').on('keyup', function(event) {
                    var $comment = $(this).closest('.panel-google-plus-comment');

                    $comment.find('button[type="submit"]').addClass('disabled');
                    if ($(this).val().length >= 1) {
                        $comment.find('button[type="submit"]').removeClass('disabled');
                    }
                });
            });
        };

        $scope.hideComment = function(){
            $(function () {
                $('.panel-google-plus > .panel-footer > .input-placeholder, .panel-google-plus > .panel-google-plus-comment > .panel-google-plus-textarea > button[type="reset"]').focus(function(event) {
                    var $panel = $(this).closest('.panel-google-plus');

                    $panel.toggleClass('panel-google-plus-show-comment');

                });
            });
        }

    });


