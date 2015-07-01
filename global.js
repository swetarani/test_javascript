var wizrocket = {event:[], profile:[], account:[], enum:function(e){return '$E_' + e}};
wizrocket.account.push({"id": "WWW-WWW-WWWZ"});
(function () {
	var wzrk = document.createElement('script');
	wzrk.type = 'text/javascript';
	wzrk.async = true;
	wzrk.src = ('https:' == document.location.protocol ? 'https://d2r1yp2w7bby2u.cloudfront.net' : 'http://static.wizrocket.com') + '/js/a.js';
	var s = document.getElementsByTagName('script')[0];
	s.parentNode.insertBefore(wzrk, s);
})();

var push_add_to_cart = function(){
	var add_to_cart = [];
 	add_to_cart.push("Added To Cart",{
 		"Product name" : product_title,
 		"Category" : product_category_name,
 		"Price" : product_price,
 		"Currency" : currency
 	})
 	wizrocket.event = add_to_cart;
	alert(JSON.stringify(wizrocket.event));
}
document.getElementsByName("add")[0].onclick = push_add_to_cart;


var push_product_viewed = function(){
	var p = [];
	p.push("Product_viewed",{
 		"Product name" : product_title,
 		"Category" : product_category_name,
 		"Price" : product_price,
 		"Currency" : currency
 	});
 	alert("p");
 	wizrocket.event = p;
	alert(JSON.stringify(wizrocket.event));
}

var ref = window.location.href
var res = ref.match(/products/gi);
alert (res)
if (res != null){
	alert ("its product page");
	push_product_viewed;
}
