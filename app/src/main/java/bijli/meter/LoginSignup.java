package bijli.meter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginSignup extends Activity {

    private TextView Signup;
    private TextView Login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);

        setupUIView();


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginSignup.this,Login.class);
                startActivity(intent);
            }
        });


        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginSignup.this,SignUp.class);
                startActivity(intent);
            }
        });
    }

    private void setupUIView(){
        Signup = (Button)findViewById(R.id.signUp);
        Login =  (Button)findViewById(R.id.logIn);
    }
}
