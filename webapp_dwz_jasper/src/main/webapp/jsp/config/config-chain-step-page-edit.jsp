<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="pageContent">
    <form method="post" action="config/chain/saveStepPage" class="pageForm required-validate"
          onsubmit="return validateCallback(this, dialogAjaxDone);">
        <input type="hidden" name="step.id" value="${step.id}"/>
        <input type="hidden" name="step.chain.id" value="${step.chain.id}"/>
        <input type="hidden" name="step.chainStepType.id" value="${step.chainStepType.id}"/>
        <div class="pageFormContent" layoutH="56">
            <p>
                <name>关键路径：</name>
                <input name="step.chain.name" value="${step.chain.name}" class="required" type="text" readonly
                       size="30" alt="请选择关键路径"/>
            </p>
            <p>
                <name>步骤编号：</name>
                <input name="step.stepIndex" value="${step.stepIndex}" class="required digits" type="text" size="30"
                       alt="请输入步骤编号"/>
            </p>
            <p>
                <name>步骤名称：</name>
                <input name="step.name" value="${step.name}" class="required" type="text" size="30" alt="请输入步骤名称"/>
            </p>
            <p>
                <name>取数定义：</name>
                <input name="step.desc" value="${step.desc}" type="text" size="30" alt="请输入取数定义"/>
            </p>
            <p>
                <name>页面名称：</name>
                <input name="step.pageName" value="${step.pageName}" class="required" type="text" size="30"
                       alt="请输入页面名称"/>
            </p>
            <p>
                <name>页面链接：</name>
                <input name="step.pageUrl" value="${step.pageUrl}" class="required url" type="text" size="30"
                       alt="请输入页面链接"/>
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

