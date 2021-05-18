package dev.boxz.kingofkaraoke;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;


public class QuizFragment extends Fragment {
    private static final String QUESTION = "QUESTION";
    MediaPlayer mediaPlayer;
    Button play;
    Button stop;
    Button pause;
    private Question mQuestion;
    SeekBar seekBar;
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
        Button previousBtn=view.findViewById(R.id.previousBtn);
        Button nextBtn=view.findViewById(R.id.nextBtn);
        Button submitBtn=view.findViewById(R.id.submitBtn);
        ViewPager2 viewPager2=getActivity().findViewById(R.id.viewPage);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager2.getCurrentItem()<Question.questionArrayList.size()) {
                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1, true);
                }
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent();
                intent1.setClass(view.getContext(),ScoreActivity.class);
                startActivity(intent1);

            }
        });
        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager2.getCurrentItem()>0) {
                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1, true);
                }
            }
        });
        TextView questionTextView=view.findViewById(R.id.questionTextView);
        seekBar=view.findViewById(R.id.seekBar);
        play=view.findViewById(R.id.startMusicBtn);
        stop=view.findViewById(R.id.stopMusicBtn);
        pause=view.findViewById(R.id.pauseMusicBtn);
        ImageView imageView=view.findViewById(R.id.coverImage);
        RadioGroup radioGroup=view.findViewById(R.id.radioGroup);
        RadioButton radioButtonA=view.findViewById(R.id.optionA);
        RadioButton radioButtonB=view.findViewById(R.id.optionB);
        RadioButton radioButtonC=view.findViewById(R.id.optionC);
        RadioButton radioButtonD=view.findViewById(R.id.optionD);
        questionTextView.setText("Who is the singer/ author?");
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
        mediaPlayer=MediaPlayer.create(getActivity().getApplicationContext(), Uri.fromFile(new File(getActivity().getFilesDir(),mQuestion.getSinger()+"MUSIC")));
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer==null){
                    mediaPlayer=MediaPlayer.create(getActivity().getApplicationContext(), Uri.fromFile(new File(getActivity().getFilesDir(),mQuestion.getSinger()+"MUSIC")));
                }
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
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(String.valueOf(new File(getActivity().getFilesDir(),mQuestion.getSinger()+"MUSIC")));
        byte [] data = mmr.getEmbeddedPicture();
        if(data != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            imageView.setImageBitmap(bitmap);
        }
        else {
            imageView.setImageResource(R.drawable.ic_launcher_background);
        }
//        Bitmap bitmap= BitmapFactory.decodeFile(new File(getActivity().getFilesDir(),mQuestion.getSinger()+"COVER").toString());

        if (Question.userAnswer.get(Question.questionArrayList.indexOf(mQuestion))==0){
            radioButtonA.setChecked(true);
        }
        if (Question.userAnswer.get(Question.questionArrayList.indexOf(mQuestion))==1){
            radioButtonB.setChecked(true);
        }
        if (Question.userAnswer.get(Question.questionArrayList.indexOf(mQuestion))==2){
            radioButtonC.setChecked(true);
        }
        if (Question.userAnswer.get(Question.questionArrayList.indexOf(mQuestion))==3){
            radioButtonD.setChecked(true);
        }

        radioButtonA.setText(mQuestion.getOptions().get(0));
        radioButtonB.setText(mQuestion.getOptions().get(1));
        radioButtonC.setText(mQuestion.getOptions().get(2));
        radioButtonD.setText(mQuestion.getOptions().get(3));
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int location=Question.questionArrayList.indexOf(mQuestion);
                if (checkedId==R.id.optionA){
                    Question.userAnswer.set(location,0);
                    Question.checkAnswer(mQuestion,0);
                }
                if (checkedId==R.id.optionB){
                    Question.userAnswer.set(location,1);
                    Question.checkAnswer(mQuestion,1);
                }
                if (checkedId==R.id.optionC){
                    Question.userAnswer.set(location,2);
                    Question.checkAnswer(mQuestion,2);
                }
                if (checkedId==R.id.optionD){
                    Question.userAnswer.set(location,3);
                    Question.checkAnswer(mQuestion,3);
                }
            }
        });

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (mediaPlayer==null){
            mediaPlayer=MediaPlayer.create(getActivity().getApplicationContext(), Uri.fromFile(new File(getActivity().getFilesDir(),mQuestion.getSinger()+"MUSIC")));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mediaPlayer.stop();
        mediaPlayer=null;
        play.setEnabled(true);
        pause.setEnabled(false);
        stop.setEnabled(false);
        seekBar.setProgress(0);
    }

    @Override
    public void onStop() {
        super.onStop();
        mediaPlayer=null;
        play.setEnabled(true);
        pause.setEnabled(false);
        stop.setEnabled(false);
    }
}