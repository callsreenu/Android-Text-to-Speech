package com.androidmyway.demo.texttospeech;

import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

public class TTSDemo extends Activity implements TextToSpeech.OnInitListener {
        private static final String TAG = "TextToSpeechDemo";
        private TextToSpeech mTts;
        private Button mAgainButton,mSetPitchButton,mSetSpeedButton;
        private EditText inputText;
        private SeekBar pitchslider,speedslider;
        @Override
        public void onCreate(Bundle savedInstanceState) {
               super.onCreate(savedInstanceState);
               setContentView(R.layout.activity_main);
               // Initialize text-to-speech. This is an asynchronous operation.
               // The OnInitListener (second argument) is called after initialization completes.
               mTts = new TextToSpeech(this,this); //TextToSpeech.OnInitListener

               inputText = (EditText) findViewById(R.id.inputtext);
               mAgainButton = (Button) findViewById(R.id.again_button);
               pitchslider = (SeekBar) findViewById(R.id.pitchslider);
               mSetPitchButton = (Button) findViewById(R.id.set_pitch);
               speedslider = (SeekBar) findViewById(R.id.speedslider);
               mSetSpeedButton = (Button) findViewById(R.id.set_speed);
               mAgainButton.setOnClickListener(new View.OnClickListener() {
            	   public void onClick(View v){
            		   sayHello();
                   }
               });

               mSetPitchButton.setOnClickListener(new OnClickListener() {
                      @Override
                      public void onClick(View v) {
                             // TODO Auto-generated method stub
                             double pitch = (pitchslider.getProgress() + 1);
                             pitch = pitch / 10;
                             mTts.setPitch((float)pitch);
                             Toast.makeText(getApplicationContext(), "Pitch: " + pitch,Toast.LENGTH_SHORT).show();
                      }
               });

               mSetSpeedButton.setOnClickListener(new OnClickListener() {
		           @Override
		           public void onClick(View v) {
		        	   // TODO Auto-generated method stub
		        	   double speed = (speedslider.getProgress() + 1);
		        	   speed = speed / 10;
		        	   mTts.setSpeechRate((float) speed);
		        	   Toast.makeText(getApplicationContext(), "Speed: " + speed,Toast.LENGTH_SHORT).show();
		           }
               });
        }

        @Override
        public void onDestroy() {
        	// Don't forget to shutdown!
        	if (mTts != null) {
        		mTts.stop();
	            mTts.shutdown();
        	}
        	super.onDestroy();
        }

        // Implements TextToSpeech.OnInitListener.
        public void onInit(int status) {
        	// status can be either TextToSpeech.SUCCESS or TextToSpeech.ERROR.
        	if (status == TextToSpeech.SUCCESS) {
        		int result = mTts.setLanguage(Locale.US);
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                	// Lanuage data is missing or the language is not supported.
                	Log.e(TAG, "Language is not available.");
                } else {
                     // Check the documentation for other possible result codes.
                     // For example, the language may be available for the locale,
                     // but not for the specified country and variant.
                     // The TTS engine has been successfully initialized.
                     // Allow the user to press the button for the app to speak again.
                     mAgainButton.setEnabled(true);
                     // Greet the user.
                     sayHello();
                }
        	} else {
                  // Initialization failed.
                  Log.e(TAG, "Could not initialize TextToSpeech.");
        	}
        }

        private void sayHello() {
               // Select a random hello.
               // Drop allpending entries in the playback queue.
               mTts.speak(inputText.getText().toString(),TextToSpeech.QUEUE_FLUSH,null);
        }

}
