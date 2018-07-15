(function() {
    'use strict';
    angular
        .module('jguestApp')
        .factory('BookingReport', BookingReport);

    BookingReport.$inject = ['$resource'];

    function BookingReport ($resource) {
        var resourceUrl  =  'api/booking_report/:fromDate/:toDate';

        return $resource(resourceUrl, {fromDate:'@fromDate',toDate:'@toDate'}, {
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
