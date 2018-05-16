package bijli.meter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class myDevices extends Activity {
    private Button addDevice;
    private Button Device;
    ListView listView;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    CreateDevice createDevice;
    String id;
    String key;
    String keyArray[]=new String[200];
    int count =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_devices);

        listView=(ListView)findViewById(R.id.listview);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        createDevice = new CreateDevice();
        arrayList=new ArrayList<>();
        arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
        databaseReference=firebaseDatabase.getReference().child("devices");

        addDevice = (Button)findViewById(R.id.addDevice);
        //Device = (Button)findViewById(R.id.device1);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                String userId=user.getUid();
                if(userId==null){
                    Intent intent = new Intent(myDevices.this, LoginSignup.class);
                    startActivity(intent);
                    finish();
                }
                count=0;
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    id=ds.child("id").getValue().toString();
                    String device = ds.child("device").getValue().toString().toUpperCase();
                    String room = ds.child("room").getValue().toString().toUpperCase();
                    key=ds.getKey();

                    if(id.equals(userId)){
                       //Log.d("device", device);
                        //Log.d("room", room);

                        arrayList.add(room );
                        keyArray[count]=key;
                        count++;
                        Log.d("room",room);
                    }
                }
                listView.setAdapter(arrayAdapter);
               listView.setOnItemClickListener(myListClickListener);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        addDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(myDevices.this,AddDevice.class);
                startActivity(intent);
            }
        });
    }

    private AdapterView.OnItemClickListener myListClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            Intent intent = new Intent(myDevices.this, DeviceAppliances.class);
            intent.putExtra("device",keyArray[i]);
            startActivity(intent);
            finish();



        }
    };
}
