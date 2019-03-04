function nextImage(slide_div, slide){
	if(slide.index>=slide.slides.length-1){
		slide.index=0;	 
		slide_div.children[0].setAttribute("src","img/"+slide.slides[slide.index].imageFileName);
		slide_div.children[2].innerHTML= slide.slides[slide.index].caption;
	} else {
		slide.index++;	 
		slide_div.children[0].setAttribute("src","img/"+slide.slides[slide.index].imageFileName);
		slide_div.children[2].innerHTML= slide.slides[slide.index].caption;
	}
}	 
function previousImage(slide_div, slide){	
	if(slide.index < 0){
		slide.index=slide.slides.length;	 
		slide_div.children[0].setAttribute("src","img/"+slide.slides[slide.index].imageFileName);
		slide_div.children[2].innerHTML= slide.slides[slide.index].caption;
	} else {
		slide.index--;	 
		slide_div.children[0].setAttribute("src","img/"+slide.slides[slide.index].imageFileName);
		slide_div.children[2].innerHTML= slide.slides[slide.index].caption;
	}
}

function playImg(slide_div, slide) {        
	slide.index = (slide.index + 1) % slide.slides.length;
	slide_div.children[0].setAttribute("src","img/"+slide.slides[slide.index].imageFileName);
	slide_div.children[2].innerHTML= slide.slides[slide.index].caption;
}

function hyperlinkize(text, link) {
	return '<a href="' + link + '">' + text + '</a>';
}

function comparebybegin(a, b) {
	return a.begin - b.begin;
}

function insertImg(elem, data) {
    var elemImage = document.createElement("img");      
    var imageName = data.imageFileName;                              
    elemImage.src = "img/"+imageName;		
    elemImage.setAttribute("height",data.imageHeight);
    elemImage.setAttribute("width",data.imageWidth);
    elem.appendChild(elemImage);  
}

function insertVideo(elem, data) {
    var obj,source;
    obj = document.createElement('video');			
    obj.height = data.videoHeight;
    obj.width = data.videoWidth;

    $(obj).attr('controls', "");
    source = document.createElement('source');
    $(source).attr('type', 'video/mp4');
    $(source).attr('src', 'video/'+data.videoFileName);

    $(obj).append(source);
    elem.appendChild(obj);
}

function insertSlideShow(elem, data) {
	var slide_show = document.createElement('div');
	slide_show.title = data.title;
	img = document.createElement('img');
	img.setAttribute('src', 'img/'+data.slides[0].imageFileName);
	img.height=300;
	img.width=400;
	slide_show.appendChild(img);
	data.index = 0;
	var btn_div = document.createElement('div');
	var btn_prev = document.createElement('button');
	btn_prev.innerHTML = "Previous";
	btn_prev.onclick = (function(){var x = data; var y = slide_show; function a() {previousImage(y, x);}; return a;})();
	var btn_play = document.createElement('button');
	btn_play.innerHTML = "Play/Pause";
	//btn_play.onclick = (function(){var x = page_data.slideShowComponents[i]; var y = slide_show; function a() {playImg(y, x);}; return a;})();
	btn_play.onclick = (function(){
		var x = data;
		var y = slide_show; 
		var timer;
		return function () {
			if(timer){
				clearInterval(timer);
				timer=null;
			}
			else{
				timer = window.setInterval(function(){playImg(y,x)},3000);
			}  
		};})();
	var btn_next = document.createElement('button');
	btn_next.innerHTML = "Next";
	btn_next.onclick = (function(){var x = data; var y = slide_show; function a() {nextImage(y, x);}; return a;})();
	btn_div.appendChild(btn_prev);
	btn_div.appendChild(btn_play);
	btn_div.appendChild(btn_next);
	slide_show.appendChild(btn_div);		

	var caption_div = document.createElement('div');
	caption_div.innerHTML= data.slides[0].caption;
	slide_show.appendChild(caption_div);
	elem.appendChild(slide_show);
}

function insertList(elem, data) {
    var list = document.createElement('ul');
    list.style = "font-size:" + data.textFontSize + "px;" + "font-family:" + data.textFontFamily + ";";
    for (var i = 0; i < data.array.length; ++i) {
        var item = document.createElement('li');
        item.innerHTML = data.array[i];
        list.appendChild(item);
    }
    elem.appendChild(list);
}

function insertParagraph(elem, data) {
    var newParagraph = document.createElement('p');
    newParagraph.innerHTML = data.textContent;
    newParagraph.style = "font-size:" + data.textFontSize + "px;" + "font-family:" + data.textFontFamily + ";";
    elem.appendChild(newParagraph);
}	

var INSERT_FUNCTIONS = {"image": insertImg, "video": insertVideo, "slides": insertSlideShow, "list": insertList, "Paragraph": insertParagraph};


$(document).ready(function(){    
	$.getJSON("why.json", function(data){    
        var filename = location.href.split("/").slice(-1);
        for (var i = 0; i < data.length; ++i) {
            var page = data[i];
            var ul = document.getElementById("navigationBar")
            var li = document.createElement("li");
            var pagelink = document.createElement("a");
            pagelink.href = page.title + ".html";
            pagelink.innerHTML = page.title;
            li.appendChild(pagelink);
            ul.appendChild(li);
            if (filename[0] == page.title + ".html") {
                    if (page.bannerImageFileName != null) {
                        var img = document.createElement("img");
                        img.src = "img/"+page.bannerImageFileName;
                        document.getElementById("headbanner").appendChild(img);
                    }
                    document.title = page.title;
                    var student_name = document.getElementById("student");
                    student_name.innerHTML = page.studentName;
                    for (var j = 0; j < page.components.length; ++j) {
                            var component = page.components[j];
                            INSERT_FUNCTIONS[component.type](document.getElementById("content"), component);
                    }
                    document.getElementById("footer").innerHTML = page.pageFooter;
            }
        }
	});
});
