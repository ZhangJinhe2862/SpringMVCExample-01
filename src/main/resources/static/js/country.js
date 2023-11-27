$(document).ready(function () {
    $("#detailContains").css("display", "none");
    // when click the create button, show the detailContains
    $("#selCreate").on('click', function () {
        // clear all input
        $(':input', '#frmDetail')
            .not(':button, :submit, :reset, :hidden')
            .val(''); 
        // show the detailContains
        $("#detailContains").css("display", "block");
        // hide the queryContainer
        $("#queryContainer").css("display", "none");
        
        $("#addBtn").show();
        $("#updateBtn").hide();
        $("#deleteBtn").hide();
        $("#countryId").prop("readonly",false);
        $("#countryName").prop("readonly",false);
    });

    // when click the update button, show the queryContainer
    $("#selUpdate").on('click', function () {
        // show the queryContainer
        $("#queryContainer").css("display", "block");
        // hide the detailContains
        $("#detailContains").css("display", "none");
        // set the form action for update
        $("#frmDetail").attr("action", "/UpdateCountry");
        
        $("#addBtn").hide();
        $("#updateBtn").show();
        $("#deleteBtn").hide();
        $("#queryInput").prop("readonly",false);
        $("#countryId").prop("readonly",true);
        $("#countryName").prop("readonly",false);
    });

    // when click the update button, show the queryContainer
    $("#selDelete").on('click', function () {
        // show the queryContainer
        $("#queryContainer").css("display", "block");
        // hide the detailContains
        $("#detailContains").css("display", "none");
        // set the form action for update
        $("#frmDetail").attr("action", "/DeleteCountry");
        
        $("#addBtn").hide();
        $("#updateBtn").hide();
        $("#deleteBtn").show();
        $("#queryInput").prop("readonly",false);
        $("#countryId").prop("readonly",true);
        $("#countryName").prop("readonly",true);
    });

    // when click the return button, hide the detailContains
    $("#returnBtn").on('click', function () {
        // show the queryContainer
        // $("#queryContainer").css("display", "block");
        // hide the detailContains
        $("#detailContains").css("display", "none");
    });

    $("#clearBtn").on('click', function () {
        $("#queryInput").val("");
        $("#queryInput").prop("readonly",false);
        $("#detailContains").css("display", "none");
    });

    $("#queryBtn").on('click', function () {
        // use ajax to post data to controller
        // recived the data from controller with json
        // show the data in the detailContains
        $.ajax({
            type: "POST",
            url: "/country/getCountry",        //  <- controller function name
            data: $("#frmSearch").serialize(),
            dataType: 'json',
            success: function (data) {
                $("#detailContains").css("display", "block");
                // show the data in the detailContains
                $("#countryId").val(data.countryId);
                $("#countryName").val(data.countryName);
                $("#queryInput").prop("readonly",true);
                
            },
            error: function (e) {
                alert("レコードが存在しない。");
            }
        });
    });
    
    $("#updateBtn").on('click', function () {
        // use ajax to post data to controller
        // recived the data from controller with json
        // show the data in the detailContains
        $.ajax({
            type: "POST",
            url: "/country/updateCountry",        //  <- controller function name
            data: $("#frmDetail").serialize(),
            dataType: 'json',
            success: function () {
                $("#queryInput").val("");
                $("#countryId").val("");
                $("#countryName").val("");
                $("#queryInput").prop("readonly",false);
                $("#detailContains").css("display", "none");
                alert("更新成功");
            },
            error: function (e) {
                alert("更新失敗");
            }
        });
    });
    
        $("#addBtn").on('click', function () {
        // use ajax to post data to controller
        // recived the data from controller with json
        // show the data in the detailContains
        $.ajax({
            type: "POST",
            url: "/country/addCountry",
            data: $("#frmDetail").serialize(),
            dataType: 'json',
            success: function () {
                $("#detailContains").css("display", "block");
                $("#countryId").val("");
                $("#countryName").val("");
                // show the data in the detailContains
                alert("登録成功");
            },
            error: function (e) {
                alert("登録失敗");
            }
        });
    });
    
        $("#deleteBtn").on('click', function () {
        // use ajax to post data to controller
        // recived the data from controller with json
        // show the data in the detailContains
        $.ajax({
            type: "POST",
            url: "/country/deleteCountry",
            data: $("#frmDetail").serialize(),
            dataType: 'json',
            success: function () {
                $("#queryInput").val("");
                $("#countryId").val("");
                $("#countryName").val("");
                $("#queryInput").prop("readonly",false);
                $("#detailContains").css("display", "none");
                // show the data in the detailContains
                alert("削除成功");
            },
            error: function (e) {
                alert("削除失敗");
            }
        });
    });
});