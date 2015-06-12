<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="pageContent">
    <form method="post" action="config/userDefinedReport/save" enctype="multipart/form-data"
          class="pageForm required-validate" onsubmit="return iframeCallback(this, dialogAjaxDone);">
        <input type="hidden" name="id" value="${userDefinedReport.id}"/>

        <div class="pageFormContent" layoutH="56">
            <p>
                <label>名称：</label>
                <input name="name" value="${userDefinedReport.name}" class="required" type="text"
                       style="width: 60%" value="" alt="请输入报表名称"/>
            </p>

            <p>
                <label>报表文件：</label>
                <input name="reportFile" type="file" class="required" style="width: 60%" value="${reportFile}"
                       alt="请上传报表文件"/>
            </p>

            <p>
                <label>描述：</label>
                <textarea class="textInput" name="desc" style="width: 60%">${userDefinedReport.desc}</textarea>
            </p>
        </div>
        <div class="formBar">
            <ul>
                <li>
                    <div class="buttonActive">
                        <div class="buttonContent">
                            <button type="submit">保存</button>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="button">
                        <div class="buttonContent">
                            <button type="button" class="close">取消</button>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </form>
</div>

