
function mtoggle() {

    // e.preventDefault();
    $("#wrapper").toggleClass("toggled");

}
function mtoggle2() {

    $("#wrapper").toggleClass("toggled-2");
    $('#menu ul').hide();    
}

function initMenu() {
    $('#menu ul').hide();
    $('#menu ul').children('.current').parent().show();
    //$('#menu ul:first').show();
    $('#menu li a').click(           
            function () {    
                $('#menu .fa-chevron-up').attr('class','fa fa-chevron-down fa-stack-1x');
                var checkElement = $(this).next();
                if ((checkElement.is('ul')) && (checkElement.is(':visible'))) {
                    $('#menu ul').slideUp('normal');                    
                    return false;
                }
                if ((checkElement.is('ul')) && (!checkElement.is(':visible'))) {
                    $('#menu ul:visible').slideUp('normal');
                    checkElement.slideDown('normal');
                    $(this).find('#arrow').attr('class','fa fa-chevron-up fa-stack-1x');
                    return false;
                }
            }
    );
}
$(document).ready(function () {
    initMenu();
});