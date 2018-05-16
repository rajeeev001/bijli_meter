package bijli.meter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class Login extends Activity {
    private EditText Email;
    private EditText Password;
    private Button refLogin;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        FirebaseUser user=firebaseAuth.getCurrentUser();

        if(user != null){
            finish();
            Intent intent=new Intent(Login.this,Home.class);
            startActivity(intent);
        }

        Button forgotPasswordBtn = (Button) findViewById(R.id.forgotPassword);

        forgotPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent classActivity = new Intent("com.example.lenovopc.jagrati.FORGOTPASSWORD");
                startActivity(classActivity);
            }
        });

        getEmailPass();

        refLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=Email.getText().toString();
                String password=Password.getText().toString();
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Login.this, "Email and Password can't be Empty", Toast.LENGTH_LONG).show();
                }
                else {
                    validate(Email.getText().toString(), Password.getText().toString());
                }
            }
        });
    }

    private void getEmailPass(){
        Email = (EditText)findViewById(R.id.email);
        Password = (EditText)findViewById(R.id.password);
        refLogin = (Button)findViewById(R.id.logIn);


    }

    private void validate(String email, String password){
        progressDialog.setMessage("running");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(Login.this,"Login Successful",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Login.this,Home.class);
                    startActivity(intent);
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(Login.this,"wrong credential",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
