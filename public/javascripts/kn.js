$(function(){
	var cellStr=''+
	'<div class="cell">'+
        '<div class="cell-img"><img src="/assets/images/2251708.jpg" class="img-rounded"></div>'+
		//'<img class="tail" src="tail.jpg"/>'+
        '<div class="cell-content">'+
            '<div class="album-title">王大锤的幸福生活</div>'+
            '<div class="album-description">无节操新闻联播的魅力在于换任何台都可以看见完整的视频</div>'+
            '<div class="album-index">'+
                '<div class="dz"><img src="/assets/images/kuaina_conmon_icon_card_zan.png"/>35</div>'+
                '<div class="dy"><img src="/assets/images/kuaina_conmon_icon_ren.png"/>23</div>'+
            '</div>'+
        '</div>'+
    '</div>';
	
	for(var index=0;index<20;index++){
		$(".container-body").append(cellStr);
	}
});