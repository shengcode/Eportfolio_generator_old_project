function nextImage(slide_div, slide){
	if(slide.index>=slide.slides.length-1){
		slide.index=0;	 
		slide_div.children[0].setAttribute("src","img/"+slide.slides[slide.index].image_file_name);
		slide_div.children[2].innerHTML= slide.slides[slide.index].caption;
	} else {
		slide.index++;	 
		slide_div.children[0].setAttribute("src","img/"+slide.slides[slide.index].image_file_name);
		slide_div.children[2].innerHTML= slide.slides[slide.index].caption;
	}
}	 
function previousImage(slide_div, slide){	
	if(slide.index < 0){
		slide.index=slide.slides.length;	 
		slide_div.children[0].setAttribute("src","img/"+slide.slides[slide.index].image_file_name);
		slide_div.children[2].innerHTML= slide.slides[slide.index].caption;
	} else {
		slide.index--;	 
		slide_div.children[0].setAttribute("src","img/"+slide.slides[slide.index].image_file_name);
		slide_div.children[2].innerHTML= slide.slides[slide.index].caption;
	}
}

function playImg(slide_div, slide) {        
	slide.index = (slide.index + 1) % slide.slides.length;
	slide_div.children[0].setAttribute("src","img/"+slide.slides[slide.index].image_file_name);
	slide_div.children[2].innerHTML= slide.slides[slide.index].caption;
}

function hyperlinkize(text, link) {
	return '<a href="' + link + '">' + text + '</a>';
}

function comparebybegin(a, b) {
	return a.begin - b.begin;
}

$(document).ready(function(){    
	$.getJSON("eportfolio.json", function(data){    

		idx = document.getElementById( "hidElem" ).value;
		var page_data = data.pages[idx]; 



		// css style_sheet_layout	
		// image components       
		for(var i = 0; i<page_data.imageComponents.length;i++)   {    
			var elemImage = document.createElement("img");      
			var imageName = page_data.imageComponents[i].image_file_name;                              
			elemImage.src = "img/"+imageName;		
			//elemImage.setAttribute("alt", "myImage");
			elemImage.setAttribute("height","300");
			elemImage.setAttribute("width","500");
			document.getElementById("imageComponent").appendChild(elemImage);  

		}
		// video components      
		video_div = document.getElementById("videoComponent");
		for(var i = 0; i<page_data.videoComponents.length;i++){            
			var obj,source;
			obj = document.createElement('video');			
			obj.height = 300;
			obj.width = 700;


			$(obj).attr('controls', "");
			source = document.createElement('source');
			$(source).attr('type', 'video/mp4');
			$(source).attr('src', 'video/'+page_data.videoComponents[i].video_file_name);

			$(obj).append(source);
			video_div.appendChild(obj);
		}

		// slideShow components 
		var index;

		var slideshow_div = document.getElementById("slideShowComponent");
		for(var i = 0; i<page_data.slideShowComponents.length; i++){
			var slide_show = document.createElement('div');
			slide_show.title = page_data.slideShowComponents[i].title;
			img = document.createElement('img');
			img.setAttribute('src', 'img/'+page_data.slideShowComponents[i].slides[0].image_file_name);
			img.height=300;
			img.width=400;
			slide_show.appendChild(img);
			page_data.slideShowComponents[i].index = 0;
			var btn_div = document.createElement('div');
			var btn_prev = document.createElement('button');
			btn_prev.innerHTML = "Previous";
			btn_prev.onclick = (function(){var x = page_data.slideShowComponents[i]; var y = slide_show; function a() {previousImage(y, x);}; return a;})();
			var btn_play = document.createElement('button');
			btn_play.innerHTML = "Play/Pause";
			//btn_play.onclick = (function(){var x = page_data.slideShowComponents[i]; var y = slide_show; function a() {playImg(y, x);}; return a;})();
			btn_play.onclick = (function(){
				var x = page_data.slideShowComponents[i];
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
			btn_next.onclick = (function(){var x = page_data.slideShowComponents[i]; var y = slide_show; function a() {nextImage(y, x);}; return a;})();
			btn_div.appendChild(btn_prev);
			btn_div.appendChild(btn_play);
			btn_div.appendChild(btn_next);
			slide_show.appendChild(btn_div);		

			var caption_div = document.createElement('div');
			caption_div.innerHTML= page_data.slideShowComponents[i].slides[0].caption;
			slide_show.appendChild(caption_div);
			slideshow_div.appendChild(slide_show);
		}
		// text components
		for(var i = 0; i<page_data.textComponents.length; i++){	  
			var paragraph = page_data.textComponents[i];

			var newParagraph = document.createElement('p');
			//newParagraph.innerHTML =;
			//newParagraph.textContent = 'aksdfjksdjf';
			//newParagraph.innerHTML = newParagraph.innerHTML + ' is a good one' + '<a href="http://www.npr.org/sections/krulwich/2014/05/25/315259156/a-young-woman-falls-in-love-with-everything">this</a>' ;
			var links = [];
			
			for(var j  = 0; j< paragraph.hyperlinkers.length;j++){ 
				var text_to_linker =page_data.textComponents[i].text_content.substring(page_data.textComponents[i].hyperlinkers[j].begin, page_data.textComponents[i].hyperlinkers[j].end);	      
				links.push({begin:page_data.textComponents[i].hyperlinkers[j].begin, 
					end:page_data.textComponents[i].hyperlinkers[j].end, 
					link:hyperlinkize(text_to_linker, page_data.textComponents[i].hyperlinkers[j].textLink)});
			}
			links = links.sort(comparebybegin);

			var j = 0;
			var k = 0;
			while (k < paragraph.text_content.length) {
				if (j < links.length) {
					if (k < links[j].begin) {
						newParagraph.innerHTML = newParagraph.innerHTML + paragraph.text_content[k];
						k++;
					} else {
						newParagraph.innerHTML = newParagraph.innerHTML + links[j].link;
						k = k + links[j].end - links[j].begin;
						j++;
					}
				} else {
					newParagraph.innerHTML = newParagraph.innerHTML + paragraph.text_content[k];
					k++;
				}
			}	
			document.getElementById("textComponent").appendChild(newParagraph);
		}
	});
});
