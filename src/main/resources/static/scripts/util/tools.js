function follow(entityType, entityId) {
    if (entityType === 'content') {
        followContent(entityId);
    } else if (entityType === 'user') {
        followUser(entityId)
    }
}

function followUser(id) {
    $.post('/user/follow', {'userId':id},
        function (res) {
            if (res.code === 0) {
                location.reload();
            }
        }, 'json'
    );
}

function unfollowUser(id) {
    $.post('/user/disFollow', {'userId':id},
        function (res) {
            if (res.code === 0) {
                location.reload();
            }
        }, 'json'
    );
}

function followContent(id) {
    let $followSpan = $("#follow-span-" + id);
    let text = $.trim($followSpan.text());
    if (text === '收藏') {
        $.get('/content/follow', {'id':id},
            function (res) {
                if (res.code === 0) {
                    $followSpan.text("取消收藏");
                }
            }, 'json'
        );
    } else if (text === '取消收藏') {
        $.get('/content/disFollow', {'id':id},
            function (res) {
                if (res.code === 0) {
                    $followSpan.text("收藏");
                }
            }, 'json'
        );
    }
}

function followQuestion(id) {
    let $followQuestion = $("#followQuestion");
    let text = $.trim($followQuestion.text());
    if (text === '收藏问题') {
        $.get('/question/follow', {'id':id},
            function (res) {
                if (res.code === 0) {
                   location.reload();
                }
            }, 'json'
        );
    } else if (text === '取消收藏') {
        $.get('/question/disFollow', {'id':id},
            function (res) {
                if (res.code === 0) {
                    location.reload();
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
                    if ($form.attr('name') === 'article-comment') {
                        location.reload();
                    } else {
                        let $tbody = $form.parent("div").next("table").children("tbody");
                        $tbody.html($tbody.html() + '<tr><td>\n' +
                            '<span><img src="' + res.data.user.headUrl + '" class="Avatar"></span>\n' +
                            '<span style="margin-left: 10px">' + res.data.comment.content + '</span>\n' +
                            '</td></tr>');
                    }
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
        // editor.customConfig.uploadImgServer = '/upload';
        editor.customConfig.uploadImgShowBase64 = true;
        editor.create()
    }
});

let editor;

function submitArticle() {
    let title = $("#articleTitle").val();
    submitContent('article', title, -1);
}

function submitContent(contentType, title, qId) {
    let text = editor.txt.html();
    let data = {
        content:text,
        contentType:contentType,
        title:title,
    };
    $.post('/content/add', data,
        function (res) {
            if (qId > 0) {
                location.href = '/content/' + qId;
            } else {
                location.href = '/content/' + res.data.id;
            }
        }, 'json'
    );
}