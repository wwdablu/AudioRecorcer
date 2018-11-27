# Aucoder
Library (**Au**dio Re**coder**) that allows to record audio from microphone in Android. The library will create a service and allow audio to be recorded even if the application is closed. The recording can be stopped from the notification.  
  
  
### Gradle Usage  
```  
allprojects {
	repositories {
		maven { url 'https://jitpack.io' }
	}
}

dependencies {
    implementation 'com.github.wwdablu:Aucoder:1.0.0'
}
```  
Mention the above in the gradle files. Once done, sync and you should be good to go.  

### Library Usage  
At first, a configuration needs to be created which will provide the audio capturing information. For example:  
```
AucoderConfig config = Aucoder.Builder()
    .channel(AucoderConfig.CHANNEL_STEREO)
    .sampleRate(AucoderConfig.SAMPLE_RATE_48000)
    .recordSource(AucoderConfig.SOURCE_MIC)
    .format(AucoderConfig.FORMAT_3GPP)
    .audioEncoder(AucoderConfig.AUDIO_ENC_AMR_NB)
    .filename(getFileName())
    .build();
```  

This configuration is required to pass to Aucoder with which it will begin the recording process.  
```  
Aucoder aucoder = Aucoder.with(context, config);  
aucoder.begin();
```  
  
Once the recording is complete or we want to stop, then we can call `aucoder.end()`. Or if we tap on the stop button on the notification then it would stop the recording.  

### Permissions  
Any runtime permission for microphone and storage is required to be obtained by the application as the library does not handle it. Incase required permissions are not provided then the library may fail silently or even crash. This has been done purposefully so that the application developers has the controls of it entirely.  

### Screenshot  
![Version 1.0.0 Screenshot](https://github.com/wwdablu/Aucoder/blob/master/screenshot/screenshot_1.0.0.png)
