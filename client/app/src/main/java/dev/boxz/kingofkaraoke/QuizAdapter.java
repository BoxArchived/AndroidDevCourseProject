package dev.boxz.kingofkaraoke;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class QuizAdapter extends FragmentStateAdapter {
    private ArrayList<Question> questionArrayList;
    public QuizAdapter(@NonNull FragmentActivity fragmentActivity, ArrayList<Question> questionArrayList) {
        super(fragmentActivity);
        this.questionArrayList=questionArrayList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        QuizFragment quizFragment=QuizFragment.newInstance(questionArrayList.get(position));
        return quizFragment;
    }

    @Override
    public int getItemCount() {
        return questionArrayList.size();
    }
}
