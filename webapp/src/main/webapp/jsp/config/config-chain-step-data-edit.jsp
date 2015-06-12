<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="pageContent">
    <form method="post" action="config/chain/saveStepData" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
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
                <label>order_type：</label>
                <input name="step.orderType" value="${step.orderType}" type="text" size="30" alt="请输入order_type"/>
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
