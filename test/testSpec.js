Screw.Unit(function() {
	
	describe("JS Test Runner", function(){
		
		it("should work", function(){
			expect($("#test_message").text()).to(equal, "test is ok!");
		});
	});
	
});