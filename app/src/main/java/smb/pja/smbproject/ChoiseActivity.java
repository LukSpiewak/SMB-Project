package smb.pja.smbproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import smb.pja.smbproject.first.FirstExerciseMain;

public class ChoiseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choise);
    }

    public void onClickFirstExercise(View view) {
        Intent intent = new Intent(this, FirstExerciseMain.class);
        startActivity(intent);
    }

    public void onClickSecondExcercise(View view) {
    }

    public void onClickThirdExcercise(View view) {
    }

    public void onClickFourthExcercise(View view) {
    }

    public void onClickFifthExcercise(View view) {
    }
}
