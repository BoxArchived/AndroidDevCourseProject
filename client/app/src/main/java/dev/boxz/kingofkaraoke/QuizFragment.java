package dev.boxz.kingofkaraoke;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;


public class QuizFragment extends Fragment {
    private static final String QUESTION = "QUESTION";
    MediaPlayer mediaPlayer;

    private Question mQuestion;

    public QuizFragment() {
    }


    public static QuizFragment newInstance(Question param1) {
        QuizFragment fragment = new QuizFragment();
        Bundle args = new Bundle();
        args.putSerializable(QUESTION, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mQuestion = (Question) getArguments().getSerializable(QUESTION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_quiz, container, false);
        TextView questionTextView=view.findViewById(R.id.questionTextView);
        SeekBar seekBar=view.findViewById(R.id.seekBar);
        Button play=view.findViewById(R.id.startMusicBtn);
        Button stop=view.findViewById(R.id.stopMusicBtn);
        Button pause=view.findViewById(R.id.pauseMusicBtn);
        ImageView imageView=view.findViewById(R.id.coverImage);
        RadioGroup radioGroup=view.findViewById(R.id.radioGroup);
        questionTextView.setText("Who is the singer?");
        play.setEnabled(true);
        pause.setEnabled(false);
        stop.setEnabled(false);
        Handler handler=new Handler(Looper.myLooper());
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer!=null)
                {
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    handler.postDelayed(this, 200);
                }
            }
        };
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer==null){
                    mediaPlayer=MediaPlayer.create(getActivity().getApplicationContext(), Uri.fromFile(new File(getActivity().getFilesDir(),mQuestion.getFilePath())));
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mediaPlayer.stop();
                            mediaPlayer.release();
                            mediaPlayer=null;
                            seekBar.setProgress(0);
                            play.setEnabled(true);
                            stop.setEnabled(false);
                            pause.setEnabled(false);
                        }
                    });
                    mediaPlayer.start();
                    play.setEnabled(false);
                    stop.setEnabled(true);
                    pause.setEnabled(true);
                    seekBar.setMax(mediaPlayer.getDuration());
                    handler.postDelayed(runnable,200);
                }
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play.setEnabled(true);
                pause.setEnabled(false);
                stop.setEnabled(true);
                mediaPlayer.pause();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play.setEnabled(true);
                pause.setEnabled(false);
                stop.setEnabled(false);
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer=null;
                seekBar.setProgress(0);
            }
        });
        Bitmap bitmap= BitmapFactory.decodeFile(new File(getActivity().getFilesDir(),mQuestion.getImagePath()).toString());
        imageView.setImageBitmap(bitmap);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

            }
        });

        return view;
    }
}