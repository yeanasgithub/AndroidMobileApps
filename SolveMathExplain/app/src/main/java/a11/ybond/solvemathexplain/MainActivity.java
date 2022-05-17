package a11.ybond.solvemathexplain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    Button btn_start, btn_answer0, btn_answer1, btn_answer2, btn_answer3;
    TextView tv_score, tv_quest_num, tv_questions, tv_timer, tv_result;
    ProgressBar prog_timer;

    Solving solving = new Solving();

    int secondsLeft = 20;

    TextView tvTime;
    boolean isRecording = false;
    boolean isPlaying = false;
    int seconds = 0;

    LottieAnimationView endHere;
    int dummySeconds = 0;
    int playableSeconds = 0;

    private Button StartRecording, StopRecording, StartPlaying, StopPlaying;
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private String AudioSavaPath = null;


    Handler handler;

    //ExecutorService executorService = Executors.newSingleThreadExecutor();

    CountDownTimer timer = new CountDownTimer(20000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            secondsLeft--;
            tv_timer.setText(Integer.toString(secondsLeft) + " seconds");
            prog_timer.setProgress(20 - secondsLeft);

        }

        @Override
        public void onFinish() {
            // when the time is up, first we are disabling the answer buttons
            endHere.setVisibility(View.VISIBLE);
            btn_answer0.setEnabled(false);
            btn_answer1.setEnabled(false);
            btn_answer2.setEnabled(false);
            btn_answer3.setEnabled(false);

            tv_result.setText("Time is up! " + solving.getNumberCorrect() + "/" + (solving.getTotalQuestions() - 1) );

            final Handler handlerQ = new Handler();
            handlerQ.postDelayed(new Runnable() {
                @Override
                public void run() {
                    endHere.setVisibility(View.INVISIBLE);
                    btn_start.setVisibility(View.VISIBLE);


                }
            }, 3000);



        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        endHere = findViewById(R.id.ended);

        endHere.setVisibility(View.INVISIBLE);

        getSupportActionBar().hide(); // 25:39

        btn_start = findViewById(R.id.btn_start);
        btn_answer0 = findViewById(R.id.btn_answer0);
        btn_answer1 = findViewById(R.id.btn_answer1);
        btn_answer2 = findViewById(R.id.btn_answer2);
        btn_answer3 = findViewById(R.id.btn_answer3);

        tv_score = findViewById(R.id.tv_score);
        tv_result = findViewById(R.id.tv_result);
        tv_quest_num = findViewById(R.id.tv_quest_num); // questionNumber
        tv_questions = findViewById(R.id.tv_questions); // questionPhrase
        tv_timer = findViewById(R.id.tv_timer);
        prog_timer = findViewById(R.id.prog_timer);

        tv_questions.setText("");
        tv_timer.setText("0 seconds");
        tv_quest_num.setText("Question");
        tv_result.setText("Press Start");
        tv_score.setText("0 pts");
        prog_timer.setProgress(0);

        View.OnClickListener startButtonClickListener = v -> {

            Button start_button = (Button) v;
            start_button.setVisibility(View.INVISIBLE);
            // If start button is pressed again, secondsLeft should be reset to 90 seconds
            secondsLeft = 20;
            solving = new Solving();
            startSolving();
            timer.start();

        };

        View.OnClickListener answerButtonClickListener = v -> {
            // capturing button that was clicked
            Button buttonClicked = (Button) v;
            // getting the answer content
            int answerSelected = Integer.parseInt(buttonClicked.getText().toString());

            // to test if each button is being wired well
            // Toast.makeText(MainActivity.this, "AnswerSelected=" + answerSelected, Toast.LENGTH_SHORT).show();

            solving.checkAnswer(answerSelected);
            tv_score.setText(Integer.toString(solving.getScore()) + " points");

            startSolving();

        };

        btn_start.setOnClickListener(startButtonClickListener);

        // All four answer buttons share OnClickListener
        btn_answer0.setOnClickListener(answerButtonClickListener);
        btn_answer1.setOnClickListener(answerButtonClickListener);
        btn_answer2.setOnClickListener(answerButtonClickListener);
        btn_answer3.setOnClickListener(answerButtonClickListener);


        tvTime = findViewById(R.id.tv_time);


        StartRecording = findViewById(R.id.startRecord);
        StopRecording = findViewById(R.id.stopRecord);
        StartPlaying = findViewById(R.id.startPlaying);
        StopPlaying = findViewById(R.id.stopPlaying);

        StartRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkPermissions() == true) {

                    if (!isRecording) {

                        // teacher can look at the recorded file per question
                        // and find out who solved and explained the answer

                        AudioSavaPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                                + "/" + "recordingAudio" +
                                //"Q" + solving.getTotalQuestions() +
                                ".mp3";

                        mediaRecorder = new MediaRecorder();


                        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

                        //These kinds might have better quality, but not working in the app
                        //mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                        //mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

                        mediaRecorder.setOutputFile(AudioSavaPath);

                        try {
                            mediaRecorder.prepare();
                            mediaRecorder.start();
                            playableSeconds = 0;
                            seconds = 0;
                            dummySeconds = 0;
                            isRecording = true;
                            runTimer();

                            Toast.makeText(MainActivity.this, "Recording started", Toast.LENGTH_SHORT).show();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    else {

                        // Toast.makeText(MainActivity.this, "Press the green button", Toast.LENGTH_SHORT).show();

                    }



                } else {

                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                            Manifest.permission.RECORD_AUDIO,Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },1);
                }
            }
        });

        StopRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isRecording) {

                    mediaRecorder.stop();
                    mediaRecorder.release();
                    mediaRecorder = null;
                    playableSeconds = seconds;
                    dummySeconds = seconds;
                    seconds = 0;
                    isRecording = false;
                    //Toast.makeText(MainActivity.this, "Recording stopped", Toast.LENGTH_SHORT).show();
                }

                else {

                    isPlaying = false;

                    Toast.makeText(MainActivity.this, "You have NOT recorded anything yet!", Toast.LENGTH_SHORT).show();

                }

            }
        });

        StartPlaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!isPlaying) {
                    if (AudioSavaPath != null) {

                        mediaPlayer = new MediaPlayer();
                        try {
                            mediaPlayer.setDataSource(AudioSavaPath);
                            mediaPlayer.prepare();
                            mediaPlayer.start();

                            Toast.makeText(getApplicationContext(), (dummySeconds - 1) + " seconds will be played.", Toast.LENGTH_SHORT).show();
                            //Toast.makeText(MainActivity.this, "Start playing", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        isPlaying = true;
                        runTimer();
                    }

                    else
                    {
                        Toast.makeText(getApplicationContext(), "NO Recording Present!", Toast.LENGTH_SHORT).show();

                    }
                }

                else {

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            // Toast.makeText(getApplicationContext(), "NO Recording Present!", Toast.LENGTH_SHORT).show();
                        }
                    }, 1000);

                }

            }


        });

        StopPlaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mediaPlayer != null) {

                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                    isPlaying = false;
                    isRecording = false;
                    seconds = 0;

                    handler.removeCallbacksAndMessages(null);
                    //Toast.makeText(MainActivity.this, "Stopped playing", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void runTimer()
    {
        handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                int minutes = (seconds % 3600 ) / 60;
                int secs = seconds % 60;
                String duration = String.format(Locale.getDefault(), "%02d:%02d", minutes, secs);
                tvTime.setText(duration);   // Displaying 00:00

                if(isRecording && !isPlaying)
                {
                    seconds++;
                }
                //handler.removeCallbacksAndMessages(null);
                handler.postDelayed(this, 1000);

                if(isPlaying && !isRecording) {
                    // ***************************************************************** //
                    // HERE is the debuggig point where I spent hours :
                    // I wanted to display timer the opposite way when playing
                    // recorded explanation, but, for some reason, the timer did not work
                    // the way it does to show recording time.

                   // int minutes = (dummySeconds % 3600 ) / 60;
                    //int secs = dummySeconds % 60;
                    //String duration = String.format(Locale.getDefault(), "%02d:%02d", minutes, secs);
                    //tvTime.setText(duration);
                    //dummySeconds--;



                    //minutes = (playableSeconds % 3600 ) / 60;
                    //secs = playableSeconds % 60;
                    //duration = String.format(Locale.getDefault(), "%02d:%02d", minutes, secs);

                    //playableSeconds--;
                    //tvTime.setText(duration);
                    //duration = String.format(Locale.getDefault(), "%02d:%02d", minutes, secs);
                    //tvTime.setText(duration);   // Displaying
                    //playableSeconds = dummySeconds;

                    //for (playableSeconds = dummySeconds; playableSeconds > -1; playableSeconds--)
                    //{
                    //    duration = String.format(Locale.getDefault(), "%02d:%02d", minutes, secs);
                    //    tvTime.setText(duration);   // Displaying

                    //}

                    // ***************************************************************** //
                    // BELOW IF block does not do anything
                    // Playing time is shown via Toast instead

                    if(isPlaying && playableSeconds == -1)
                    {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        // 37:22
                        isPlaying = false;
                        mediaPlayer = null;
                        mediaPlayer = new MediaPlayer();
                        playableSeconds = dummySeconds;
                        handler.removeCallbacksAndMessages(null);

                        return;
                    }

                }

            }

        });
    }




    private boolean checkPermissions() {
        int first = ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.RECORD_AUDIO);
        int second = ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        return first == PackageManager.PERMISSION_GRANTED &&
                second == PackageManager.PERMISSION_GRANTED;
    }


    private void startSolving()
    {
        solving.makeNewQuestions();
        int [] answer = solving.getCurrentQuestion().getAnswerArray();
        btn_answer0.setText(Integer.toString(answer[0]));
        btn_answer1.setText(Integer.toString(answer[1]));
        btn_answer2.setText(Integer.toString(answer[2]));
        btn_answer3.setText(Integer.toString(answer[3]));

        btn_answer0.setEnabled(true);
        btn_answer1.setEnabled(true);
        btn_answer2.setEnabled(true);
        btn_answer3.setEnabled(true);


        endHere.setVisibility(View.INVISIBLE);

        tv_score.setText(solving.getScore() + " pts");

        tv_quest_num.setText("Q" + solving.getTotalQuestions() + ": " + solving.getCurrentQuestion().getQuestionEquation());

        tv_questions.setText(solving.getCurrentQuestion().getQuestionPhrase());

        tv_result.setText(solving.getNumberCorrect() + "/" + (solving.getTotalQuestions() - 1) );

    }


}