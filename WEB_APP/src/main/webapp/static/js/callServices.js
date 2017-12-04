function getAllProducts() {
    $.ajax({
        url : '/getAllProducts',
        type: 'GET',
        success: function(response) {
            var data = JSON.parse(response);
            alert(response);
        },
        error: function (response) {
            alert("ERROR!");
        }
    });
}