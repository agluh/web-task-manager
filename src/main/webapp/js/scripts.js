function updateTasksList() {
    $.getJSON("/tasks")
    .done(function(data) {
        var status = $('#status-template').html();
        var actions = $('#actions-template').html();

        $("#tasks").empty();

        $.each(data, function(i, item) {
            var tr = $("<tr>").data("id", item.id).appendTo("#tasks");
            var task_status = item.status.toLowerCase();

            $("<td>").html("<div class='task__title'>" + item.title + "</div><div class='task__description text-secondary'>" + item.description + "</div>").appendTo(tr);
            $("<td>").html(status).appendTo(tr);
            $("<td>").html(actions).appendTo(tr);

            $(tr).addClass('task').addClass('task--' + task_status);

            $(tr).find("select").val(task_status);
        });

        $(".edit-button").click(function(){
            var id = $(this).parents("tr").data("id");
            showUpdateTaskModal(id);
        });

        $(".remove-button").click(function(){
            var id = $(this).parents("tr").data("id");
            removeTask(id);
        });

        $(".task-status").change(function(){
            var id = $(this).parents("tr").data("id");
            var status = $(this).val();
            changeTaskStatus(id, status);
        });
    });
}

function removeTask(id) {
    $.ajax({
        type: "DELETE",
        url: "/tasks/" + id,
        success: function(data) {
            updateTasksList();
            showToast("Item was successfully removed");
        }
    });
}

function showToast(text) {
    var toastTemplate = $('#toast-template').html();
    var toastEl = $(toastTemplate);

    $(toastEl).find(".toast-body").text(text);
    var toast = new bootstrap.Toast(toastEl);
    $(toastEl).appendTo("#toast-container");

    toast.show();
}

function changeTaskStatus(id, status) {
    $.ajax({
        type: "PUT",
        url: "/tasks/" + id + "/status",
        data: {"status": status},
        success: function(data) {
            updateTasksList();
            showToast("Task status was successfully changed");
        }
    });
}

$("#add_task_form").submit(function(e) {
    e.preventDefault();

    var form = $(this);
    var data = convertFormToJSON(form);
    var actionUrl = form.attr('action');

    $.ajax({
        type: "POST",
        url: actionUrl,
        data: JSON.stringify(data),
        processData: false,
        contentType: 'application/json',
        success: function(data) {
            updateTasksList();

            showToast("Task was successfully created");

            $(':input', form)
            .not(':button, :submit, :reset, :hidden')
            .val('')
            .prop('checked', false)
            .prop('selected', false);

            $('select', form)
            .val("new");
        }
    });
});

function showUpdateTaskModal(id) {
    $('#update-task-modal').modal('hide');

    $.ajax({
        type: "GET",
        url: "/tasks/" + id,
        success: function(data) {
            $('#update-task-form__title').val(data.title);
            $('#update-task-form__description').val(data.description);
            $('#update-task-form__id').val(data.id);
            $('#update-task-form__status').val(data.status.toLowerCase());
            $('#update-task-modal').modal('show');
        }
    });
}

function updateTask(id, data) {
    $.ajax({
        type: "PATCH",
        url: "/tasks/" + id,
        data: JSON.stringify(data),
        processData: false,
        contentType: 'application/json',
        success: function(data) {
            updateTasksList();
            showToast("Task was successfully updated");
        }
    });
}

function convertFormToJSON(form) {
    const array = $(form).serializeArray();
    const json = {};
    $.each(array, function () {
        json[this.name] = this.value || "";
    });
    return json;
}

$(document).ready(function() {
    updateTasksList();

    $("#update-task-form__submit").click(function(e) {
        $('#update-task-modal').modal('hide');
        var id = $('#update-task-form__id').val();
        var form = $('#update-task-form');
        var data = convertFormToJSON(form);
        updateTask(id, data);
    });
});