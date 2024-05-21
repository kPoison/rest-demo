$(document).ready(function() {
    $("#menu-admin-button a").addClass("active");

    fetch("api/get-user-info")
        .then(response => response.json())
        .then(user => {
            let roles = "";
            user.roles.forEach(role => {
                roles += (role.name + " ").replace("ROLE_", "");
            });

            $("#header-email").text(user.email);
            $("#header-roles").text(roles);
        });

    function showTable() {
        $("#admin-page-nav a:first").addClass("active");
        $("#admin-page-nav a:last").removeClass("active");
        $("#admin-page-new-user").hide();
        $("#admin-page-users-table").show();

        fetch("api/admin/get-users-table")
            .then(response => response.json())
            .then(users => {
                $("#users-table-body").empty();

                users.forEach(user => {
                    let roles = "";
                    user.roles.forEach(role => {
                        roles += (role.name + " ").replace("ROLE_", "");
                    });

                    $("#users-table-body").append(`
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.username}</td>
                            <td>${user.email}</td>
                            <td>${roles}</td>
                            <td>
                                <button type="button" class="users-table-edit btn btn-info btn-sm text-white">
                                    Edit
                                </button>
                            </td>
                            <td>
                                <button type="button" class="users-table-delete btn btn-danger btn-sm">
                                    Delete
                                </button>
                            </td>
                        </tr>
                    `)
                })

                $(".users-table-edit").click(function() {
                    let $modal = $("#edit-modal");
                    let tableData = $(this).parent().siblings();

                    $modal.find("input:eq(0)").attr("value", tableData.eq(0).html());
                    $modal.find("input:eq(1)").attr("value", tableData.eq(1).html());
                    $modal.find("input:eq(2)").attr("value", tableData.eq(2).html());
                    $modal.find("input:eq(3)").attr("value", "");

                    if (String(tableData.eq(3).html()).includes("USER")) {
                        $modal.find("option:eq(0)").attr("selected", "selected");
                        $modal.find("option:eq(1)").removeAttr("selected");
                    } else {
                        $modal.find("option:eq(1)").attr("selected", "selected");
                        $modal.find("option:eq(0)").removeAttr("selected");
                    }

                    $modal.modal("show");
                })

                $(".users-table-delete").click(function() {
                    let $modal = $("#delete-modal");
                    let tableData = $(this).parent().siblings();

                    $modal.find("input:eq(0)").attr("value", tableData.eq(0).html());
                    $modal.find("input:eq(1)").attr("value", tableData.eq(1).html());
                    $modal.find("input:eq(2)").attr("value", tableData.eq(2).html());
                    $modal.find("input:eq(3)").attr("value", tableData.eq(3).html());

                    $modal.modal("show");
                })
            })
    }

    $("#edit-modal-request").click(function() {
        let user = {
            id: $("#edit-modal-id").val(),
            username: $("#edit-modal-username").val(),
            password: $("#edit-modal-password").val(),
            email: $("#edit-modal-email").val(),
            roles: [{
                id: $("#edit-modal-role").val()
            }]
        }

        fetch("api/admin/edit-user", {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(user)
        })
            .then(function() {
                $("#edit-modal").modal("hide");
                showTable();
            })
    })

    $("#delete-modal-request").click(function() {
        fetch("api/admin/delete-user?id=" + $("#delete-modal-id").val(), {
            method: 'DELETE'
        })
            .then(function() {
                $("#delete-modal").modal("hide");
                showTable();
            })
    })

    showTable();

    $("#admin-page-nav li:first a").click(function() {
        showTable();
    })


    $("#admin-page-nav li:last a").click(function() {
        $("#admin-page-nav a:last").addClass("active");
        $("#admin-page-nav a:first").removeClass("active");
        $("#admin-page-users-table").hide();
        $("#admin-page-new-user").show();
    })

    $("#new-user-add").click(function() {
        let newUser = {
            username: $("#username").val(),
            email: $("#email").val(),
            password: $("#password").val(),
            roles: [{
                id: $("#role").val()
            }]
        };

        fetch("api/admin/create-user", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(newUser)
        })
            .then(showTable);
    })
});

