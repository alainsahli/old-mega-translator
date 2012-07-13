$(function () {
    $('#dropzone').filedrop({
        url:'@Url.Action("UploadFiles")',
        paramname:'files',
        maxFiles:5,
        dragOver:function () {
            $('#dropzone').css('background', 'blue');
        },
        dragLeave:function () {
            $('#dropzone').css('background', 'gray');
        },
        drop:function () {
            $('#dropzone').css('background', 'gray');
        },
        afterAll:function () {
            alert('afterAll');
            $('#dropzone').html('The file(s) have been uploaded successfully!');
        },
        uploadFinished:function (i, file, response, time) {
            $('#uploadResult').append('<li>' + file.name + '</li>');
        }
    });
});