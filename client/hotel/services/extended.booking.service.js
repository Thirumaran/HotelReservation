(function() {
    'use strict';
    angular
        .module('jguestApp')
        .factory('ExtendedBooking', ExtendedBooking);

    ExtendedBooking.$inject = ['$resource'];

    function ExtendedBooking ($resource) {
        var resourceUrl  =  'api/guest_booking/:guestId';

        return $resource(resourceUrl, {guestId:'@guestId'}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            }
        });
    }
})();
