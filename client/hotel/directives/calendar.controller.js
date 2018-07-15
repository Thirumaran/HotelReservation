(function() {
  'use strict';

  angular
    .module('jguestApp')
    .controller('BookingCalendarController', BookingCalendarController);

  BookingCalendarController.$inject = ['moment', '$window', 'calendarConfig', 'UnavailableBooking'];

  function BookingCalendarController(moment, $window, calendarConfig, UnavailableBooking) {

    var vm = this;

    vm.unavailableRooms = "";
    vm.error = "";

    function NewEvent(eventName, color, startDate, endDate) {
      this.title = eventName;
      this.color = color;
      this.startsAt = startDate;
      this.endsAt = endDate;
      this.draggable = true;
      this.resizable = true;
    }
 

    vm.events = [];

    vm.unavailableRooms = function() {
        
        UnavailableBooking.query({},onSuccess, onError)
        
        function onSuccess(data, headers) {
            vm.unavailableRooms = data;

            for(var i=0;i<vm.unavailableRooms.length;i++) {
              var roomData = vm.unavailableRooms[i];
              for(var j=0;j<roomData.rooms.length;j++) {
                 vm.addEventToCalendar(roomData, roomData.rooms[j]);
              }
            }
        }

        function onError(error) {
            vm.error = error.data.message;
        }
    }

    vm.addEventToCalendar = function(roomData,room) {

      var guestName = roomData.guestName;
      var bookingStatus = roomData.bookingStatus;
      var checkIn = roomData.checkIn;
      var checkOut = roomData.checkOut;
      var roomName = room.name;
      var telNo = roomData.telNo;
      var roomLocation = room.location;
      var roomType = room.roomTypeRoomType;
      var indicatorColor = null;
      var backgroundColor = null;

      if (room.roomTypeRoomType == "Budget") {
        indicatorColor = "blue";
        backgroundColor = "yellow";
      } else {
        indicatorColor = "green";
        backgroundColor = "green";
      }

      vm.events.push(new NewEvent(guestName+"   ["+telNo+"]     ["+bookingStatus+"]     ["+roomName+"]    ["+roomLocation+"]",{primary: indicatorColor, secondary: backgroundColor},checkIn,checkOut));
    }
    


    vm.unavailableRooms();


    vm.calendarView = 'month';
    vm.viewDate = new Date();
  
    vm.cellIsOpen = false;

    vm.addEvent = function() {
      vm.events.push({
        title: 'New event',
        startsAt: moment().startOf('day').toDate(),
        endsAt: moment().endOf('day').toDate(),
        color: calendarConfig.colorTypes.important,
        draggable: true,
        resizable: true
      });
    };

    vm.eventClicked = function(event) {
      $window.show('Clicked', event);
    };

    vm.eventEdited = function(event) {
      $window.show('Edited', event);
    };

    vm.eventDeleted = function(event) {
      $window.show('Deleted', event);
    };

    vm.eventTimesChanged = function(event) {
      $window.show('Dropped or resized', event);
    };

    vm.toggle = function($event, field, event) {
      $event.preventDefault();
      $event.stopPropagation();
      event[field] = !event[field];
    };

    vm.timespanClicked = function(date, cell) {

      if (vm.calendarView === 'month') {
        if ((vm.cellIsOpen && moment(date).startOf('day').isSame(moment(vm.viewDate).startOf('day'))) || cell.events.length === 0 || !cell.inMonth) {
          vm.cellIsOpen = false;
        } else {
          vm.cellIsOpen = true;
          vm.viewDate = date;
        }
      } else if (vm.calendarView === 'year') {
        if ((vm.cellIsOpen && moment(date).startOf('month').isSame(moment(vm.viewDate).startOf('month'))) || cell.events.length === 0) {
          vm.cellIsOpen = false;
        } else {
          vm.cellIsOpen = true;
          vm.viewDate = date;
        }
      }

    };
  }
})();