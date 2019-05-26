<template>
  <div class="charts" style="width: 100%;">
    <div id="visitorpie" class="l" style="width: 30%;height:145px;"></div>
    <div id="two" class="l" style="width: 18%;height:145px;"></div>
    <div id="three" class="l" style="width:18%;height:145px;"></div>
    <div id="userNum" class="r" style="width:30%;height:145px;border-left:1px #66BB6A dashed"></div>
  </div>
</template>

<script>
  import echarts from 'echarts/lib/echarts';
  // 引入柱状图
  import 'echarts/lib/chart/pie';
  import 'echarts/lib/component/title';
  import 'echarts/lib/component/legend';
  import 'echarts/lib/component/tooltip';

  export default {
    mounted(){
      this.myChart_1 = echarts.init(document.getElementById('visitorpie'));
      this.myChart_2 = echarts.init(document.getElementById('two'));
      this.myChart_3 = echarts.init(document.getElementById('three'));
      this.myChart_4 = echarts.init(document.getElementById('userNum'));
      var data = {}
      this.postHttp(this,data,"statistics/queryPrograms",initData);
    },
    props: ['pieData'],
    methods: {
      
    },
    watch: {
      pieData: function (){
        initData();
      }
    }
  }
  
  function initData(obj,data){
  		var sa = data.result.sa;
  		var studentsnum = data.result.studentNum;
  		var teachersnum = data.result.userNum - data.result.studentNum;
        const option_1 = {
          title : {
            text: '直播节目',
            textStyle: {
              fontSize: 14,
              fontWeight: 'normal',
              color: '#272727',
            },
            x:'right',
            padding:[0,60,0,0],
            bottom: 5
          },
          tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
          },
          color:['#8ED16F','#59CFFF','#FE7359 '],
          legend: {
            padding: [40,0,0,40],
            orient: 'vertical',
            left: 'left',
            data: [
              {name: '已完成', icon: 'circle', color: '#272727'},
              {name: '正在进行', icon: 'circle', color: '#272727'},
              {name: '未开始', icon: 'circle', color: '#272727'}
            ]
          },
          series : [
            {
              name: '访问来源',
              type: 'pie',
              radius : '70%',
              textStyle: {
                fontSize: 12,
                color: '#272727',
              },
              center: ['70%', '45%'],
              data:[
                {value:sa.endedLiveNum, name:'已完成'},
                {value:sa.ongoingLiveNum, name:'正在进行'},
                {value:sa.notStartedLiveNum, name:'未开始'}
              ],
              itemStyle: {
                emphasis: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                },
                normal:{
                  label:{
                    show: false,
                  },
                  labelLine :{show:false}
                }
              }
            }
          ]
        };
        
        const option_2 = {
          title : {
            text: '录制节目',
            textStyle: {
              fontSize: 14,
              fontWeight: 'normal',
              color: '#272727',
            },
            x:'right',
            padding:[0,60,0,0],
            bottom: 5
          },
          tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
          },
          color:['#8ED16F','#59CFFF','#FE7359 '],
          legend: {
            padding: [40,0,0,0],
            orient: 'vertical',
            left: 'left',
          },
          series : [
            {
              name: '访问来源',
              type: 'pie',
              radius : '70%',
              textStyle: {
                fontSize: 12,
                color: '#272727',
              },
              center: ['50%', '45%'],
              data:[
                {value:sa.endedRecordNum, name:'已完成'},
                {value:sa.ongoingRecordNum, name:'正在进行'},
                {value:sa.notStartedRecordNum, name:'未开始'}
              ],
              itemStyle: {
                emphasis: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                },
                normal:{
                  label:{
                    show: false,
                  },
                  labelLine :{show:false}
                }
              }
            }
          ]
        };
        
        const option_3 = {
          title : {
            text: '点播节目',
            textStyle: {
              fontSize: 14,
              fontWeight: 'normal',
              color: '#272727',
            },
            x:'right',
            padding:[0,60,0,0],
            bottom: 5
          },
          tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
          },
          color:['#8ED16F','#59CFFF','#FE7359 '],
          legend: {
            padding: [40,0,0,0],
            orient: 'vertical',
            left: 'left',
          },
          series : [
            {
              name: '访问来源',
              type: 'pie',
              radius : '70%',
              textStyle: {
                fontSize: 12,
                color: '#272727',
              },
              center: ['50%', '45%'],
              data:[
                {value:sa.endedVodNum, name:'已完成'},
                {value:sa.ongoingVodNum, name:'正在进行'},
                {value:sa.notStartedVodNum, name:'未开始'}
              ],
              itemStyle: {
                emphasis: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                },
                normal:{
                  label:{
                    show: false,
                  },
                  labelLine :{show:false}
                }
              }
            }
          ]
        };
        
        const option_4 = {
          title : {
            text: '用户人数',
            textStyle: {
              fontSize: 14,
              fontWeight: 'normal',
              color: '#272727',
            },
            x:'right',
            padding:[0,60,0,0],
            bottom: 5
          },
          tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
          },
          color:['#8ED16F','#59CFFF'],
          legend: {
            padding: [40,0,0,40],
            orient: 'vertical',
            left: 'left',
            data: [
              {name: '学生', icon: 'circle', color: '#272727'},
              {name: '老师', icon: 'circle', color: '#272727'},
            ]
          },
          series : [
            {
              name: '人数',
              type: 'pie',
              radius : '70%',
              textStyle: {
                fontSize: 12,
                color: '#272727',
              },
              center: ['70%', '45%'],
              data:[
                {value:studentsnum, name:'学生'},
                {value:teachersnum, name:'老师'}
              ],
              itemStyle: {
                emphasis: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                },
                normal:{
                  label:{
                    show: false,
                  },
                  labelLine :{show:false}
                }
              }
            }
          ]
        };
        

        obj.myChart_1.setOption(option_1);
        obj.myChart_2.setOption(option_2);
        obj.myChart_3.setOption(option_3);
        obj.myChart_4.setOption(option_4);
      }
</script>