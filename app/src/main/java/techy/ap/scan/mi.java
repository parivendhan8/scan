package techy.ap.scan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;

public class mi extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private MapFragment mapFragment;

    Marker mCurrLocationMarker;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        try {

            initilizeMap();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initilizeMap() {

         SupportMapFragment supportMapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(
                    R.id.map_fragmet));
        assert supportMapFragment != null;
        supportMapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;


    }
}
