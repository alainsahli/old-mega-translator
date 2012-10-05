angular.module('mega', [])
    .directive('upload', function () {
        return {
            restrict: 'A',
            link: function (scope, element, attrs, controller) {
                $(element).filedrop({
                    url: '/translation/upload',
                    queuefiles: 1,
                    paramname: 'file',
                    headers: {
                        'Accept': 'application/json'
                    },
                    maxFiles: 25,
                    dragOver: function () {
                        $(element).css('background', '#EEEFFF');
                    },
                    drop: function () {
                        $(element).css('display', 'none');
                    },
                    uploadFinished: function (i, file, response, time) {
                        scope.$eval(attrs.callback)(response);
                        scope.$digest();
                    },
                    afterAll: function() {

                    }
                });
            }
        };
    });