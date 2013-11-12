

app.controller('viewController',['$scope', function viewController($scope){
	$.ajax({
		type: "POST",
		url: "/HotelBooking/ViewRooms",
		dataType: 'json',
		async: false,
		data: "{}",
		success:function(result) {
		
		$scope.elements = result;
		//console.log($scope.elements);
		//console.log($scope.elements[0].imgurl);
		},
		error:function(data) {
			alert(data);
		}
	});
}]);
