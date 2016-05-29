package com.example.kishore.accommodate;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Kishore on 23-May-16.
 */
//public class DownloadImages extends AsyncTask {
//
//    @Override
//    protected Object doInBackground(Object[] objects) {
//        //URL urls = (URL)objects;
//        int count = urls.length;
//        long totalSize = 0;
//        for (int i = 0; i < count; i++) {
//            totalSize += Downloader.downloadFile(urls[i]);
//            publishProgress((int) ((i / (float) count) * 100));
//            // Escape early if cancel() is called
//            if (isCancelled()) break;
//        }
//        return totalSize;
//    }
//
////    @Override
////    protected void onProgressUpdate(Integer... progress) {
////        //setProgressPercent(progress[0]);
////    }
//
////    @Override
////    protected void onPostExecute(Long result) {
////        showDialog("Downloaded " + result + " bytes");
////    }
//}

public class GetImageTask extends AsyncTask<String, int[], Bitmap> {

    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap bitmap = null;

        int count = params.length;

        for (int i = 0; i < count; i++) {

            String s = params[i];
            try {
                URL url = null;
                url = new URL(s);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                //conn.setRequestMethod("GET");
                InputStream in = urlConnection.getInputStream();
                Bitmap imageFromDB = BitmapFactory.decodeStream(in);
                if (imageFromDB == null) {
                    //Toast.makeText(getApplicationContext(), "Image from DB null ", Toast.LENGTH_SHORT).show();
                    continue;
                } else {
                    return Bitmap.createScaledBitmap(imageFromDB, 900, 960, true);
                    //Toast.makeText(getApplicationContext(), "DB Image not null ", Toast.LENGTH_SHORT).show();
                }

                //dbImgView.setImageBitmap(imageFromDB);
            }catch (Exception e) {
                //Toast.makeText(getApplicationContext(), "Image DB catch" + e.getMessage() , Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

        // Get your image bitmap here

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmapResult) {
        super.onPostExecute(bitmapResult);
        // Add your image to your view
        //holder.avatar.setImageBitmap(bitmapResult);
        //ImageView dbImgView = (ImageView) relativeLayout.findViewById(R.id.dbImageView);
    }
}
