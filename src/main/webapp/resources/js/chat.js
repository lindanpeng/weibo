$(document).ready(function() {
	$("#chat_content").scrollTop(10000);
    var deleteIndex;//点击的li的index值
    $("li").bind("contextmenu", function() {
        return false;
    })
    $(".contacts li").mousedown(function(e) {

            //记录点击的li的index值
            deleteIndex = $(this).index();
            //右键为3
            if (3 == e.which) {
                var hide_div = document.getElementById("hid_div");
                var e = event || window.event;
                var scrollX = document.documentElement.scrollLeft || document.body.scrollLeft;
                var scrollY = document.documentElement.scrollTop || document.body.scrollTop;
                var x = e.pageX || e.clientX + scrollX;
                var y = e.pageY || e.clientY + scrollY;
                hide_div.style.display = "block";
                hide_div.style.position = "absolute";
                $('#hid_div').css('left',x-465 + 'px');
                $('#hid_div').css('top',y -75+ 'px');
                $(this).addClass('wait');
                console.log("- -");
            } 
            else if (1 == e.which) {
           /*     $(".hide_div").hide();
                $(".con2").removeClass("con2");
                $(this).addClass("con2");
                $(this).removeClass('wait');
                $(".welcome").hide();
                $(".containt").show();*/
            }
        })

  /*  $(".contacts3 li").click(function(){
        $(".contacts").show();
        $(".hide_per").hide();
    });
*/

     /*  $(".findsomone").click(function(){
    	  
            $(".hide_persons").addClass("hide_per");
            $(".hide_persons").removeClass("hide_persons");
            $(".contacts").hide();
            $(".hide_per").show();
       });*/

     /*  $(".containt").click(function(){
            $(".contacts").show();
            $(".hide_per").hide();
       });*/

       /* $(".welcome").click(function(){
            $(".contacts").show();
             $(".hide_per").hide();
       });*/
        

  $('#hid_div').click(function(){
        // console.log("= =");
         // alert("123");
        //找到对应li，删除
        $('.contacts li:eq('+deleteIndex+')').remove();
        $(this).hide();
  });
        
document.oncontextmenu = function() {
    return false;
};
});
