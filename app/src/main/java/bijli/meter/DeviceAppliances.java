package bijli.meter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.ProgressDialog;
import android.widget.Toast;

import java.lang.String;
import  java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.firebase.database.Query;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.ChildEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import android.widget.CheckBox;

public class DeviceAppliances extends Activity {

    private String deviceId;
    private String monthData="0";
    private String todayData="0";
    private TextView deviceName;
    private TextView st;
    private TextView dayData;
    private TextView wholeData;
    private Button OnBtn;
    private Button OffBtn;
    private CheckBox checkBox;

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference1;
    CreateDevice createDevice;
    private ProgressDialog progressDialog;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_appliances);
        dayData=(TextView) findViewById(R.id.todayData);
        wholeData=(TextView) findViewById(R.id.monthData);
        deviceName=(TextView)findViewById(R.id.deviceLabel);
        st=(TextView)findViewById(R.id.status);
        OnBtn=(Button) findViewById(R.id.on);
        OffBtn=(Button) findViewById(R.id.off);

        progressDialog = new ProgressDialog(this);

        deviceId = getIntent().getStringExtra("device");
        checkBox=(CheckBox)findViewById(R.id.switch1);
        Log.d("Id is: ", deviceId);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        createDevice = new CreateDevice();

        databaseReference = firebaseDatabase.getReference().child("deviceData").child(deviceId);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID=user.getUid();

        databaseReference1 = firebaseDatabase.getReference().child("devices").child(deviceId).child("status");
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String val=dataSnapshot.getValue(String.class);
                st.setText("Device Status: "+val);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        databaseReference1 = firebaseDatabase.getReference().child("devices").child(deviceId).child("device");
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String val=dataSnapshot.getValue(String.class);
                deviceName.setText(val.toUpperCase());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        OnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DeviceAppliances.this, "Device is Turned On", Toast.LENGTH_SHORT).show();

                databaseReference1 = firebaseDatabase.getReference().child("devices").child(deviceId).child("status");

                databaseReference1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String key = dataSnapshot.getKey();


                            databaseReference1.setValue("ON");
                            Intent intent = new Intent(DeviceAppliances.this, DeviceAppliances.class);
                            intent.putExtra("device", deviceId);
                            startActivity(intent);
                            finish();
//                        OnBtn.setEnabled(false);
//                        OffBtn.setEnabled(true);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });


        OffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DeviceAppliances.this, "Device is Turned Off", Toast.LENGTH_SHORT).show();

                databaseReference1 = firebaseDatabase.getReference().child("devices").child(deviceId).child("status");

                databaseReference1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String key = dataSnapshot.getKey();
                        databaseReference1.setValue("OFF");
                        Intent intent =new Intent(DeviceAppliances.this,DeviceAppliances.class);
                        intent.putExtra("device",deviceId);
                        startActivity(intent);
//                        OnBtn.setEnabled(true);
//                        OffBtn.setEnabled(false);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });



//        checkBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (((CheckBox) view).isChecked()) {
//                    Toast.makeText(DeviceAppliances.this, "Device is switch On", Toast.LENGTH_SHORT).show();
//
//                    databaseReference = firebaseDatabase.getReference().child("devices").child(deviceId).child("status");
//
//                    databaseReference.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            String key = dataSnapshot.getKey();
//                            databaseReference.setValue("ON");
//
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });
//
//
//
//                }
//
////               else {
////
////                    Toast.makeText(DeviceAppliances.this, "Device is Swithced Off", Toast.LENGTH_SHORT).show();
////
////                    databaseReference = firebaseDatabase.getReference().child("devices").child(deviceId).child("status");
////                    databaseReference.addValueEventListener(new ValueEventListener() {
////                        @Override
////                        public void onDataChange(DataSnapshot dataSnapshot) {
////                            String key = dataSnapshot.getKey();
////                            databaseReference.setValue("OFF");
////                            //Intent intent = new Intent(DeviceAppliances.this, DeviceAppliances.class);
////                            //intent.putExtra("device",deviceId);
////                            //startActivity(intent);
////
////                        }
////
////                        @Override
////                        public void onCancelled(DatabaseError databaseError) {
////
////                        }
////                    });
////
////
////
////                }
//            }
//        });



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int count=0;
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userID=user.getUid();

                for(DataSnapshot ds: dataSnapshot.getChildren()){
                  //  Log.d("data", ds.toString());
                    Calendar now = Calendar.getInstance();
                    String year = String.valueOf(now.get(Calendar.YEAR));
                    String  month = String.valueOf(now.get(Calendar.MONTH) + 1);
                    String day = String.valueOf(now.get(Calendar.DAY_OF_MONTH));




                    String data = ds.getValue().toString();
                    //Log.d("data",data);
                    String key=ds.getKey().toString();
                    Log.d("day",key);
                    String keyMonth=String.valueOf(key.charAt(5));
                    String keyDay=String .valueOf(key.charAt(7));


                    if(keyMonth.equals(month)) {
                        if (data != null) {
                            monthData = String.valueOf(Float.parseFloat(monthData)
                                    + Float.parseFloat(data));
                            //Log.d("data", monthData);
                        }
                    }
                    String date1=year+"-"+month+"-"+day;
                    if(key.equals(date1)) {
                        if (data != null) {
                            todayData = data;
                        }
                    }




                    //String data = ds.child(date1).getValue(String.class);


                    // if (data != null)
                    // todayData= data;
                    //Log.d("todayDate",date1);
                   // Log.d("monthData",monthData);
                   // Log.d("todayData",todayData);



                }
                wholeData.setText("Today's Consumptions: "+monthData);
                dayData.setText("Month's Consumptions: "+todayData);


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}