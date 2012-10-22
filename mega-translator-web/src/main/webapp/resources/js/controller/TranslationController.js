var TranslationController = function ($scope) {
    $scope.savePart = false;
    $scope.uploadedFileResults = [];
    $scope.addFileResult = function (response) {
        $scope.uploadedFileResults.push(response);
    };
    $scope.showSavePart = function () {
        $scope.savePart = true;
    }
};
