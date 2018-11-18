var EZ_Calendar = function (json) {

   
  var isMouseDown = false;
  var wasSelectedElement = null;
  var isEmbedded = json.isEmbedded;

  this.List = json.list || [];
  this.RowFixed = json.rowFixed || true;

  this.Rows = json.rows || 1;
  this.Cols = json.cols || 1;
  this.Context = json.context;
  this.ToDay = json.toDay || new Date();
  
  this.ID = "ez_calendar_" + new Guid().UniqueId(8);
  this.Wrap = json.wrap

  this.SelectedChanged = json.selectedChanged || function () { };
  this.Culture = json.culture;
  this.SelectedDate = json.selectedDate;
  this.PatternDays = json.patternDay || [];
  this.DisplayModeChanged = json.displayModeChanged;
  this.DisplayDate = json.displayDate;
  this.DisplayDateChanged = json.displayDateChanged;
  this.DisplayMode = CalendarMode.Month;
  this.RowHeight = json.rowHeight || 22;

  var getRelativeTime = function (culture) {

    if (culture == "ko-KR") {
      return {
        d: '일',
        m: '월',
        y: '년',
        months: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"]
      };
    }

    if (culture == "en-US") {
      return {
        d: '일',
        m: '월',
        y: '',
        months: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"]
      };
    }
  }
  
  var getWeekDays = function (culture) {

    if (culture == "ko-KR") {
      return [
        { Name: '일', headerClass: 'back_color01', bodyClass: 'back_color03' },
        { Name: '월' },
        { Name: '화' },
        { Name: '수' },
        { Name: '목' },
        { Name: '금' },
        { Name: '토', headerClass: 'back_color02', bodyClass: 'back_color05' }
      ];
    }

    if (culture == "en-US") {
      return [
        { Name: 'Su', headerClass: 'back_color01', bodyClass: 'back_color03' },
        { Name: 'Mo' },
        { Name: 'Tu' },
        { Name: 'We' },
        { Name: 'Th' },
        { Name: 'Fr' },
        { Name: 'Sa', headerClass: 'back_color02', bodyClass: 'back_color05' }
      ];
    }
  }

  var OnSelectedChanged = function (sender, e) {

    var date = $(e.target);
    var td = e.target;

    if (e.target.nodeName.toLowerCase() != "td") {

      td = $(e.target).parents("td")[0];
    }
   
    //if (td.dataset.issuedate == "") {
    //  return;
      //}

    if (td.getAttribute("data-issuedate") == "") {
        return;
    }

      //date = td.dataset.issuedate.split('-');
    date = td.getAttribute("data-issuedate").split('-');

    var year = parseInt(date[0]);
    var month = parseInt(date[1]) - 1;
    var day = parseInt(date[2]);

    if (sender.SelectedDate &&
        new Date(sender.SelectedDate.toString()).getFullYear() == year &&
        new Date(sender.SelectedDate.toString()).getMonth() == month &&
        new Date(sender.SelectedDate.toString()).getDate() == day) {
      return;
    }
   
    if (wasSelectedElement != null) {
      wasSelectedElement.find("div").eq(0).toggleClass("calendar_day_selected", false);
    }

    wasSelectedElement = $(td);
    wasSelectedElement.find("div").eq(0).toggleClass("calendar_day_selected", true);

    if (sender.SelectedChanged) {
        sender.SelectedDate = new Date(year, month, day);
        sender.SelectedChanged(sender, { SelectedDate: sender.SelectedDate });
    }
  }

  var initializeCompoent = function (sender, e) {
      
    $(sender.Context)
       .mouseup(function () {
           isMouseDown = false;
           
       });

    if (!sender.DisplayDate) {
      sender.DisplayDate = new Date();
    }

    var calendars = [];
    var index = 1;

    for (var row = 0; row < sender.Rows; row++) {

      var calendar = [];

      for (var col = 0; col < sender.Cols; col++) {
        calendar.push(sender.ID + "_month_" + (index++));
      }

      calendars.push(calendar);
    }

    var html = '' +
      '<table class="calendar_wrap noselect">' +
      '  <colgroup>' +
      '    <col style="width:100%">' +
      '  </colgroup>' +
      '  <tbody>' +
      '    <tr>' +
      '      <td>' +
      '        <table id="{{calendarId}}" style="width:100%;">' +
      '          <tbody>' +
      '          {{#each calendars}}' +
      '            <tr>' +
      '            {{#each this}}' +
      '             <td>' +
      '               <div id="{{this}}"' + (sender.Cols * sender.Rows > 1 ? ' style="margin:3px;"' : '') + '></div>' +
      '             </td>' +
      '            {{/each}}' +
      '            </tr>' +
      '          {{/each}}' +
      '          </tbody>' +
      '        </table>' +
      '      </td>' +
      '    </tr>' +
      '  </tbody>' +
      '</table>';

    var template = Handlebars.compile(html);
    
    sender.Wrap.html(template({
      "calendarId": sender.ID,
      "calendars": calendars
    }));
  
    sender.Open();
  }

  var OnDisplayDateChanged = function (sender, e) {

    sender.DisplayDate = new Date(e.displayDate);

    var displayDageChangedArgs = {
      DisplayDate: new Date(e.displayDate.getFullYear(), e.displayDate.getMonth(), 1),
      Cancel: false
    };

    if (sender.DisplayDateChanged != null) {
      sender.DisplayDateChanged(sender, displayDageChangedArgs);
    }

    if (e.Cancel) {
      return;
    }
    
    sender.Open();
  }

  this.Open = function () {

    var baseDate = {
        Year: new Date(this.DisplayDate.toString()).getFullYear(),
        Month: new Date(this.DisplayDate.toString()).getMonth(),
        FirstDate: new Date(new Date(this.DisplayDate.toString()).getFullYear(), new Date(this.DisplayDate.toString()).getMonth(), 1),
        LastDate: new Date(new Date(this.DisplayDate.toString()).getFullYear(), new Date(this.DisplayDate.toString()).getMonth() + 1, 0)
    }
   
    
    var index = 0;
    for (var row = 0; row < this.Rows; row++) {
        for (var col = 0; col < this.Cols; col++) {
        getMonthDays(this, {
            displayDate: new Date(this.DisplayDate.toString()).addMonths(index),
          target: this.Context.$('#' + this.ID + '_month_' + (++index)),
          baseDate: baseDate
        });
      }
    }

  }

  var getMonthDays = function (sender, e) {

    var toDay = sender.ToDay.toStandardDateString();
    
    var thisDate = {
        Year: new Date(e.displayDate.toString()).getFullYear(),
        Month: new Date(e.displayDate.toString()).getMonth(),
        FirstDate: new Date(new Date(e.displayDate.toString()).getFullYear(), new Date(e.displayDate.toString()).getMonth(), 1),
        LastDate: new Date(new Date(e.displayDate.toString()).getFullYear(), new Date(e.displayDate.toString()).getMonth() + 1, 0),
        DayOfWeek: new Date(new Date(e.displayDate.toString()).getFullYear(), new Date(e.displayDate.toString()).getMonth(), 1).getDay()
    }
    
    var prevMonth = new Date(thisDate.Year, thisDate.Month, thisDate.FirstDate.getDate() - 1);
    var nextMonth = new Date(thisDate.Year, thisDate.Month, thisDate.LastDate.getDate() + 1);

    var week = 6;
    var dw = 7;

    var day = 1;
    var date = null;
    var dateString = null;
    var prevDay = prevMonth.getDate() - thisDate.DayOfWeek + 1;
    var nextDay = nextMonth.getDate();
   
    var i = 0;

    var isPrevMonth = false;
    var isNextMonth = false;
    var isThisMonth = false;
    var weeks = [];
    var data = null;
    var bodycss = ""

    for (var week = 0; week < 6; week++) {

      var days = [];

      for (var dw = 0; dw < 7; dw++) {

        bodycss = "";

        if (thisDate.DayOfWeek > i) {
          isPrevMonth = true;
          isThisMonth = false;
          isNextMonth = false;

          if (isEmbedded) {

            date = new Date(prevMonth.getFullYear(), prevMonth.getMonth(), prevDay);
            dateString = date.toStandardDateString();
          }
          else {
            date = null;
            dateString = null;
          }
          data = null;
          prevDay++;
        } else if ((thisDate.LastDate.getDate() + (thisDate.FirstDate.getDay() - 1)) < i) {
          isPrevMonth = false;
          isThisMonth = false;
          isNextMonth = true;

          if (isEmbedded) {
            date = new Date(nextMonth.getFullYear(), nextMonth.getMonth(), nextDay);
            dateString = date.toStandardDateString();
          }
          else {
            date = null;
            dateString = null;
          }

          data = null;

          nextDay++;
        } else {

          isPrevMonth = false;
          isThisMonth = true;
          isNextMonth = false;

          date = new Date(thisDate.Year, thisDate.Month, day);
          dateString = date.toStandardDateString();
          data = sender.List[dateString];

          if (sender.PatternDays.indexOf(dateString) > -1) {
            bodycss += "calendar_pattern ";
          }

          day++;
        }

        if (toDay == dateString) {
          bodycss += "calendar_today ";
        }

        days.push({
          "isdisabled": isPrevMonth || isNextMonth,
          "day": date == null ? "" : date.getDate(),
          "date": dateString,
          "isdata": data != undefined,
          "datas": data,
          "bodycss": bodycss,
          "height": sender.RowHeight,
          "cheight": sender.RowHeight - 2
        });

        i++;
      }

      weeks.push(days);
    }

    

    var html = '' +
      '<div class="month_select">' +
      '  <table style="width:100%;margin-top:3px;margin-bottom:3px;vertical-align:top;">' +
      '    <colgroup>' +
      '      <col style="width:20px;" />' +
      '      <col/>' +
      '      <col style="width:20px;" />' +
      '    </colgroup>' +
      '    <tbody>' +
      '    <tr>' +
      '      <td class="month_left_btn"><img src="../images/main/month_left_btn.gif" /></td>' +
      '      <td class="month_view"><p>{{ month }}</p></td>' +
      '      <td class="month_right_btn"><img src="../images/main/month_right_btn.gif" /></td>' +
      '    </tr>' +
      '    </tbody>' +
      '  </table>' +
      '</div>' +
      '<div class="calendar_day_zone">' +
      '  <table style="width:100%;vertical-align:top;">' +
      '    <colgroup>' +
      '      <col style="width:15%">' +
      '      <col style="width:14%">' +
      '      <col style="width:14%">' +
      '      <col style="width:14%">' +
      '      <col style="width:14%">' +
      '      <col style="width:14%">' +
      '      <col style="width:15%">' +
      '    </colgroup>' +
      '    <thead>' +
      '      <tr>' +
      '        {{#each weekday}}' +
      '        <th class="{{headercss}}">{{ Name }}</th>' +
      '        {{/each}}' +
      '      </tr>' +
      '    </thead>' +
      '    <tbody>' +
      '      {{#each week}}' +
      '      <tr>' +
      '        {{#each this}}' +
      '        <td data-issuedate="{{date}}" class="{{bodycss}}" style="height:{{ height }}px;">' +
      '          <div class="{{ on_class }}" style="height:{{ cheight }}px;">' +
      '            {{#if isdisabled}}' +
      '            <strong class="off">{{ day }}</strong>' +
      '            {{else }}' +
      '            <strong>{{ day }}</strong>' +
      '            {{/if}}' +
      '            {{#if isdata}}' +
      '            <div class="content" style="width:100%;">' +
      '            {{#each datas}}' +
      '            <p title="{{ this }}">{{ this }}</p>' +
      '            {{/each}}' +
      '            </div>' +
      '            {{/if}}' +
      '          </div>' +
      '        </td>' +
      '        {{/each}}' +
      '      </tr>' +
      '      {{/each}}' +
      '    </tbody>' +
      '  </table>' +
      '</div>';
    
    var template = Handlebars.compile(html);

    e.target.html(template({
      "monthId": e.monthId,
      "month": e.displayDate.toLocaleString(sender.Culture, DateFormat.shortDateFormat),
      "week": weeks,
      "weekday": getWeekDays(sender.Culture)
    }));

   
    if (sender.SelectedDate != null &&
        e.displayDate.getFullYear() == new Date(sender.SelectedDate.toString()).getFullYear() &&
        new Date(e.displayDate.toString()).getMonth() == new Date(sender.SelectedDate.toString()).getMonth()
        )
    {
        var dateString = new Date(sender.SelectedDate.toString()).toStandardDateString();
        var selectedElement = e.target.find('td[data-issuedate="' + dateString + '"]');

        if (selectedElement != undefined) {
            wasSelectedElement = $(selectedElement[0]);
            wasSelectedElement.find("div").eq(0).toggleClass("calendar_day_selected", true);
        }
    }   

    e.target.find(".month_select").find("img").eq(0).bind("click", function () {
      OnDisplayDateChanged(sender, { displayDate: prevMonth });
    });

    e.target.find(".month_select").find("img").eq(1).bind("click", function () {
      OnDisplayDateChanged(sender, { displayDate: nextMonth });
    });
    
    e.target.find(".calendar_day_zone").find("td").bind("click", function (event) {
      OnSelectedChanged(sender, event);
    });

    e.target.find(".month_select").find("p").eq(0).bind("click", function (event) {
      if (isEmbedded) {
        getYears(sender, { target: e.target, displayDate: sender.SelectedDate ? sender.SelectedDate : e.displayDate });
      }
    });
  }

  var getYears = function (sender, e) {

    OnDisplayModeChanged(sender, e);

    var style = '' +
    '<div class="month_select">' +
    '  <table style="width:100%;margin-top:3px;margin-bottom:3px;vertical-align:top;">' +
    '      <colgroup>' +
    '        <col style="width:20px;" />' +
    '        <col/>' +
    '        <col style="width:20px;" />' +
    '      </colgroup>' +
    '      <tr>' +
    '        <td class="month_left_btn"><img src="../images/main/month_left_btn.gif" /></td>' +
    '        <td class="month_view"><p>{{ month }}</p></td>' +
    '        <td class="month_right_btn"><img src="../images/main/month_right_btn.gif" /></td>' +
    '      </tr>' +
    '    </table>' +
    '</div>' +
    '<div class="calendar_day_zone">' +
    '  <table style="width:100%;vertical-align:top;">' +
    '    <colgroup>' +
    '      <col style="width:25%">' +
    '      <col style="width:25%">' +
    '      <col style="width:25%">' +
    '      <col style="width:25%">' +
    '    </colgroup>' +
    '    <tbody>' +
    '      {{#each rows}}' +
    '      <tr>' +
    '        {{#each this}}' +
    '        <td data-issuedate="{{value}}" style="height:54px;text-align:center;vertical-align: middle;">' +
    '          {{#if on}}' +
    '            <strong style="display:inline-block; padding:19px 0; width:100%; font-weight:600 !important; border:1px solid red;">{{displayMonth}}{{text}}</strong>' +
    '          {{else }}' +
    '            <strong>{{text}}</strong>' +
    '          {{/if}}' +
    '          </div>' +
    '        </td>' +
    '        {{/each}}' +
    '      </tr>' +
    '    {{/each}}' +
    '    </tbody>' +
    '  </table>' +
    '</div>';

    var prevYear = new Date(e.displayDate.getFullYear() - 1, e.displayDate.getMonth(), e.displayDate.getDate());
    var nextYear = new Date(e.displayDate.getFullYear() + 1, e.displayDate.getMonth(), e.displayDate.getDate());

    var relativeTime = getRelativeTime(sender.Culture);

    var template = Handlebars.compile(style);

    var html = template({
      "month": e.displayDate.toLocaleString(sender.Culture, DateFormat.shortDateFormat),
      "rows": [
        [
          { value: 0, text: relativeTime.months[0], on: e.displayDate.getMonth() == 0 },
          { value: 1, text: relativeTime.months[1], on: e.displayDate.getMonth() == 1 },
          { value: 2, text: relativeTime.months[2], on: e.displayDate.getMonth() == 2 },
          { value: 3, text: relativeTime.months[3], on: e.displayDate.getMonth() == 3 }],
        [
          { value: 4, text: relativeTime.months[4], on: e.displayDate.getMonth() == 4 },
          { value: 5, text: relativeTime.months[5], on: e.displayDate.getMonth() == 5 },
          { value: 6, text: relativeTime.months[6], on: e.displayDate.getMonth() == 6 },
          { value: 7, text: relativeTime.months[7], on: e.displayDate.getMonth() == 7 }],
        [
          { value: 8, text: relativeTime.months[8], on: e.displayDate.getMonth() == 8 },
          { value: 9, text: relativeTime.months[9], on: e.displayDate.getMonth() == 9 },
          { value: 10, text: relativeTime.months[10], on: e.displayDate.getMonth() == 10 },
          { value: 11, text: relativeTime.months[11], on: e.displayDate.getMonth() == 11 }]
      ]
    });

    sender.DisplayMode = CalendarMode.Year;

    e.target.html(html);

    e.target.find(".month_select").find("img").eq(0).bind("click", function () { getYears(sender, { target: e.target, displayDate: prevYear }); });
    e.target.find(".month_select").find("img").eq(1).bind("click", function () { getYears(sender, { target: e.target, displayDate: nextYear }); });
    e.target.find(".month_select").find("p").eq(0).bind("click", function (event) {
      getDecades(sender, { target: e.target, displayDate: e.displayDate });
    });
    e.target.find(".calendar_day_zone").find("td").bind("click", function (event) {
      var displayDate = new Date(e.displayDate.getFullYear(), $(event.currentTarget).data("issuedate").toString().padLeft(2, '0'), 1);

      var lastDate = new Date(e.displayDate.getFullYear(), $(event.currentTarget).data("issuedate"), 0).getDate();

      if (e.displayDate.getDate() > lastDate)
        displayDate.setDate(lastDate);
      else
        displayDate.setDate(e.displayDate.getDate());

      var baseDate = {
        Year: displayDate.getFullYear(),
        Month: displayDate.getMonth(),
        FirstDate: new Date(displayDate.getFullYear(), displayDate.getMonth(), 1),
        LastDate: new Date(displayDate.getFullYear(), displayDate.getMonth() + 1, 0)
      }

      getMonthDays(sender, { target: e.target, displayDate: displayDate, baseDate: baseDate });

    });
  }

  var getDecades = function (sender, e) {

    OnDisplayModeChanged(sender, e);

    var style = '' +
    '<div class="month_select">' +
    '  <table style="width:100%;margin-top:3px;margin-bottom:3px;vertical-align:top;">' +
    '    <colgroup>' +
    '      <col style="width:20px;" />' +
    '      <col/>' +
    '      <col style="width:20px;" />' +
    '    </colgroup>' +
    '    <tr>' +
    '      <td class="month_left_btn"><img src="../images/main/month_left_btn.gif" /></td>' +
    '      <td class="month_view"><p>{{ month }}</p></td>' +
    '      <td class="month_right_btn"><img src="../images/main/month_right_btn.gif" /></td>' +
    '    </tr>' +
    '  </table>' +
    '</div>' +
    '<div class="calendar_day_zone">' +
    '  <table style="width:100%;vertical-align:top;">' +
    '    <colgroup>' +
    '      <col style="width:25%">' +
    '      <col style="width:25%">' +
    '      <col style="width:25%">' +
    '      <col style="width:25%">' +
    '    </colgroup>' +
    '    <tbody>' +
    '      {{#each rows}}' +
    '      <tr>' +
    '        {{#each this}}' +
    '        <td data-issuedate="{{value}}" style="height:54px;text-align:center;vertical-align: middle;">' +
    '          <div>' +
    '            <strong>{{value}}{{text}}</strong>' +
    '          </div>' +
    '        </td>' +
    '        {{/each}}' +
    '      </tr>' +
    '    {{/each}}' +
    '    </tbody>' +
    '  </table>' +
    '</div>';

    var relativeTime = getRelativeTime(sender.Culture);
    var template = Handlebars.compile(style);

    var year = Math.floor(parseFloat(e.displayDate.getFullYear()) / 10.0) * 10;

    var html = template({
      "month": year + relativeTime.y + "-" + (year + 9) + relativeTime.y,
      "rows": [
        [
          { value: year - 1, text: relativeTime.y, on: false },
          { value: year + 0, text: relativeTime.y, on: false },
          { value: year + 1, text: relativeTime.y, on: false },
          { value: year + 2, text: relativeTime.y, on: false }],
        [
          { value: year + 3, text: relativeTime.y, on: false },
          { value: year + 4, text: relativeTime.y, on: false },
          { value: year + 5, text: relativeTime.y, on: false },
          { value: year + 6, text: relativeTime.y, on: false }],
        [
          { value: year + 7, text: relativeTime.y, on: false },
          { value: year + 8, text: relativeTime.y, on: false },
          { value: year + 9, text: relativeTime.y, on: false },
          { value: year + 10, text: relativeTime.y, on: false }]
      ]
    });

    e.target.html(html);

    e.target.find("img").eq(0).bind("click", function () { getDecades(sender, { target: e.target, displayDate: new Date(year - 1, e.displayDate.getMonth(), e.displayDate.getDate()) }) });
    e.target.find("img").eq(1).bind("click", function () { getDecades(sender, { target: e.target, displayDate: new Date(year + 10, e.displayDate.getMonth(), e.displayDate.getDate()) }) });

    e.target.find(".calendar_day_zone").find("td").bind("click", function (event) {
      var displayDate = new Date($(event.currentTarget).data("issuedate"), e.displayDate.getMonth(), e.displayDate.getDate());

      var baseDate = {
        Year: displayDate.getFullYear(),
        Month: displayDate.getMonth(),
        FirstDate: new Date(displayDate.getFullYear(), displayDate.getMonth(), 1),
        LastDate: new Date(displayDate.getFullYear(), displayDate.getMonth() + 1, 0)
      }

      getYears(sender, { target: e.target, displayDate: displayDate, baseDate: baseDate });

    });
  }

  var OnDisplayModeChanged = function (sender, e) {
    if (sender.DisplayModeChanged) {
      sender.DisplayModeChanged(sender, e);
    }
  }

  initializeCompoent(this);
  
}