var ajaxLock = 0;
function initDatepicker(startDate,endDate,monthCnt){
    startDate.datepicker({
        showAnim:"slideDown",
        numberOfMonths: monthCnt,
        inline: true,
        dateFormat: 'yy-mm-dd' , monthNames:monthNames ,dayNamesMin:dayNamesMin,
        showMonthAfterYear:true,
        onClose: function(selectedDate){
            endDate.datepicker("option", "minDate", selectedDate );
        }
    });

    endDate.datepicker({
        showAnim:"slideDown",
        numberOfMonths: monthCnt,
        inline: true,
        dateFormat: 'yy-mm-dd' , monthNames:monthNames ,dayNamesMin:dayNamesMin,
        showMonthAfterYear:true,
        onClose: function(selectedDate){
            startDate.datepicker("option", "maxDate", selectedDate );
        }
    });
}

Date.prototype.Format = function(fmt) {
    var o = {
        "M+" : this.getMonth()+1,                 //月份
        "d+" : this.getDate(),                    //日
        "h+" : this.getHours(),                   //小时
        "m+" : this.getMinutes(),                 //分
        "s+" : this.getSeconds(),                 //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        "S"  : this.getMilliseconds()             //毫秒
    };
    if(/(y+)/.test(fmt)){
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    }
    for(var k in o){
        if(new RegExp("("+ k +")").test(fmt)){
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        }
    }
    return fmt;
};
/*alert message*/
/*
 * type:normal/info/error/warning
 * timeout:milliseconds
 * */
var alertMsgHtml="<div class='modal ntf-modal in' id='_alertDialog'><div class='modal-dialog modal-sm'><div class='modal-content'>"
    +"<div class='modal-body'><button type='button' class='close' data-dismiss='modal' aria-hidden='true'>&times;</button><p class='rp'></p></div>"
    +"</div></div></div>";
function showMessage(msg, type, timeout, container) {
    var $dlg = $("#_alertDialog");
    if(!$dlg.html()){
        $container = container ? $(container) : $("body");
        $container.append(alertMsgHtml);
        $dlg = $("#_alertDialog");
    }
    type = type ? type : "normal";
    $dlg.addClass(type);
    $dlg.find("p").html(msg);
    $dlg.modal({keyboard:true,backdrop:false,show:true});
    if(timeout != "keep") {
        window.setTimeout(function(){
            $dlg.modal('hide');
        }, timeout?timeout:6000);
    }
    $dlg.on('hidden.bs.modal', function (e) {
        $dlg.remove();
    });
}
function HTMLDecode(text) { var temp = document.createElement("div"); temp.innerHTML = text; var output = temp.innerText || temp.textContent; temp = null; return output; }
function updatePeriodTime(btn) {
    var startD = $("#planStartDate").val();
    var endD = $("#planEndDate").val();
    if(startD == "" || endD == ""){
        alert("请指定开始和结束时间！");
        return;
    }
    if(ajaxLock == 0){
        ajaxLock = 1;
        btn.button('loading');
        $.ajax({
            url: contextPath+"process/addPlanDate",
            type: "POST",
            data:{processId:$("#hdProcessId").val(),startDate:startD,endDate:endD},
            dataType: "text",
            success: function(data){
                if(data != "error") {
                    $("#setTimeDlg").modal('hide');
                    alert("更新成功");
                    window.location.reload();
                }else{
                    alert("处理过程发生错误，请稍候再试");
                }
            },
            complete:function(){
                ajaxLock = 0;
                btn.button('reset');
            }
        });
    }
}

function applyExamine(btn,projectId) {
    if(ajaxLock == 0){
        ajaxLock = 1;
        btn.button('loading');
        $.ajax({
            url: contextPath+"process/applyExamine",
            type: "POST",
            data:{procstepid:$("#procstepid").val()},
            dataType: "text",
            success: function(data){
                if(data != "error") {
                    alert("申请成功");
                    if(projectId){
                        window.location.href=contextPath+"project/show/"+projectId;
                    }else{
                        window.location.reload();
                    }
                }else{
                    alert("处理过程发生错误，请稍候再试");
                }
            },
            complete:function(){
                ajaxLock = 0;
                btn.button('reset');
            }
        });
    }
}

function deletePic(btn){
    var pics = $("#_picbox .selected");
    if(pics.length > 0){
        if(ajaxLock == 0 && confirm("删除后将不能恢复，确认要删除吗？")){
            ajaxLock = 1;
            btn.button('loading');
            var picids = [];
            $.each(pics,function(i,ids){
                picids.push($(ids).data("pid"));
            });
            $.ajax({
                url: contextPath + "process/deletePic",
                type: "POST",
                data: {'picids': picids},
                dataType: "text",
                success: function (data) {
                    if (data != "error") {
                        alert("删除成功");
                        window.location.reload();
                    } else {
                        alert("处理过程发生错误，请稍候再试");
                    }
                },
                complete: function () {
                    ajaxLock = 0;
                    btn.button('reset');
                }
            });
        }
    }else{
        alert("请点击要删除的图片");
    }
}

