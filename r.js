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
	//wizrocket.event=[];
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
