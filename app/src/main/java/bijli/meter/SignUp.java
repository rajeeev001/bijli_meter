package bijli.meter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends Activity {
    private EditText Name;
    private EditText Phone;
    private EditText Email;
    private EditText Password;
    private EditText rePassword;
    private EditText ProductId;
    private Button button;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private DatabaseReference databaseUser;
    String name,email,password,phone,productId,repassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setupUIview();

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        Button alreadyRegisteredBtn = (Button) findViewById(R.id.alreadyRgistered);

        alreadyRegisteredBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent classActivity = new Intent("com.example.lenovopc.jagrati.LOGIN");
                startActivity(classActivity);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    progressDialog.setMessage("running");
                    progressDialog.show();
                    String user_email=Email.getText().toString().trim();
                    String user_password=Password.getText().toString().trim();
                    firebaseAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                progressDialog.dismiss();
                                sendUserData();
                                Toast.makeText(SignUp.this, "Registration Successfull", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUp.this,Home.class));
                            }
                            else{
                                progressDialog.dismiss();
                                Toast.makeText(SignUp.this, "Registration Failled", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

}

    private void setupUIview(){
        Name=(EditText)findViewById(R.id.name);
        Phone=(EditText)findViewById(R.id.phone);
        Email=(EditText)findViewById(R.id.email);
        Password=(EditText)findViewById(R.id.password);
        rePassword=(EditText)findViewById(R.id.rePassowrd);
        ProductId=(EditText)findViewById(R.id.productid);
        button=(Button) findViewById(R.id.signup);
    }




    private boolean validate(){
        name=Name.getText().toString();
        phone=Phone.getText().toString();
        email=Email.getText().toString();
        password=Password.getText().toString();
        repassword=rePassword.getText().toString();
        productId=ProductId.getText().toString();

        if(name.isEmpty() || phone.isEmpty() || email.isEmpty() ||
                password.isEmpty() || repassword.isEmpty()){
            Toast.makeText(this,"please Enter all details",Toast.LENGTH_SHORT);
            return false;
        }
        else if(!password.equals(repassword))
            return false;
        else
            return true;
    }




    private void sendUserData(){
        databaseUser =FirebaseDatabase.getInstance().getReference("users");
        String id = databaseUser.push().getKey();
        User userProfile=new User(id,name,email,phone,password);
        databaseUser.child(id).setValue(userProfile);
    }




}
