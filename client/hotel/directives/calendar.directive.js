'use strict';

angular.module('jguestApp')
    .directive('bookingCalendar', function () {
        return {
            restrict: 'E',
            scope: {
                unavailableRooms2:'='
            },
            controller : 'BookingCalendarController',
            templateUrl: 'app/hotel/directives/calendar.html'
        };
    });