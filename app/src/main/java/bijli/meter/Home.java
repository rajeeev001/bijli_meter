package bijli.meter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class Home extends Activity {
    private Button LogoutButton;
    private ImageButton Mydevice;
    private ImageButton Home;
    private ImageButton Expenses;
    private ImageButton Profile;
    FirebaseAuth firebaseAuth;
    private TextView wholeData;
    private TextView bill;
    String idArray[]=new String[1000];
    DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private  String todayData="0";
    private String monthData="0";
    private int count=0;
    private String deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        deviceId = getIntent().getStringExtra("device");
        if(deviceId!=null){Intent intent=new Intent(Home.this,myDevices.class);
            startActivity(intent);
            finish();
        }

        firebaseAuth = FirebaseAuth.getInstance();
        LogoutButton = (Button)findViewById(R.id.logout);
        Mydevice = (ImageButton)findViewById(R.id.myDevices);
        Home = (ImageButton)findViewById(R.id.home);
        Expenses=(ImageButton)findViewById(R.id.expenses);
        Profile=(ImageButton)findViewById(R.id.profile);
        wholeData=(TextView) findViewById(R.id.unitsConsumed);
        bill=(TextView) findViewById(R.id.estimatrdaBill);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("devices");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                count=0;
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userID=user.getUid();

                //Log.d("userId",userID);
                if(userID==null){
                    Intent intent = new Intent(Home.this, LoginSignup.class);
                    startActivity(intent);
                    finish();
                }

                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    String id=ds.child("id").getValue().toString();

                    id = ds.child("id").getValue().toString();
                    if(id.equals(userID)) {
                        id=ds.getKey();
                        //idArray[count] = id;
                        //count++;
                       // Log.d("hmm",String.valueOf(count));

                        try {
                            databaseReference = firebaseDatabase.getReference().child("deviceData").child(id);
                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    String userID = user.getUid();

                                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                      //  Log.d("data", ds.toString());
                                        Calendar now = Calendar.getInstance();
                                        String year = String.valueOf(now.get(Calendar.YEAR));
                                        String month = String.valueOf(now.get(Calendar.MONTH) + 1);
                                        String day = String.valueOf(now.get(Calendar.DAY_OF_MONTH));


                                        String data = ds.getValue().toString();
                                        //Log.d("data",data);
                                        String key = ds.getKey().toString();
                                        String keyMonth = String.valueOf(key.charAt(5));
                                        String keyDay = String.valueOf(key.charAt(7));

                                        //Log.d("day", month);
                                        if (keyMonth.equals(month)) {
                                            if (data != null) {
                                                monthData = String.valueOf(Float.parseFloat(monthData)
                                                        + Float.parseFloat(data));
                                               // Log.d("data", monthData);
                                            }
                                        }
                                        String date1 = year + "-" + month + "-" + day;
                                        if (key.equals(date1)) {
                                            if (data != null) {
                                                todayData = data;
                                            }
                                        }



                                       // Log.d("monthData", monthData);
                                       // Log.d("todayData", todayData);

                                        wholeData.setText(todayData);
                                        float f=Float.parseFloat(todayData)*7;
                                        bill.setText("₹ "+String.valueOf(f));
                                    }



                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                        catch (Exception e){
                           // Log.d("moveoN","moveON");
                        }


                    }




                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        Mydevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Home.this,myDevices.class);
                startActivity(intent);
                finish();
            }
        });

        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Home.this,Home.class);
                startActivity(intent);
            }
        });

        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Home.this,Profile.class);
                startActivity(intent);
                finish();
            }
        });










    }
}
