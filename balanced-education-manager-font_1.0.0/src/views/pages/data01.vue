<template>
  <div class="charts">
    <div id="visitorpie" style="width: 300px;height:145px;border: 1px solid red;"></div>
  </div>
</template>

<script>
  import echarts from 'echarts/lib/echarts';
  // 引入柱状图
  import 'echarts/lib/chart/pie';
  import 'echarts/lib/component/title';
  import 'echarts/lib/component/legend';

  export default {
    mounted(){
      this.myChart = echarts.init(document.getElementById('visitorpie'));
      this.initData();
    },
    props: ['pieData'],
    methods: {
      initData(){
        const option = {
          title : {
            text: '直播节目',
            textStyle: {
              fontSize: 14,
              fontWeight: 'normal',
              color: '#272727',
            },
            x:'center',
            bottom: 0

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
              center: ['50%', '45%'],
              data:[
                {value:335, name:'已完成'},
                {value:310, name:'正在进行'},
                {value:234, name:'未开始'}
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

        this.myChart.setOption(option);
      }
    },
    watch: {
      pieData: function (){
        this.initData()
      }
    }
  }
</script>