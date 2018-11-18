
/// <summary>현재 System.Globalization.CultureInfo 언어를 나타내는 두 문자로 된 ISO 639-1 코드입니다.</summary>
var locale = GetFrameworkLocale();

/// <summary>메시지 출력창 입니다.</summary>
/// <param name="message" type="String">메시지 창의 내용입니다.</param>
/// <param name="title" type="String">메시지 창의 제목 입니다.</param>
function MsgBox(message, title)
{
    $.li18n.reset(GetFrameworkLocale());
    var y = $.li18n.translate("Ok");
    var t = (title != null) ? title : $.li18n.translate("Alert");
    
    $("<div>" + message + "</div>").dialog({
        buttons: [{
            text: y,
            click: function () {
                $(this).remove();
            }
        }],
        close: function (event, ui) { $(this).remove(); },
        resizable: false,
        width: "400px",
        title: t,
        modal: true
    }); //.text().parent().addClass("alert");
}

/// <summary>선택 확인 상자 출력창 입니다.
///          - okText, cancelText, title 값을 넣지 않을 경우 li18n 를 통한 다국어 처리로 Ok, Cancel, Confirm 에 대한 국가별 단어를 표시 합니다.
/// </summary>
/// <param name="message" type="String">선택 창의 내용입니다.</param>
/// <param name="okCallBack" type="object">ok 선택 시 수행될 함수 입니다.</param>
/// <param name="param" type="String">ok 선택 시 수행될 함수에 전달되는 파라미터 입니다.</param>
/// <param name="okText" type="String">[option] ok 버튼에 대한 text 입니다.</param>
/// <param name="cancelText" type="String">[option] cancel 버튼에 대한 text 입니다.</param>
/// <param name="title" type="String">[option] 선택창의 제목에 대한 text 입니다.</param>
function ConfirmBox(message, okCallBack, param, okText, cancelText, title)
{    
    $.li18n.reset(GetFrameworkLocale());
   
    var y = (okText != null) ? okText : $.li18n.translate("Ok");
    var n = (cancelText != null) ? cancelText : $.li18n.translate("Cancel");
    var t = (title != null) ? title : $.li18n.translate("Confirm");

    $("<div>" + message + "</div>").dialog({
        buttons: [{
            text: y,
            click: function () {
                if (okCallBack) okCallBack(param);
                $(this).remove();                
            }
        },
        {
            text: n,
            click: function () {
                $(this).remove();                
            }
        }
        ],
        close: function (event, ui) { $(this).remove(); },
        resizable: false,
		width: "400px",
        title: t,
        modal: true
    }); //.text().parent().addClass("alert");
}

/// <summary>현재 System.Globalization.CultureInfo 언어를 나타내는 두 문자로 된 ISO 639-1 코드를 가져 옵니다.</summary>
function GetFrameworkLocale() {
    var letter = "ko";
    $.ajax({
        type: 'POST',
        url: "/Common/GetSystemLocale",
        contentType: 'application/json; charset=utf-8',
        async: false,        
        success: function (data, status, xhr) {
            letter = data
        },
        error: function (request, status, error) {
            letter = "ko";
        }
    });
    return letter;
}
/*---------------------------------------------------------------------------------------------------------------------------*/

