alert ("hello how are you");
var push_checkout = function(){
	wizrocket.event=[]
 	wizrocket.event.push("Chechout",{
 		"Amount" : Shopify.checkout.payment_due
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
document.getElementsByClassName("thank-you") = push_checkout
