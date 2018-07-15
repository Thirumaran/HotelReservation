(function() {
    'use strict';

    angular
        .module('jguestApp')
        .controller('HotelReportController', HotelReportController);

    HotelReportController.$inject = ['$scope', '$http', 'Principal', 'LoginService', '$state', 'DateUtils', 'entity', 'moment', 'BookingStatus', 'BookingReport'];

    function HotelReportController ($scope, $http, Principal, LoginService, $state, DateUtils, entity, moment, BookingStatus, BookingReport) {
        var vm = this;


        vm.booking = entity;
        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;

        vm.error = "";

        vm.booking.bookingStatusId = null;
        vm.bookingList = null;

        vm.bookingstatuses = BookingStatus.query();

        vm.checkIn = null;
        vm.checkOut = null;


        vm.datePickerOpenStatus.checkIn = false;
        vm.datePickerOpenStatus.checkOut = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }

        function clear () {
         
        }

        vm.bookingReport = function() {

            if (vm.checkIn == null) {
                vm.checkIn = new Date(new Date().getFullYear(), 0, 1);
                vm.checkOut = new Date();
            }

            BookingReport.query({fromDate: vm.checkIn.toUTCString(),toDate: vm.checkOut.toUTCString()},onSuccess, onError)
            
            function onSuccess(data, headers) {
                vm.bookingList = data;
            }

            function onError(error) {
                vm.error = error.data.message;
            }
        }


        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
    }
})();
