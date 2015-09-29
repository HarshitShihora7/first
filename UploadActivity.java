package com.dcode.classifieds.classifieds.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dcode.classifieds.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by Lenovo on 27-08-2015.
 */
public class UploadActivity extends AppCompatActivity{


    private static final int RESULT_LOAD_IMAGE = 1;
    private String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_upload);
        Button button= (Button) findViewById(R.id.upload_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_image();
            }
        });

    }


    public void select_image()
    {
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, RESULT_LOAD_IMAGE);
        
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if((requestCode == RESULT_LOAD_IMAGE) && (resultCode == RESULT_OK) && (null != data)){

            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            filePath=picturePath;
            new UploadFileToServer().execute();

        }


    }

    private class UploadFileToServer extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {

            Log.i("Entered background","");
            return upload(filePath);


        }
        public Void upload(String filepath){

            Log.i("Entered upload","");
            String responseString=null;

            HttpClient httpClient=new DefaultHttpClient();
            HttpPost httppost=new HttpPost("http://d-codesolutions.com/classifieds/index_go.php");


            File file=new File(filepath);
            MultipartEntity entity= new MultipartEntity();
            try {
                entity.addPart("test",new StringBody("testing string"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            entity.addPart("image", new FileBody(file));
            httppost.setEntity(entity);

            try {
                HttpResponse response = httpClient.execute(httppost);
                HttpEntity r_entity=response.getEntity();
                int statusCode=response.getStatusLine().getStatusCode();
                if(statusCode==200){
                    responseString= EntityUtils.toString(r_entity);
                }
                else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                }


            } catch (ClientProtocolException e) {
               Log.i("ClientProtocolExceptio","");
                responseString = e.toString();
            } catch (IOException e) {
                Log.i("IOException","");
                responseString = e.toString();
            }

            Log.i("response",responseString);

            Log.i("out of background","");
           // Toast.makeText(getBaseContext(),responseString,Toast.LENGTH_LONG).show();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.i("out of onpostexecute","");
        }
    }




}




