<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="pageContent">
    <form method="post" action="config/pagetag/save" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <input type="hidden" name="rTag.id" value="${rTag.id}"/>
        <div class="pageFormContent" layoutH="56">
            <p>
                <label>功能：</label>
                <input name="rTag.name" value="${rTag.name}" class="required" type="text" size="30" value="" alt="请输入功能名称"/>
            </p>
            <p>
                <label>功能编号：</label>
                <input name="rTag.rtag" value="${rTag.rtag}" class="required" type="text" size="30" value="" alt="请输入功能编号"/>
            </p>
            <p>
                <label>对应URL：</label>
                <input name="rTag.info" value="${rTag.info}" class="required url" type="text" size="30" alt="请输入页面地址"/>
            </p>
        </div>
        <div class="formBar">
            <ul>
                <li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
                <li>
                    <div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
                </li>
            </ul>
        </div>
    </form>
</div>

