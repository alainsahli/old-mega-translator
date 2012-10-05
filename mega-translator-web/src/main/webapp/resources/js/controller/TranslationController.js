var TranslationController = function ($scope) {
    $scope.name = 'Pedro';
    $scope.messages = [];
    $scope.addMessage = function (response) {
        $scope.messages.push(response.message);
    }
};
