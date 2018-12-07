package smb.pja.smbproject.first;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import smb.pja.smbproject.R;
import smb.pja.smbproject.first.list.ListActivity;

public class SignInActivity extends AppCompatActivity {

    private EditText login;
    private EditText password;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        login = findViewById(R.id.sign_in_login_text_editor);
        password = findViewById(R.id.sign_in_password_text_editor);

        auth = FirebaseAuth.getInstance();
    }

    public void redirectToSignUp(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void signUp(View view) {
        String mail = login.getText().toString().trim();
        String pass = password.getText().toString().trim();

        if (TextUtils.isEmpty(mail)) {
            Toast.makeText(this, "Proszę podać e-mail", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "Proszę podać hasło", Toast.LENGTH_LONG).show();
            return;
        }

        auth.signInWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(SignInActivity.this, ListActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "Niepoprawne dane", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
