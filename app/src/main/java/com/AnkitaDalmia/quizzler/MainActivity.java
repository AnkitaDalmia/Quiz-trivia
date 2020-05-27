package com.AnkitaDalmia.quizzler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    // TODO: Declare constants here


    // TODO: Declare member variables here:

    Button mTrueButton;
    Button mFalseButton;
    TextView mQuestionTextView;
    int mIndex;
    int mQuestion;
    ProgressBar mProgressBar;
    TextView  mScoreTextView;
    int mScore;

    // TODO: Uncomment to create question bank
    private TrueFalse[] mQuestionBank = new TrueFalse[] {
            new TrueFalse(R.string.question_1, true),
            new TrueFalse(R.string.question_2, true),
            new TrueFalse(R.string.question_3, true),
            new TrueFalse(R.string.question_4, true),
            new TrueFalse(R.string.question_5, true),
            new TrueFalse(R.string.question_6, false),
            new TrueFalse(R.string.question_7, true),
            new TrueFalse(R.string.question_8, false),
            new TrueFalse(R.string.question_9, true),
            new TrueFalse(R.string.question_10, true),
            new TrueFalse(R.string.question_11, false),
            new TrueFalse(R.string.question_12, false),
            new TrueFalse(R.string.question_13,true)
    };
    final int PROGRESS_BAR_INCREMENT= (int) Math.ceil(100.0/mQuestionBank.length);//since math.ceil takes decimal as input we make 100 as 100.0

    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //handling screenRotation
        if(savedInstanceState!=null){
            mScore=savedInstanceState.getInt("ScoreKey");
            mIndex=savedInstanceState.getInt("IndexKey");

        }
        else{
            mScore=0;
            mIndex=0;

        }

        mTrueButton=(Button)findViewById(R.id.true_button);
        mFalseButton=(Button)findViewById(R.id.false_button);

        mQuestionTextView=(TextView)findViewById(R.id.question_text_view);
        mScoreTextView=(TextView)findViewById(R.id.score);
        //mQuestiontextview.setText("hie");

        mProgressBar=(ProgressBar)findViewById(R.id.progress_bar);



        TrueFalse firstQuestion = mQuestionBank[mIndex];
        mQuestion=firstQuestion.getQuestionID();// or mQuestion=mQuestionBank[mIndex].getQuestionID()
        mQuestionTextView.setText(mQuestion);//it can take res if or text (string) as parameter

        mScoreTextView.setText("Score "+mScore+ "/" + mQuestionBank.length);


        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkAnswer(true);
                updateQuestion();

                //Toast.makeText(getApplicationContext(),"True pressed!",Toast.LENGTH_SHORT).show();
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkAnswer(false);
                updateQuestion();
                //Toast.makeText(getApplicationContext(),"False pressed!",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateQuestion(){

        mIndex=(mIndex+1) % mQuestionBank.length ;

        if(mIndex==0) {


            AlertDialog.Builder alert = new AlertDialog.Builder(this);//getApplicatonContext or (this) is same it returns current/main activity object
            alert.setTitle("Game Over");
            alert.setCancelable(false);//will not disable dialog box by clicking anywhere in the background
            alert.setMessage("You Scored " +mScore+ " points!");
            alert.setPositiveButton("Close Application", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    finish();//exits the app
                }
            });
            alert.show();
            //.setProgress(0);
           // mScore=0;
        }

        mQuestion=mQuestionBank[mIndex].getQuestionID();// or mQuestion=mQuestionBank[mIndex].getQuestionID()
        mQuestionTextView.setText(mQuestion);
        mProgressBar.incrementProgressBy(PROGRESS_BAR_INCREMENT);
        mScoreTextView.setText("Score "+mScore+ "/" + mQuestionBank.length);
    }
    private void checkAnswer(boolean userSelection){

        boolean correctAns=mQuestionBank[mIndex].isAnswer();
        if(userSelection==correctAns)
        {
            Toast.makeText(getApplicationContext(),R.string.correct_toast,Toast.LENGTH_SHORT).show();
            mScore+=1;
        }
        else{
            Toast.makeText(getApplicationContext(),R.string.incorrect_toast,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //info stored in bundle as key-Value Pair like dictionary
        outState.putInt("ScoreKey",mScore);
        outState.putInt("IndexKey",mIndex);
    }
}
