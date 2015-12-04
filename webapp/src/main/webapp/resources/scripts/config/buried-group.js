//(function ($) {
//    $.fn.extend({
//        select2_sortable: function (params) {
//            var select = $(this);
//            $(select).select2(params);
//            var ul = $('.select2-selection__rendered').first('ul');
//            ul.sortable({
//                placeholder: 'ui-state-highlight',
//                items: 'li:not(.select2-search)',
//                tolerance: 'pointer',
//                //stop: function () {
//                //    sortPoints();
//                //}
//            });
//        }
//    });
//}(jQuery));

var BuriedGroupTable = function () {

    var initTable = function () {

        var buriedGroupTable = $('#table_buried_group');

        // begin table_buried_group
        buriedGroupTable.dataTable({

            // Internationalisation. For more info refer to http://datatables.net/manual/i18n
            "language": {
                "aria": {
                    "sortAscending": ": 升序排列",
                    "sortDescending": ": 降序排列"
                },
                "emptyTable": "无数据",
                "info": "显示 _START_ 到 _END_ 条记录，共 _TOTAL_ 条记录",
                "infoEmpty": "未找到相应记录",
                "infoFiltered": "(从 _MAX_ 条记录中过滤)",
                "lengthMenu": "每页显示： _MENU_ ",
                "search": "查找：",
                "zeroRecords": "无匹配的记录",
                "paginate": {
                    "previous": "上一页",
                    "next": "下一页",
                    "last": "末页",
                    "first": "首页"
                }
            },

            "bProcessing": true,
            "bStateSave": false, // save datatable state(pagination, sort, etc) in cookie.

            "lengthMenu": [
                [5, 15, 30, -1],
                [5, 15, 30, "全部"] // change per page values here
            ],
            // set the initial value
            "pageLength": 15,
            "pagingType": "bootstrap_full_number",
            "columnDefs": [{  // set default column settings
                'orderable': false,
                'targets': [0]
            }, {
                'width': '5%',
                'targets': [0]
            }, {
                'width': '20%',
                'targets': [1, 2, 3, 4]
            }, {
                'width': '15%',
                'targets': [5]
            }],
            "order": [
                [3, "desc"]
            ],
        });

        var tableWrapper = jQuery('#table_buried_group_wrapper');

        buriedGroupTable.find('.group-checkable').change(function () {
            var set = jQuery(this).attr("data-set");
            var checked = jQuery(this).is(":checked");
            jQuery(set).each(function () {
                if (checked) {
                    //$(this).attr("checked", true);
                    $(this).prop("checked", true);
                    $(this).parents('tr').addClass("active");
                } else {
                    //$(this).attr("checked", false);
                    $(this).removeAttr("checked");
                    $(this).parents('tr').removeClass("active");
                }
            });
            jQuery.uniform.update(set);
        });

        buriedGroupTable.on('change', 'tbody tr .checkboxes', function () {
            $(this).parents('tr').toggleClass("active");
        });

        tableWrapper.find('.dataTables_length select').addClass("form-control input-xsmall input-inline"); // modify table per page dropdown

        $('#table_buried_group_new').click(function (e) {
            e.preventDefault();
            //postAjax("config/buriedGroup/add");
            location.href = "/config/buriedGroup/add";
        });

        $('#table_buried_group_delete').click(function (e) {
            e.preventDefault();
            var ids = "";
            $(".checkboxes", buriedGroupTable).each(function () {
                if ($(this).parents('tr').hasClass("active"))
                    ids += $(this).val() + ",";
                return ids;
            });
            if (ids == undefined || ids == null || ids == "")
                swal("请勾选需要删除的数据！", "", "info");
            else
                deleteData("/config/buriedGroup/delete", {ids: ids}, "/config/buriedGroup");
            //location.href = "/config/buriedGroup/delete?ids=" + ids.substring(0, ids.length - 1);
        });

        $('#table_buried_group_export').click(function (e) {
            e.preventDefault();
            location.href = "/config/buriedGroup/exportExcel";
        });

        buriedGroupTable.on('click', '.edit', function (e) {
            e.preventDefault();
            location.href = "/config/buriedGroup/edit?id=" + $(this).attr("id").substring(5);
        });

        buriedGroupTable.on('click', '.delete', function (e) {
            e.preventDefault();
            deleteData("/config/buriedGroup/delete", {ids: $(this).attr("id").substring(7)}, "/config/buriedGroup");
            //location.href = "/config/buriedGroup/delete?ids=" + $(this).attr("id").substring(7);
        });

        buriedGroupTable.on('click', '.enable', function (e) {
            e.preventDefault();
            enableData("/config/buriedGroup/enable", {ids: $(this).attr("id").substring(7)}, "/config/buriedGroup");
            //location.href = "/config/buriedGroup/enable?ids=" + $(this).attr("id").substring(7);
        });

        buriedGroupTable.on('click', '.disable', function (e) {
            e.preventDefault();
            disableData("/config/buriedGroup/disable", {ids: $(this).attr("id").substring(8)}, "/config/buriedGroup");
            //location.href = "/config/buriedGroup/disable?ids=" + $(this).attr("id").substring(8);
        });

    }

    return {

        //main function to initiate the module
        init: function () {
            activeMenu('/config/buriedGroup');
            initTable();
        }

    };

}();

