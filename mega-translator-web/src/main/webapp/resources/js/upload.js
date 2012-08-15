$(function () {
    $('#dropzone').filedrop({
        url:'/translation/upload',
        paramname:'file',
        data: {
            fileSetId: uniqueId()
        },
        maxFiles: 25,
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
        }
    });
});

function uniqueId() {
    var date = new Date;
    var uniqueId = date.getTime() * Math.random();
    return uniqueId;
}