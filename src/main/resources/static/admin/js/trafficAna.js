/**
 * Created by 16255 on 2018/3/5.
 */
// the most access ip address
var chart1=echarts.init(document.getElementById('srcIpAddr'));
    option = {
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',
                right:'right',
                top:'bottom',

                data: []
            },
            series : [
                {
                    type: 'pie',
                    radius : '55%',
                    center: ['50%', '60%'],
                    data:[],
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
    chart1.setOption(option);
    chart1.showLoading();//显示加载动画
var ipAddress = [];//类别数组（用于存放饼图的类别）
var datas = [];
$.ajax({
    url:'./accessAnalysis/getAccessMostIpAddr.do',
    dataType:'json',
    type:'post',
    success:function (result) {
        console.dir(result);
        // 遍历result
        $.each(result,function(index,item){
            // 保存IP地址
            ipAddress.push(item.ipAddress);
            // 保存整个数据
            datas.push({
                value:item.accessNum,
                name:item.ipAddress

            });
        });
        console.dir(ipAddress);
        console.dir(datas);
        chart1.hideLoading();//隐藏加载动画
        //刷新数据
        chart1.setOption({
            legend:{
                data:ipAddress
            },
            series:[{
                data:datas
            }]
        });
    },
    error:function (errorMsg) {
        //请求失败时执行该函数
        alert("图标请求数据失败");
        chart1.hideLoading();
    }
});


//the most accessed ip address
var chart2=echarts.init(document.getElementById('destIpAddr'));
option = {

    tooltip : {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
        orient: 'vertical',
        right:'right',
        top:'bottom',

        data: []
    },
    series : [
        {
            type: 'pie',
            radius : '55%',
            center: ['50%', '60%'],
            data:[],
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
chart2.setOption(option);
chart2.showLoading();
var destIpAddress = [];
var destIpdatas = [];
$.ajax({
    url:'./accessAnalysis/getAccessedMostIpAddr.do',
    dataType:'json',
    type:'post',
    success:function (result) {
        $.each(result,function(index,item){
            destIpAddress.push(item.ipAddress);
            destIpdatas.push({
                name:item.ipAddress,
                value:item.accessNum
            });
        });
        chart2.hideLoading();
        chart2.setOption({
            legend:{
                data:destIpAddress
            },
            series:[{
                data:destIpdatas
            }]
        });
    },
    error: function (errorMsg) {
        //请求失败时执行该函数
        alert("图表请求数据失败!");
        chart2.hideLoading();
    }
});

//this month flow in-out the fire wall
var chart3=echarts.init(document.getElementById('ioFlow'));
option = {
    xAxis: {
        name:'时间',
        type: 'category',

    },
    yAxis: {
        name:'流量',
        type: 'value'
    },
    legend: {
        data:['出','入']
    },
    series: [{
        name:'出',
        data: [820, 932, 901, 934, 1290, 1330, 820],
        type: 'line'
    },
    {
        name:'入',
        data:[800,987,342,567,1233,453,1323],
        type:'line'
    }
    ]
};
chart3.setOption(option);

//the protocol accessed
var chart4=echarts.init(document.getElementById('protocolAccess'));
option = {
    xAxis: {
        name:'占比',
        type: 'category',
        data: []
    },
    yAxis: {
        name:'访问次数',
        type: 'value'
    },
    series: [
        {
            type: 'bar',
            barWidth : 60,
            data: [],
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
//加载
chart4.showLoading();
var portNum = [];
var accessNum = [];
$.ajax({
   url:'./accessAnalysis/getProtocolAccessed.do',
    dataType:'json',
    type:'post',
    success:function (result) {
       // 控制台输出数据
        console.dir(result);
        $.each(result,function (index,item) {
            portNum.push(item.ipAddress);
            accessNum.push(item.accessNum);
        });
        chart4.hideLoading();
        chart4.setOption({
            xAxis:{
                data:portNum
            },
            series:{
                data:accessNum
            }
        });
    },
    error:function (error) {
        alert("数据请求失败");
        chart4.hideLoading();
    }
});