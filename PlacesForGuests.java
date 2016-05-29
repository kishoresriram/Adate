package com.example.kishore.accommodate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.io.FileOutputStream;
import java.util.List;


public class PlacesForGuests extends Activity {

        private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewPlacesForGuests();
        setContentView(R.layout.activity_places_for_guests);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_places_for_guests, menu);
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

    private void viewPlacesForGuests()
    {
        try {

            Backendless.Persistence.of( Hosts.class).find( new AsyncCallback<BackendlessCollection<Hosts>>(){
                @Override
                public void handleResponse( BackendlessCollection<Hosts> foundContacts )
                {
                    List<Hosts> hostsList = foundContacts.getData();

                    for (int i = 0; i < hostsList.size(); i++) {
                        LayoutInflater layoutInflater = getLayoutInflater();
                        RelativeLayout relativeLayout = (RelativeLayout) layoutInflater.inflate(R.layout.activity_relativelayout_for_places_list, null);


                        TextView accommodationTypePlace = (TextView) relativeLayout.findViewById(R.id.txtAccommodationType);
                        TextView roomTypePlace = (TextView) relativeLayout.findViewById(R.id.txtRoomType);
                        //TextView roomForPlace = (TextView) relativeLayout.findViewById(R.id.txtRoomFor);
                        TextView cityPlace = (TextView) relativeLayout.findViewById(R.id.txtCityPlace);
                        TextView localityPlace = (TextView) relativeLayout.findViewById(R.id.txtLocalityPlace);

                        final ImageView dbImgView = (ImageView) relativeLayout.findViewById(R.id.dbImageView);

                        final Hosts tempHost = hostsList.get(i);
                        accommodationTypePlace.setText(tempHost.getAccommodationType());
                        roomTypePlace.setText(tempHost.getRoomType());
                        //roomForPlace.setText(tempHost.getRoomFor());
                        cityPlace.setText(tempHost.getCity());
                        localityPlace.setText(tempHost.getLocation());

                        Bitmap imageFromDB = null;

                        if(tempHost.getHostedImageUrl() != null && !tempHost.getHostedImageUrl().isEmpty() && !tempHost.getHostedImageUrl().equals("null"))
                        {
                            Toast.makeText(getApplicationContext(), tempHost.getHostedImageUrl(), Toast.LENGTH_SHORT).show();
                            //image code
                            try {
                                imageFromDB = new GetImageTask().execute(tempHost.getHostedImageUrl()).get();
                                dbImgView.setImageBitmap(imageFromDB);
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Image DB catch" + e.getMessage() , Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }
                        else
                        {
                            continue;
                        }

                        linearLayout = (LinearLayout) findViewById(R.id.innerLinearLayout);
                        linearLayout.addView(relativeLayout);
                        final Bitmap finalImageFromDB = imageFromDB;
                        relativeLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                layoutClicked(tempHost.getAccommodationType(),tempHost.getRoomType(),"",
                                        tempHost.getCity(),tempHost.getLocation(), finalImageFromDB);
                            }
                        });
                    }


                }
                @Override
                public void handleFault( BackendlessFault fault )
                {
                    // an error has occurred, the error code can be retrieved with fault.getCode()
                }
            });





        }
        catch (Exception e)
        {
            e.printStackTrace();
            Log.d("Message - ", e.getMessage());
        }
    }


    private void layoutClicked(String accommodationType,String roomType,String roomFor,String city,String location,Bitmap bmp)
    {
        Intent intent = new Intent(this,PlaceDetail.class);
        String fullText = "A secure "+accommodationType+" of which "+roomType+" is available.\n It can hold " + roomFor +" guests "
                + "\n We wish you a wonderful time at " + city;
        intent.putExtra("txt_fullText",fullText);
//        intent.putExtra("imgView",bmp);

        //Convert to byte array
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
//        byte[] byteArray = stream.toByteArray();
//        intent.putExtra("image",byteArray);
        try {
            //Write file
            String filename = "bitmap.png";
            FileOutputStream stream = this.openFileOutput(filename, Context.MODE_PRIVATE);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);

            //Cleanup
            stream.close();
            bmp.recycle();

            intent.putExtra("image",filename);

        }catch (Exception e) {
            e.printStackTrace();
        }




        startActivity(intent);
        //Toast.makeText(getApplicationContext(),"Work in Progress for "+city,Toast.LENGTH_SHORT).show();
    }

}
