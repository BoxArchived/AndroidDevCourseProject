package dev.boxz.kingofkaraoke;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UpdateActivity extends AppCompatActivity {
    final String API_URL="";
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
        textView1.findViewById(R.id.progressText);
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Question.questionArrayList==null){
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

                }
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
                        try {
                            JSONArray array=new JSONArray(response.body().string());
                            Question.questionArrayList=new ArrayList<>();
                            for (int i = 0; i < array.length(); i++) {
                                Question.questionArrayList.add(new Question(array.getJSONObject(i)));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                DownloadAsyncTask downloadAsyncTask=new DownloadAsyncTask();
                downloadAsyncTask.execute(Question.questionArrayList.toArray(new Question[Question.questionArrayList.size()]));

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
                Request request=new Request.Builder().url(FILE_URL+question.getImagePath()).build();
                try {
                    Response response=client.newCall(request).execute();
                    InputStream inputStream=response.body().byteStream();
                    Long size=response.body().contentLength();
                    FileOutputStream fileOutputStream=openFileOutput(question.getImagePath(),MODE_PRIVATE);
                    byte[] bytes=new byte[1024*4];
                    Long download=0L;
                    int read;
                    while ((read=inputStream.read(bytes))!=-1){
                        download+=read;
                        fileOutputStream.write(bytes,0,read);
                        publishProgress(download,size, 0L,i);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                request=new Request.Builder().url(FILE_URL+question.getFilePath()).build();
                try {
                    Response response=client.newCall(request).execute();
                    InputStream inputStream=response.body().byteStream();
                    Long size=response.body().contentLength();
                    FileOutputStream fileOutputStream=openFileOutput(question.getFilePath(),MODE_PRIVATE);
                    byte[] bytes=new byte[1024*4];
                    Long download=0L;
                    int read;
                    while ((read=inputStream.read(bytes))!=-1){
                        download+=read;
                        fileOutputStream.write(bytes,0,read);
                        publishProgress(download,size, 1L,i);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return "Success";
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            okBtn.setEnabled(false);
            noBtn.setEnabled(false);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(),"Download finished!",Toast.LENGTH_LONG).show();
            finish();
        }

        @Override
        protected void onProgressUpdate(Long... values) {
            super.onProgressUpdate(values);
            if (values[2]==0L){
                textView.setText("Downloading the NO."+values[3]+" item's cover image.");
            }
            if (values[2]==1L){
                textView.setText("Downloading the NO."+values[3]+" item's mp3 file");
            }
            textView1.setText(values[0]+"/ "+values[1]);
            progressBar.setMax(values[1].intValue());
            progressBar.setProgress(values[0].intValue());

        }
    }
}