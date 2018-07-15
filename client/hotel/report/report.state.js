(function() {
    'use strict';

    angular
        .module('jguestApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        
        $stateProvider
        .state('report', {
            parent: 'hotelApp',
            url: '/',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/hotel/report/report.html',
                    controller: 'HotelReportController',
                    controllerAs: 'vm',
                    resolve: {
                        entity: function () {
                            return {
                                checkIn: null,
                                checkOut: null,
                                status: true,
                                bookingStatusId: null,
                                guestId: null,
                                rooms: []
                            };
                        }
                    }
                }
            },
            resolve: {
                mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('home');
                    return $translate.refresh();
                }]
            }
        })
        .state('manage-guest-new1', {
            parent: 'hotelHome',
            url: '/new',
            data: {
                authorities: []
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/hotel/manage-guest.html',
                    controller: 'ManageGuestController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                address: null,
                                telNo: null,
                                srilankan: false,
                                identityNo: null,
                                note: null,
                                status: true,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('hotelHome');
                }, function() {
                    $state.go('hotelHome');
                });
            }]
        });
    }
})();
