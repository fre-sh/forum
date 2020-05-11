function getBrief(text) {
    if (text.length > 200) {
        return text.slice(0, 200) + '...';
    }
    return text;
}

function $postJson(url, data, callback, type){
    $.ajax({
        url: url,
        type: "POST",
        data: JSON.stringify(data),
        contentType: "application/json; charset=utf-8",
        dataType: type,
        success: callback,
    });
}

$(function () {
    $(".text-brief").each(function () {
        let originalText = $(this).html();
        $(this).html(getBrief(originalText));

        $(this).next().click(function () {
            let $content = $(this).prev();
            let html = $content.html();
            if (html !== originalText) {
                $content.html(originalText);
                $(this).text("收起")
            } else {
                $content.html(getBrief(originalText));
                $(this).text("显示全部")
            }
            $(this).toggleClass('toggle-expand-right')
        })
    });

    if ($("#wangEditor").length > 0) {
        const E = window.wangEditor;
        editor = new E('#wangEditor');
        editor.create()
    }
});

let editor;
function submitContent(contentType, title, qId) {
    let text = editor.txt.html();
    let data = {
        content:text,
        contentType:contentType,
        title:title,
    };
    $.post('/content/add', data,
        function (res) {
            location.href = '/question/' + qId
        }, 'json'
    );
}