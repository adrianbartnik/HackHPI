package hackhpi.de.graden;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment fragment = null;

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = getMapFragment();
                    break;
                case R.id.navigation_messages:
                    Toast.makeText(getApplicationContext(), "Sorry, not implemented :(", Toast.LENGTH_LONG).show();
                    return true;
                case R.id.navigation_profile:
                    Toast.makeText(getApplicationContext(), "Profile", Toast.LENGTH_LONG).show();
                    return true;
            }

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, fragment, "blub")
                    .commit();

            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Fragment fragment = getMapFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, fragment, "blub")
                .commit();
    }

    public Fragment getMapFragment() {
        SupportMapFragment fragment = SupportMapFragment.newInstance();

        fragment.getMapAsync(this);

        return fragment;
    }

    private static final LatLng BERLIN = new LatLng(52.520008, 13.404954);


    @Override
    public void onMapReady(GoogleMap googleMap) {

        for (Garden garden : getGardens()) {
            LatLng latLng = new LatLng(garden.lat, garden.lng);

            BitmapDescriptor bitmap;

            if (garden.type.equals("Private")) {
                bitmap = BitmapDescriptorFactory.fromResource(R.drawable.flower_closed);
            } else {
                bitmap = BitmapDescriptorFactory.fromResource(R.drawable.flower_open);
            }

            Marker marker = googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(garden.name)
                    .icon(bitmap));
            marker.setTag(garden);
        }

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(BERLIN, 15));

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra(DetailActivity.GARDEN_PARAMETER, (Garden) marker.getTag());
                startActivity(intent);

                return true;
            }
        });
    }

    public List<Garden> getGardens() {
        List<Garden> gardens = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Garden garden = new Garden();

            garden.type = (new Random()).nextBoolean() ? "Private" : "Public";
            garden.lat = 52.520008 + ((new Random().nextBoolean()) ? new Random().nextFloat() * .005 : -new Random().nextFloat() * .005);
            garden.lng = 13.404954 + ((new Random().nextBoolean()) ? new Random().nextFloat() * .005 : -new Random().nextFloat() * .005);

            gardens.add(garden);
        }

        return gardens;
    }
}
