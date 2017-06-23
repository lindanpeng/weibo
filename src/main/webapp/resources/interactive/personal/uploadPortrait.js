
	 var cropper;
	   function init()
	   {   
	       //绑定
	       cropper = new ImageCropper(300, 300, 180, 180);
	       cropper.setCanvas("cropper");
	       cropper.addPreview("preview180");
	       cropper.addPreview("preview100");
	       cropper.addPreview("preview50");
	       //检测用户浏览器是否支持imagecropper插件
	       if(!cropper.isAvaiable())
	       {
	           alert("Sorry, your browser doesn't support FileReader, please use Firefox3.6+ or Chrome10+ to run it.");
	       }
	   }
	   //打开本地图片
	   function selectImage(fileList)
	   {
	       cropper.loadImage(fileList[0]);
	   }

	   //旋转图片
	   function rotateImage(e)
	   {
	       switch(e.target.id)
	       {
	           case "rotateLeftBtn":
	               cropper.rotate(-90);
	               break;
	           case "rotateRightBtn":
	               cropper.rotate(90);
	               break;
	       }
	   }

	   //上传图片
	   function saveImage()
	   {
	       //选个你需要的大小
	       var imgData = cropper.getCroppedImageData(100,100);
	     //  alert("上传了："+imgData);
	      
	       $.post("/weibo/personal/uploadPortrait",{"imgStr":imgData},function(data){
	    	   if(data.code==500){
	    		   alert("上传成功!");
	    		  window.opener.refreshWin();
	    	   }
	       });
	       //在这里写你的上传代码
	   }