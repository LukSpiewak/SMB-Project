package smb.pja.smbproject.first.options;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import smb.pja.smbproject.R;


public class OptionsActivity extends AppCompatActivity {

    private SharedPreferences sp;

    private EditText sizeEditText;
    private Spinner colorSpinner;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        sizeEditText = findViewById(R.id.options_size_editText);
        sizeEditText.setOnFocusChangeListener(sizeKeyListener());
        colorSpinner = findViewById(R.id.options_color_spinner);
        colorSpinner.setOnItemSelectedListener(itemSelectedListener());
        textView = findViewById(R.id.options_object_textView);
        setSharedPreferences();
    }

    @Override
    protected void onPause() {
        super.onPause();
        savePreferences();
    }

    public void onClick(View view) {
        StringBuilder sb = new StringBuilder();
        sb.append(sp.getFloat("options_size", 5f)).append(",");
        sb.append(sp.getInt("options_color", 0));

        Toast.makeText(this, sb.toString(), Toast.LENGTH_LONG)
                .show();
    }


    private void setSharedPreferences() {
        sp = this.getPreferences(Context.MODE_PRIVATE);
        fillSavePreferences();
    }

    private void savePreferences() {
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat("options_size", Float.valueOf(sizeEditText.getText().toString()));
        editor.putInt("options_color", colorSpinner.getSelectedItemPosition());
        editor.commit();
    }

    private void fillSavePreferences() {
        if (sp.contains("options_size")) {
            sizeEditText.setText(String.valueOf(sp.getFloat("options_size", 5f)));
        }

        if (sp.contains("options_color")) {
            colorSpinner.setSelection(sp.getInt("options_color", 0));
        }

    }

    private AdapterView.OnItemSelectedListener itemSelectedListener() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String color = (String) colorSpinner.getItemAtPosition(position);
                textView.setTextColor(getColorId(color));
            }

            private int getColorId(String color) {
                switch (color) {
                    case "CZERWONY":
                        return Color.RED;
                    case "CZARNY":
                        return Color.BLACK;
                    case "SZARY":
                        return Color.GRAY;
                    case "BIAŁY":
                        return Color.WHITE;
                    case "ZIELONY":
                        return Color.GREEN;
                    case "NIEBIESKI":
                        return Color.BLUE;
                    case "ŻÓŁTY":
                        return Color.YELLOW;
                    default:
                        return Color.RED;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // ignore
            }
        };
    }

    private View.OnFocusChangeListener sizeKeyListener() {
        return (v, hasFocus) -> {
            if (!hasFocus)  {
                textView.setTextSize(Float.valueOf(sizeEditText.getText().toString()));
            }
        };
    }
}
