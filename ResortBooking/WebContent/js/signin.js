app.controller("signin", function($scope,$http,$location,$rootScope,$cookieStore) {
	
	$scope.validate = function() {
		$rootScope.hideNgView();
		$scope.data2= {
			email: $scope.eMail,
			password:$scope.password
			  };
		//alert($scope.eMail);
		$http.post("/ResortBooking/MailServlet",
		         JSON.stringify($scope.data2))  //converts js value to JSON string      
		           .success(function(result) {
		        	   var data3=result.email;
		        	   $cookieStore.put('email',result.email);
		        	   $rootScope.emailResult=data3;
		               $rootScope.login = "Logout";
		            	 
		   		  	   $location.path('/Save');	     	             
		           	   $rootScope.showNgView
		           	   
		           	   ();
		           	   //  alert("inside sucess");
		        	 })
		        	 .error(function(data2) {
		               alert(data+"data");
		             $rootScope.showNgView();
		             });
				  };
		  	});
