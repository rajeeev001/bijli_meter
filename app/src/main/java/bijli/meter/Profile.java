package bijli.meter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends Activity {
    private ImageButton EditProfileButton;
    FirebaseAuth firebaseAuth;
    private Button ChangePasswordButton;
    private Button MyDevicesButton;
    private Button LogoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        firebaseAuth = FirebaseAuth.getInstance();
        EditProfileButton=(ImageButton)findViewById(R.id.editProfile);
        ChangePasswordButton=(Button)findViewById(R.id.changePassword);
        MyDevicesButton=(Button)findViewById(R.id.myDevices);
        LogoutBtn =(Button) findViewById(R.id.logout);



        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                Intent intent=new Intent(Profile.this,LoginSignup.class);
                startActivity(intent);
                finish();
            }
        });

        EditProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Profile.this,EditProfile.class);
                startActivity(intent);
            }
        });

        ChangePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Profile.this,ChangePassword.class);
                startActivity(intent);
            }
        });

        MyDevicesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Profile.this,myDevices.class);
                startActivity(intent);
            }
        });
    }
}
