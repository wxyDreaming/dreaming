
var webSocket = null;
var url = 'ws://localhost:8080/fishChat';

if ('WebSocket' in window) {  
	webSocket = new WebSocket(host);  
} else if ('MozWebSocket' in window) {  
	webSocket = new MozWebSocket(host);  
} else {  
    Console.log('Error: WebSocket is not supported by this browser.');  
} 

webSocket.onerror = function(event) {
	onError(event)
};

webSocket.onopen = function(event) {
	onOpen(event)
};

webSocket.onmessage = function(event) {
	onMessage(event)
};

function onMessage(event) {
	document.getElementById('messages').innerHTML += '<br />' + event.data;
}

function onOpen(event) {
	document.getElementById('messages').innerHTML = 'Connection established';
}

function onError(event) {
	alert(event.data);
}

function start() {
	webSocket.send('hello');
	return false;
}