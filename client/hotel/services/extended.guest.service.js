(function() {
    'use strict';
    angular
        .module('jguestApp')
        .factory('ExtendedGuest', ExtendedGuest);

    ExtendedGuest.$inject = ['$resource'];

    function ExtendedGuest ($resource) {
        var resourceUrl  =  'api/guests/:telNo/:identityNo';

        return $resource(resourceUrl, {telNo:'@telNo',identityNo:'@identityNo'}, {
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
