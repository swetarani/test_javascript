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
 	});
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
if (res == "products"){
	alert ("its product page");
	push_product_viewed;
}

var push_checkout = function(){
	var len = Shopify.checkout.line_items.length;
	var items = [];
	for (i=0; i<len; i++) {
		var obj = {};
	 	//alert(Shopify.checkout.line_items[i].title);
	 	obj["product_id"] = Shopify.checkout.line_items[i].product_id;
	 	obj["title"] = Shopify.checkout.line_items[i].title;
	 	obj["quantity"] = Shopify.checkout.line_items[i].quantity;
	 	obj["vendor"] = Shopify.checkout.line_items[i].vendor;
	 	items.push(obj);
 	}
	wizrocket.event=[];
 	wizrocket.event.push("Chechout",{
 		"Amount" : Shopify.checkout.total_price,
 		"Currency" : Shopify.checkout.currency,
 		"Ship_country" : Shopify.checkout.shipping_address.country,
 		"Ship_region" : Shopify.checkout.shipping_address.province,
 		"Ship_city" : Shopify.checkout.shipping_address.city,
 		"email" : Shopify.checkout.email,
 		"items" : items
 		
// Payment mode (Credit Card, Paypal)
// Items (see the platform specific integration guide for sending a list of products sold)
 	})
 	alert ("hello checkout");
	alert(JSON.stringify(wizrocket.event));
}
var ref = window.location.href;
var res = ref.match(/thank_you/gi);
alert (res);
if (res != null){
	alert ("its thank_you page");
	push_checkout();
}
