(function() {
    'use strict';
    angular
        .module('jguestApp')
        .factory('UnavailableBooking', UnavailableBooking);

    UnavailableBooking.$inject = ['$resource'];

    function UnavailableBooking ($resource) {
        var resourceUrl  =  'api/unavailable_rooms';

        return $resource(resourceUrl, {}, {
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
