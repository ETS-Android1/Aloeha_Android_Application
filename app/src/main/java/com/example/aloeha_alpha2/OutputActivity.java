package com.example.aloeha_alpha2;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class OutputActivity extends AppCompatActivity {

    ImageView selectedImage;
    ImageView processedImage;
    TextView textview;
    String imgString64;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);
        selectedImage = findViewById(R.id.displayImageView);
        processedImage = findViewById(R.id.reconImageView);
        JavaToPython j2p = new JavaToPython();

        Uri image = Uri.parse(getIntent().getStringExtra("image"));
        selectedImage.setImageURI(image);
        textview = (TextView)findViewById(R.id.textView);

        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image);
            imgString64= getStringImage(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("TEST",imgString64);

        /*try {
            File file = new File("file.txt");
            file.createNewFile();
            FileWriter myWriter = new FileWriter("file.txt");
            myWriter.write(imgString64);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        Log.d("UNIT TEST", imgString64);
        */




        /*
        if(!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }


        Python py = Python.getInstance();
        PyObject pyobj = py.getModule("Aloe-Detection_v2");

        PyObject obj = pyobj.callAttr("preprocessor",imgString64);
        String str = obj.toString();
        byte data[] = android.util.Base64.decode(str,Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
        processedImage.setImageBitmap(bmp);

         */
    }


    private String getStringImage (Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

        byte[] imageBytes = baos.toByteArray();

        String encodedImage = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;

    }


}