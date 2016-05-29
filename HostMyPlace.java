package com.example.kishore.accommodate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.async.callback.BackendlessCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.files.BackendlessFile;

import java.util.ArrayList;
import java.util.List;


public class HostMyPlace extends Activity {

    Button hosts_continueToDb;
    private static final int SELECT_PHOTO = 100;
    private String backendImagePath;
    private Bitmap imageToUpload;
    public static Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_myplace);

        continueButton = (Button) findViewById(R.id.hostsContinue);
        continueButton.setClickable(false);
        continueButton.setEnabled(false);

        // Spinner element
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Spinner click listener
    //    spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        final List<String> categories = new ArrayList<String>();
        categories.add("Apartment");
        categories.add("House");
        categories.add("Hostel");
        categories.add("Villa");
        categories.add("Other");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);



        // Spinner element2
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        // Spinner click listener
        //    spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories2 = new ArrayList<String>();
        categories2.add("Entire Home");
        categories2.add("Single room");
        categories2.add("Shared room");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories2);
        // Drop down layout style - list view with radio button
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner2.setAdapter(dataAdapter2);

        // Spinner element3
            Spinner spinner3 = (Spinner) findViewById(R.id.spinner3);
            // Spinner click listener
            //    spinner.setOnItemSelectedListener(this);

            // Spinner Drop down elements
            List<String> categories3 = new ArrayList<String>();
            categories3.add("1");
            categories3.add("2");
            categories3.add("3");
            categories3.add("4");
            categories3.add("5+");

            // Creating adapter for spinner
            ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories3);
            // Drop down layout style - list view with radio button
            dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // attaching data adapter to spinner
            spinner3.setAdapter(dataAdapter3);

        //hosts continue to DB
        hosts_continueToDb = (Button) findViewById(R.id.hostsContinue);
        hosts_continueToDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Spinner accommodationType = (Spinner) findViewById(R.id.spinner);
                String txtAccommodationType = accommodationType.getSelectedItem().toString();

                Spinner roomType = (Spinner)findViewById(R.id.spinner2);
                String txtRoomType = roomType.getSelectedItem().toString();

                Spinner roomFor = (Spinner)findViewById(R.id.spinner3);
                String txtRoomFor = roomFor.getSelectedItem().toString();

                String txtCity = ((EditText) findViewById(R.id.txtCity)).getText().toString();
                String txtLocality = ((EditText) findViewById(R.id.txtLocality)).getText().toString();

                continueHostsToDb(txtAccommodationType,txtRoomType,txtRoomFor,txtCity,txtLocality);
            }
        });
    }

    private void continueHostsToDb(String accommodationType,String roomType,String roomFor,String city,String location) {

        final BackendlessUser user = new BackendlessUser();
        user.setEmail( "kishore.ps.cse@gmail.com" );



        //while(backendImagePath.isEmpty() || backendImagePath == null)
        {

        }

        Toast.makeText(getApplicationContext(), backendImagePath, Toast.LENGTH_LONG).show();

        Backendless.Persistence.save( new Hosts(user.getEmail() ,accommodationType,roomType,roomFor,city,location,backendImagePath), new BackendlessCallback<Hosts>()
        {
            @Override
            public void handleResponse( Hosts h)
            {
                Log.i("Hosts", "New house hosted " + user.getEmail());
            }
        } );

        Context context = getApplicationContext();
        CharSequence text = "New house hosted by " + user.getEmail();
        int duration = Toast.LENGTH_LONG;
        Toast hosts_Hosted = Toast.makeText(context,text,duration);
        hosts_Hosted.show();

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_host_aplace, menu);
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

    public void uploadImage(View view) {

        Intent photoPickerIntent = new Intent();
        photoPickerIntent.setType("image/*");
        photoPickerIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {

                    Uri uri = imageReturnedIntent.getData();

                    try {
                        imageToUpload = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                        if (imageToUpload != null) {

                            Long tsLong = System.currentTimeMillis()/1000;
                            String ts = tsLong.toString();

                            Backendless.Files.Android.upload(imageToUpload,
                                    Bitmap.CompressFormat.PNG,
                                    100,
                                    ts+".png",
                                    "mypics",
                                    new AsyncCallback<BackendlessFile>() {
                                        @Override
                                        public void handleResponse(final BackendlessFile backendlessFile) {

                                            backendImagePath = backendlessFile.getFileURL();

                                            continueButton = (Button) findViewById(R.id.hostsContinue);
                                            continueButton.setClickable(true);
                                            continueButton.setEnabled(true);

                                            //continueButton.setClickable(true);

                                            Toast.makeText(getApplicationContext(), " path coming" + backendImagePath, Toast.LENGTH_LONG).show();
                                            //fileMapping.profile_url = backendlessFile.getFileURL();
                                        }

                                        @Override
                                        public void handleFault(BackendlessFault backendlessFault) {
                                            Toast.makeText(getApplicationContext(), "backend exception!!", Toast.LENGTH_LONG).show();
                                        }
                                    });
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Image NULLLL!!", Toast.LENGTH_LONG).show();
                        }
//                        ImageView imageView = (ImageView) findViewById(R.id.imgView);
//                        imageView.setImageBitmap(imageToUpload);

                    }
                    catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Exception!!", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
        }
    }

}
