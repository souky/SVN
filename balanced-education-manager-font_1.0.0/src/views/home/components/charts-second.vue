<template>
  <div class="charts">
    <div id="two" style="width: 180px;height:145px;"></div>
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
      this.myChart = echarts.init(document.getElementById('two'));
      this.initData();
    },
    props: ['pieData'],
    methods: {
      initData(){
        const option = {
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