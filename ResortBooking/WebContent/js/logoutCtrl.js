app.controller("logoutCtrl",function($cookieStore,$rootScope){
	$cookieStore.remove("email");
	//Login is my path name and it is coming from app.js where as login is varible coming from index page
	$rootScope.login = "Login";
	$rootScope.emailResult="";
});