package dev.boxz.kingofkaraoke;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UpdateActivity extends AppCompatActivity {
    final String API_URL="https://cisc3002api.boxz.dev/songs";
    Button okBtn;
    Button noBtn;
    ProgressBar progressBar;
    TextView textView;
    TextView textView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        okBtn=findViewById(R.id.ok);
        noBtn=findViewById(R.id.no);
        progressBar=findViewById(R.id.progressBar);
        textView=findViewById(R.id.infoText);
        textView1=findViewById(R.id.progressText);
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.version==-1){
                    Toast.makeText(getApplicationContext(),"Need to update for the first start ",Toast.LENGTH_LONG);
                    finish();
                }
                File file=new File(getFilesDir(),Question.FILE_NAME);
                try {
                    BufferedReader bufferedReader=new BufferedReader(new FileReader(file));
                    String inString;
                    StringBuilder sb = new StringBuilder();
                    while ((inString = bufferedReader.readLine()) != null) {
                        sb.append(inString);
                    }
                    JSONArray array=new JSONArray(sb.toString());
                    Question.questionArrayList=new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        Question.questionArrayList.add(new Question(array.getJSONObject(i)));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


                ArrayList<Question> questionArrayList=new ArrayList<>();
                Random random=new Random();
                while (questionArrayList.size()<=Math.min(8,Question.questionArrayList.size())){
                    int  result=random.nextInt(Question.questionArrayList.size());
                    if (!questionArrayList.contains(Question.questionArrayList.get(result))){
                        questionArrayList.add(Question.questionArrayList.get(result));
                    }
                }
                Question.questionArrayList=questionArrayList;
                Question.generateOption();
                finish();
            }
        });

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClient okHttpClient=new OkHttpClient();
                Request requestApi=new Request.Builder().url(API_URL).build();
                okHttpClient.newCall(requestApi).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        String data=response.body().string();
                        File file=new File(getFilesDir(),Question.FILE_NAME);
                        if (!file.exists()){
                            file.createNewFile();
                        }
                        byte[] bytes = data.getBytes();
                        int b = data.length();
                        FileOutputStream fileOutputStream= new FileOutputStream(file);
                        fileOutputStream.write(bytes);
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        JSONArray array= null;
                        try {
                            array = new JSONArray(data);
                            Question.questionArrayList=new ArrayList<>();
                            for (int i = 0; i < array.length(); i++) {
                                try {
                                    Question.questionArrayList.add(new Question(array.getJSONObject(i)));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            DownloadAsyncTask downloadAsyncTask=new DownloadAsyncTask();
                            downloadAsyncTask.execute(Question.questionArrayList.toArray(new Question[Question.questionArrayList.size()]));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });


            }
        });

    }
    private class DownloadAsyncTask extends AsyncTask<Question, Long, String> {
        final String FILE_URL="";
        @Override
        protected String doInBackground(Question... questions) {


            for (long i = 0; i < questions.length; i++) {
                Question question=questions[(int) i];
                OkHttpClient client=new OkHttpClient();
                Request request=new Request.Builder().url(question.getFilePath()).build();
                try {
                    Response response=client.newCall(request).execute();
                    InputStream inputStream=response.body().byteStream();
                    Long size=response.body().contentLength();
                    FileOutputStream fileOutputStream=openFileOutput(question.getSinger()+"MUSIC",MODE_PRIVATE);
                    byte[] bytes=new byte[1024*4];
                    Long download=0L;
                    int read;
                    while ((read=inputStream.read(bytes))!=-1){
                        download+=read;
                        fileOutputStream.write(bytes,0,read);
                        publishProgress(download,size, 1L,i);
                    }
                } catch (IOException e  ) {
                    e.printStackTrace();
                }

            }
            Question.generateOption();
            return "Success";
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    okBtn.setEnabled(false);
                    noBtn.setEnabled(false);
                }
            });
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            File file=new File(getFilesDir(),"version");
            byte[] bytes= (MainActivity.version+"").getBytes();
            try {
                FileOutputStream fileOutputStream=new FileOutputStream(file);
                fileOutputStream.write(bytes);
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Toast.makeText(getApplicationContext(),"Download finished!",Toast.LENGTH_LONG).show();
            finish();
        }

        @Override
        protected void onProgressUpdate(Long... values) {
            super.onProgressUpdate(values);
            if (values[2]==0L){
                textView.setText("Downloading the NO."+(values[3]+1)+" item's cover image.");
            }
            if (values[2]==1L){
                textView.setText("Downloading the NO."+(values[3]+1)+" item's mp3 file");
            }
            textView1.setText(values[0]+"/ "+values[1]);
            textView.setMaxWidth(500);
            progressBar.setMax(values[1].intValue());
            progressBar.setProgress(values[0].intValue());

        }
    }
}