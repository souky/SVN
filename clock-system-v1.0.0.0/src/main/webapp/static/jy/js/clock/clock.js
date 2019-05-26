var extend = function (d, c) {
    for (var p in c) {
        d[p] = c[p]
    }
    return d;
}

function jy_creatList() {
    this.configs = {
        building: 1,
        floor: 1,
        classNo: 1,
        dataList: [],
        basePath: '',
        buildingNameList: [],
        powerOff: 1
    }
}

jy_creatList.prototype = {
    creats: function (configs) {
        var jy_configs = extend(this.configs, configs);
        var jy_floorsDemo = ['一', '二', '三', '四', '五', '六', '七', '八', '九', '十', '十一', '十二', '十三', '十四', '十五', '十六', '十七', '十八', '十九', '二十', '二十一', '二十二', '二十三', '二十四', '二十五', '二十六', '二十七', '二十八', '二十九', '三十'];
        for (var i = 0; i < jy_configs.building; i++) {
            var builddiv = document.createElement("div");
            builddiv.id = "buildDom" + i;
            builddiv.className = "domClass";

            var buildDOm = document.createElement("div");
            buildDOm.className = "buildDOm";

            for (var j = 0; j < jy_configs.floor; j++) {
                var floordiv = document.createElement("div");
                floordiv.id = "floorDom" + j;
                floordiv.className = "floorClass";
                buildDOm.appendChild(floordiv);
                for (var z = 0; z < jy_configs.classNo; z++) {
                    var classdiv = document.createElement("div");
                    var divIdnum = i * jy_configs.floor * jy_configs.classNo + j * jy_configs.classNo + z;
                    classdiv.id = "classDom" + divIdnum;
                    classdiv.className = "items fl bkint";
                    var imgitem = document.createElement("div");
                    imgitem.className = "img_item";
                    var imgs_ = "<img src='" + jy_configs.basePath + "/static/jy/img/pcImg/houseJian.png'>"
                    imgitem.innerHTML = imgs_;

                    var imgtext = document.createElement("div");
                    imgtext.className = "img_text";
                    var imgdiv1 = document.createElement("div");
                    imgtext.appendChild(imgdiv1);
                    
                    var imgtime = document.createElement("div");
                    imgtime.className = "img_time";
                    var imgtime1 = document.createElement("div");
                    imgtime.appendChild(imgtime1);
                    
                    var exam_num = document.createElement("div");
                    exam_num.className = "exam_num ac";
                    exam_num.innerHTML = '123123';

                  

                    var imgdelect = document.createElement("div");
                    imgdelect.className = "delectOne";
                    var imgsetting = document.createElement("div");
                    imgsetting.className = "editOne";

                    var imgs_delect1 = "<img style='width:100%' src='" + jy_configs.basePath + "/static/jy/img/pcImg/delect2x.png' >";
                    imgdelect.innerHTML = imgs_delect1;

                    var imgs_edit1 = "<img style='width:100%' src='" + jy_configs.basePath + "/static/jy/img/pcImg/setting2x.png' onclick='goEdit(this," + divIdnum + ")'>";
                    imgsetting.innerHTML = imgs_edit1;

                    for (var k = 0; k < jy_configs.dataList.length; k++) {
                        if (jy_configs.dataList[k].listOrder != null && jy_configs.dataList[k].listOrder == divIdnum) {
                            if (jy_configs.dataList[k].address == '') {
                                imgdiv1.innerHTML = '无';
                            }
                            else {
                                imgdiv1.innerHTML = jy_configs.dataList[k].address;
                            }
                            if (jy_configs.dataList[k].status == 1) {
                                classdiv.className = "items fl";
                            }
                            if (jy_configs.dataList[k].status == 2) {
                                classdiv.className = "items fl";
                            }
                           var imgs_delect = "<img style='width:100%' src='" + jy_configs.basePath + "/static/jy/img/pcImg/delect2x.png' name='" + jy_configs.dataList[k].id + "' onclick='delectshield(this," + divIdnum + ")'>";
                            imgdelect.innerHTML = imgs_delect;

                            var imgs_edit = "<img style='width:100%' src='" + jy_configs.basePath + "/static/jy/img/pcImg/setting2x.png' name='" + jy_configs.dataList[k].id + "' onclick='goEdit(this," + divIdnum + ")'>";
                            imgsetting.innerHTML = imgs_edit;
                            
                            imgtime1.innerHTML = jy_configs.dataList[k].time;
                            
                        } else {

                        }
                    }
                    classdiv.appendChild(imgitem);
                    classdiv.appendChild(imgtext);
                    classdiv.appendChild(imgtime);
                    classdiv.appendChild(imgdelect);
                    classdiv.appendChild(imgsetting);
                    floordiv.appendChild(classdiv);
                }
                var floorON = document.createElement("div");
                floorON.className = "floorON";
                floorON.innerHTML = jy_floorsDemo[j] + '楼';
                floordiv.appendChild(floorON);
            }
            var topclass = document.createElement("div");
            topclass.className = "topClass";
            var topclassspan = document.createElement("div");
            topclassspan.innerHTML = jy_configs.buildingNameList[i];//jy_configs.buildingNameList[i]
            if(jy_configs.buildingNameList[i]==undefined){
            	 topclassspan.innerHTML = '教学楼'+(i+1);
            }
            topclassspan.setAttribute('onclick', 'editBuliding(this)');
            topclass.appendChild(topclassspan);

            var topclassslow = document.createElement("div");
            topclassslow.className = "topclassslow";
            var lowimg = "<img style='width:100%' class='transitionviews-down' src='" + jy_configs.basePath + "/static/jy/img/pcImg/Star@2x.png' onclick='shieldshow(this)'>";
            topclassslow.innerHTML = lowimg;
            topclass.appendChild(topclassslow);

            builddiv.appendChild(topclass);
            builddiv.appendChild(buildDOm);
            document.getElementById('allDom').appendChild(builddiv);
        }
    }
}