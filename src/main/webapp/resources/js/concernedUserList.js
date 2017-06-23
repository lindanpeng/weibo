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