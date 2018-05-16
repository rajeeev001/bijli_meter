package bijli.meter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends Activity {
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogoLauncher logoLauncher = new LogoLauncher();

        logoLauncher.start();
        //ActionBar actionBar = getActionBar();
        //actionBar.hide();
    }

    private class LogoLauncher extends Thread{

        public void run(){
            try{
                sleep(2000);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
            firebaseAuth=FirebaseAuth.getInstance();
            FirebaseUser user=firebaseAuth.getCurrentUser();
            if(user!=null){
                Intent intent=new Intent(MainActivity.this,Home.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
            else {
                Intent intent = new Intent(MainActivity.this, LoginSignup.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        }

    }
}
