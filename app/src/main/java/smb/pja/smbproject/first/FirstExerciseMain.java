package smb.pja.smbproject.first;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import smb.pja.smbproject.R;
import smb.pja.smbproject.first.list.ListActivity;
import smb.pja.smbproject.first.options.OptionsActivity;

public class FirstExerciseMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_exercise_main);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.first_exercise_list_button:
                changeViewToList();
                break;
            case R.id.first_exercise_options_button:
                changeViewToOptions();
                break;
            default:
                Toast.makeText(this, "Kliknij w przycisk", Toast.LENGTH_LONG).show();
        }

    }

    private void changeViewToList() {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    private void changeViewToOptions() {
        Intent intent = new Intent(this, OptionsActivity.class);
        startActivity(intent);
    }
}
