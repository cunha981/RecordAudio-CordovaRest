/*
 This demo shows how to collect the raw microphone data, encode it into WAV format and then use the resulting blob
 as src for a HTML Audio element. No Web Audio API support is needed for this to work.
 */

// Capture configuration object
var captureCfg = {};

// Audio Buffer
var audioDataBuffer = [];

var objectURL = null;

// URL shim
window.URL = window.URL || window.webkitURL;

//Web Worker to encode MP3
 var encoderWorker = new Worker('js/audio/mp3Worker.js');


/**
 * Called continuously while AudioInput capture is running.
 */
function onAudioInputCapture(evt) {
    try {
        if (evt && evt.data) {
            // Add the chunk to the buffer
            audioDataBuffer = audioDataBuffer.concat(evt.data);
        }
        else {
            alert("Unknown audioinput event!");
        }
    }
    catch (ex) {
        alert("onAudioInputCapture ex: " + ex);
    }
}


/**
 * Called when a plugin error happens.
 */
function onAudioInputError(error) {
    alert("onAudioInputError event recieved: " + JSON.stringify(error));
}


/**
 *
 */
var startCapture = function () {
    try {
        if (window.audioinput && !audioinput.isCapturing()) {
			var channelsMono = 1; //stereo = 2
			var audioSource = 0 //DEFAULT (Android/iOS)
			

            // audio capture configuration
            //
            captureCfg = {
                sampleRate: 44100,
                bufferSize: 16384,
                channels: channelsMono,
                format: 'PCM_16BIT',
                audioSourceType: audioSource
            };


            audioinput.start(captureCfg);
			timer();
            consoleMessage("Microphone input started!");
			
            if (objectURL && URL.revokeObjectURL) {
                URL.revokeObjectURL(objectURL);
            }

            disableStartButton();
        }
    }
    catch (e) {
        alert("startCapture exception: " + e);
    }
};


/**
 *
 */
var stopCapture = function () {
    try {
        if (window.audioinput && audioinput.isCapturing()) {

            if (window.audioinput) {
                audioinput.stop();
            }
			clearTimer();

            consoleMessage("Encoding WAV...");
            var encoder = new WavAudioEncoder(captureCfg.sampleRate, captureCfg.channels);
            encoder.encode([audioDataBuffer]);

            consoleMessage("Encoding WAV finished");

            var blob = encoder.finish("audio/wav");

            consoleMessage("BLOB created");

            var reader = new FileReader();

            reader.onload = function (evt) {
				console.log("Convertendo para MP3");
				showModal("Aguarde, enquanto processamos o aúdio.");
				
				encoderWorker.postMessage({ cmd: 'init', config:{
					mode : 3,
					channels:1,
					samplerate: 48000,
					bitrate: 16
				}});
				encoderWorker.postMessage({ cmd: 'encode', rawInput: evt.target.result });
				encoderWorker.postMessage({ cmd: 'finish'});
				encoderWorker.onmessage = function(e) {
					if (e.data.cmd == 'end') {

						console.log("Conversão realizada para Mp3");
						hideModal();

						var mp3Blob = new Blob(e.data.buf, {type: 'audio/mp3'});
						audioGlobal.push(mp3Blob);;
						
						var li = document.createElement('li');
						var divAudio = document.createElement('div');
						divAudio.className = 'col-xs-10 col-sm-11 col-md-11 vertical-center padding0';
						
						var divDelete = document.createElement('div');
						divDelete.className = 'col-xs-1 col-sm-1 col-md-1 vertical-center';
						
						var blobUrl = window.URL.createObjectURL(mp3Blob)
						var audio = document.createElement('audio');
						audio.className = 'width100 float-left';
						audio.controls = true;
						audio.src = blobUrl;
						
						var btDelete = document.createElement('i');
						btDelete.className = 'btn glyphicon glyphicon-trash btn-delete';
						btDelete.onclick = function(){this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode)};
						
						divAudio.appendChild(audio);
						divDelete.appendChild(btDelete);
						li.appendChild(divAudio);
						li.appendChild(divDelete);
						recordingslist.appendChild(li);
						
						consoleMessage("Audio created");
						audioDataBuffer = [];
					}
				};
            };
            consoleMessage("Loading from BLOB");
            reader.readAsArrayBuffer(blob);

            disableStopButton();
        }
    }
    catch (e) {
        alert("stopCapture exception: " + e);
    }
};


/**
 *
 */
var initUIEvents = function () {
    document.getElementById("btn-start").addEventListener("click", startCapture);
    document.getElementById("btn-stop").addEventListener("click", stopCapture);
};


/**
 * When cordova fires the deviceready event, we initialize everything needed for audio input.
 */
var onDeviceReady = function () {
    if (window.cordova && window.audioinput) {
        initUIEvents();

        consoleMessage("Use 'Start Capture' to begin...");

        // Subscribe to audioinput events
        //
        window.addEventListener('audioinput', onAudioInputCapture, false);
        window.addEventListener('audioinputerror', onAudioInputError, false);
    }
    else {
        consoleMessage("cordova-plugin-audioinput not found!");
        disableAllButtons();
    }
};

// Make it possible to run the demo on desktop
if (!window.cordova) {
    // Make it possible to run the demo on desktop
    console.log("Running on desktop!");
    onDeviceReady();
}
else {
    // For Cordova apps
    document.addEventListener('deviceready', onDeviceReady, false);
}
var success;
function enviarGravacao(){
	showModal('Aguarde, enquanto enviamos o aúdio.');
	setTimeout(function() {
		$('#recordingslist li').each(function (index){
			uploadAudio(audioGlobal[index], this);
		});
		if(!success){
			alert("Erro ao enviar gravação.");
		}else if(success){
			alert("Gravação enviada.");
		}
		success = null;
		hideModal();
	}, 0);
}
function uploadAudio(mp3Data, element){
	var loja = localStorage.getItem("logista");
	var categoria = localStorage.getItem("categoria");
	categoria = categoria.replace("ç","c");
	categoria = categoria.replace("ó","o");
	
	var recordForm = new FormData();
	recordForm.append("audio", mp3Data);
	recordForm.append("loja", loja);
	recordForm.append("categoria", categoria);
	
	var xhr = new XMLHttpRequest();
	xhr.open('POST', "http://192.168.3.5:8443/record-audio", false);

	xhr.addEventListener("load", function (evt) {
	  var data = evt.target.response;
	  if (xhr.readyState == 4 && this.status === 200) {
		  success = true;
		  $(element).remove();
			console.log("enviou");
	  }else if(this.status === 403){
		  location.reload();
	  }else {
		  success = false;
	  }
	}, false);
	xhr.send(recordForm);
}
