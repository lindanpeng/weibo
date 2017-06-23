 function openDownList(){
    var a=document.getElementById("downList1");
    a.style.display="block";
    var b=document.getElementById("openDownListN");
    b.style.display="none";
    var c=document.getElementById("openDownListY");
    c.style.display="block";
  }
  function closeDownList(){
    var a1=document.getElementById("downList1");
    a1.style.display="none";
    var b1=document.getElementById("openDownListN");
    b1.style.display="block";
    var c1=document.getElementById("openDownListY");
    c1.style.display="none";
  }
 function check(){
		var searchKey=document.getElementById("searchKey").value;
		if(searchKey=="")
			{
			 alert("请输入搜索内容！");
			  return false;
			}
		return true;
	}
 var c2=document.getElementById("openDownListY");
 c2.style.display="none";
 var a=document.getElementById("downList1");
 a.style.display="none";
function openDownList(){
 var a=document.getElementById("downList1");
 a.style.display="block";
 var b=document.getElementById("openDownListN");
 b.style.display="none";
 var c=document.getElementById("openDownListY");
 c.style.display="block";
}

function closeDownList(){
 var a1=document.getElementById("downList1");
 a1.style.display="none";
 var b1=document.getElementById("openDownListN");
 b1.style.display="block";
 var c1=document.getElementById("openDownListY");
 c1.style.display="none";
}
function refreshData(){
	  $.post("/weibo/personal/refreshData",function(dataWrapper){
		  if(dataWrapper.unReadCommentAmount==0){
			  $("#unReadCommentAmount").hide();
		  }
		  else{
			  $("#unReadCommentAmount").text(dataWrapper.unReadCommentAmount);
			  $("#unReadCommentAmount").show();
		  }
		  if(dataWrapper.unReadMessageAmount==0){
			  $("#unReadMessageAmount").hide();
		  }
		  else{
			  $("#unReadMessageAmount").text(dataWrapper.unReadMessageAmount);
			  $("#unReadMessageAmount").show();
		  }
		  if(dataWrapper.unReadAtmeAmount==0){
			  $("#unReadAtmeAmount").hide();
		  }
		  else{

			  $("#unReadAtmeAmount").text(dataWrapper.unReadAtmeAmount);
			  $("#unReadAtmeAmount").show();
		  }
		  if(dataWrapper.unReadThumbsupAmount==0){
			  $("#unReadThumbsupAmount").hide();
		  }
		  else{

			  $("#unReadThumbsupAmount").text(dataWrapper.unReadThumbsupAmount);
			  $("#unReadThumbsupAmount").show();
		  }
	      if(dataWrapper.totalAmount==0){
		      $(".newsNum").hide();
	      }
	      else
	      {
	    	  $(".newsNum").text(dataWrapper.totalAmount);
	    	  $(".newsNum").show();
	      }
	  })
}
setInterval(refreshData,1500);