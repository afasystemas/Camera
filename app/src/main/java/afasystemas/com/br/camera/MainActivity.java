package afasystemas.com.br.camera;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Picture;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.File;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class MainActivity extends AppCompatActivity {

    private Camera mCamera;
    private CameraPreview mPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button capture = (Button)findViewById(R.id.button_capture);

        boolean cameraReleased = checkCameraHardware(this);
        if(cameraReleased){
            mCamera = getCameraInstance(this);

            mPreview = new CameraPreview(this,mCamera);
            FrameLayout preview = (FrameLayout)findViewById(R.id.camera_preview);
            preview.addView(mPreview);

            capture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CameraPreview myPicture = new CameraPreview(getApplicationContext(),mCamera);

                    mCamera.takePicture(null,null, myPicture.mPicture);
                }
            });

        }
        else{
            Toast.makeText(this, "Câmera não pode ser utilizada!", Toast.LENGTH_SHORT).show();
        }





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        return super.onOptionsItemSelected(item);
        int id = item.getItemId();
        return false;

    }

    public static Camera getCameraInstance(Context context){
        Camera c = null;
        try {
            c = Camera.open();

        }catch (Exception e ){
            Toast.makeText(context, "Câmera nao disponível", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(context, c.toString(), Toast.LENGTH_SHORT).show();
        return c;
    }

    private boolean checkCameraHardware(Context context){
        if(context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            return true;
        }
        else{
            return false;
        }
    }



}
