$(function(){
    var $iosActionsheet = $('#iosActionsheet');
    var $iosMask = $('#iosMask');

    function hideActionSheet() {
        $iosActionsheet.removeClass('weui-actionsheet_toggle');
        $iosMask.fadeOut(200);
    }

    $iosMask.on('click', hideActionSheet);
    $('#iosActionsheetCancel').on('click', hideActionSheet);
    $(".stuclass-operate").on("click", function(){
        $iosActionsheet.addClass('weui-actionsheet_toggle');
        $iosMask.fadeIn(200);
    });
    $(".stuclass-operate").click(function (e) {
        e.stopPropagation();
    })
    $(".stuclass-item").click(function () {
        var classid=$(this).find(".hideid").html();
        window.location.href="stuclassdetail.action?classid="+classid;
    });
});