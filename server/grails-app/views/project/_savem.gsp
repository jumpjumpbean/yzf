<td>${pm?.material?.type}</td>
<td>${pm?.material?.brand}/${pm?.material?.modelno}</td>
<td>${pm?.process?.period}</td>
<td>${pm?.cnt}</td>
<td>${pm?.memo}</td>
<td>
    <button type="button" data-loading-text="删除中..." class="btn btn-danger btn-sm" onclick="deletePM('${pm.id}','${indx}',$(this))">删除</button>
</td>