function updateStatus(btn,status){
    if(ajaxLock == 0){
        ajaxLock = 1;
        btn.button('loading');
        var pid = $("#procstepid").val();
        $.ajax({
            url: contextPath+"process/updateStatus",
            type: "POST",
            data:{procstepid:pid,status:status},
            dataType: "text",
            success: function(data){
                if(data != "error") {
                    alert("状态更新成功");
                    window.location.href=contextPath + "project/show/"+data;
                }else{
                    alert("处理过程发生错误，请稍候再试");
                }
            },
            complete:function(){
                ajaxLock = 0;
                btn.button('reset');
            }
        });
    }
}

function verify(btn){
    var attitude = $("input[name='attitude']:checked").val();
    var memo = $.trim($("#memo").val());
    var pid = $("#procstepid").val();
    if(attitude == 1 && memo.length == 0){
        alert("很遗憾没能通过你的验收，请给出你的意见，方便我们改善！");
        $("#memo").focus();
        return;
    }
    if(ajaxLock == 0){
        ajaxLock = 1;
        btn.button('loading');
        $.ajax({
            url: contextPath+"process/doverify",
            type: "POST",
            data:{procstepid:pid,attitude:attitude,memo:memo},
            dataType: "text",
            success: function(data){
                if(data != "error") {
                    if(attitude == 1){
                        alert("我们会尽快按照你的意见去改善");
                    }else{
                        alert("验收成功，从今天起将进入下一阶段的工作");
                    }
                    window.location.href=contextPath + "project/show/"+data;
                }else{
                    alert("处理过程发生错误，请稍候再试");
                }
            },
            complete:function(){
                ajaxLock = 0;
                btn.button('reset');
            }
        });
    }
}

function drawTimeLine(pid,dlg){
    var elem = $("#tline"+pid);
    if(ajaxLock == 0){
        ajaxLock = 1;
        if(dlg){
            if(elem.parent(":visible").length > 0){
                elem.parent().hide('slow');
            }else{
                elem.parent().show('slow');
            }
        }
        elem.html('');
        $.ajax({
            url: contextPath+"process/drawtl",
            type: "POST",
            data:{projectId:pid},
            dataType: "json",
            success: function(json){
                if(json != "error") {
                    Morris.Line({
                        element: 'tline'+pid,
                        data: json.plan,
                        ymin: 0,
                        ymax: 2,
                        events: json.evt,
                        eventLineColors:['#ff0000'],
                        xLabels:'day',
                        grid:true,
                        yLabelFormat:function(y){if(y==2)return "实际";return "计划"},
                        xkey: 'k',
                        ykeys: ['v','v2'],
                        //xLabelAngle: 60,
                        labels: ['t','s','y','q']
                    });
                }else{
                    alert("处理过程发生错误，请稍候再试");
                }
            },
            complete:function(){
                ajaxLock = 0;
            }
        });
    }
}

function changeQc(pid,curQcId) {
    if($("#changeQcContainer").html()){
        $("#changeQc").modal({show:true,backdrop:'static'});
    }else if(ajaxLock == 0){
        ajaxLock = 1;
        $.ajax({
            url: contextPath+"process/changeQc",
            type: "POST",
            data:{processId:pid,curqc:curQcId},
            dataType: "text",
            success: function(data){
                if(data != "error") {
                    $("#changeQcContainer").html(data);
                    $("#changeQc").modal({show:true,backdrop:'static'});
                }else{
                    alert("处理过程发生错误，请稍候再试");
                }
            },
            complete:function(){
                ajaxLock = 0;
            }
        });
    }
}

function updateQc(btn) {
    if(ajaxLock == 0){
        ajaxLock = 1;
        btn.button('loading');
        $.ajax({
            url: contextPath+"process/updateQc",
            type: "POST",
            data:{processId:$("#qcProcessId").val(),newqc:$("#newqc").val()},
            dataType: "text",
            success: function(data){
                if(data != "error") {
                    alert("更新成功");
                    window.location.reload();
                }else{
                    alert("处理过程发生错误，请稍候再试");
                }
            },
            complete:function(){
                ajaxLock = 0;
                btn.button('reset');
            }
        });
    }
}

