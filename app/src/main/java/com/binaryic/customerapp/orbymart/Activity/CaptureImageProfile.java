package com.binaryic.customerapp.orbymart.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.binaryic.customerapp.orbymart.Common.Utils;
import com.binaryic.customerapp.orbymart.Controller.ImageController;

import com.binaryic.customerapp.orbymart.R;
import com.theartofdev.edmodo.cropper.CropImageView;


public class CaptureImageProfile extends AppCompatActivity {
    String uri;
    Bitmap bitmap, bitmapThumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_image_profile);
        ImageView img_reset_image = (ImageView) findViewById(R.id.img_reset_image);
        ImageView img_back = (ImageView) findViewById(R.id.img_back);
        TextView tv_crop = (TextView) findViewById(R.id.tv_crop);
        final CropImageView img_capture = (CropImageView) findViewById(R.id.img_capture);
        getExtras();
        bitmap = Utils.uriToBitmap(this, uri);
        bitmapThumbnail = ThumbnailUtils.extractThumbnail(bitmap, 400, 600);
        img_capture.setImageBitmap(bitmapThumbnail);
        img_capture.setAspectRatio(1, 1);
        tv_crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Bitmap cropped = img_capture.getCroppedImage();
                    uploadProfile(cropped);
                } catch (Exception e) {
                    e.getMessage();
                }
            }
        });
        img_reset_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageController controller = new ImageController();
                controller.selectUploadType(CaptureImageProfile.this);
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getExtras() {
        Intent intent = getIntent();
        uri = intent.getStringExtra("imageBit");
        Log.e("Got Extra", "Capture Image");
    }

    private void uploadProfile(final Bitmap bitmapCropped) {
        Utils.showProgress("Save Profile picture...",CaptureImageProfile.this);
        final Bitmap bitmapSend = compressBitmap(bitmapCropped);
        Intent data = new Intent();
        data.putExtra("bitmap",bitmapSend);
        setResult(Activity.RESULT_OK, data);
        finish();
    }
    private Bitmap compressBitmap(Bitmap bitmapsend) {
        try {
            bitmapsend =  ThumbnailUtils.extractThumbnail(bitmapsend, 200, 200);
        } catch (Exception ex) {
        }
        return bitmapsend;
    }
}
