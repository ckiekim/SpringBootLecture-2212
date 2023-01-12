/**
 * 
 */
var audioCtx = new (window.AudioContext || window.webkitAudioContext)();

var source = audioCtx.createBufferSource();

// Assume that you have already loaded the audio file into a buffer
source.buffer = audioBuffer;

// Create a script processor node with a buffer size of 4096 and 1 input and 1 output channel
var scriptNode = audioCtx.createScriptProcessor(4096, 1, 1);

// Set the new sampling rate (e.g., 44100 Hz)
scriptNode.onaudioprocess = function(audioProcessingEvent) {
    var inputBuffer = audioProcessingEvent.inputBuffer;
    var outputBuffer = audioProcessingEvent.outputBuffer;
    var newSampleRate = 44100;

    for (var channel = 0; channel < inputBuffer.numberOfChannels; channel++) {
	    var inputData = inputBuffer.getChannelData(channel);
	    var outputData = outputBuffer.getChannelData(channel);
	
	    for (var sample = 0; sample < inputBuffer.length; sample++) {
		    var interpolation = sample * newSampleRate / audioCtx.sampleRate;
		    var index = Math.floor(interpolation);
		    var frac = interpolation - index;
		    outputData[sample] = inputData[index] + frac * (inputData[index + 1] - inputData[index]);
	    }
    }
};

// Connect the script processor node to the source and destination
source.connect(scriptNode);
scriptNode.connect(audioCtx.destination);

// Start playing the audio
source.start();
