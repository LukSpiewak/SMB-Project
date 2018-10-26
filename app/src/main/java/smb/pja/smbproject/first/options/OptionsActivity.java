package smb.pja.smbproject.first.options;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import smb.pja.smbproject.R;


public class OptionsActivity extends AppCompatActivity {

    private EditText sizeEditText;
    private Spinner colorSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        sizeEditText = findViewById(R.id.options_size_editText);
        colorSpinner = findViewById(R.id.options_color_spinner);
    }

    public void onClick(View view) {
        StringBuilder sb = new StringBuilder();
        sb.append(sizeEditText.getText().toString()).append(",");
        sb.append(colorSpinner.getSelectedItem().toString());

        Toast.makeText(this, sb.toString(), Toast.LENGTH_LONG)
                .show();
    }
}
