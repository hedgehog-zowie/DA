<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="pageContent">
    <form method="post" action="config/chain/saveStepAction" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <input type="hidden" name="step.id" value="${step.id}"/>
        <input type="hidden" name="step.chain.id" value="${step.chain.id}"/>
        <input type="hidden" name="step.chainStepType.id" value="${step.chainStepType.id}"/>
        <div class="pageFormContent" layoutH="56">
            <p>
                <label>关键路径：</label>
                <input name="step.chain.name" value="${step.chain.name}" class="required" type="text" readonly
                       size="30" alt="请选择关键路径"/>
            </p>
            <p>
                <label>步骤编号：</label>
                <input name="step.stepIndex" value="${step.stepIndex}" class="required digits" type="text" size="30"
                       alt="请输入步骤编号"/>
            </p>
            <p>
                <label>步骤名称：</label>
                <input name="step.name" value="${step.name}" class="required" type="text" size="30" alt="请输入步骤名称"/>
            </p>
            <p>
                <label>取数定义：</label>
                <input name="step.desc" value="${step.desc}" type="text" size="30" alt="请输入取数定义"/>
            </p>
            <p>
                <label>功能编码：</label>
                <input id="stepRTag" name="step.rTag.id" value="${step.rTag.id}" type="hidden">
                <input name="step.rTag.rtag" value="${step.rTag.rtag}" class="required" type="text" readonly>
                <a rel="addRTag" class="btnLook" href="config/chain/listRTags" lookupGroup="step.rTag">添加RTag</a>
                <%--<input class="required" name="step.rTag.rtag" value="${step.rTag.rtag}" type="text" size="30" alt="请选择功能编码" readonly/>--%>
            </p>
            <p>
                <label>页面名称：</label>
                <input name="step.rTag.name" value="${step.rTag.name}" class="required" type="text" size="30" readonly/>
            </p>
            <p>
                <label>页面链接：</label>
                <input name="step.rTag.info" value="${step.rTag.info}" class="required url" type="text" size="30" readonly/>
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

