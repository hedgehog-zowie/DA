var ChannelTable = function () {

    var initTable = function () {

        var channelTable = $('#table_channel');

        // begin table_channel
        channelTable.dataTable({

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
                'width': '10%',
                'targets': [0, 5]
            }, {
                'width': '20%',
                'targets': [1, 2, 3, 4]
            }, {
                "searchable": false,
                "targets": [0]
            }],
            "order": [
                [3, "desc"]
            ],
        });

        var tableWrapper = jQuery('#table_channel_wrapper');

        channelTable.find('.group-checkable').change(function () {
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

        channelTable.on('change', 'tbody tr .checkboxes', function () {
            $(this).parents('tr').toggleClass("active");
        });

        tableWrapper.find('.dataTables_length select').addClass("form-control input-xsmall input-inline"); // modify table per page dropdown

        $('#table_channel_new').click(function (e) {
            e.preventDefault();
            location.href = "/config/channel/add";
        });

        $('#table_channel_delete').click(function (e) {
            e.preventDefault();
            var ids = "";
            $(".checkboxes", channelTable).each(function () {
                if ($(this).parents('tr').hasClass("active"))
                    ids += $(this).val() + ",";
                return ids;
            });
            location.href = "/config/channel/delete?ids=" + ids.substring(0, ids.length - 1);
        });

        $('#table_channel_export').click(function (e) {
            e.preventDefault();
            location.href = "/config/channel/exportExcel";
        });

        channelTable.on('click', '.edit', function (e) {
            e.preventDefault();
            location.href = "/config/channel/edit?id=" + $(this).attr("id").substring(5);
        });

        channelTable.on('click', '.delete', function (e) {
            e.preventDefault();
            location.href = "/config/channel/delete?ids=" + $(this).attr("id").substring(7);
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

var ChannelFormValidation = function () {

    // validation
    var handleValidation = function () {
        // for more info visit the official plugin documentation:
        // http://docs.jquery.?/Plugins/Validation

        var channelForm = $('#form_channel');
        var channelError = $('.alert-danger', channelForm);
        var channelSuccess = $('.alert-success', channelForm);

        $.validator.addMethod("baiduShortUrl", function (value, element) {
            return this.optional(element) || /^(http):\/\/dwz.cn\/[a-zA-Z0-9]+$/i.test(value);
        });

        $.validator.addMethod("notExistChannel", function (value, element) {
            var exist;
            $.ajax({
                type: "post",
                dataType: "html",
                cache: false,
                async: false,
                data: {
                    channelId: $('#channel-id').val(),
                    channelCode: $('#channelType').val() + $('#channelSerial').val() + $('#datepicker').val()
                },
                url: "/config/channel/existCode",
                success: function (result) {
                    exist = (result == "false");
                }, error: function (obj, info, errObj) {
                    exist = false;
                }
            });
            return exist;
        }, "已经存在此渠道code");

        channelForm.validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block help-block-error', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "", // validate all fields including form hidden input
            rules: {
                name: {
                    required: true,
                },
                originalUrl: {
                    required: true,
                    url: true
                },
                channelType: {
                    required: true,
                    notExistChannel: true,
                },
                channelSerial: {
                    required: true,
                    digits: true,
                    minlength: 2,
                    maxlength: 2,
                    notExistChannel: true,
                },
                activeDate: {
                    required: true,
                    notExistChannel: true,
                },
                promotionUrl: {
                    required: true,
                    url: true
                },
                shortUrl: {
                    required: true,
                    baiduShortUrl: true
                }
            },

            messages: { // custom messages for radio buttons and checkboxes
                name: {
                    required: "必须字段",
                },
                originalUrl: {
                    required: "必须字段",
                    url: "必须是url"
                },
                channelType: {
                    required: "必须字段",
                    notExistChannel: "已经存在此渠道code",
                },
                channelSerial: {
                    required: "必须字段",
                    digits: "必须是2位整数",
                    minlength: "必须是2位整数",
                    maxlength: "必须是2位整数",
                    notExistChannel: "已经存在此渠道code",
                },
                activeDate: {
                    required: "必须字段",
                    notExistChannel: "已经存在此渠道code",
                },
                promotionUrl: {
                    required: "必须字段",
                    url: "必须是url"
                },
                shortUrl: {
                    required: "必须字段",
                    baiduShortUrl: "必须是百度短链接"
                }
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
                channelSuccess.hide();
                channelError.show();
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
                channelSuccess.show();
                channelError.hide();
                form.submit(); // submit the form
            }
        });

        //apply validation on select2 dropdown value change, this only needed for chosen dropdown integration.
        $('.select2me', channelForm).change(function () {
            regenerateUrl();
            channelForm.validate().element($(this)); //revalidate the chosen dropdown value and show error or success message for the input
        });

        $('.change', channelForm).keyup(function () {
            regenerateUrl();
            channelForm.validate().element($(this)); //revalidate the chosen dropdown value and show error or success message for the input
        });

        //initialize datepicker
        $('.date-picker').datepicker({
            autoclose: true,
        });
        $('.date-picker .form-control').change(function () {
            regenerateUrl();
            channelForm.validate().element($(this)); //revalidate the chosen dropdown value and show error or success message for the input
        });

        var regenerateUrl = function () {
            $('#promotion_url').val('');
            $('#short_url').val('');
        }

        $('#button_generate_promotion_url').click(function (e) {
            if (channelForm.validate().element($('#original_url')) && channelForm.validate().element($('#channelType'))
                && channelForm.validate().element($('#channelSerial')) && channelForm.validate().element($('#datepicker'))) {
                $('#promotion_url').val($('#original_url').val() + '?ad_id=' + $('#channelType').val() + $('#channelSerial').val() + $('#datepicker').val());
                channelForm.validate().element($('#promotion_url'));
            }
        });

        $('#button_generate_short_url').click(function (e) {
            if (channelForm.validate().element($('#promotion_url'))) {
                $.ajax({
                    type: "post",
                    dataType: "html",
                    cache: false,
                    data: {longUrl: $('#promotion_url').val()},
                    url: "/config/channel/generateShortUrl",
                    success: function (data) {
                        $('#short_url').val(data);
                        channelForm.validate().element($('#short_url'));
                    }, error: function (obj, info, errObj) {
                        alert(info);
                    }
                });
            }
        });

        $('#cancel_form_channel').click(function (e) {
            location.href = "/config/channel";
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
    $(".treeview-menu [href='/config/channel']").parents("li:eq(0)").parents("li:eq(0)").parents("li:eq(0)").addClass("active");
    $(".treeview-menu [href='/config/channel']").parents("li:eq(0)").parents("li:eq(0)").addClass("active");
    $(".treeview-menu [href='/config/channel']").parents("li:eq(0)").addClass("active");
}