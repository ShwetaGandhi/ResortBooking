'use strict';

var hotelData = angular.module('hotelData',['app']);

hotelData.config(['$routeProvider',
                  function($routeProvider) {
	$routeProvider.
	when('/Home',{
		templateUrl:'html/welcome.html',
		controller: 'mainController'
	}).
	
	when('/confirmation',{
		templateUrl:'html/confirmation.html'
	}).
	when('/Save',{
		templateUrl:'html/finalreservation.html',
		controller:'finalController'
	}).
	when('/FeedBack',{
		templateUrl:'html/feedback.html',
		controller:'feedbackController'
	}).
	when('/ViewRooms',{
		templateUrl:'html/viewrooms.html',
		controller: 'viewController'
	}).
	when('/Save',{
		templateUrl:'html/finalreservation.html',
		controller:'finalController'
	}).
	when('/ViewRooms/:room',{
		templateUrl:'html/viewroomsdetail.html',
		controller: 'detailController'
	}).
	when('/Dining',{
		templateUrl:'html/dining.html',
		controller: 'diningController'
	}).
	when('/Update',{
		templateUrl:'html/update.html',
		controller: 'updateController'
	}).
	when('/signin',{
		templateUrl:'html/signin.html',
		controller: 'signin'
	}).
	when('/Login',{
		templateUrl:'html/login.html',
		controller: 'loginCtrl'
	}).
	when('/Logout', {
		templateUrl: 'html/welcome.html',
		controller: 'logoutCtrl'
	}).
	when('/SignUp',{
		templateUrl:'html/signup.html',
		controller: 'signupCtrl'
	}).
	when('/Aboutus',{
		templateUrl:'html/aboutus.html',
		controller:'aboutusController'
	}).
	otherwise({redirectTo:'/Home'
	});
}]);