alert ("hello how are you");
var push_checkout = function(){
	wizrocket.event=[]
 	wizrocket.event.push("Chechout",{
 		"Amount" : Shopify.checkout.total_price,
 		"Currency" : Shopify.checkout.currency,
 		"Ship_country" : Shopify.checkout.shipping_address.country,
 		"Ship_region" : Shopify.checkout.shipping_address.province,
 		"Ship_city" : Shopify.checkout.shipping_address.city
// Payment mode (Credit Card, Paypal)
// Items (see the platform specific integration guide for sending a list of products sold)
 	})
 	alert ("hello checkout");
	alert(JSON.stringify(wizrocket.event))
}
var ref = window.location.href;
var res = ref.match(/thank_you/gi);
alert (res)
if (res != null){
	alert ("its thank_you page");
	push_checkout();
}
