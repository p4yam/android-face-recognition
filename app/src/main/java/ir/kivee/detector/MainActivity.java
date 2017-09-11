package ir.kivee.detector;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView imgContainer;
    Button btnAddImage;
    Button btnDetectFaces;
    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap sourceImage;
    private String ERROR_TAG = "ERROR OCCURRED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgContainer = findViewById(R.id.image_container);
        btnAddImage = findViewById(R.id.select_image);
        btnDetectFaces = findViewById(R.id.detect_faces);

        btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "انتخاب عکس")
                        , PICK_IMAGE_REQUEST);
            }
        });

        btnDetectFaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VisionUtils.DetailedFaceRecognition(getApplicationContext(),sourceImage);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST &&
                resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                sourceImage = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imgContainer.setImageBitmap(sourceImage);
            } catch (Exception e) {
                Log.d(ERROR_TAG, e.getLocalizedMessage());
            }
        }
    }
}
