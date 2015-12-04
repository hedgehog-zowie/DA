/**
 * Created by Administrator on 2015/11/27 0027.
 */

var activeMenu = function (url) {
    $(".content-wrapper").css("min-height", window.screen.availHeight - 200);
    $(".treeview-menu [href='" + url + "']").parents("li:eq(0)").parents("li:eq(0)").parents("li:eq(0)").addClass("active");
    $(".treeview-menu [href='" + url + "']").parents("li:eq(0)").parents("li:eq(0)").addClass("active");
    $(".treeview-menu [href='" + url + "']").parents("li:eq(0)").addClass("active");
}

// 增加或修改
var saveData = function (url, data, callBackUrl) {
    $.post(url, data, function (result) {
        if (result.code == "0") {
            swal({
                title: "保存成功!",
                type: "success",
            }, function () {
                location.href = callBackUrl;
            });
        }
        else
            swal("保存失败！", "失败原因：" + result.msg, "error");
    }, "json");
}
// 启用
var enableData = function (url, data, callBackUrl) {
    swal({
            title: "确定要启用这些数据？",
            text: "启用后将可查询与其相关的运营数据。\n若要再禁用，请点击‘禁用’。",
            type: "warning",
            showCancelButton: true,
            cancelButtonText: "取消",
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "确认，启用！",
            showLoaderOnConfirm: true,
            closeOnConfirm: false
        },
        function () {
            $.post(url, data, function (result) {
                if (result.code == "0") {
                    swal({
                        title: "已启用！",
                        type: "success",
                    }, function () {
                        location.href = callBackUrl;
                    });
                }
                else
                    swal("删除失败！", "失败原因：" + result.msg, "error");
            }, "json");
        });
}
// 禁用
var disableData = function (url, data, callBackUrl) {
    swal({
            title: "确定要禁用这些数据？",
            text: "禁用后该数据项仍可查看，但不再查询与其相关的运营数据。\n若要再启用，请点击‘启用’。",
            type: "warning",
            showCancelButton: true,
            cancelButtonText: "取消",
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "确认，禁用！",
            showLoaderOnConfirm: true,
            closeOnConfirm: false
        },
        function () {
            $.post(url, data, function (result) {
                if (result.code == "0") {
                    swal({
                        title: "已禁用！",
                        type: "success",
                    }, function () {
                        location.href = callBackUrl;
                    });
                }
                else
                    swal("删除失败！", "失败原因：" + result.msg, "error");
            }, "json");
        });
}
// 删除
var deleteData = function (url, data, callBackUrl) {
    swal({
            title: "确定要删除这些数据？",
            text: "此操作不可逆，若要暂时禁用，请点击‘禁用’。",
            type: "warning",
            showCancelButton: true,
            cancelButtonText: "取消",
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "确认，删除！",
            showLoaderOnConfirm: true,
            closeOnConfirm: false
        },
        function () {
            $.post(url, data, function (result) {
                if (result.code == "0") {
                    swal({
                        title: "已删除！",
                        type: "success",
                    }, function () {
                        location.href = callBackUrl;
                    });
                }
                else
                    swal("删除失败！", "失败原因：" + result.msg, "error");
            }, "json");
        });
}

