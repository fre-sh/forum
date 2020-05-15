function followQuestion(id) {
    let $followQuestion = $("#followQuestion");
    let text = $.trim($followQuestion.text());
    if (text === '关注问题') {
        $.get('/question/follow', {'id':id},
            function (res) {
                if (res.code === 0) {
                    $followQuestion.text("取消关注");
                    $followQuestion.attr('style', 'background: darkgray;border: 1px solid darkgray;')
                }
            }, 'json'
        );
    } else if (text === '取消关注') {
        $.get('/question/disFollow', {'id':id},
            function (res) {
                if (res.code === 0) {
                    $followQuestion.text("关注问题");
                    $followQuestion.attr('style', '')
                }
            }, 'json'
        );
    }
}

function selectOption($that) {

}

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
    $("form[action='/comment/add']").each(function () {
        let $form = $(this);
        $(this).children("button").click(function () {
            let formData = $form.serializeArray();
            $.post('/comment/add', formData,
                function (res) {
                    let $tbody = $form.parent("div").next("table").children("tbody");
                    $tbody.html($tbody.html() + '<tr><td>\n' +
                        '<span><img src="' + res.data.user.headUrl + '" class="Avatar"></span>\n' +
                        '<span style="margin-left: 10px">' + res.data.comment.content + '</span>\n' +
                        '</td></tr>');
                }, 'json'
            );
        })
    });

    $(".text-brief").each(function () {
        let originalText = $(this).html();
        if (originalText.length < 200){
            $(this).next().remove();
            return;
        }
        $(this).html(getBrief(originalText));

        let lableA = $(this).next();
        // lableA.attr('href', '#' + $(this).id)
        lableA.click(function () {
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