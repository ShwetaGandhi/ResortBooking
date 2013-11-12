app.controller("feedbackController", function($scope,$location,$http,$rootScope,$cookieStore){
	
	/*
	$http.post("/ResortBooking/showcommentServlet")
	.success(function(result){
alert("i am in  feed back controller");
		$scope.results = result;
	$rootScope.results = result;
	console.log(result);
	//$location.path("/ViewRooms");
})
.error(function(result){
	alert(result+"result");
});*/

	$scope.submit=function(){
		
		$scope.data = {
				name:$scope.Person,
				email:$scope.emailaddress,
				rate:$scope.rating,
				comment:$scope.suggestions
		};
		$http.post("/ResortBooking/feedbackServlet",
				JSON.stringify($scope.data))
				.success(function(result){
					//console.log("result"+result);
					$scope.results = result;
					$rootScope.results = result;
					$location.path("/FeedBack");
				})
				
				
				.error(function(result){
					alert(result+"result");
				});
		
		
	};
});