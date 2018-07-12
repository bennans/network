$(document).ready(function(){
    $("#Verify").click(function () {
       this.src="/getCode?date"+new Date();
    });
    var ref = setInterval(function(){
        isallowbtn();
    },500);
    $("#loginbtn").click(function () {
        var email = $("#email").val();
        var pwd = $("#pwd").val();
        var code = $("#code").val();
        $.ajax({
            type:"post",
            url:"/login",
            data:{email:email,pwd:pwd,code:code},
            dataType:"text",
            success:function (data) {
                if(data == 0){
                    alert("验证码错误");
                }
                if(data == 1){
                    alert("用户名和密码错误")
                }
                if(data == 2){
                    window.location.href="/toindex";
                }
            }
        })
    });
});
function isallowbtn() {
    var email = $("#email").val();
    var pwd = $("#pwd").val();
    var code = $("#code").val();
    if(email === "" || pwd ===""|| code ===""){
        $("#loginbtn").attr("disabled",true);
    }else {
        $("#loginbtn").attr("disabled",false);
    }
}