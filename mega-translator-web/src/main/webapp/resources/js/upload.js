$(function () {
    $('#dropzone').filedrop({
        url:'/translation/upload',
        paramname:'file',
        headers: {
            'Accept' : 'application/json'
        },
        maxFiles: 25,
        dragOver:function () {
            $('#dropzone').css('background', '#EEEFFF');
        },
        drop:function () {
            $('#dropzone').css('background', '#EEEEEE');
        },
        uploadFinished: function(i, file, response, time) {
            $('#uploadResult').append('<li>' + response.message + '</li>');
        }
    });
});