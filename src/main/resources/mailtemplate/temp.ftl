<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8"/>
            <title>PDF导出</title>
            <style type="text/css">
      *{
        box-sizing: border-box;
      }
      body{
        font-family: SimSun;
      }
      .content {
        width: 100%;
        height: 100%;
        text-align: center;
        font-size: 16px;
      }

      .contentHeader {
        height: 60px;
        line-height: 60px;
        font-size: 30px;
      }

      .title {
        width: 100%;
        height: 25px;
        line-height: 25px;
      }

      .titleLeft {
        padding-left: 20px;
        float: left;
      }

      .titleRight {
        padding-right: 20px;
        float: right;
      }

      table {
        width: 98%;
        height: 100%;
        border-collapse: collapse;
        text-align: center;
        font-size: 16px;
        margin: 0 2%;
      }

      td {
        padding: 8px;
      }

      .tdOne {
        width: 120px;
      }

      .footerTable {
        width: 98%;
        height: 100%;
        border-collapse: collapse;
        text-align: center;
        font-size: 16px;
      }
      .footerTd{
        border-bottom: 1px solid transparent;
      }
    </style>
        </head>
        <body>
            <div class='content'>
                <div class='contentHeader'>设备任务单</div>
                <div class='title'>
                    <span class='titleLeft'>所属组织： ${pkcompany}</span>
                    <span class='titleRight'>打印人：${psnname}</span>
                </div>
                <table border="1">
                    <tr>
                        <td class='tdOne'>标题</td>
                        <td class='tdTwo'>${contractno}设备任务单</td>
                        <td class='tdOne'>流水号</td>
                        <td class='tdTwo'>${pk_equtask}</td>
                    </tr>
                    <tr>
                        <td class='tdOne'>提交人</td>
                        <td class='tdTwo'>${creatorname}</td>
                        <td class='tdOne'>所属部门</td>
                        <td class='tdTwo'>${creatordepname}</td>
                    </tr>
                    <tr>
                        <td class='tdOne'>申请日期</td>
                        <td class='tdTwo'>${createdate}</td>
                        <td class='tdOne'>生产代码</td>
                        <td class='tdTwo'>${shengchandaima}</td>
                    </tr>
                    <tr>
                        <td class='tdOne'>合同号</td>
                        <td class='tdTwo'>${contractno}</td>
                        <td class='tdOne'>制号</td>
                        <td class='tdTwo'>${zhihao}</td>
                    </tr>
                    <tr>
                        <td class='tdOne'>客户所在地</td>
                        <td class='tdTwo'>${cusaddress}</td>
                        <td class='tdOne'>数量</td>
                        <td class='tdTwo'>1.0</td>
                    </tr>
                    <tr>
                        <td class='tdOne'>内部型号</td>
                        <td class='tdTwo'>${equxinghao_task}</td>
                        <td class='tdOne'>内部设备名称</td>
                        <td class='tdTwo'>${equname_task}</td>
                    </tr>
                    <tr>
                        <td class='tdOne'>合同型号</td>
                        <td class='tdTwo'>${innerEquxinghao}</td>
                        <td class='tdOne'>合同设备名称</td>
                        <td class='tdTwo'>${innerEquname}</td>
                    </tr>
                    <tr>
                        <td class='tdOne'>开始日期~要求交货时间</td>
                        <td class='tdTwo' colspan="3">${begindate}~${jiaohuodate}</td>
                    </tr>
                    <tr>
                        <td class='tdOne'>操作画面语言</td>
                        <td class='tdTwo'>${caozuoxitong}</td>
                        <td class='tdOne'>电气图纸语言</td>
                        <td class='tdTwo'>${def1}</td>
                    </tr>
                    <tr>
                        <td class='tdOne'>说明书语言</td>
                        <td class='tdTwo'>${def2}</td>
                        <td class='tdOne'>设备标识、标牌语言</td>
                        <td class='tdTwo'>${def3}</td>
                    </tr>
                    <tr>
                        <td class='tdOne'>电压要求</td>
                        <td class='tdTwo'>${dianya}</td>
                        <td class='tdOne'>CE认证</td>
                        <td class='tdTwo'>${cerenzheng}</td>
                    </tr>
                    <tr>
                        <td class='tdOne'>技术要求</td>
                        <td class='tdTwo'>${jishuyaoqiu}</td>
                        <td class='tdOne'>客户环境（温度）说明</td>
                        <td class='tdTwo'>${kehuhuanjing}</td>
                    </tr>
                    <tr>
                        <td class='tdOne'>所属制造部</td>
                        <td class='tdTwo'>${makedept}</td>
                        <td class='tdOne'>所属技术组</td>
                        <td class='tdTwo'>${tecdept}</td>
                    </tr>
                    <tr>
                        <td class='tdOne'>合同负责人</td>
                        <td class='tdTwo'>${salername}</td>
                        <td class='tdOne'>紧急程度</td>
                        <td class='tdTwo'>${jinjichengdu}</td>
                    </tr>
                    <tr>
                        <td colspan="4" class='footerTd'>审批记录</td>
                    </tr>
                </table>
                <table border="1" class='footerTable'>
                    <tr>
                        <td>节点名称</td>
                        <td>审批操作</td>
                        <td>审批意见</td>
                        <td>审批人</td>
                        <td>审批时间</td>
                    </tr>
                    <#list listKey as t>
                    <tr>
                        <td>${t.taskdefname}</td>
                        <td>${t.approveresult}</td>
                        <td>${t.approvecontentvalue}</td>
                        <td>${t.noticename}</td>
                        <td>${t.approvedatevalue}</td>
                    </tr>
                    </#list>
                </table>
            </div>
        </body>
    </html>