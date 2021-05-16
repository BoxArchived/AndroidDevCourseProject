package dev.boxz.kingofkaraoke;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable {
    public static  ArrayList<Question> questionArrayList=new ArrayList<>();
    private String filePath;
    private String question;
    private ArrayList<String> options;
    private String imagePath;
    private Integer answer;
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

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Integer getAnswer() {
        return answer;
    }

    public void setAnswer(Integer answer) {
        this.answer = answer;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }
}
