var MessageBoxButton = {
    OK: "Ok",
    OKCancel: "OKCancel",
    YesNoCancel: "YesNoCancel",
    YesNo: "YesNo"
}

var MessageBoxImage = {
    MSG_TYPE_EXCLAMATION: "MSG_TYPE_EXCLAMATION",
    MSG_TYPE_QUESTION: "MSG_TYPE_QUESTION",
    MSG_TYPE_ERROR: "MSG_TYPE_ERROR",
    MSG_TYPE_INFORMATION: "MSG_TYPE_INFORMATION"
}

var MessageBoxResult = {
    Ok: "Ok",
    Cancel: "Cancel",
    Yes: "Yes",
    No: "No"
}

var CalendarMode = {
    Month: "Month",
    Year: "Year",
    Decade: "Decade"
}

var DataGridSelectionMode = {
    Extended: "Extended",
    Single: "Single"
}

var DataGridSelectionUnit = {
    Cell: "Cell",
    FullRow: "FullRow",
    CellOrRowHeader: "CellOrRowHeader"
}

var Guid = function () {

    this.UniqueId = function (length) {
        var arr = ["a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", 1, 2, 3, 4, 5, 6, 7, 8, 9];
        var str = "";
        for (var i = 0, j = length; i < j; i++) {
            var idx = Math.floor(Math.random() * (arr.length - 1));
            str += arr[idx];
        }

        return str;
    }
}

var DateDiff = function (datepart, fromDate, toDate) {

    switch (datepart) {
        case 'dd':
        case 'd':
            var diff = new Date(toDate - fromDate);
            return Math.ceil(diff / 1000 / 60 / 60 / 24);
        case 'mm':
        case 'm':
            var months;
            months = (toDate.getFullYear() - fromDate.getFullYear()) * 12;
            months -= fromDate.getMonth();
            months += toDate.getMonth();

            return months <= 0 ? 0 : months;
        default:
            break;
    }

    return null;

}

var DaysInMonth = function (date) {
    return new Date(date.getFullYear(), date.getMonth() + 1, 0).getDate();
}

var IsLeapYear = function (year) {
    return ((year % 4) == 0 && ((year % 100) != 0 || (year % 400) == 0));
}

var DateFormat = {
    shortDateFormat: { year: 'numeric', month: 'narrow' },
    longDateFormat: { year: 'numeric', month: 'short', day: 'numeric' },
    longDateTimeFormat: { year: 'numeric', month: 'short', day: 'numeric', hour: '2-digit', minute: '2-digit', second: '2-digit' },
    WeekDateFormat: { year: 'numeric', month: 'numeric', day: 'numeric', weekday: 'long' }
}

var DateMonths = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
var WeekDays = ["Sub", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];

Array.prototype.indexOf = function (item, from) {
    var len = this.length;
    for (var i = (from < 0) ? Math.max(0, len + from) : from || 0; i < len; i++) {
        if (this[i] === item) return i;
    }
    return -1;
}

Array.prototype.unique = function (key) {
    var a = [];
    var l = this.length;
    for (var i = 0; i < l; i++) {
        for (var j = i + 1; j < l; j++) {

            if (this[i][key] === this[j][key])
                j = ++i;
        }
        a.push(this[i]);
    }
    return a;
}

Array.prototype.Remove = function (property, value) {
    var source = this;

    $.each(source, function (index) {
        if (this[property] == value) {
            source.splice(index, 1);
        }
    });
}

Date.prototype.addDays = function (days) {
    var dat = new Date(this.valueOf());
    dat.setDate(dat.getDate() + days);
    return dat;
}

Date.prototype.addMonths = function (months) {

    var dat = new Date(this.getFullYear(), this.getMonth(), 1, this.getHours(), this.getMinutes(), this.getSeconds(), this.getMilliseconds());
    dat.setMonth(this.getMonth() + months);

    var lastDate = new Date(dat.getFullYear(), dat.getMonth() + 1, 0).getDate();

    if (this.getDate() > lastDate) {
        dat.setDate(lastDate);
    } else {
        dat.setDate(this.getDate());
    }

    return dat;
}

Date.prototype.Equals = function (compareValue) {
    return this.toStandardDateString() == compareValue.toStandardDateString();
}

Date.prototype.getDisplayMonth = function () {
    return this.getMonth() + 1;
}

Date.prototype.toAge = function (today) {
    var diff = today.getTime() - this.getTime();
    var daysPast = Math.floor(diff / (1000 * 60 * 60 * 24));
    return Math.floor(daysPast / 365.25);
}

Date.prototype.toJsonDateTime = function () {
    if (this == null) {
        return null;
    }

    var tmp = new Date(DateMonths[this.getMonth()] + ' ' + this.getDate() + ', ' + this.getFullYear() + ' ' + this.getHours() + ':' + this.getMinutes() + ':' + this.getSeconds() + ' GMT');

    var gmtString = tmp.toGMTString();

    return "/Date(" + Date.parse(gmtString) + ")/";
}

Date.prototype.toJsonDate = function () {
    if (this == null) {
        return null;
    }
    var tmp = new Date(DateMonths[this.getMonth()] + ' ' + this.getDate() + ', ' + this.getFullYear() + ' ' + '00:00:00 GMT');

    var gmtString = tmp.toGMTString();

    return "/Date(" + Date.parse(gmtString) + ")/";
}

