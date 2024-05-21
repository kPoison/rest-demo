$(document).ready(function() {
    $("#menu-user-button a").addClass("active");

    fetch("api/get-user-info")
        .then(response => response.json())
        .then(user => {
        let roles = "";
        user.roles.forEach(role => {
            roles += (role.name + " ").replace("ROLE_", "");
        });

        let tableBody = `
            <tr class="table-active">
                <th scope="row">${user.id}</th>
                <td>${user.username}</td>
                <td>${user.email}</td>
                <td>${roles}</td>
            </tr>
        `;

        if (!roles.includes("ADMIN")) {
            $("#menu-admin-button").hide();
        }


        $("#header-email").text(user.email);
        $("#header-roles").text(roles);
        $("#table-user-info").html(tableBody);
        });
});