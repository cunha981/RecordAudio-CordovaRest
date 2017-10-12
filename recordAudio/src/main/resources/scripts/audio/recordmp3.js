(function(window){

  var WORKER_PATH = 'scripts/audio/js/recorderWorker.js';
  var encoderWorker = new Worker('scripts/audio/js/mp3Worker.js');

  var Recorder = function(source, cfg){
    var config = cfg || {};
    var bufferLen = config.bufferLen || 4096;
    var numChannels = config.numChannels || 2;
    this.context = source.context;
    this.node = (this.context.createScriptProcessor ||
                 this.context.createJavaScriptNode).call(this.context,
                 bufferLen, numChannels, numChannels);
    var worker = new Worker(config.workerPath || WORKER_PATH);
    worker.postMessage({
      command: 'init',
      config: {
        sampleRate: this.context.sampleRate,
        numChannels: numChannels
      }
    });
    var recording = false,
      currCallback;

    this.node.onaudioprocess = function(e){
      if (!recording) return;
      var buffer = [];
      for (var channel = 0; channel < numChannels; channel++){
          buffer.push(e.inputBuffer.getChannelData(channel));
      }
      worker.postMessage({
        command: 'record',
        buffer: buffer
      });
    }

    this.configure = function(cfg){
      for (var prop in cfg){
        if (cfg.hasOwnProperty(prop)){
          config[prop] = cfg[prop];
        }
      }
    }

    this.record = function(){
      recording = true;
    }

    this.stop = function(){
      recording = false;
    }

    this.clear = function(){
      worker.postMessage({ command: 'clear' });
    }

    this.getBuffer = function(cb) {
      currCallback = cb || config.callback;
      worker.postMessage({ command: 'getBuffer' })
    }

    this.exportWAV = function(cb, type){
      currCallback = cb || config.callback;
      type = type || config.type || 'audio/wav';
      if (!currCallback) throw new Error('Callback not set');
      worker.postMessage({
        command: 'exportWAV',
        type: type
      });
    }

	//Mp3 conversion
    worker.onmessage = function(e){
      var blob = e.data;
	  console.log("the blob " +  blob + " " + blob.size + " " + blob.type);

	  var arrayBuffer;
	  var fileReader = new FileReader();

	  fileReader.onload = function(){
		arrayBuffer = this.result;
		var buffer = new Uint8Array(arrayBuffer),
        data = parseWav(buffer);

        console.log(data);
		console.log("Convertendo para MP3");
		document.getElementById("txt-carregar").innerHTML  = 'Aguarde, enquanto processamos o aúdio.';
		document.getElementById("modal-carregar").className='';

        encoderWorker.postMessage({ cmd: 'init', config:{
            mode : 3,
			channels:1,
			samplerate: data.sampleRate,
			bitrate: data.bitsPerSample
        }});

        encoderWorker.postMessage({ cmd: 'encode', rawInput: arrayBuffer });
        encoderWorker.postMessage({ cmd: 'finish'});
        encoderWorker.onmessage = function(e) {
            if (e.data.cmd == 'end') {

				console.log("Conversão realizada para Mp3");
				document.getElementById("modal-carregar").className='modal';

				var mp3Blob = new Blob(e.data.buf, {type: 'audio/mp3'});
				audioGlobal.push(mp3Blob);;

				var blobUrl = window.URL.createObjectURL(mp3Blob);
				
				var li = document.createElement('li');
				var divAudio = document.createElement('div');
				divAudio.className = 'col-xs-10 col-sm-11 col-md-11 vertical-center padding0';
				
				var divDelete = document.createElement('div');
				divDelete.className = 'col-xs-1 col-sm-1 col-md-1 vertical-center';
				
				var au = document.createElement('audio');
				au.className = 'width100 float-left';
				au.controls = true;
				au.src = blobUrl;
				
				var btDelete = document.createElement('i');
				btDelete.className = 'btn glyphicon glyphicon-trash btn-delete';
				btDelete.onclick = function(){this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode)};
				
				divAudio.appendChild(au);
				divDelete.appendChild(btDelete);
				li.appendChild(divAudio);
				li.appendChild(divDelete);
				recordingslist.appendChild(li);
            }
        };
	  };
	  fileReader.readAsArrayBuffer(blob);
      currCallback(blob);
    }
	function encode64(buffer) {
		var binary = '',
			bytes = new Uint8Array( buffer ),
			len = bytes.byteLength;

		for (var i = 0; i < len; i++) {
			binary += String.fromCharCode( bytes[ i ] );
		}
		return window.btoa( binary );
	}
	function parseWav(wav) {
		function readInt(i, bytes) {
			var ret = 0,
				shft = 0;

			while (bytes) {
				ret += wav[i] << shft;
				shft += 8;
				i++;
				bytes--;
			}
			return ret;
		}
		if (readInt(20, 2) != 1) throw 'Invalid compression code, not PCM';
		if (readInt(22, 2) != 1) throw 'Invalid number of channels, not 1';
		return {
			sampleRate: readInt(24, 4),
			bitsPerSample: readInt(34, 2),
			samples: wav.subarray(44)
		};
	}
	function Uint8ArrayToFloat32Array(u8a){
		var f32Buffer = new Float32Array(u8a.length);
		for (var i = 0; i < u8a.length; i++) {
			var value = u8a[i<<1] + (u8a[(i<<1)+1]<<8);
			if (value >= 0x8000) value |= ~0x7FFF;
			f32Buffer[i] = value / 0x8000;
		}
		return f32Buffer;
	}
    source.connect(this.node);
    this.node.connect(this.context.destination);    //this should not be necessary
  };
  window.Recorder = Recorder;
})(window);
