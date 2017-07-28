$(function () {
    var oneMenu = $('.md-selectonemenu > .ui-selectonemenu');

    for (var i = 0; i < oneMenu.length; i++) {
        var selectTag = oneMenu.eq(i).find('select');

        if (selectTag.val() !== "") {
            oneMenu.addClass("ui-state-filled");
        }
    }
});

function animationFun($this) {
    var menuId = $($this).closest('.ui-selectonemenu').attr('id'),
        menu = $(PrimeFaces.escapeClientId(menuId)),
        selectTag = menu.find('select');

    if (selectTag.val() !== "") {
        menu.addClass("ui-state-filled");
    } else {
        menu.removeClass("ui-state-filled");
    }
}