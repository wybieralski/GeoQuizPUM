package com.example.lw.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {

    MainActivity mainActivity;
    TextView mGrade,mFinalScore;
    Button mRetryButton, mQuitButton;
    int totalQuestions = mainActivity.getQuestionNumber();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);


        mGrade = (TextView)findViewById(R.id.grade);
        mFinalScore = (TextView)findViewById(R.id.outOf);
        mRetryButton=(Button)findViewById(R.id.retry);
        mQuitButton = (Button)findViewById(R.id.quit);

        Bundle bundle = getIntent().getExtras();
        int score = bundle.getInt("finalScore");
        mFinalScore.setText("Uzyskałeś "+score + " na "+ totalQuestions);

        mRetryButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(ResultsActivity.this,MainActivity.class));
                ResultsActivity.this.finish();
            }
        });

        mQuitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                ResultsActivity.this.finish();
                System.exit(0);            }
        });
    }
}
