package dev.boxz.kingofkaraoke;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;


public class QuizFragment extends Fragment {
    private static final String QUESTION = "QUESTION";


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
        Button startBtn=view.findViewById(R.id.startMusicBtn);
        Button stopBtn=view.findViewById(R.id.stopMusicBtn);
        
        questionTextView.setText("Who is the singer?");


        return view;
    }
}