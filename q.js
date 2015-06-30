alert ("hello how are you");
var push_checkout = function(){
	wizrocket.event=[]
 	wizrocket.event.push("Chechout",{
 		"Amount" : Shopify.checkout.total_price,
 		"Currency" : Shopify.checkout.currency
//  		Amount (150, 499)
// Currency (USD, INR)
// Payment mode (Credit Card, Paypal)
// Items (see the platform specific integration guide for sending a list of products sold)
// Ship country (US, India)
// Ship region (California, Maharashtra)
// Ship city (San Francisco, Mumbai)
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
