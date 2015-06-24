var settings = {
	"async": true,
	"crossDomain": true,
	"url": "https://test-store-1017.myshopify.com/admin/script_tags.json",
	"method": "PUT",
	"headers": {
		"content-type": "application/json",
		"x-shopify-access-token": "480f6d63072def2fb625e753e116a034"
		
	},
	"processData": false,
	"data": "{\r\n    \"script_tag\": {\r\n    \"event\": \"onload\",\r\n    \"src\": \"https://raw.githubusercontent.com/swetarani/test_javascript/master/test.js\"\r\n        \r\n    }\r\n}"
	
}

$.ajax(settings).done(function (response) {
	console.log(response);
});
