app.controller("detailController",function($scope,$rootScope,$http,$location, $routeParams) {
	
	/*$scope.buttonshow=false;
	
	if($rootScope.availableRoom>0)
		{
		$scope.buttonshow = true;
		}*/
	
	$scope.room=$routeParams.room;
	
	//console.log($routeParams.weekday_pz+"prizeeeeeeeeeeeeee");
	
	 var dateToday = new Date();
		$("#datepicker").datepicker({ 
			altField: "#alternate",
			altFormat: "DD, d MM, yy",
			minDate: dateToday,
			onClose: function(selectedDate) {
				$("#datepicker1").datepicker( "option", "minDate", selectedDate );
				$scope.CheckIn = selectedDate;
				$scope.$apply();
			}
		});
	
     $("#datepicker1").datepicker({ 
       	 	altField: "#alt",
       	 	altFormat: "DD, d MM, yy",
       	 minDate: dateToday,
       	 	onClose: function(selectedDate) {
       	 	$("#datepicker").datepicker( "option", "maxDate", selectedDate );
       	 		$scope.CheckOut = selectedDate;
       	 		$scope.$apply();
       	 	}
      });
 	
      
    /* $('#calculate').click(function (){
     	//alert("hi");
     	 var a = $("#datepicker").datepicker('getDate').getTime(),
          b = $("#datepicker1").datepicker('getDate').getTime(),
          c = 24*60*60*1000, // converts date into miliseconds. 1000-1msec, 60sec-1min...
          diffDays = Math.round(Math.abs((a - b)/(c)));//abs convert -ve to +ve
      alert(diffDays);
     // alert($('#preview').text(99));
      
     
     	 
     });
 */

$scope.reserve= function() {
	
	var a = $("#datepicker").datepicker('getDate').getTime(),
    b = $("#datepicker1").datepicker('getDate').getTime(),
    c = 24*60*60*1000, // converts date into miliseconds. 1000-1msec, 60sec-1min...
    diffDays = Math.round(Math.abs((a - b)/(c)));//abs convert -ve to +ve
	alert("Making reservation for"+diffDays+"days");
	//var d = diffDays*weekday_pz;
	//alert (d+".......prize....");
	
	alert($scope.CheckIn+"checkin");

   alert($scope.CheckOut+"checkout");
   
   $rootScope.data={
			CheckIn:$scope.CheckIn,
			CheckOut:$scope.CheckOut,
			Room_type:$scope.room,
			email:""
			//weekday_pz:$scope.weekday_pz
	}

$http.post("/ResortBooking/ReservationServlet",
        JSON.stringify($rootScope.data))        
        .success(function(result) {
     	$rootScope.availableRoom = result. availableRoom;   	  
     	   $location.path("/Save");
     	   //console.log($rootScope.availableRoom+"...................");
     	  
        }).error(function(data) {
         
        	alert(data+"data");
          
          });



}
	
});