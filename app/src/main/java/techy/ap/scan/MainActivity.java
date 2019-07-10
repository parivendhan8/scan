package techy.ap.scan;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.ksoap2.serialization.SoapPrimitive;

import me.dm7.barcodescanner.zbar.ZBarScannerView;

import static android.Manifest.permission_group.CAMERA;
import static android.content.pm.PackageManager.*;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private TextView txtView;
    private FusedLocationProviderClient client;


    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtView = findViewById(R.id.txtView);


        client = LocationServices.getFusedLocationProviderClient(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{
                            Manifest.permission.CAMERA,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION

                    },
                    100);
        }

        requestPermission();

        txtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.layout.scan_fragment, new ScanFragment())

//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.first, new ScanFragment()) //<---replace a view in your layout (id: container) with the newFragment
//                        .commit();


//                startActivity(new Intent(getApplicationContext(), scanActivity.class));
                startActivity(new Intent(getApplicationContext(), mi.class));

            }
        });


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                Log.d(TAG, "onSuccess: " + location.getLatitude());
            }
        });

    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, 100);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        getSupportFragmentManager().popBackStackImmediate();


    }
}


