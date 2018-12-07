package smb.pja.smbproject.first;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import smb.pja.smbproject.R;

public class SignUpActivity extends AppCompatActivity {

    private EditText login;
    private EditText password;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        login = findViewById(R.id.sign_up_login_text_editor);
        password = findViewById(R.id.sign_up_password_text_editor);

        auth = FirebaseAuth.getInstance();
    }

    public void createAccount(View view) {
        String log = login.getText().toString().trim();
        String pass = password.getText().toString().trim();

        if (TextUtils.isEmpty(log)) {
            Toast.makeText(this, "Proszę podać e-mail", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "Proszę podać hasło", Toast.LENGTH_LONG).show();
            return;
        }

        auth.createUserWithEmailAndPassword(log, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SignUpActivity.this, "Konto dodane, można się zalogować", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
