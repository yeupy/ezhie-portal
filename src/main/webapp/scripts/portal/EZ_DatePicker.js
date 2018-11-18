/// <summary>EZ_DatePicker �� ����(������) �մϴ�.</summary>
/// <param name="json" type="json">��ü�� �����ϱ� ���� ������ ���Ե� json ��ü �Դϴ�.</param>
var EZ_DatePicker = function (json) {
    var calendar = null;                                    // EZ_DatePicker ���� ȭ�鿡 ǥ�õ� EZ_Calendar
    var context = (json.context) ? json.context : window;   // EZ_DatePicker �� ������ window
    var dateFormat = json.dateFormat                        // EZ_DatePicker ���� ǥ�õ� ��¥ ����(EZ_Common . DataFormat)

    var inputId = json.inputId;                             // ��¥�� ǥ�õ� input type text element �� ID
    var triggerId = json.triggerId;                         // click �̺�Ʈ�� ������ input type img element �� ID
    var selectedDate = json.selectedDate;                   // ���ʷ� ǥ�õ� ��¥ ��
    var culture = json.culture;                             // ���� ��ȭ��
    var isIn = false;                                       // EZ_Calendar �� display ����

    this.CalendarClosed = (json.calendarClosed) ? json.calendarClosed : null;                   // EZ_Calendar �� ������ �߻��ϴ� �̺�Ʈ
    this.SelectedDateChanged = (json.selectedDateChanged) ? json.selectedDateChanged : null;;   // EZ_Calendar ���� ��¥�� �������� �� �߻��ϴ� �̺�Ʈ

    /// <summary>EZ_Calendar �� ������ �߻��ϴ� �̺�Ʈ�� ����</summary>
    var OnCalendarClosed = function (sender, e) {
        $(context.document).find("body").find("#ui-ez-datepicker").remove();
        calendar = null;

        if (sender.CalendarClosed) {
            sender.CalendarClosed(sender, e);
        }
    }

    /// <summary>EZ_DatePicker ���� �⺻������ �����ϰ� focusout / click �̺�Ʈ�� ���� �մϴ�.</summary>
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

    /// <summary>EZ_DatePicker �̸��� ���ڿ��� ��ȯ �մϴ�.</summary>
    this.toString = function () {
        return "EZ_DatePicker";
    }

    /// <summary>EZ_Calendar �� ȭ�鿡 ǥ���ϰų� �����մϴ�.</summary>
    /// <param name="value" type="bool">EZ_Calendar �� ����(false) / ����(open) ����</param>
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


            /*      **** GCS main ������ ��ȿ �ϹǷ� �Ϲ� ���� ���������� offset �� ���� ***
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

            /// <summary>EZ_Calendar �� �����ϱ� ���� json ��ü �Դϴ�.</summary>
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

            /// <summary>EZ_Calendar �� SelectedChanged �� EZ_DatePicker �� SelectedDateChanged �� ���� �մϴ�.</summary>
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

    /// <summary>��¥�� ǥ�õǴ� input type text ��ü�� �����ɴϴ�.</summary>
    this.GetTextBox = function () {
        return context.$("#" + inputId);
    }

    /// <summary>���� ���õ� �ð� ������ �����ɴϴ�.</summary>
    this.GetDatetime = function () {
        return selectedDate
    }

    /// <summary>������ �ð��� ���� �մϴ�.</summary>
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


/// <summary>EZ_DatePicker �� �����Ͽ� ��ȯ �մϴ�.</summary>
/// <param name="inputId" type="String">��¥�� ǥ�õ� input type text element �� ID</param>
/// <param name="targetId" type="String">click �̺�Ʈ�� ������ input type img element �� ID</param>
/// <param name="selectedDate" type="String">���ʷ� ǥ�õ� ��¥ ��</param>
/// <param name="type" type="String">EZ_DatePicker ���� ǥ�õ� ��¥ ����(EZ_Common . DataFormat)</param>
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