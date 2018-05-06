/**
 * Created by 16255 on 2018/1/30.
 */
var myChart = echarts.init(document.getElementById('main'));

option = {
    tooltip: {
        trigger: 'item',
        formatter: "{a} <br/>{b}: {c} ({d}%)"
    },
    legend: {
        orient: 'vertical',
        x: 'right',
        y: 'bottom',
        data:['病毒攻击','异常流量','扫描攻击','DDoS攻击']
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
            ]
        }
    ]
};
myChart.setOption(option);

layui.use('table',function(){
    var table  = layui.table;
    table.render({
        elem: '#attackEventTable'
        ,url:'/demo/table/user/'
        ,page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
            layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
            //,curr: 5 //设定初始在第 5 页
            ,groups: 1 //只显示 1 个连续页码
            ,first: false //不显示首页
            ,last: false //不显示尾页

        }
        ,cols: [[
            {field:'id', width:80, title: 'id', sort: true}
            ,{field:'startTime', width:200, title: '开始时间',sort:true}
            ,{field:'endTime', width:200, title: '结束时间', sort: true}
            ,{field:'attackType', width:150, title: '攻击类型'}
            ,{field:'introduce', width:200,title: '详细描述'}
        ]]

    });
});