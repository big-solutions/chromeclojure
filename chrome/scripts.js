function loadChromeClojureOptions(){
    var responseMethod = localStorage['responseMethod'];
    $('.response_method').filter("[value="+responseMethod+"]").prop("checked",true);
}

function saveChromeClojureOptions(){
    var responseMethod = $('input:radio[name=responseMethod]:checked').val();
    localStorage['responseMethod'] = responseMethod;
    $('#message').text('Saved response method: ' + responseMethod);
}

$(document).ready(loadChromeClojureOptions());

$('#button').on('click', function() {
    saveChromeClojureOptions();
});

$(document).ready(function() {
    $('.response_method').checkboxradio();
});


