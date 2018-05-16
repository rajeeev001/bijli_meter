package bijli.meter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddDevice extends AppCompatActivity {
    private EditText DeviceName;
    private EditText Room;
    private EditText DeviceId;
    private Button Add;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);

        DeviceName=(EditText)findViewById(R.id.deviceName);
        Room = (EditText)findViewById(R.id.room);
        Add=(Button)findViewById(R.id.addButton);
        DeviceId=(EditText) findViewById(R.id.deviceId);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=firebaseDatabase.getReference(firebaseAuth.getUid());

        progressDialog = new ProgressDialog(this);


        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    progressDialog.setMessage("Adding");
                    progressDialog.show();
                    sendDeviceData();
                    progressDialog.dismiss();
                    Toast.makeText(AddDevice.this,"Device Added",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(AddDevice.this,myDevices.class);
                    startActivity(intent);
                    finish();

                }
                else {
                    Toast.makeText(AddDevice.this,"input requirred",Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent(AddDevice.this,AddDevice.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private boolean validate(){
        String device=DeviceName.getText().toString();
        String room=Room.getText().toString();
        String id=DeviceId.getText().toString();

        if(device.isEmpty() || room.isEmpty() || id.isEmpty())
            return false;
        else
            return true;
    }

    private void sendDeviceData(){
        String deviceName = DeviceName.getText().toString();
        String room=Room.getText().toString();
        String id=DeviceId.getText().toString();
        databaseUser =FirebaseDatabase.getInstance().getReference("devices");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID=user.getUid();
        String deviceStatus="ON";
        CreateDevice createDevice=new CreateDevice(userID,deviceName,room,deviceStatus);
        databaseUser.child(id).setValue(createDevice);
    }
}