Date.prototype.toStandardDateString = function () {
    return this.getFullYear() + '-' + (this.getMonth() + 1).toString().padLeft(2, '0') + '-' + this.getDate().toString().padLeft(2, '0')
}

Date.prototype.toLocaleString = function (culture, format) {

    var date = {
        year: this.getFullYear(),
        month: this.getDisplayMonth(),
        day: this.getDate(),
        hour: this.getHours(),
        minute: this.getMinutes(),
        second: this.getSeconds()
    }

    var string = "";

    if (culture == "ko-KR") {
        if (format.year)
            string += date.year;

        if (format.month) {
            if (format.month == "narrow")
                string += "년 " + date.month + "월";
            else
                string += "-" + date.month.toString().padLeft(2, '0');
        }

        if (format.day)
            string += "-" + date.day.toString().padLeft(2, '0');

        if (format.hour)
            string += " " + date.hour.toString().padLeft(2, '0');

        if (format.minute)
            string += ":" + date.minute.toString().padLeft(2, '0');

        if (format.second)
            string += ":" + date.second.toString().padLeft(2, '0');

    } else if (culture == "en-US") {

        if (format.month) {
            if (format.month == "narrow")
                string += DateMonths[this.getMonth()];
            else
                string += date.month;
        }

        if (format.day)
            string += "/" + date.day.toString().padLeft(2, '0');

        if (format.year) {

            if (format.month == "narrow")
                string += ", " + date.year;
            else
                string += "/" + date.year;


        }

        if (format.hour)
            string += " " + date.hour.toString().padLeft(2, '0');

        if (format.minute)
            string += ":" + date.minute.toString().padLeft(2, '0');

        if (format.second)
            string += ":" + date.second.toString().padLeft(2, '0');

    } else {
        return this.toStandardDateString();
    }

    return string;
}

String.prototype.replaceAll = function (regex, replacement) {
    return this.split(regex).join(replacement);
}

String.prototype.trim = function () {
    return this.replace(/(^\s*)|(\s*$)/g, "");
}

String.prototype.toDateTime = function () {
    if (!this) {
        return null;
    }

    var jsonStrArray = this.replace(/\/Date\((.*?)\)\//gi, '$1').split('+');

    var str = "/Date(" + jsonStrArray[0] + "+0000" + ")/"

    var date = new Date(str.match(/\d+/)[0] * 1);

    if (System.IsLocal() && jsonStrArray.length == 2) {
        return date;
    }

    var gmtString = date.toGMTString();

    var localeDate = new Date(gmtString + System.TimezoneOffset);

    return localeDate;
}

String.prototype.toDateTimeLocaleString = function (culture, isDate) {
    var date = this.toDateTime();

    if (isDate) {

        return date.toLocaleString(culture, DateFormat.longDateFormat);
    }

    return date.toLocaleString(culture, DateFormat.longDateTimeFormat);
}

String.prototype.toJsonDateTime = function () {
    var d = this.split('-');
    if (d[1].length == 1) {
        d[1] = "0" + d[1];
    }
    if (d[2].length == 1) {
        d[2] = "0" + d[2];
    }

    return "/Date(" + Date.parse(d[0] + "-" + d[1] + "-" + d[2]) + ")/";
}

String.prototype.Equals = function (value) {
    return this == value;
}

String.prototype.padLeft = function (n, str) {
    return Array(n - this.length + 1).join(str || '0') + this;
}

String.prototype.padRight = function (n, str) {
    return this + Array(n - this.length + 1).join(str || '0');
}

String.prototype.startsWith = function (prefix) {
    return this.indexOf(prefix) === 0;
}

String.prototype.endsWith = function (suffix) {
    return this.match(suffix + "$") == suffix;
};

String.prototype.isValdatationEmail = function () {
    var email = this;
    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}

String.prototype.toInt = function () {
    var data = this;
    if (data.toString().trim() == '') {
        data = 0;
        return data;
    }
    return parseInt(data);
}

var string = {
    isNullOrEmpty: function (obj) {
        if (obj == null || obj == undefined)
            return true;

        if (obj.toString().trim() == '')
            return true;

        return false;
    },
    isValidationKey: function (data) {
        var results = [];
        var isDuplicateKey = null;
        for (var j = 0; j < data.length; j++) {
            var key = data[j].toString();
            if (!results[key]) {
                results[key] = 1
            } else {
                isDuplicateKey = key;
                return true;
            }
        }
        return false;
    }
}

var System = function () {
    this.Domain = null;
    this.TimezoneOffset = null;
    this.Cal = null;
    this.CalTime = null;
    var islocal = false;

    var init = function (self) {

        var timeOffset = new Date().getTimezoneOffset();

        var offset = ((timeOffset < 0 ? '+' : '-') +
                  (parseInt(Math.abs(timeOffset / 60))).toString().padLeft(2, '0') +
                  (Math.abs(timeOffset % 60)).toString().padLeft(2, '0'));
        var cal = (timeOffset < 0 ? '+' : '-');
        var calTime = (parseInt(Math.abs(timeOffset / 60))).toString().padLeft(2, '0');


        self.TimezoneOffset = offset;
        self.Domain = document.domain;

        self.Cal = cal;
        self.CalTime = calTime;

        if (self.Domain == "localhost" || self.Domain == "127.0.0.1") {
            islocal = true;
        }
    }

    this.IsLocal = function () {
        return islocal;
    }
    init(this);
}

var System = new System();