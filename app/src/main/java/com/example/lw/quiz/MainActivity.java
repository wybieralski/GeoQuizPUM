package com.example.lw.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView mScoreView, mQuestion;
    private ImageView mImageView;
    private Button mTrueButton, mFalseButton;
    private ImageButton mBackButton, mNextButton;

    private boolean mAnswer, mWasTrue=true;
    private int mScore = 0;
    private int mQuestionNumber = 0;
    private int mCurrentIndex = 0;

    public int getQuestionNumber(){
        return mQuestionBank.length;
    }
    // All questions
    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_stolica_polski, true),
            new Question(R.string.question_stolica_dolnego_slaska, false),
            new Question(R.string.question_sniezka, true),
            new Question(R.string.question_wisla, true)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mScoreView = (TextView) findViewById(R.id.points);
        mQuestion = (TextView) findViewById(R.id.question);
        mTrueButton = (Button) findViewById(R.id.trueButton);
        mFalseButton = (Button) findViewById(R.id.falseButton);
        mBackButton = (ImageButton) findViewById(R.id.backButton);
        mNextButton = (ImageButton) findViewById(R.id.nextButton);

        //new
        mQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuestionNumber = (mQuestionNumber + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
                updateScore(mScore);
            }
        });
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
                updateScore(mScore);
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mQuestionNumber < mQuestionBank.length-1)
                    mQuestionNumber = (mQuestionNumber + 1);
                updateQuestion();
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( mQuestionNumber == 0 )
                    mQuestionNumber = 0;
                else
                    mQuestionNumber = (mQuestionNumber -1);
                updateQuestion();
            }
        });

        updateQuestion();

    }

    private void updateQuestion() {
            int question = mQuestionBank[mQuestionNumber].getTextResId();
            mQuestion.setText(question);

    }



    public void updateScore(int point){
        if (mQuestionNumber <= mQuestionBank.length)
            mScoreView.setText(""+mScore);
    }


    private void checkAnswer(boolean userPressedTrue) {

        boolean answerIsTrue = mQuestionBank[mQuestionNumber].isAnswerTrue();

        int toastMessageId=0;

        if (userPressedTrue == answerIsTrue) {
            toastMessageId = R.string.correct_toast;
            if (mScore <= mQuestionNumber){
                if(mWasTrue==true)
                    mScore +=1;
            }
            mWasTrue=true;
        } else {
            toastMessageId =  R.string.incorrect_toast;
            mWasTrue=false;
        }

        if (mQuestionNumber == mQuestionBank.length) {
            Intent i = new Intent(MainActivity.this, ResultsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("finalScore", mScore);
            i.putExtras(bundle);
//            MainActivity.this.finish();
            startActivity(i);
        }

        Toast toast = Toast.makeText(this, toastMessageId, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP,0,150);
        toast.show();

        if (mQuestionNumber < mQuestionBank.length-1){
            mQuestionNumber = (mQuestionNumber + 1);
            updateQuestion();
        }
    // if it's end, run ResultActivity

        if (mQuestionNumber == mQuestionBank.length-1){
            Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
            Bundle b = new Bundle();
            b.putInt("score", mScore); //Your id
            b.putInt("number", mQuestionBank.length); //Your id
            intent.putExtras(b); //Put your id to your next Intent
            startActivity(intent);
            finish();
        }

    }

}
