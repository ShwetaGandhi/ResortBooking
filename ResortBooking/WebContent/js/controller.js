//mydirectives is custom directive made for inserting youtube videos
var app = angular.module('app',['ngCookies','mydirectives']);

app.controller("mainController", function($rootScope,$http, $scope) {
	//alert ("hello");
	$http.post("/ResortBooking/showcommentServlet")
				.success(function(result){
//alert("i am in controller");
					$scope.results = result;
				$rootScope.results = result;
				console.log(result);
				//$location.path("/ViewRooms");
			})
			.error(function(result){
				alert(result+"result");
			});
});

app.controller("indexController",function($scope,$rootScope,$cookieStore){
	var userEmail = $cookieStore.get('email');
	 $rootScope.emailResult=$cookieStore.get('email');
	 if (userEmail != null){
		 $rootScope.login = "Logout";
	 }else{
		 $rootScope.login = "Login";
	 }
	
	$scope.showNgHide=false;
	$rootScope.hideNgView=function(){
		$scope.showNgHide=true;
		
	};
	$rootScope.showNgView=function(){
		$scope.showNgHide=false;
		
	};
});






/*app.controller('detailController',['$scope', '$routeParams',function($scope, $routeParams){
	$scope.room=$routeParams.room;
	}]);
*/


/*app.controller('mainController',function mainController($scope, $location, $cookieStore, UserService ){
	
	 $scope.checkLogin = function() {
		    UserService.user = $cookieStore.get('user');

		    if (!UserService.user || !UserService.user.isLogged)
		      $location.path("/home");

		  };

		  $scope.checkLogin();

		  $scope.logout = function() {
		    UserService.user = {
		      isLogged: false,
		      username: '',
		      firstname: '',
		      lastname: ''
		    };
		    $location.path("/Login");
		    $cookieStore.remove('user');
		  };
		      
		  $scope.$on("$locationChangeStart", function(){        
		      $cookieStore.remove('user');
		  });
	 	
	
});

app.factory('UserService', function() {
	  return {
	    user: {
	      isLogged: false,
	      username: '',
	      firstname: '',
	      lastname: ''
	    }
	  };
	});

	app.controller('loginCtrl',function loginCtrl($scope, $location, $cookieStore, UserService) {

	  $scope.login = function() {
	    
	    if ($scope.username == "aaa" && $scope.psw == "bbb") {
	    UserService.user.isLogged = true;
	    UserService.user.username = $scope.username;
	    UserService.user.firstname = 'myFirstName';
	    UserService.user.lastname = 'myLastName';
	    $cookieStore.put('user', UserService.user);
	    $location.path("/ViewRooms");
	    }
	    
	  };
	});
*/

app.controller("signupCtrl",['$scope','$location', function signupCtrl($scope, $location) {
		
		//check for input type
		$scope.Ctrl = function(){
			$scope.name = 'guest';
			$scope.word = /^\s*\w*\s*$/; 
			};
		/*ccn is name from html file the ng-model name and creditcardnumber is name from roomdetails.java*/	
			$scope.addUser = function() {
				$scope.data = {

						name: $scope.name,
						password: $scope.id,
						email:$scope.email,
						creditcardnumber: $scope.ccn,
						address: $scope.address,
						age: $scope.age
			};
				console.log(JSON.stringify($scope.data));
				$.ajax({
					type: "POST",
					url: "/ResortBooking/SaveUser",
					dataType: 'json',
					async: false,
					data: JSON.stringify($scope.data),
					success:function(result) {
						//alert("sucess");
						
						//$location.path("/ViewRooms");
						$location.path("/confirmation");
						
					},
					error:function(data) {
						alert(data);
					}
				});
			};
		}]);




app.controller('diningController',['$scope','$http',function diningController($scope, $http){
	//alert ("hello");
	$http.get('json/dining.json').success(function(data){
		$scope.dining = data;
		//console.log($scope.dining);
	});
	 $scope.orderProp = 'age';
}]);
