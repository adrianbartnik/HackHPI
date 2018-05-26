package hackhpi.de.graden;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    MainActivity mainActivity;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment fragment = null;

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = getMapFragment();
                    break;
                case R.id.navigation_dashboard:
                    Toast.makeText(getApplicationContext(), "Sorry, not implemented :(", Toast.LENGTH_LONG).show();
                    return true;
                case R.id.navigation_notifications:
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
    }

    public Fragment getMapFragment() {
        SupportMapFragment fragment = SupportMapFragment.newInstance();

        fragment.getMapAsync(this);

        return fragment;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        for (Garden garden : getGardens()) {
            LatLng latLng = new LatLng(garden.lat, garden.lng);
            googleMap.addMarker(new MarkerOptions().position(latLng).title(garden.name));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                startActivity(intent);

                return true;
            }
        });
    }

    public List<Garden> getGardens() {
        List<Garden> gardens = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Garden garden = new Garden();

            garden.name = "Garden " + i;
            garden.owner = "Owner " + i;
            garden.lat = 52.520008 + ((new Random().nextBoolean()) ? new Random().nextFloat() * .3 : -new Random().nextFloat() * .3);
            garden.lng = 13.404954 + ((new Random().nextBoolean()) ? new Random().nextFloat() * .3 : -new Random().nextFloat() * .3);

            gardens.add(garden);
        }

        return gardens;
    }
}
