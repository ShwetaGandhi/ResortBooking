app.controller("mailController", function($scope,$http,$location,$rootScope,$cookieStore) {
	$scope.mail=function(){
		$http.post("/ResortBooking/MailServlet",
		        JSON.stringify($rootScope.data))        
		        .success(function(result) {
		        	$location.path("/Home");
		        	
		        	})
		        .error(function(data2) {
		               alert(data+"data");
});
	}
});