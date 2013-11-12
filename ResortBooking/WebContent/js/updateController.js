app.controller("updateController", function($scope,$location,$http,$rootScope,$cookieStore){
	$scope.cancel = function(){
		var userEmail = $cookieStore.get("email");
	/*	$rootScope.emailResult = $cookieStore.get("email");
		$rootScope.data.email = userEmail;*/
		//for $scope.data we need to pass some value and to pass we have to declare and declaration contain data we are getting.
		//$scope.Reserve_id (name of my ng-model)in html file
		//Reserv_id : I used in my POJO file and I am creating obj and passing to query.
		$scope.data = {Reserv_id : $scope.Reserve_id};
		if (userEmail != null){
			$http.post("/ResortBooking/UpdateServlet",
					JSON.stringify($scope.data))
					.success(function(result){
						$location.path("/ViewRooms")
					})
					.error(function(result){
						alert(result+"result");
					});
			}
			else{
				$location.path("/signin");
			}
		};
		
	});