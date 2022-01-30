function sendToken(message, channel, event){
	window.parent.postMessage(message, "*");
}