(function() {
    'use strict';

    angular
        .module('jguestApp')
        .controller('HotelHomeController', HotelHomeController);

    HotelHomeController.$inject = ['$scope', '$http', 'dialogs', 'Principal', 'LoginService', '$state', 'entity', 'moment', 'calendarConfig', 'ExtendedGuest', 'Booking', 'BookingStatus', 'Room', 'UnavailableBooking', 'ExtendedBooking'];

    function HotelHomeController ($scope, $http, dialogs, Principal, LoginService, $state, entity, moment, calendarConfig, ExtendedGuest, Booking, BookingStatus, Room, UnavailableBooking, ExtendedBooking) {
        var vm = this;


        vm.booking = entity;
        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        vm.telNo = "";
        vm.identityNo = "";
        
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.selectedBookingStatusId = null;        

        vm.guestList = null;
        vm.selectedGuest = null;
        vm.guestEdit = false;
        vm.error = "";
        vm.searchEnabled = true;

        vm.newGuestEnabled = false;
        vm.viewBookings = false;
        vm.viewNewBooking = false;

        vm.bookedRooms = null;
        vm.activeRooms = [];
        vm.TotalBooked = 0;
        vm.TotalCancelled = 0;

        vm.bookingstatuses = BookingStatus.query();
        //vm.unavailableBookings = UnavailableBooking.query();        
        vm.rooms = Room.query();
        vm.isBookingEdit = false;

        vm.showBookings = function() {
            vm.viewBookings = !vm.viewBookings;
        }

        vm.findBooking = function(id) {
            for(var i=0; i<vm.bookedRooms.length; i++) {
                if (vm.bookedRooms[i].id == id) {
                    return vm.bookedRooms[i];
                }
            }
            return null;
        }

        vm.enableNewGuest = function() {
            vm.newGuestEnabled = !vm.newGuestEnabled;
        }

        vm.newBooking = function() {
            vm.viewNewBooking = !vm.viewNewBooking;
        }

        vm.editBooking = function(id) {
            vm.booking.id = id;
            var booking = vm.findBooking(id);
            vm.viewNewBooking = true;
            vm.booking.checkIn = new Date(booking.checkIn);
            vm.booking.checkOut = new Date(booking.checkOut);
            vm.booking.bookingStatusId = booking.bookingStatusId;
            vm.booking.rooms = booking.rooms;
        }

        vm.checkedIn = "Checked In";
        vm.checkedOut = "Checked Out";
        vm.cancelled = "Cancelled";
        vm.booked  = "Booked";

        vm.editGuest = function() {
            vm.guestEdit = !vm.guestEdit;
        }

        vm.isCheckedIn = function (index) {
            if (vm.activeRooms[index].bookingStatusBookingStatus == vm.checkedIn) {
                return false;
            }
            return true;
        }

        vm.findBookingStatus = function(status) {
            for (var i=0;i <vm.bookingstatuses.length; i++) {
                var bookStatus = vm.bookingstatuses[i];

                if (bookStatus.bookingStatus == status) {
                   return  bookStatus.id;
                } 
            }  
        } 

        vm.checkInRoom = function(id) {

            dialogs.confirm("Check In",'Do you want the customer check in ?')
            .result
            .then( function(){
                var status_id = vm.findBookingStatus(vm.checkedIn);
                vm.modifyBookingStatus(id,status_id); 
           });
          
        }

        vm.checkOutRoom = function(id) {

            dialogs.confirm("Check out",'Do you want the customer check out ?')
            .result
            .then( function(){
                var status_id = vm.findBookingStatus(vm.checkedOut);
                vm.modifyBookingStatus(id,status_id); 
           });
          
        }

        vm.cancellRoom = function(id) {
        
            dialogs.confirm("Cancellation",'Do you want the customer to cancell the booking ?')
            .result
            .then( function(){
                var status_id = vm.findBookingStatus(vm.cancelled);
                vm.modifyBookingStatus(id,status_id);
                
            });
        }

        vm.modifyBookingStatus = function(id, statusId) {
            vm.booking.id = id;
            var booking = vm.findBooking(id);
            vm.viewNewBooking = false;
            vm.booking.checkIn = new Date(booking.checkIn);
            vm.booking.checkOut = new Date(booking.checkOut);
            vm.booking.bookingStatusId = statusId;
            vm.booking.rooms = booking.rooms;
            vm.save();
        }


        vm.enableSearch = function() {
            vm.searchEnabled = true;
            vm.newGuestEnabled = false;
            vm.columnExpand = 3;
            vm.guestList = null;
            vm.selectedGuest = null;
            vm.activeRooms = [];
        }

        vm.getBookedRooms = function(id) {
            
            ExtendedBooking.query({guestId:id},onSuccess, onError)
            
            function onSuccess(data, headers) {
                vm.bookedRooms = data;
                
                vm.activeRooms = [];
                vm.TotalBooked = 0;
                vm.TotalCancelled = 0;

                for (var i=0;i <vm.bookedRooms.length; i++) {
                    var bookingStatus = vm.bookedRooms[i].bookingStatusBookingStatus;
                    if (bookingStatus == vm.checkedOut) {
                        vm.TotalBooked = vm.TotalBooked + 1;
                    } else if (bookingStatus == vm.cancelled) {
                        vm.TotalCancelled = vm.TotalCancelled + 1;
                    } else if ((bookingStatus == vm.booked) || (bookingStatus == vm.checkedIn)) {
                        vm.activeRooms.push(vm.bookedRooms[i]);
                    }
                }

                vm.TotalBooked = vm.TotalBooked + vm.activeRooms.length;



                if (vm.activeRooms.length == 0) {
                    vm.viewNewBooking = true;
                    vm.viewBookings = false;         
                } else {
                    vm.viewNewBooking = false;
                    vm.viewBookings = true;
                }
                
            }

            function onError(error) {
                vm.error = error.data.message;
            }
        }



        var unsubscribe = $scope.$on('jguestApp:GuestUpdated', function(event, result) {
            vm.guestEdit = false;   
            vm.selectedGuest = result;
            vm.setBooking(vm.selectedGuest.id);
            vm.viewNewBooking = false;
            vm.viewBookings = true;
            vm.searchEnabled = false;
            vm.newGuestEnabled = false;
        });
        
        $scope.$on('$destroy', unsubscribe);



        vm.chooseGuest = function(index) {
            vm.selectedGuest = vm.guestList[index];
            vm.setBooking(vm.selectedGuest.id);
        }

        vm.setBooking = function(id) {
            vm.booking.guestId = id;
            vm.getBookedRooms(vm.booking.guestId);   
        }

        vm.searchGuest = function() {
            if (vm.telNo === "") {
                vm.telNo="Empty";
            }

            if (vm.identityNo === "") {
                vm.identityNo="Empty";
            }

            vm.bookedRooms = null;
            vm.resetBookingAndGuest();

            ExtendedGuest.query({telNo:vm.telNo,identityNo:vm.identityNo},onSuccess, onError)
            
            function onSuccess(data, headers) {
                if (data.length >0) {
                    
                    if (data.length === 1) {
                        vm.selectedGuest = data[0];
                    }
                    else {
                        vm.guestList = data;
                    }

                    vm.telNo="";
                    vm.identityNo="";
                    vm.searchEnabled = false;

                    vm.columnExpand = 6;

                    if (data.length === 1) {
                        vm.booking.guestId = vm.selectedGuest.id;
                        vm.getBookedRooms(vm.booking.guestId);
                    }
                }
                else
                {
                    vm.telNo="";
                    vm.identityNo="";
                    vm.name=""; 
                }
            }

            function onError(error) {
                vm.error = error.data.message;
            }
        }

        function clear () {
            vm.resetBooking();
            vm.viewNewBooking = false;

        }

        vm.save = function () {
            vm.isSaving = true;
            if (vm.booking.id !== null) {
                vm.isBookingEdit = true;
                Booking.update(vm.booking, onSaveSuccess, onSaveError);
            } else {
                Booking.save(vm.booking, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            
            vm.getBookedRooms(result.guestId);

            vm.isSaving = false;
            vm.resetBooking();
            vm.viewNewBooking = false;
        }


        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.checkIn = false;
        vm.datePickerOpenStatus.checkOut = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }


        vm.resetBooking = function () {

            vm.booking.checkIn = null;
            vm.booking.checkOut = null;
            vm.booking.bookingStatusId = null;
            vm.booking.rooms = [];
            vm.booking.id = null;
        }

        vm.resetBookingAndGuest = function () {

            vm.booking.checkIn = null;
            vm.booking.checkOut = null;
            vm.booking.bookingStatusId = null;
            vm.booking.guestId = null;
            vm.booking.rooms = [];
            vm.booking.id = null;
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
        function register () {
            $state.go('register');
        }
    }
})();
