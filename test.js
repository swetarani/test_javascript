var settings = {
    "async": true,
	"crossDomain": true,
	"url": "https://test-store-1017.myshopify.com/admin/themes/33274243/assets.json",
	"method": "PUT",
	"headers": {
		"content-type": "application/json",
		"x-shopify-access-token": "480f6d63072def2fb625e753e116a034"
	},
	"processData": false,
	"data": "{\r\n    \"asset\": {\r\n    \"key\": \"layout\\/alternate.liquid\",\r\n    \"source_key\": \"layout\\/theme.liquid\"\r\n        \r\n    }\r\n}"
}

$.ajax(settings).done(function (response) {
	console.log(response);
});
