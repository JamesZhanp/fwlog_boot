/**
 * Created by 16255 on 2018/1/30.
 */
// 遭受攻击的数量以及比例
var chart1 = echarts.init(document.getElementById('dayAttackNum'));
option1 = {

    tooltip : {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
        orient: 'vertical',
        x: 'right',
        y:'bottom',
        data: ['病毒攻击','异常流量','扫描攻击','DDos攻击','其它']
    },
    series : [
        {
            type: 'pie',
            radius : '55%',
            position:'center',
            center: ['50%', '60%'],
            data:[
                {value:23, name:'病毒攻击'},
                {value:22, name:'异常流量'},
                {value:35, name:'扫描攻击'},
                {value:20, name:'DDos攻击'},
                {value:30, name:'其它'}
            ],
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
};
chart1.setOption(option1);

// 一周内遭受攻击的趋势图
var chart2=echarts.init(document.getElementById('charts2'));
option = {
    xAxis: {
        name:'时间',
        type: 'category',
        data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
    },
    yAxis: {
        name:'数量',
        type: 'value'
    },
    series: [{
        data: [20, 32, 41, 44, 48, 59, 68],
        type: 'line',
        smooth: true
    }]
};

chart2.setOption(option);

// 被攻击最多的IP地址
var chart3=echarts.init(document.getElementById('charts3'));
option = {
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'shadow'
        }
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis: {
        name:'次数',
        type: 'value',
        splitLine:{ show:false},
        boundaryGap: [0, 0.01]

    },
    yAxis: {
        name:'内网IP',
        type: 'category',
        data:['202.107.201.4','202.107.201.104','202.107.201.89','202.107.201.173','202.107.201.206']
    },
    series: [
        {
            type: 'bar',
            barWidth : 15,
            data: [89, 83, 70, 44, 30],
            itemStyle: {
                normal:{
                    color: function (params){
                        var colorList = ['#c23531','#2f4554','#61a0a8','#d48265','#91c7ae'];
                        return colorList[params.dataIndex];
                    }
                },
            }
        },
    ]
};

chart3.setOption(option);

// 发起攻击最多的IP地址
var chart4=echarts.init(document.getElementById('charts4'));
option = {
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'shadow'
        }
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis: {
        name:'次数',
        type: 'value',
        splitLine:{ show:false},
        boundaryGap: [0, 0.01]

    },
    yAxis: {
        name:'IP地址',
        type: 'category',
        data:['172.31.214.196','89.214.213.21','290.192.168.1','172.61.216.149','192.31.214.132']
    },
    series: [
        {
            type: 'bar',
            barWidth : 18,
            data: [89, 74, 70, 44, 30],
            itemStyle: {
                normal:{
                    color: function (params){
                        var colorList = ['#c23531','#2f4554','#61a0a8','#d48265','#91c7ae'];
                        return colorList[params.dataIndex];
                    }
                },
            }
        },
    ]
};


chart4.setOption(option);