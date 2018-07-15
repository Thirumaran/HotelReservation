'use strict';

angular.module('jguestApp')
    .directive('newGuest', function () {
        return {
            restrict: 'E',
            scope: {
                guestId :'@'
            },
            controller : 'NewGuestController',
            templateUrl: 'app/hotel/directives/new-guest.html'
        };
    });