package ir.kivee.detector;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

/**
 * Created by payam on 9/11/17.
 */

public class VisionUtils {

    private static float smilingProbability = .6f;
    private static float openEyesProbability = .15f;

    static void DetailedFaceRecognition(Context context, Bitmap picture) {
        FaceDetector detector = new FaceDetector.Builder(context)
                .setTrackingEnabled(false)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .build();

        Frame frame = new Frame.Builder().setBitmap(picture).build();
        SparseArray<Face> faces = detector.detect(frame);
        if (faces.size() == 0)
            Toast.makeText(context, "هیچ چهره ای پیدا نشد", Toast.LENGTH_LONG).show();
        else {
            Toast.makeText(context, faces.size() + " چهره یافت شد ",
                    Toast.LENGTH_LONG).show();
            for (int i = 0; i < faces.size(); i++) {
                String expression = facialExpressions(faces.valueAt(i));
                Toast.makeText(context, expression, Toast.LENGTH_SHORT).show();
            }
        }

        detector.release();
    }

    private static String facialExpressions(Face face) {
        String facialExpression = face.getIsSmilingProbability() > smilingProbability ?
                "چهره خندان و " : "چهره خندان نیست و ";

        if (face.getIsRightEyeOpenProbability() > openEyesProbability
                && face.getIsLeftEyeOpenProbability() > openEyesProbability)
            facialExpression += "چشم ها باز هستند.";
        else if (face.getIsLeftEyeOpenProbability() > openEyesProbability)
            facialExpression += "چشم چپ باز است.";
        else if (face.getIsRightEyeOpenProbability() > openEyesProbability)
            facialExpression += "چشم راست باز است.";
        else
            facialExpression += "چشم ها بسته هستند.";

        return facialExpression;
    }
}


