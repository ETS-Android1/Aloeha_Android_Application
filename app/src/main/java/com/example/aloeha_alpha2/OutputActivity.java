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
import java.net.URI;

public class OutputActivity extends AppCompatActivity {

    ImageView selectedImage;/** image view where the user's input image will be displayed **/
    ImageView processedImage;/** image view where the reconstructed input image will be displayed **/
    String imgString64;/** base 64 string that contains data of user's selected image. this will be passed to python. **/
    public static final String INTENT_TAG_IMAGE= "image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);
        selectedImage = findViewById(R.id.displayImageView);
        processedImage = findViewById(R.id.reconImageView);

        Uri image = getImageFromIntent(INTENT_TAG_IMAGE);

        selectedImage.setImageURI(image);/** display selected image on 'selectedImage' image view**/


        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image);/** Set URI to Bitmap **/
            imgString64= getStringImage(bitmap);/** encode bitmap to base64 string **/
        } catch (IOException e) {
            e.printStackTrace();
        }


        //Log.d("TEST",imgString64);

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


        /**Python APi**/
        if(!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }


        Python py = Python.getInstance();
        PyObject pyobj = py.getModule("Aloe-Detection_v2"); /**get python file**/

        PyObject obj = pyobj.callAttr("preprocessor",imgString64); /**get function from python file and set parameter as imgString64, return will be stored in obj **/

        /**the return value of the python method called previously will be a base 64 string. this converts the string back to an image. **/
        String str = obj.toString();
        byte data[] = android.util.Base64.decode(str,Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
        processedImage.setImageBitmap(bmp);


    }

    private Uri getImageFromIntent(String name){
        return Uri.parse(getIntent().getStringExtra("image"));

    }


    /**
     * a function that encodes bitmaps to base64 string.
     * @param bitmap bitmap
     * @return encoded image into base 64 string
     */
    private String getStringImage (Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

        byte[] imageBytes = baos.toByteArray();

        String encodedImage = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;

    }


}