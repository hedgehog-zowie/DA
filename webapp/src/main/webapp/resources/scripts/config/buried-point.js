var BuriedPointTable = function () {

    var initTable = function () {

        var buriedPointTable = $('#table_buried_point');

        // begin table_buried_point
        buriedPointTable.dataTable({

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
            "bStateSave": true, // save datatable state(pagination, sort, etc) in cookie.

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
                'targets': [0, 1]
            }, {
                'width': '10%',
                'targets': [2, 3, 6],
            }, {
                'width': '15%',
                'targets': [4, 5, 7, 8]
            }, {
                'searchable': false,
                'targets': [0]
            }],
            "order": [
                [6, "desc"]
            ],
        });

        var tableWrapper = jQuery('#table_buried_point_wrapper');

        buriedPointTable.find('.group-checkable').change(function () {
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

        buriedPointTable.on('change', 'tbody tr .checkboxes', function () {
            $(this).parents('tr').toggleClass("active");
        });

        tableWrapper.find('.dataTables_length select').addClass("form-control input-xsmall input-inline"); // modify table per page dropdown

        $('#table_buried_point_new').click(function (e) {
            e.preventDefault();
            //postAjax("config/buriedPoint/add");
            location.href = "/config/buriedPoint/add";
        });

        $('#table_buried_point_delete').click(function (e) {
            e.preventDefault();
            var ids = "";
            $(".checkboxes", buriedPointTable).each(function () {
                if ($(this).parents('tr').hasClass("active"))
                    ids += $(this).val() + ",";
                return ids;
            });
            location.href = "/config/buriedPoint/delete?ids=" + ids.substring(0, ids.length - 1);
        });

        $('#table_buried_point_export').click(function (e) {
            e.preventDefault();
            location.href = "/config/buriedPoint/exportExcel";
        });

        buriedPointTable.on('click', '.edit', function (e) {
            e.preventDefault();
            location.href = "/config/buriedPoint/edit?id=" + $(this).attr("id").substring(5);
        });

        buriedPointTable.on('click', '.delete', function (e) {
            e.preventDefault();
            location.href = "/config/buriedPoint/delete?ids=" + $(this).attr("id").substring(7);
        });

        buriedPointTable.on('click', '.enable', function (e) {
            e.preventDefault();
            location.href = "/config/buriedPoint/enable?ids=" + $(this).attr("id").substring(7);
        });

        buriedPointTable.on('click', '.disable', function (e) {
            e.preventDefault();
            location.href = "/config/buriedPoint/disable?ids=" + $(this).attr("id").substring(8);
        });

    }

    return {

        //main function to initiate the module
        init: function () {
            activeMenu();

            // set right height
            $(".content-wrapper, .right-side").css('min-height', 516);

            if (!jQuery().dataTable) {
                return;
            }
            initTable();
        }

    };

}();

var BuriedPointFormValidation = function () {

    // validation
    var handleValidation = function () {
        $(".data-mask").inputmask();

        // for more info visit the official plugin documentation:
        // http://docs.jquery.?/Plugins/Validation

        var buriedPointForm = $('#form_buried_point');
        var buriedPointError = $('.alert-danger', buriedPointForm);
        var buriedPointSuccess = $('.alert-success', buriedPointForm);

        $.validator.addMethod("noBlank", function (value, element) {
            return this.optional(element) || !/_+/i.test(value);
        });

        buriedPointForm.validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block help-block-error', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "", // validate all fields including form hidden input
            rules: {
                pointFlag: {
                    required: true,
                    noBlank: true,
                },
                websiteCode: {
                    required: true,
                },
                website: {
                    required: true,
                },
                pageName: {
                    required: true,
                },
                pagePosition: {
                    required: true,
                },
            },

            messages: { // custom messages for radio buttons and checkboxes
                pointFlag: {
                    required: "必须字段",
                    noBlank: "埋点编码未输入完整",
                },
                websiteCode: {
                    required: "必须字段",
                },
                website: {
                    required: "必须字段",
                },
                pageName: {
                    required: "必须字段",
                },
                pagePosition: {
                    required: "必须字段",
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
                buriedPointSuccess.hide();
                buriedPointError.show();
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
                buriedPointSuccess.show();
                buriedPointError.hide();
                form.submit(); // submit the form
            }
        });

        //apply validation on select2 dropdown value change, this only needed for chosen dropdown integration.
        $('.select2me', buriedPointForm).change(function () {
            buriedPointForm.validate().element($(this)); //revalidate the chosen dropdown value and show error or success message for the input
        });

        $('#cancel_form_buried_point').click(function (e) {
            location.href = "/config/buriedPoint";
        });

    }

    return {
        //main function to initiate the module
        init: function () {
            activeMenu();
            handleValidation();
        }

    };

}();

var activeMenu = function () {
    $(".treeview-menu [href='/config/buriedPoint']").parents("li:eq(0)").parents("li:eq(0)").parents("li:eq(0)").addClass("active");
    $(".treeview-menu [href='/config/buriedPoint']").parents("li:eq(0)").parents("li:eq(0)").addClass("active");
    $(".treeview-menu [href='/config/buriedPoint']").parents("li:eq(0)").addClass("active");
}