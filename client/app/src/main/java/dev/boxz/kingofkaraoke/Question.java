package dev.boxz.kingofkaraoke;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Question implements Serializable {
    public static final String FILE_NAME="question.json";
    public static ArrayList<Question> questionArrayList=new ArrayList<>();
    public static ArrayList<Integer> userAnswer=new ArrayList<>();
    public static ArrayList<Boolean> isCorrectList=new ArrayList<>();
    private String filePath;
    private String question;
    private ArrayList<Integer> options;
    private String imagePath;
    private int answer;
    private String singer;

    public Question(JSONObject data) throws JSONException {
        this.question="Who is the singer of this song "+data.getString("song");
        this.filePath=data.getString("songURL");
        this.imagePath=data.getString("coverURL");
        this.singer=data.getString("singer");
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<Integer> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<Integer> options) {
        this.options = options;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public static void generateOption(){
        isCorrectList=new ArrayList<>();
        userAnswer=new ArrayList<>();
        for (int i = 0; i < questionArrayList.size(); i++) {
            ArrayList<Integer> options=new ArrayList<>();
            Random random=new Random();
            while(options.size()<=3){
                int result=random.nextInt(questionArrayList.size());
                if (result!=i&&!options.contains(result)){
                    options.add(result);
                }
            }
            int result=random.nextInt(4);
            options.add(result,i);
            questionArrayList.get(i).setOptions(options);
            questionArrayList.get(i).setAnswer(result);
            isCorrectList.add(false);
            userAnswer.add(4);
        }

    }

    public static void checkAnswer(Question question,int answer){
        int location=questionArrayList.indexOf(question);
        isCorrectList.set(location,question.getAnswer()==answer);
    }
}