function submitFb(){
    $("#summary").val($.trim(editor.document.getBody().getText()));
    $("#fcontent").val(editor.getData());
    $("#feedbackFrm").submit();
}
function submitWxFb(){
    var tit = $("#ftitle").val();
    var cont = $("#fcontent").val();
    if(!tit || !cont){
        alert("请填写反馈主题和内容")
        return
    }
    if(ajaxLock == 0) {
        ajaxLock = 1;
        $("#feedbackFrm").ajaxSubmit({
            type: "POST",
            dataType: "text",
            success: function(data){
                if(data != "error") {
                    $("#discontent").html("<p>我们已经收到您的反馈意见，我们会在第一时间关注，你可以登录电脑端，查看我们的回复和处理结果</p>");
                }else{
                    alert("处理过程发生错误，请稍候再试");
                }
            },
            complete:function(){
                ajaxLock = 0;
            }
        });
    }
}

function checkMtype(frm){
    var typei = $("#typei").val();
    var types = $("#types").val();
    if(!typei && !types){
        showMessage("请指定或者输入材料类型","error");
        return;
    }
    if(types){
        $("#rtype").val(types);
    }else{
        $("#rtype").val(typei);
    }
    $(frm).submit();
}

function fbcomment(btn,isFromList){
    var fcontent = $.trim($("#fbContent").val());
    if(!fcontent){
        showMessage("请输入要评价的内容");
    }
    if(ajaxLock == 0){
        ajaxLock = 1;
        btn.button('loading');
        $.ajax({
            url: contextPath+"feedback/reply",
            type: "POST",
            data:{feedbackId:$("#hdFeedbackId").val(),content:fcontent},
            dataType: "text",
            success: function(data){
                if(data != "ok") {
                    showMessage(data);
                }else{
                    if(isFromList){
                        $('#feedbackDlg').modal('hide')
                        showMessage("回复成功");
                    }else{
                        window.location.reload();
                    }
                }
            },
            complete:function(){
                ajaxLock = 0;
                btn.button('reset');
            }
        });
    }
}

function addPM(indx,btn){
    var materialId = $.trim($("#mtrs_"+indx).val());
    var cnt = $.trim($("#cnt_"+indx).val());
    var processId = $.trim($("#prcs_"+indx).val());
    if(!materialId || !cnt){
        showMessage("请选择材料和输入需要的数量");
    }
    var memo = $.trim($("#memo_"+indx).val());
    if(ajaxLock == 0){
        ajaxLock = 1;
        btn.button('loading');
        $.ajax({
            url: contextPath+"project/saveMaterial",
            type: "POST",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data:{materialId:materialId,cnt:cnt,memo:memo,projectId:$("#projectId").val(),indx:indx,processId:processId},
            dataType: "text",
            success: function(data){
                if(data != "error") {
                    $("#mtr_"+indx).html(data);
                }else{
                    showMessage("保存过程发生错误，请稍后再试");
                }
            },
            complete:function(){
                ajaxLock = 0;
                btn.button('reset');
            }
        });
    }
}

function deletePM(pmId,indx,btn){
    if(ajaxLock == 0){
        ajaxLock = 1;
        btn.button('loading');
        $.ajax({
            url: contextPath+"project/deleteMaterial",
            type: "POST",
            data:{id:pmId},
            dataType: "text",
            success: function(data){
                if(data != "error") {
                    $("#mtr_"+indx).remove();
                }else{
                    showMessage("保存过程发生错误，请稍后再试");
                }
            },
            complete:function(){
                ajaxLock = 0;
                btn.button('reset');
            }
        });
    }
}

function resetPswd(btn){
    var account = $("#acntpop").val();
    var passwd = $("#pswdpop").val();
    var userId = $("#edtUserId").val();
    if(!account || !passwd){
        showMessage("请输入账号和密码","error",3000);
        return
    }else{
        if(ajaxLock == 0) {
            ajaxLock = 1;
            btn.button('loading');
            $.ajax({
                url: contextPath + "user/resetPswd",
                type: "POST",
                data: {userId: userId, account: account, passwd: passwd},
                dataType: "json",
                success: function (data) {
                    if (data.msg) {
                        showMessage(data.msg,"error","keep");
                    } else {
                        showMessage("更新成功");
                        $("#setPasswdDlg").modal("hide");
                    }
                },
                complete: function () {
                    ajaxLock = 0;
                    btn.button('reset');
                }
            });
        }
    }
}

function filterAuth(){
    var allAuths = $("#edtUserAuth").val();
    if(!allAuths || allAuths == "[]"){
        return false;
    }
    $("#allUserAuthes input[value="+allAuths+"]").prop("checked",true);
    /*$.each(arrAuth,function(i,auth){
        $("#allUserAuthes input[data-role='"+ $.trim(auth)+"']").prop("checked",true);
    });*/
}

function continueAdd(){
    var curCnt = $("#totalCnt").val();
    var bakTr = $("#bakTr").clone(true);
    bakTr.attr("id","mtr_"+curCnt);
    bakTr.html(bakTr.html().replace(/@indx@/g,curCnt));
    $("#pm_list_tbl").append(bakTr);
    $("#totalCnt").val(parseInt(curCnt) + 1);
}