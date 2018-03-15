/**
 * Created by 16255 on 2018/3/5.
 */

//the io flow of the fire wall
var myCharts = echarts.init(document.getElementById('io-flow'));
option = {
    title: {
        text: '本月出入防火墙流量图'
    },
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        data:['出','入']
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    toolbox: {
        feature: {
            saveAsImage: {}
        }
    },
    xAxis: {
        type: 'category',
        boundaryGap: false,
        data: ['1号','6号','12号','18号','24号','30号']
    },
    yAxis: {
        type: 'value'
    },
    series: [
        {
            name:'出',
            type:'line',
            stack: '总量',
            data:[120, 132, 101, 134, 90, 230]
        },
        {
            name:'入',
            type:'line',
            stack: '总量',
            data:[220, 182, 191, 234, 290, 330,]
        },

    ]
};
myCharts.setOption(option);

//the number of today's attack event
var myChart = echarts.init(document.getElementById('day-attack-charts'));

option = {
    title:{
        text:'当日攻击事件数量及比例'
    },
    tooltip: {
        trigger: 'item',
        formatter: "{a} <br/>{b}: {c} ({d}%)"
    },
    legend: {
        orient: 'vertical',
        x: 'right',
        y: 'bottom',
        data:['病毒攻击','异常流量','扫描攻击','DDoS攻击','其他']
    },
    series: [
        {
            name:'攻击类型',
            type:'pie',
            radius: ['50%', '70%'],
            avoidLabelOverlap: false,
            label: {
                normal: {
                    show: false,
                    position: 'center'
                },
                emphasis: {
                    show: true,
                    textStyle: {
                        fontSize: '25',
                        fontWeight: 'bold'
                    }
                }
            },
            labelLine: {
                normal: {
                    show: false
                }
            },
            data:[
                {value:23, name:'病毒攻击'},
                {value:22, name:'异常流量'},
                {value:35, name:'扫描攻击'},
                {value:20, name:'DDoS攻击'},
                {value:30,name:'其他'}
            ]
        }
    ]
};
myChart.setOption(option);