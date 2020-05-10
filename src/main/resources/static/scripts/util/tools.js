function getBrief(text) {
    if (text.length > 200) {
        return text.slice(0, 200) + '...';
    }
    return text;
}

$(function () {
    $(".text-brief").each(function () {
        let originalText = $(this).html();
        $(this).html(getBrief(originalText));

        $(this).next().click(function () {
            let $content = $(this).prev();
            let html = $content.html();
            if (html.length === 203) {
                $content.html(originalText);
                $(this).text("收起")
            } else {
                $content.html(getBrief(originalText))
                $(this).text("显示全部")
            }
            $(this).toggleClass('toggle-expand-right')
        })
    })

    if ($("#wangEditor").length > 0) {
        const E = window.wangEditor;
        const editor1 = new E('#wangEditor');
        editor1.create()
    }
})