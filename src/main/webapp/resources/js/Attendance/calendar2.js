
 /* 
1.모든 이벤트 제거
2.이벤트 소스추가
3.랜더링 이벤트 

refetch event 모든소스의 이벤트를 다시가져와 화면에 렌더

다시생성은 .fullCalendar ( 'addEventSource', events

var Calendar = FullCalendar.Calendar;
var Draggable = FullCalendarInteraction.Draggable;
var containerEl = document.getElementById('external-events');
var calendarEl = document.getElementById('calendar');
*/

	$(".namecal").on("click", function() {
		
		console.log($(this).children('td[name="empno"]').text());
		var empno = $(this).children('td[name="empno"]').text();
		$ .ajax ({
			url : "/Annual/annualUser.do",
			dataType: "json",
			data:{
				empno:empno
				},
			success : function (data) {
			let test = JSON.parse (data);
			
			console.log (test);
			$ ( '# fullCalendar'). fullCalendar ( 'removeEvents',test);// 데이터 제거 
			$ ( '# fullCalendar'). fullCalendar ( 'rerenderEvents',test);//새이벤트 업데이트 
			$ ( '# fullCalendar'). fullCalendar ( 'refetchEvents',test);//최신이벤트 가져오기 
			
		
		}
	});	
	//calendar.addEvent( event [, source] )
	//calendar.refetchEvents()
	//extrapram >> 동적,.,,?

});
	//db일정 받아오기 
	var eventFeed = function(info, successCallback,failureCallback,empno) {
		$.ajax({
			type: "GET",
			url: "/Annual/calendarList.do",
			dataType: "json",
			success: function(data) {
				successCallback(data);
				console.log(data);
			},
			error: function(xhr) {
				console.log(xhr.status)
			}
		});
	}
//.fullCalendar( ‘renderEvent’, event [, stick ] )
// $("#calendar").fullCalendar('renderEvent', { "empno",empno}


document.addEventListener('DOMContentLoaded', function() {

	var calendarEl = document.getElementById('calendar');


	var calendar = new FullCalendar.Calendar(calendarEl,{

			

			headerToolbar: {
				left: 'prev,next today',
				center: 'title',
				right: 'custom,dayGridMonth,timeGridWeek,timeGridDay,listWeek'
			},
			buttonText: {
				today: '오늘',
				month: '월',
				week: '주',
				day: '일',
				list: '주간 일정표'
			},

			//initialDate : '2020-09-12', // 캘린더 지정날짜로 보이게하기 
			navLinks: true, // can click day/week names to navigate views
			nowIndicator: true,
			weekNumbers: true,
			weekNumberCalculation: 'ISO',
			editable: true,
			selectable: true,
			dayMaxEvents: true, // allow "more" link when too many events
			/*events:[
				{
					
					  title: '휴가',
					  start: '2021-01-07',
					  end: '2021-01-10'
					} 

				]*/
			events: eventFeed

		});
	calendar.render();
	calendar.refetchEvents();

});
