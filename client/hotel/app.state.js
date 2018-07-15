(function() {
    'use strict';

    angular
        .module('jguestApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('hotelApp', {
            abstract: true,
            views: {
                'hotelNavbar@': {
                    templateUrl: 'app/hotel/navbar/navbar.html',
                    controller: 'HotelNavbarController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                authorize: ['Auth',
                    function (Auth) {
                        return Auth.authorize();
                    }
                ],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('global');
                }]
            }
        });
    }
})();
