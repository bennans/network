document.oncontextmenu = function(){
    return false;
};
$(document).ready(function(){
    $("#uploafile-add").on("change", function () {
        $("#processBar").css("width","0%");
        $("#processBar").html("");
    });
    //ajax上传文件
    $("#upload-btn").click(function () {
        var upfile = document.getElementById("file").files[0];
        var filename = upfile.name;
        var formData = new FormData($('#uploadform')[0]);
        let parentfdid = $.trim($("#parentfdid").text());
        formData.append("pid",parentfdid);
        formData.append("filename",filename);
        $.ajax({
            type: 'post',
            url: "/upload",
            async:true,
            data: formData,
            cache: false,
            processData: false,
            contentType: false,
            xhr:function(){
                myXhr = $.ajaxSettings.xhr();
                if(myXhr.upload){ // check if upload property exists
                    myXhr.upload.addEventListener('progress',function(e){
                        var loaded = e.loaded;                  //已经上传大小情况
                        var total = e.total;                      //附件总大小
                        var percent = Math.floor(100*loaded/total)+"%";     //已经上传的百分比
                        console.log("已经上传了："+percent);
                        $("#processBar").css("width",percent);
                        $("#processBar").html(percent);
                    }, false); // for handling the progress of the upload
                }
                return myXhr;
            }
        }).success(function (data) {
            if(data==0){
                alert("文件不能为空");
            }else if(data==1){
                alert("文件类型暂时不允许")
            } else if(data ==2){
                alert("上传失败")
            }
            else {
                let dataArr = data.split("-");
                console.log(data);
                if(dataArr[2]==1){
                    $("#file-list").append("<div class=\"col-4 col-sm-3 col-md-2 col-lg-1 file-item text-center\"th:each=\"folderlist:${folderlist}\">\n" +
                        "            <span class=\"fdid hide\">"+dataArr[1]+"</span>\n" +
                        "            <div class=\"file-img\">\n" +
                        "                <img src=\"img/"+dataArr[0]+".png\">\n" +
                        "            </div>\n" +
                        "            <div class=\"file-desc\">\n" +
                        "                <span>"+filename+"</span>\n" +
                        "            </div>\n" +
                        "        </div>")
                }
            };
        }).error(function () {
            alert("上传失败");
        });

    });
    //获取文件内容
    /*var str="";
    $("#uploafile-add").on("change", function () {
        var upfile = document.getElementById("uploafile-add").files[0];
        var formData = new FormData(upfile);
        var str="<div class=\"uploadfile-item row\">\n" +
            "                     <input type=\"file\" name=\"files\" value='"+upfile+"'/>\n" +
            "                            <span class=\"uploadfile-size col-sm-3\">"+upfile.size+"KB</span>\n" +
            "                            <span class=\"uploadfile-stat col-sm-3\">等待上传</span>\n" +
            "                        </div>";
        $("#uploadfile-list").append(str);
       /!* $.ajax({
            type:"post",
            enctype: 'multipart/form-data',
            url:"/upload",
            data:formData,
            processData: false,
            contentType: false,
            dataType:"text",
            success:function (data) {

            }
        })*!/
      /!*  var upfiletype = upfile.type;
        var str="<div class=\"uploadfile-item row\">\n" +
            "                            <span class=\"uploadfile-name col-sm-6\">"+upfile.name+"</span>\n" +
            "                            <span class=\"uploadfile-size col-sm-3\">"+upfile.size+"KB</span>\n" +
            "                            <span class=\"uploadfile-stat col-sm-3\">等待上传</span>\n" +
            "                        </div>";
        $("#uploadfile-list").append(str);*!/
    });*/


        //返回上一页
    $("#backpage").click(function () {
        history.back(-1);
    });
    //创建文件夹验证是否输入文件名
    var ref = setInterval(function(){
        let foldername = $("#foldername").val();
        if(foldername ===""){
            $("#createfolderbtn").attr("disabled",true);
        }else {
            $("#createfolderbtn").attr("disabled",false);
        }
    },500);
    //ajax创建文件
    $("#createfolderbtn").click(function () {
       let foldername = $("#foldername").val();
       let parentfdid = $.trim($("#parentfdid").text());
        $.ajax({
            type:"post",
            url:"/createfolder",
            data:{foldername:foldername,parentfdid:parentfdid},
            dataType:"text",
            success:function (data) {
                if(data == 0){
                    alert("文件夹已存在");
                }
                else{
                    $("#file-list").append("<div class=\"col-4 col-sm-3 col-md-2 col-lg-1 file-item item-folder text-center\"th:each=\"folderlist:${folderlist}\">\n" +
                        "            <span class=\"fdid hide\">"+data+"</span>\n" +
                        "            <div class=\"file-img\">\n" +
                        "                <img src=\"img/folder.png\">\n" +
                        "            </div>\n" +
                        "            <div class=\"file-desc\">\n" +
                        "                <span>"+foldername+"</span>\n" +
                        "            </div>\n" +
                        "        </div>")
                }
            }
        })
    });
    //点击文件夹进入文件夹
    var clickfolder = $("#file-list").on("click",".item-folder",function () {
        let parentfdid = $.trim($(this).find(".fdid").text());
        window.location.href="toindex?parentfdid="+parentfdid;
    });
    //当鼠标进入是，显示选中的图片
    $("#file-list").on("mouseenter",".file-item",function () {
        let src=$(this).find("img").attr("src");
        src = src.split('.');
        src = src[0];
        src = src+"act.png";
        $(this).find("img").attr("src",src);
    });
    //当鼠标移出时，显示未选中的图片
    $("#file-list").on("mouseleave",".file-item",function () {
        let src=$(this).find("img").attr("src");
        src = src.split('.');
        src = src[0];
        src=src.substring(0,src.indexOf('act'));
        src = src+".png";
        $(this).find("img").attr("src",src);
    });
    //手机端长按事件
    $(".file-img").on("taphold",function(e){
        $(".menu").hide();
        //1是左键；2是中间键；3是右键
        var key = e.which;
            var x = e.clientX+document.body.scrollLeft; //x坐标
            var y = e.clientY+document.body.scrollTop; //y坐标
            //获取menu的长宽
            var menuHeight = $(".menu").height();
            var menuWidth = $(".menu").width();
            //获取浏览器的可见长宽
            var clintHeight = getClientHeight()+document.body.scrollTop;
            var clintWidth = getClientWidth()+document.body.scrollLeft;
            //判断
            if(menuHeight + y >= clintHeight){
                y = clintHeight - menuHeight - 8;
            }
            if(menuWidth + x >= clintWidth){
                x = clintWidth - menuWidth - 8;
            }
            //之前必须要绝对定位才行
            $(".menu").show().css({left:x,top:y});

    });
    //////////////////////////////////////////////////////////
    var id;
    /////////////////////////////////////////////////////////
    $("#file-list").on("mousedown",".file-img",function(e){
        id = $(this).prev().text();
        $(".menu").hide();
        //1是左键；2是中间键；3是右键
        var key = e.which;
        if(key == 3){
            var x = e.clientX+document.body.scrollLeft; //x坐标
            var y = e.clientY+document.body.scrollTop; //y坐标
            //获取menu的长宽
            var menuHeight = $(".menu").height();
            var menuWidth = $(".menu").width();
            //获取浏览器的可见长宽
            var clintHeight = getClientHeight()+document.body.scrollTop;
            var clintWidth = getClientWidth()+document.body.scrollLeft;
            //判断
            if(menuHeight + y >= clintHeight){
                y = clintHeight - menuHeight - 8;
            }
            if(menuWidth + x >= clintWidth){
                x = clintWidth - menuWidth - 8;
            }
            //之前必须要绝对定位才行
            if($(this).parent().hasClass("item-folder")){
                $(".menu-folder").show().css({left:x,top:y});
            }else {
                $(".menu-file").show().css({left:x,top:y});
            }
        }
    });

//点击空白隐藏鼠标右键
    $(document).click(function(){
        $(".menu").hide();
    });

//响应时间
    $(".download").click(function () {
        window.location.href="downloadfile?fileid="+id;
    });
    $(".openfolder").click(function () {
        window.location.href="toindex?parentfdid="+id;
    });

    $(".deletefolder").click(function () {
        $.ajax({
            type:"post",
            url:"/deletefolder",
            data:{folderId:foldername},
            dataType:"text",
            success:function (data) {

            }
        })
    });
// 浏览器的可见高度
    function getClientHeight() {
        var clientHeight = 0;
        if (document.body.clientHeight && document.documentElement.clientHeight) {
            clientHeight = (document.body.clientHeight < document.documentElement.clientHeight) ? document.body.clientHeight: document.documentElement.clientHeight;
        } else {
            clientHeight = (document.body.clientHeight > document.documentElement.clientHeight) ? document.body.clientHeight: document.documentElement.clientHeight;
        }
        return clientHeight;
    }

// 浏览器的可见宽度
    function getClientWidth() {
        var clientWidth = 0;
        if (document.body.clientWidth && document.documentElement.clientWidth) {
            clientWidth = (document.body.clientWidth < document.documentElement.clientWidth) ? document.body.clientWidth: document.documentElement.clientWidth;
        } else {
            clientWidth = (document.body.clientWidth > document.documentElement.clientWidth) ? document.body.clientWidth: document.documentElement.clientWidth;
        }
        return clientWidth;
    }
});