var BuriedGroupFormValidation = function () {

    var initPoint = function () {
        $("#selectPointOfGroup").select2({
            closeOnSelect: false,
            language: "zh-CN",
        });
        var ul = $('.select2-selection__rendered').first('ul');
        ul.sortable({
            placeholder: 'ui-state-highlight',
            items: 'li:not(.select2-search)',
            tolerance: 'pointer',
        });
    };

    // validation
    var handleValidation = function () {
        $(".data-mask").inputmask();

        // for more info visit the official plugin documentation:
        // http://docs.jquery.?/Plugins/Validation

        var buriedGroupForm = $('#form_buried_group');
        var buriedGroupError = $('.alert-danger', buriedGroupForm);
        var buriedGroupSuccess = $('.alert-success', buriedGroupForm);

        buriedGroupForm.validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block help-block-error', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "", // validate all fields including form hidden input
            rules: {
                name: {
                    required: true,
                },
                buriedPoints: {
                    required: true,
                    minlength: 2,
                }
            },

            messages: { // custom messages for radio buttons and checkboxes
                name: {
                    required: "必须字段",
                },
                buriedPoints: {
                    required: "必须字段",
                    minlength: "至少选择两个埋点",
                },
            },

            errorPlacement: function (error, element) { // render error placement for each input type
                if (element.parent(".input-group").size() > 0) {
                    error.insertAfter(element.parent(".input-group"));
                } else if (element.attr("data-error-container")) {
                    error.appendTo(element.attr("data-error-container"));
                } else if (element.parents('.radio-list').size() > 0) {
                    error.appendTo(element.parents('.radio-list').attr("data-error-container"));
                } else if (element.parents('.radio-inline').size() > 0) {
                    error.appendTo(element.parents('.radio-inline').attr("data-error-container"));
                } else if (element.parents('.checkbox-list').size() > 0) {
                    error.appendTo(element.parents('.checkbox-list').attr("data-error-container"));
                } else if (element.parents('.checkbox-inline').size() > 0) {
                    error.appendTo(element.parents('.checkbox-inline').attr("data-error-container"));
                } else {
                    error.insertAfter(element); // for other inputs, just perform default behavior
                }
            },

            invalidHandler: function (event, validator) { //display error alert on form submit
                buriedGroupSuccess.hide();
                buriedGroupError.show();
            },

            highlight: function (element) { // hightlight error inputs
                $(element)
                    .closest('.form-group').addClass('has-error'); // set error class to the control group
            },

            unhighlight: function (element) { // revert the change done by hightlight
                $(element)
                    .closest('.form-group').removeClass('has-error'); // set error class to the control group
            },

            success: function (label) {
                label
                    .closest('.form-group').removeClass('has-error'); // set success class to the control group
            },

            submitHandler: function (form) {
                buriedGroupSuccess.show();
                buriedGroupError.hide();
                // todo ajax submit
                var points = new Array();
                var alertSortedPoints = "";
                var i = 0;
                var ul = $('.select2-selection__rendered').first('ul');
                $(ul).find('.select2-selection__choice').each(function () {
                    var textContent = $(this).attr("title");
                    var id = $('#selectPointOfGroup option:contains("' + textContent + '")')[0].getAttribute("value");
                    points[i++] = id;
                    alertSortedPoints = alertSortedPoints + i + " : " + textContent.slice(-11) + "\n";
                });
                $('#buriedPoints').val(points);
                swal({
                    title: "请确认埋点顺序!",
                    text: alertSortedPoints,
                    type: "info",
                    showCancelButton: true,
                    cancelButtonText: "重新设置埋点顺序",
                    confirmButtonColor: "#DD6B55",
                    confirmButtonText: "确认，保存!",
                }, function () {
                    saveData("/config/buriedGroup/save", buriedGroupForm.serialize(), "/config/buriedGroup");
                });
                //form.submit(); // submit the form
            }
        });

        //apply validation on select2 dropdown value change, this only needed for chosen dropdown integration.
        $('.select2me', buriedGroupForm).change(function () {
            buriedGroupForm.validate().element($(this)); //revalidate the chosen dropdown value and show error or success message for the input
        });

        $('#cancel_form_buried_group').click(function (e) {
            location.href = "/config/buriedGroup";
        });

        $("#selectPointOfGroup").change(function () {
            $(this).valid();
        });

    }

    return {
        //main function to initiate the module
        init: function () {
            activeMenu('/config/buriedGroup');
            initPoint();
            handleValidation();
        }
    };

}();
