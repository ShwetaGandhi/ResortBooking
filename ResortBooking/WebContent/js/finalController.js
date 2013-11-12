app.controller("finalController", function($scope,$http,$location,$rootScope,$cookieStore) {
	//if no room available it will not show reservation button
	$scope.showButton=false;
	$scope.hideButton=true;
	if($rootScope.availableRoom > 0){
		$scope.showButton=true;
		
	}
	else{
		$scope.hideButton=false;
	}
	
	$scope.checkback=function(){
		$location.path ("/ViewRooms");
	};
	$scope.finalreserve=function(){
		
		var userEmail = $cookieStore.get('email');
		$rootScope.emailResult = $cookieStore.get("email");
		$rootScope.data.email = userEmail;
		
		if(userEmail != null){
		
			$http.post("/ResortBooking/ReservedServlet",
		        JSON.stringify($rootScope.data))        
		        .success(function(result) {
		        	$location.path("/Home");
		           	})
		        .error(function(data2) {
		               alert(data+"data");
		        });
			}
		else{
			$location.path("/signin");
		}
	};
});