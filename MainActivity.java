package com.example.kishore.accommodate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.BackendlessCallback;


public class MainActivity extends Activity {

    private Button host_place_btn;
    private RadioGroup radioTitleGroup;
    private RadioButton radioSelectedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //backend less
        String appVersion = "v1";
        Backendless.initApp(this, "B019FC1F-368E-89F0-FFC2-3FC0B4DD4400", "5CDC4434-ADB4-C336-FFC7-6BA3B69CDA00", appVersion);

        radioTitleGroup=(RadioGroup)findViewById(R.id.radioGroup);

        //host a place button click
        host_place_btn = (Button) findViewById(R.id.btnGo);
        host_place_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = radioTitleGroup.getCheckedRadioButtonId();
                radioSelectedButton=(RadioButton)findViewById(selectedId);

                if(radioSelectedButton == (RadioButton)findViewById(R.id.radio_host))
                {
                    clickHostPlace();
                }
                else if (radioSelectedButton == (RadioButton)findViewById(R.id.radio_rent))
                {
                    clickRentPlace();
                    //sampleToast();
                }


            }
        });
        Display d = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        d.getSize(size);
        int displayHeight = size.x;

        //image animation
        ImageView mImageViewFilling = (ImageView) findViewById(R.id.imageview_animation_list_filling);
        mImageViewFilling.getLayoutParams().height = (displayHeight * 90) / 100;
        ((AnimationDrawable) mImageViewFilling.getBackground()).start();
    }

    private void clickHostPlace()
    {
        //registerUser();
        Intent intent = new Intent(this,HostMyPlace.class);
        startActivity(intent);
    }
    private void clickRentPlace()
    {
        Intent intent = new Intent(this,PlacesForGuests.class);
        startActivity(intent);
    }

    private void registerUser()
    {
        BackendlessUser user = new BackendlessUser();
        user.setEmail( "kishore.ps.cse@gmail.com" );
        user.setPassword( "123456" );

        Backendless.UserService.register( user, new BackendlessCallback<BackendlessUser>()
        {
            @Override
            public void handleResponse( BackendlessUser backendlessUser )
            {
                Context context = getApplicationContext();
                CharSequence text = "Registration" + backendlessUser.getEmail() + " successfully registered";
                int duration = Toast.LENGTH_LONG;
                Toast registration = Toast.makeText(context,text,duration);
                registration.show();
                Log.i("Registration", backendlessUser.getEmail() + " successfully registered");
            }
        } );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
