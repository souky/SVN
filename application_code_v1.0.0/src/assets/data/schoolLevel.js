/**
 * @description 主页数据
 */

export default {
    cesih:[98.8, 83.24, 91.1, 37.78, 42.6],
    countP:'500',//全校人数
    totalCount:'460',//总分平均分
    hightCount:'31',//高出全地区平均分
    ranking:'6',//地区排名
    goodsuject:['语文','数学'],//表现较好的学科名
    hightavarge:['17','22'],//高于地区平均分
    allNumber:['5','6'],//列全县多少名
    lowSuject:['化学','生物'],//较弱学科名
    lowavarge:['3'],//低于地区平均分
    allRanking:['18'],//列于全区多少名
    tableData1:[
    	{
	  	subject:'总分',
	  	countPeople:'500',
	  	fullMark:'750',
	  	average:'430',
        averages:'380',
        deviation:'50',
        ranking:'6',
        highest:'630',
        highRate:'280',
        excellent:'10%',
        inCommission:'20%',
        yield:'35%',
        failure:'10%'
  		},
  		{
	  		subject:'语文',
        countPeople:'500',
        fullMark:'750',
        average:'430',
        averages:'380',
        deviation:'50',
        ranking:'6',
        highest:'630',
        highRate:'280',
        excellent:'10%',
        inCommission:'20%',
        yield:'35%',
        failure:'10%'
  		},
  		{
	  		subject:'数学',
        countPeople:'500',
        fullMark:'750',
        average:'430',
        averages:'380',
        deviation:'50',
        ranking:'6',
        highest:'630',
        highRate:'280',
        excellent:'10%',
        inCommission:'20%',
        yield:'35%',
        failure:'10%'
  		},
  		{
	  		subject:'英语',
        countPeople:'500',
        fullMark:'750',
        average:'430',
        averages:'380',
        deviation:'50',
        ranking:'6',
        highest:'630',
        highRate:'280',
        excellent:'10%',
        inCommission:'20%',
        yield:'35%',
        failure:'10%'
  		},
  		{
	  		subject:'物理',
        countPeople:'500',
        fullMark:'750',
        average:'430',
        averages:'380',
        deviation:'50',
        ranking:'6',
        highest:'630',
        highRate:'280',
        excellent:'10%',
        inCommission:'20%',
        yield:'35%',
        failure:'10%'
  		}
    ],
    option1:{
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                crossStyle: {
                    color: '#999'
                }
            }
        },
        legend: {
           show:true,
           data:['全校']
        },
        xAxis: [
            {
                type: 'category',
                data: ['高分率','优秀率','良好率','合格率','不及格率'],
                axisPointer: {
                    type: 'shadow'
                }
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: '百分比%',
                max:100
            }
        ],
        series: [
            {
                name:'全校',
                type:'bar',
                itemStyle:{
                    normal:{
                        color:'#70CDF3'
                    }
                },
                label:{
                    normal:{
                        show:true,
                        position:'top',
                        color:'#70CDF3'
                    }
                },
                barWidth : 60,
                data:[97.8, 77.24, 87.1, 27.78, 37.6]
            }
        ]
    },
    tableData2:[
        {
        ranking:'6',
        class:'19',
        teachername:'李明',
        totalPoint:'550',
        highest:'630',
        lowest:'280',
        standard:'28.3',
        differentiation:'20%',
        highRate:'12%',
        excellent:'10%',
        inCommission:'20%',
        yield:'35%',
        failure:'10%'
        },
        {
        ranking:'5',
        class:'13',
        teachername:'李明',
        totalPoint:'550',
        highest:'630',
        lowest:'280',
        standard:'28.3',
        differentiation:'20%',
        highRate:'12%',
        excellent:'10%',
        inCommission:'20%',
        yield:'35%',
        failure:'10%'
        },
        {
         ranking:'4',
        class:'17',
        teachername:'李明',
        totalPoint:'550',
        highest:'630',
        lowest:'280',
        standard:'28.3',
        differentiation:'20%',
        highRate:'12%',
        excellent:'10%',
        inCommission:'20%',
        yield:'35%',
        failure:'10%'
        },
        {
           ranking:'8',
        class:'17',
        teachername:'李明',
        totalPoint:'550',
        highest:'630',
        lowest:'280',
        standard:'28.3',
        differentiation:'20%',
        highRate:'12%',
        excellent:'10%',
        inCommission:'20%',
        yield:'35%',
        failure:'10%'
        },
        {
            ranking:'15',
        class:'23',
        teachername:'李明',
        totalPoint:'550',
        highest:'630',
        lowest:'280',
        standard:'28.3',
        differentiation:'20%',
        highRate:'12%',
        excellent:'10%',
        inCommission:'20%',
        yield:'35%',
        failure:'10%'
        }
    ],
    option2:{
        tooltip : {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow',
                label: {
                    show: true
                }
            }
        },
        legend: {
            data:['班级平均分']
        },
        calculable : true,
        xAxis: [
            {
                type : 'category',
                data : ['区县No.1班级','19','18','17','16','15','14','13','12','11','10','9','8','7','6','5','4','3','2','1']
            }
        ],
        yAxis: [
            {
                type : 'value',
                name : '平均分'
            }
        ],
        dataZoom: [
            {
                show: true,
                start: 1,
                end: 50
            }
        ],
        series : [
            {
                name: '班级平均分',
                type: 'bar',
                itemStyle:{
                    normal:{color:'#70CDF3'}
                },
                markLine : {
                    data : [
                        { name: '全校平均分',lineStyle:{normal:{color:'#FFD244'}},yAxis: 0}
                    ]
                },
                data: [{value:684.5,itemStyle:{normal:{color:"#FF8585"}}},552.29,490.4,482.61,476.29,463.98,463.51,451.37,443.76,438.21,426.69,419.76,407.91,396.37,386.34,371.64,369.16,362.43,357.68,349.37]
            }
        ]
    },
    classNumble:'5',
    classS:['19','17','18','15','16'],
    classH:'19',
    missdistance:'96',
    classL:['7','8','9','12','14'],
    classLs:'7',
    option3:{
        tooltip : {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow',
                label: {
                    show: true
                }
            }
        },
         legend: {
            show:true,
            data:['前10名','前20名','前50名','前100名','前200名','前500名','前1000名']
        },
        calculable : true,
        dataZoom: [
            {
                    show: true,
                    start: 1,
                    end: 60
                }
        ],
        xAxis: [
            {
                type : 'category',
                data : ['19','18','17','16','15','14','13']
            }
        ],
        yAxis: [
            {
                type : 'value',
                name : '人',
                max:50
            }
        ],
        series : [
            {
                name: '前10名',
                type: 'bar',
                itemStyle:{
                    normal:{
                        color:'#FF8585'
                    }
                },
                data: [7,7,7,7,7,7,7]
            },
            {
                name: '前20名',
                type: 'bar',
                itemStyle:{
                    normal:{
                        color:'#F0D785'
                    }
                },
                data: [17,17,17,17,17,17,17]
            },
            {
                name: '前50名',
                type: 'bar',
                itemStyle:{
                    normal:{
                        color:'#85E2FF'
                    }
                },
                data: [27,27,27,27,27,27,27]
            },
            {
                name: '前100名',
                type: 'bar',
                itemStyle:{
                    normal:{
                        color:'#F0D785'
                    }
                },
                data: [47,47,47,47,47,47,47]
            },
            {
                name: '前200名',
                type: 'bar',
                itemStyle:{
                    normal:{
                        color:'#6DE1B6'
                    }
                },
                data: [33,33,33,33,33,33,33]
            },
            {
                name: '前500名',
                type: 'bar',
                itemStyle:{
                    normal:{
                        color:'#85AFFF'
                    }
                },
                data: [24,24,24,24,24,24,24]
            },
            {
                name: '前1000名',
                type: 'bar',
                itemStyle:{
                    normal:{
                        color:'#EABA73'
                    }
                },
                data: [24,24,24,24,24,24,24]
            }

        ]
    },
    tableData3:[
        {
            rank:'1',
            class:'5',
            teacher:'李明',
            subjectAerage:'115',
            countAerage:'440',
            subjectStandard:'68',
            countStandard:'75',
            contribution:'90.8'
        },{
            rank:'2',
            class:'6',
            teacher:'李明',
            subjectAerage:'115',
            countAerage:'440',
            subjectStandard:'68',
            countStandard:'75',
            contribution:'90.8'
        },{
            rank:'3',
            class:'7',
            teacher:'李明',
            subjectAerage:'115',
            countAerage:'440',
            subjectStandard:'68',
            countStandard:'75',
            contribution:'90.8'
        }
    ],
    option4:{
        tooltip : {
            trigger: 'axis',
            formatter: "{b} <br/>{a}: {c}%",
            axisPointer: {
                type: 'shadow',
                label: {
                    show: true
                }
            }
        },

        calculable : true,
        xAxis: [
            {
                type : 'category',
                axisLine: {onZero: false},
                data : ['19','18','17','16','15','14','13']
            }
        ],
        yAxis: [
            {
                type : 'value',
                nameGap:50,

                min: 80,
                max: 120
            }

        ],
        series : [
            {
                name: '科目贡献率',
                type: 'line',
                itemStyle:{
                    normal:{color:'#70CDF3'}
                },
                markLine:{
                    data : [
                        { name: '全校平均分',lineStyle:{normal:{color:'red'}},yAxis: 100}
                    ]
                },
                markArea: {
                    silent: true,
                    data:[[{
                        name: '红线以下(贡献未到位)',
                        label:{
                            normal:{
                                color:'red',
                                position:'insideTopRight'
                            }
                        },
                        yAxis:'100',
                        itemStyle:{
                            normal:{color:'rgba(112,243,229,0.30)'},
                        }
                    },{
                        yAxis:'80',
                    }],[
                        {
                        name: '红线以上(贡献到位)',
                        label:{
                            normal:{
                                color:'red',
                                position:'insideBottomRight'
                            }
                        },
                        yAxis:'100',
                        itemStyle:{
                            normal:{color:'rgba(157,243,112,0.30)'},
                        }
                    },{
                        yAxis:'120',
                    }
                    ]]
                },
                data: [103.5, 102.16, 99.73, 95.37, 101.03, 102.28, 97.26]
            }

        ]
    },
    option5:{
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        legend: {
            data: ['100%≥高分率≥90%', '90%>优秀率≥80%','80%>良好率≥70%','70%>及格率≥60%','不及格率<60%']
        },
        dataZoom: [
            {
                    show: true,
                    start: 1,
                    end: 50
                }
            ],
            xAxis:  {
                boundaryGap:['10%','10%'],
                type: 'category',
                data: ['全区县','全区县NO1班级','全校','19','18','17','16','15','14','13','12','11','10','9','8','7']
            },
            yAxis: {
               type: 'value',
               name:'百分比%'
            },
            series: [
            {
            name: '100%≥高分率≥90%',
            type: 'bar',
            stack: '占比',
            label: {
                normal: {
                    show: true,
                    position: 'inside'
                }
            },
            barWidth:'60',
            itemStyle:{
                normal:{color:'#8F72E5'}
            },
            data: [0.21, 0.44, 0.39, 0.23, 0.29, 0.24, 0.32,0.27,0.27,0.27,0.27,0.27,0.27,0.27,0.27,0.27]
        },
        {
            name: '90%>优秀率≥80%',
            type: 'bar',
            stack: '占比',
            label: {
                normal: {
                    show: true,
                    position: 'inside'
                }
            },
            barWidth:'60',
            itemStyle:{
                normal:{color:'#FFD244'}
            },
            data: [0.32, 0.15, 0.21, 0.26, 0.21, 0.32, 0.33,0.33,0.33,0.33,0.33,0.33,0.33,0.33,0.33,0.33]
        },
        {
            name: '80%>良好率≥70%',
            type: 'bar',
            stack: '占比',
            label: {
                normal: {
                    show: true,
                    position: 'inside'
                }
            },
            barWidth:'60',
            itemStyle:{
                normal:{color:'#BFEA7F'}
            },
            data: [0.32, 0.15, 0.21, 0.26, 0.21, 0.32, 0.33,0.33,0.33,0.33,0.33,0.33,0.33,0.33,0.33,0.33]
        },
        {
            name: '70%>及格率≥60%',
            type: 'bar',
            stack: '占比',
            label: {
                normal: {
                    show: true,
                    position: 'inside'
                }
            },
            barWidth:'60',
            itemStyle:{
                normal:{color:'#FF8585'}
            },
            data: [0.31, 0.21, 0.3, 0.21, 0.28, 0.18, 0.16,0.3,0.3,0.3,0.3,0.3,0.3,0.3,0.3,0.3]
        },
        {
            name: '不及格率<60%',
            type: 'bar',
            stack: '占比',
            label: {
                normal: {
                    show: true,
                    position: 'inside'
                }
            },
            barWidth:'60',
            itemStyle:{
                normal:{color:'#70CDF3'}
            },
            data: [0.16, 0.2, 0.1, 0.3, 0.22, 0.26, 0.19,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1]
        }
            ]
    }
        
}