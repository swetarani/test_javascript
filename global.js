var wizrocket = {event:[], profile:[], account:[], enum:function(e){return '$E_' + e}};
wizrocket.account.push({"id": "wizrocket_id"});
(function () {
       var wzrk = document.createElement('script');
       wzrk.type = 'text/javascript';
       wzrk.async = true;
       wzrk.src = ('https:' == document.location.protocol ? 'https://d2r1yp2w7bby2u.cloudfront.net' : 'http://static.wizrocket.com') + '/js/a.js';
       var s = document.getElementsByTagName('script')[0];
       s.parentNode.insertBefore(wzrk, s);
})();
var push_product_viewed = function(){
	wizrocket.event.push("Product_viewed",{
 		"Product name" : product_title,
 		"Category" : product_category_name,
 		"Price" : product_price,
 		"Currency" : currency
 	});
	//alert(JSON.stringify(wizrocket));
}
var category_viewed = function(){
	wizrocket.event.push("Category Viewed",{
		"Category name" : collection_name
	});
	//alert(JSON.stringify(wizrocket));
}
var push_search = function(){
	wizrocket.event.push("Searched",{
		"Search" : searchterm
	});
	//alert(JSON.stringify(wizrocket));
}
var push_add_to_cart = function(){
  	wizrocket.event.push("Added To Cart",{
 		"Product name" : product_title,
  		"Category" : product_category_name,
  		"Price" : product_price,
  		"Currency" : currency
  	});
 	//alert(JSON.stringify(wizrocket));
}

var pushcheck = function(){
	wizrocket.event.push("Charged",{
  		"Amount": Shopify.checkout.total_price,
  		"Currency": Shopify.checkout.currency,
//  		"Payment mode": "Credit Card",
  		"Charged ID": Shopify.checkout.order_id,
     	//	"Vendor": Shopify.checkout.line_items[0].vendor,
     		"Items" : Shopify.checkout.line_items
  }
);
}/*
var push_checkout = function(){
	var len = Shopify.checkout.line_items.length;
	var items = [];
	for (i=0; i<len; i++) {
		var obj = {};
	 	obj["product_id"] = Shopify.checkout.line_items[i].product_id;
	 	obj["title"] = Shopify.checkout.line_items[i].title;
	 	obj["quantity"] = Shopify.checkout.line_items[i].quantity;
	 	obj["vendor"] = Shopify.checkout.line_items[i].vendor;
	 	items.push(obj);
	}
 	wizrocket.event.push("Checkout",{
 		"Amount" : Shopify.checkout.total_price,
 		"Currency" : Shopify.checkout.currency,
 		"Ship_country" : Shopify.checkout.shipping_address.country,
 		"Ship_region" : Shopify.checkout.shipping_address.province,
 		"Ship_city" : Shopify.checkout.shipping_address.city,
 		"email" : Shopify.checkout.email,
 		"Charged Id" : Shopify.checkout.order_id,
 		"items" : items
// Payment mode (Credit Card, Paypal)
// Items (see the platform specific integration guide for sending a list of products sold)
 	});
	//alert(JSON.stringify(wizrocket));
}*/
var profile_push_checkout = function(){
	wizrocket.profile.push({
	"Site":{
		"Name" : Shopify.checkout.billing_address.first_name,
		"Email" : Shopify.checkout.email,
		"Phone" : Shopify.checkout.billing_address.phone
	}	
	});
}
//var refthank = window.location.href;
//var resthank = ref.match(/thank/g);
//var refproducts = window.location.href;
//var resproducts = ref.match(/products/gi);
if(typeof product_json!="undefined"){
	push_product_viewed();
	document.getElementsByName("add")[0].onclick = push_add_to_cart;
}
if(typeof Shopify.checkout != "undefined"){
	profile_push_checkout();
	pushcheck();
	//push_checkout();
}
if(typeof collection_name != "undefined"){
	category_viewed();
}
if(typeof searchterm != "undefined"){
	push_search();
}
