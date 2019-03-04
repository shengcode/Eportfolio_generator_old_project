
var index=0; 
var imageArray;	 
var captionArray;
var title;

var xmlhttp = new XMLHttpRequest();
var url = "title1.json";

xmlhttp.onreadystatechange = function() {
    if (xmlhttp.readyState == 4) {
        var data = JSON.parse(xmlhttp.responseText);
        title = data.title;
		document.getElementById('myTitle').innerHTML = title;
		myImageShow.setAttribute("src","img/"+data.slides[index].image_file_name);      
		document.getElementById("myCaption").innerHTML=data.slides[index].caption;
		imageArray = [];
		captionArray=[];
		for (var i = 0; i < data.slides.length; ++i) {
			imageArray.push(data.slides[i].image_file_name);
		}
		for(var i=0; i<data.slides.length;i++){
			captionArray.push(data.slides[i].caption);
		}
    }
}
xmlhttp.open("GET", url, true);
xmlhttp.send();
/*
$(document).ready(function(){    
	$.getJSON("title1.json", function(data){	     
		title = data.title;
		document.getElementById('myTitle').innerHTML = title;
		myImageShow.setAttribute("src","img/"+data.slides[index].image_file_name);      
		document.getElementById("myCaption").innerHTML=data.slides[index].caption;
		imageArray = [];
		captionArray=[];
		for (var i = 0; i < data.slides.length; ++i) {
			imageArray.push(data.slides[i].image_file_name);
		}
		for(var i=0; i<data.slides.length;i++){
			captionArray.push(data.slides[i].caption);
		}
	});     
});
*/

var timer;	
	
function nextImage(){
	document.getElementById("Previous").disabled = false;	   
	
	if(window.index>=imageArray.length-2){
		window.index++;	 
		myImageShow.setAttribute("src","img/"+imageArray[window.index]);
		document.getElementById("myCaption").innerHTML=captionArray[window.index];
		document.getElementById("Next").disabled = true;
	} else {
		window.index++;	 
		myImageShow.setAttribute("src","img/"+imageArray[window.index]);
		document.getElementById("myCaption").innerHTML=captionArray[window.index];
	}
}	 
function previousImage(){	
	document.getElementById("Next").disabled = false;	
	
	if(window.index<=1){
		window.index--;	 
		myImageShow.setAttribute("src","img/"+imageArray[window.index]);
		document.getElementById("myCaption").innerHTML=captionArray[window.index];
		document.getElementById("Previous").disabled = true;
	} else {
		window.index--;	 
		myImageShow.setAttribute("src","img/"+imageArray[window.index]);
		document.getElementById("myCaption").innerHTML=captionArray[window.index];
	}
}
	
function playImg() {
	
	window.index = (window.index + 1) % imageArray.length;
	myImageShow.setAttribute("src","img/"+imageArray[window.index]);
	document.getElementById("myCaption").innerHTML=captionArray[window.index];
}


function PlayOrPause(){
	if(timer){
		clearInterval(timer);
		timer=null;
	}
	else{
		timer = window.setInterval(playImg,3000);
	}		 
}



