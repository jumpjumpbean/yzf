<div class="panel panel-default">
    <div class="list-group">
        <div href="#" class="list-group-item">
            <h4 class="list-group-item-heading text-primary">项目人员信息</h4><hr/>
            <g:set var="supervisor" value="${process?.charger}"></g:set>
            <g:set var="qc" value="${process?.project.qc}"></g:set>
            <div class="row list-group-item-text">
                <div class="col-sm-1"><strong>监理：</strong></div>
                <div class="col-sm-3">${supervisor?.uname}(电话：${supervisor?.telephone})</div>
                <div class="col-sm-1"><strong>质检：</strong></div>
                <div class="col-sm-3">${qc?.uname}(电话：${qc?.telephone})</div>
            </div>
        </div>
        <div href="#" class="list-group-item">
            <h4 class="list-group-item-heading text-primary">项目进料单(${process?.period}阶段)</h4>
            <div class="list-group-item-text">
                <g:if test="${material == null || material.size() == 0}">
                    <h5>本阶段没有进材料</h5>
                </g:if>
                <g:else>
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>材料类型</th>
                        <th>材料名</th>
                        <th>材料品牌</th>
                        <th>材料型号</th>
                        <th>数量</th>
                        <th>备注</th>
                    </tr>
                    </thead>
                    <tbody>
                        <g:each in="${material}" var="pm" status="i">
                        <g:set var="mtrl" value="${pm.material}"></g:set>
                        <tr>
                            <td>${i+1}</td>
                            <td>${mtrl?.type}</td>
                            <td>${mtrl?.name}</td>
                            <td>${mtrl?.brand}</td>
                            <td>${mtrl?.modelno}</td>
                            <td>${pm.cnt}${mtrl?.unit}</td>
                            <td>${pm.memo}</td>
                        </tr>
                        </g:each>
                    </tbody>
                </table>
                </g:else>
            </div>
        </div>
    </div>
</div>