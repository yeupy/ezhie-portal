/// <summary>EZ_DatePicker 를 생성(생성자) 합니다.</summary>
/// <param name="json" type="json">개체를 생성하기 위한 정보가 포함된 json 개체 입니다.</param>
var EZ_DatePicker = function (json) {
    var calendar = null;                                    // EZ_DatePicker 에서 화면에 표시될 EZ_Calendar
    var context = (json.context) ? json.context : window;   // EZ_DatePicker 를 소유한 window
    var dateFormat = json.dateFormat                        // EZ_DatePicker 에서 표시될 날짜 유형(EZ_Common . DataFormat)

    var inputId = json.inputId;                             // 날짜가 표시될 input type text element 의 ID
    var triggerId = json.triggerId;                         // click 이벤트에 반응할 input type img element 의 ID
    var selectedDate = json.selectedDate;                   // 최초로 표시될 날짜 값
    var culture = json.culture;                             // 현재 문화권
    var isIn = false;                                       // EZ_Calendar 의 display 여부

    this.CalendarClosed = (json.calendarClosed) ? json.calendarClosed : null;                   // EZ_Calendar 가 닫힐때 발생하는 이벤트
    this.SelectedDateChanged = (json.selectedDateChanged) ? json.selectedDateChanged : null;;   // EZ_Calendar 에서 날짜를 선택했을 때 발생하는 이벤트

    /// <summary>EZ_Calendar 가 닫힐때 발생하는 이벤트를 정의</summary>
    var OnCalendarClosed = function (sender, e) {
        $(context.document).find("body").find("#ui-ez-datepicker").remove();
        calendar = null;

        if (sender.CalendarClosed) {
            sender.CalendarClosed(sender, e);
        }
    }

    /// <summary>EZ_DatePicker 에서 기본정보를 설정하고 focusout / click 이벤트를 연결 합니다.</summary>
    var Init = function (sender, e) {

        var target = context.$("#" + inputId);

        if (selectedDate) {
            target.val(selectedDate.toLocaleString(culture, dateFormat));
        }

        target.bind("focusout", function (e) {
            if (calendar != null) {
                if (isIn == false) {
                    sender.IsDropDownOpen(false);
                }
            }
        });

        if (triggerId) {
            context.$("#" + triggerId).bind("click", function (event) {
                sender.IsDropDownOpen(true);
            });
        }
    }

    /// <summary>EZ_DatePicker 이름을 문자열로 반환 합니다.</summary>
    this.toString = function () {
        return "EZ_DatePicker";
    }

    /// <summary>EZ_Calendar 를 화면에 표시하거나 제거합니다.</summary>
    /// <param name="value" type="bool">EZ_Calendar 를 닫힘(false) / 열림(open) 여부</param>
    this.IsDropDownOpen = function (value) {

        if (value == undefined || null) {
            return $(context.document).find("body").find("#ui-ez-datepicker").length > 0;
        }

        if (value) {

            var self = this;
            var target = context.$("#" + inputId);
            target.focus();

            var offset = {
                left: target.offset().left,
                top: target.offset().top + target.outerHeight()+10
            };

            var frameheight = $(context.window.frameElement).outerHeight();
            var framewidth = $(context.window.frameElement).outerWidth();


            /*      **** GCS main 에서만 유효 하므로 일반 적인 페이지에서 offset 을 유지 ***
                    if ((frameheight - (target.outerHeight() + target.offset().top)) < 200) {
                      offset.top = target.offset().top - 200;
                    }
                    
                    
                    if ((framewidth - offset.left) < 260) {
                      offset.left = ( offset.left + target.outerWidth()) - 260;
                    }
            */
            if (!calendar) {
                $(context.document).find("body").append('<div id="ui-ez-datepicker" style="z-Index:9999;backgroud-color:#fff;border:1px solid #bcbcbc;width:260px;position:absolute;left:' + offset.left + 'px;top:' + offset.top + 'px;background:#fff;height:auto;vertical-align:text-top;"></div>');
            }

            $(context.document).find("body").find("#ui-ez-datepicker").on('mouseover', function (e) {
                isIn = true;
            });
            $(context.document).find("body").find("#ui-ez-datepicker").on('mouseout', function (e) {
                isIn = false;
            });

            /// <summary>EZ_Calendar 를 생성하기 위한 json 개체 입니다.</summary>
            var data = {
                "context": context,
                "culture": culture,
                "displayDate": selectedDate,
                "isEmbedded": true,
                "toDay": new Date(),
                "selectedDate": selectedDate,
                "wrap": $(context.document).find("body").find("#ui-ez-datepicker")
            };

            calendar = new EZ_Calendar(data);

            calendar.DisplayDateChanged = function (sender, e) {
                target.focus();
            }
            calendar.DisplayModeChanged = function (sender, e) {
                target.focus();
            }

            /// <summary>EZ_Calendar 의 SelectedChanged 와 EZ_DatePicker 의 SelectedDateChanged 를 연결 합니다.</summary>
            calendar.SelectedChanged = function (sender, e) {
                selectedDate = e.SelectedDate;

                target.val(selectedDate.toLocaleString(culture, dateFormat));

                if (self.SelectedDateChanged) {
                    self.SelectedDateChanged(this, { SelectedDate: e.SelectedDate });
                }

                OnCalendarClosed(self, {});
            }
        } else {

            OnCalendarClosed(this, {});
        }
    }

    /// <summary>날짜가 표시되는 input type text 개체를 가져옵니다.</summary>
    this.GetTextBox = function () {
        return context.$("#" + inputId);
    }

    /// <summary>현재 선택된 시간 정보를 가져옵니다.</summary>
    this.GetDatetime = function () {
        return selectedDate
    }

    /// <summary>현재의 시간을 세팅 합니다.</summary>
    this.SetDateTime = function (value) {

        selectedDate = value;

        if (value) {
            context.$("#" + inputId).val(selectedDate.toLocaleString(culture, dateFormat));
        }
        else {
            context.$("#" + inputId).val("");
        }
    }    
    Init(this);
}


/// <summary>EZ_DatePicker 를 생성하여 반환 합니다.</summary>
/// <param name="inputId" type="String">날짜가 표시될 input type text element 의 ID</param>
/// <param name="targetId" type="String">click 이벤트에 반응할 input type img element 의 ID</param>
/// <param name="selectedDate" type="String">최초로 표시될 날짜 값</param>
/// <param name="type" type="String">EZ_DatePicker 에서 표시될 날짜 유형(EZ_Common . DataFormat)</param>
function DatePickerCreate(inputId, targetId, selectedDate, type) {
    var datePicker = new EZ_DatePicker({
        "context": self,
        "inputId": inputId,
        "triggerId": targetId,
        "selectedDate": selectedDate,
        "culture": "ko-KR",
        "dateFormat": DateFormat.longDateFormat //(type) ? type : DateFormat.longDateFormat
    });

    //Object.defineProperty(datePicker, "SelectedDate", {
    //    set: function (value) {
    //        this.SetDateTime(value)
    //    },
    //    get: function () {
    //        return this.GetDatetime();
    //    }
    //});

    return datePicker;
}