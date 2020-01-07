package com.example.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ListView listView1;
    ArrayAdapter<String> adapter;
    String [] array={"media1", "media2","media3","media4","media5"};
    int selectedRawId;
    String selectedMedia;
    Button buttonPlay, buttonStop;
    TextView textView1;
    MediaPlayer mediaPlayer;
    SeekBar seekBar1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mediaplayer);

        buttonPlay=(Button)findViewById(R.id.buttonPlay);
        buttonStop=(Button)findViewById(R.id.buttonStop);
        textView1=(TextView)findViewById(R.id.textView1);
        listView1=(ListView)findViewById(R.id.listView1);
        seekBar1=(SeekBar)findViewById(R.id.seekBar1);



        adapter=new ArrayAdapter<String >(this, android.R.layout.simple_list_item_single_choice,array);
        listView1.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView1.setAdapter(adapter);

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedMedia=array[i];
            }
        });
            buttonPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    selectedRawId = getResources().getIdentifier(selectedMedia, "raw", getPackageName());
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), selectedRawId);

                    mediaPlayer.start();
                    textView1.setText("Now playing: " + selectedMedia);
                    buttonPlay.setText("Pause");


                            if(buttonPlay.getText().equals("Pause")){
                                mediaPlayer.pause();
                                buttonPlay.setText("Resume");

                            }
                            else{
                                mediaPlayer.start();
                                buttonPlay.setText("Pause");

                            }


                    new Thread() {
                        public void run() {
                            if (mediaPlayer == null) return;

                            seekBar1.setMax(mediaPlayer.getDuration());
                            while (mediaPlayer != null && mediaPlayer.isPlaying()) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        seekBar1.setProgress(mediaPlayer.getCurrentPosition());

                                    }
                                });
                                SystemClock.sleep(100);
                            }
                        }
                    }.start();


                }

            });


        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                textView1.setText("Now playing: ");
                buttonPlay.setText("Play");
                buttonPlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        selectedRawId = getResources().getIdentifier(selectedMedia, "raw", getPackageName());
                        mediaPlayer = MediaPlayer.create(getApplicationContext(), selectedRawId);

                        mediaPlayer.start();
                        textView1.setText("Now playing: " + selectedMedia);
                        buttonPlay.setText("Pause");


                        if(buttonPlay.getText().equals("Pause")){
                            mediaPlayer.pause();
                            buttonPlay.setText("Resume");

                        }
                        else{
                            mediaPlayer.start();
                            buttonPlay.setText("Pause");

                        }


                        new Thread() {
                            public void run() {
                                if (mediaPlayer == null) return;

                                seekBar1.setMax(mediaPlayer.getDuration());
                                while (mediaPlayer != null && mediaPlayer.isPlaying()) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            seekBar1.setProgress(mediaPlayer.getCurrentPosition());

                                        }
                                    });
                                    SystemClock.sleep(100);
                                }
                            }
                        }.start();


                    }

                });



            }
        });


    }
}
