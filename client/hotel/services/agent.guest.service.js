(function() {
    'use strict';
    angular
        .module('jguestApp')
        .factory('AgentGuest', ExtendedGuest);

    ExtendedGuest.$inject = ['$resource'];

    function ExtendedGuest ($resource) {
        var resourceUrl  =  'api/agent-guest';

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
            },
            'update': { method:'PUT' }
        });
    }
})();
