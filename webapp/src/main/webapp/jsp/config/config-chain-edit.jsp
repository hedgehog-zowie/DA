<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="pageContent">
    <form method="post" action="config/chain/save" class="pageForm required-validate"
          onsubmit="return validateCallback(this, dialogAjaxDone);">
        <input type="hidden" name="chain.id" value="${chain.id}"/>
        <div class="pageFormContent" layoutH="56">
            <p>
                <label>关键路径：</label>
                <input name="chain.name" value="${chain.name}" class="required" type="text" size="30" alt="请输入关键路径名称"/>
            </p>
            <p>
                <label>产品：</label>
                <input name="chain.product" value="${chain.product}" type="text" size="30" alt="请输入产品"/>
            </p>
            <%--<p>--%>
                <%--<label>关键路径步骤：</label>--%>
                <%--<input name="chain.steps" class="required" value="" type="text" readonly>--%>
                <%--<a class="btnLook" target="navTab" rel="editChainStepDig" href="config/chain/listStep?id=${chain.id}" lookupGroup="chain">添加/修改关键路径步骤</a>--%>
            <%--</p>--%>
            <%--<p>--%>
                <%--<label>说明：</label>--%>
                <%--<textarea class="textInput" rows="3" name="chain.desc" value="${chain.desc}" alt="请输入关键路径说明"--%>
                        <%--style="width: 60%"/>--%>
            <%--</p>--%>
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

