package smb.pja.smbproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import smb.pja.smbproject.first.FirstExerciseMain;
import smb.pja.smbproject.fourth.MapsActivity;

public class ChoiseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choise);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
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
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void onClickFifthExcercise(View view) {
    }
}
