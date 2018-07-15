(function() {
    'use strict';

    angular
        .module('jguestApp')
        .controller('NewGuestController', NewGuestController);

    NewGuestController.$inject = ['$timeout', '$scope', '$stateParams', 'DataUtils', 'Guest', 'AgentGuest'];

    function NewGuestController ($timeout, $scope, $stateParams, DataUtils, Guest, AgentGuest) {

           var newGuestEntity  = {
                        name: null,
                        address: null,
                        telNo: null,
                        isAgent: false,
                        srilankan: false,
                        identityNo: null,
                        note: null,
                        status: true,
                        id: null
                    };

        $scope.guest = newGuestEntity;
        $scope.isSaving = false;


        $scope.isNewGuest = function() {
            return ($scope.guestId === null || $scope.guestId === undefined);
        }

        if (!$scope.isNewGuest()) {
            Guest.get({id : $scope.guestId}, onSuccess, onError);

            function onSuccess(data, headers) {
                $scope.guest = data;
            }

            function onError(errorData) {
                console.log("Error "+errorData);    
            }
            
            console.log("test");
        }

        $scope.saveGuest = function() {
            $scope.isSaving = true;
            if ($scope.guest.id !== null) {
                Guest.update($scope.guest, onSaveSuccess, onSaveError);
            } else {
                AgentGuest.save($scope.guest, onSaveSuccess, onSaveError);
            }

            function onSaveSuccess (result) {
                $scope.isSaving = false;
                $scope.$emit('jguestApp:GuestUpdated', result);
            }

            function onSaveError (data) {
                $scope.isSaving = false;
            }
        }
    }
})();
