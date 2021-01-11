package com.example.voice_asistant;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_RECORD_AUDIO = 1;
    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;
    private TextToSpeech tts;
    private SpeechRecognizer speechRecog;
    private ImageView image;
    public void say(String com) {tts.speak(com, TextToSpeech.QUEUE_FLUSH, null);}



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image=(ImageView)findViewById(R.id.imageview);
        tts=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.UK);
                }
            }

        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                talk();

            }

        });





    }

    public void talk()
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
        //speechRecog.startListening(intent);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "hi speak something");
        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        } catch (Exception e) {
            Toast.makeText(this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
            //e.printStackTrace();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String s = (result.get(0).toString());
                    String texts = s.toLowerCase();
                    if (texts.contains("hello")) {

                        say("satya");
                    } else if (texts.contains("youtube")) {
                        say("what you want to search sir");
                        try {
                            Thread.sleep(1000);
                            talk();

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (texts.contains("song")) {
                            say("which song do you want to play");
                            try {
                                Thread.sleep(1000);
                                talk();
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/results?search_query=" + texts));
                                startActivity(intent);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                        //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/"));
                        //startActivity(intent);

                    } else if ((texts.contains("google")) || texts.contains("who") || texts.contains("what") || texts.contains("where")) {
                        say("opening google");
                        try {
                            Thread.sleep(1000);
                            talk();
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=" + texts));
                            startActivity(intent);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                        if (texts.contains("wikipedia")) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/"));
                            startActivity(intent);
                        }
                    } else if (texts.contains("hey siri")) {
                        say("i am here sir what can i do for you");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        talk();
                    } else if (texts.contains("sin") || texts.contains(("satya"))) {
                        if (texts.contains("30")) {
                            say("answer is 0.5");
                        } else if (texts.contains("45")) {
                            say("answer is ");
                        } else {
                            String str = texts;
                            String num = str.replaceAll("[^0-9]", "");

                            Double ans = Math.toRadians(Double.valueOf(num));
                            Double value = Math.sin(ans);
                            say("answer is" + value);
                        }

                    } else if (texts.contains("camera")) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivity(intent);
                    } else if (texts.contains("selfie") || texts.contains("photo") || texts.contains("picture")) {
                        Intent intent = new Intent((MediaStore.ACTION_IMAGE_CAPTURE));
                        intent.putExtra("android.intent.extras.CAMERA_FACING", 1);
                        startActivity(intent);
                    } else if (texts.contains("encrypt")) {
                        /*say("massage please");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        talk();
                        say("ok sir i got the massage");


                        //talk();
                        //int key = Integer.parseInt(texts);*/
                        char ch;
                        String str = "satya";
                        String cipher = "";
                        for (int i = 0; i < str.length(); i++) {
                            if (Character.isUpperCase(str.charAt(i))) {
                                ch = (char) (((int) str.charAt(i) + 2 - 65) % 26 + 65);
                            } else {
                                ch = (char) (((int) str.charAt(i) + 2 - 97) % 26 + 97);
                            }
                            cipher = Character.toString(ch);
                            say(cipher);

                        }

                    }
                }
            }
            break;
            default:
                throw new IllegalStateException("Unexpected value: " + requestCode);
        }
    }
        @Override
        protected void onPause(){
            super.onPause();
            //tts.shutdown();
        }


    }

