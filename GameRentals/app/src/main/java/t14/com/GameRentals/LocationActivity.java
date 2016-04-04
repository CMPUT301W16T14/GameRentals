package t14.com.GameRentals;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.overlays.MapEventsOverlay;
import org.osmdroid.bonuspack.overlays.MapEventsReceiver;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.tileprovider.MapTileProviderBase;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MyLocationOverlay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.Serializable;

@SuppressWarnings("deprecation")
public class LocationActivity extends Activity implements MapEventsReceiver {

    public static final String MOCK_PROVIDER = "mockLocationProvider";
    public GeoPoint mapPoint = new GeoPoint(53.5444, -113.4909);
    private MyLocationOverlay myLocationOverlay;
    private Marker mapMarker;
    private MapView map;
    private IMapController mapController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        // Get the system's location manager
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Get the last known location
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            // Default location is edmonton
            mapPoint = new GeoPoint(location);

        }
        // Listener for updating location
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1,
                listener);

        // Retrieved from
        // http://stackoverflow.com/questions/22804650/how-to-handle-long-press-on-a-map-using-osmdroid-osmbonuspack-in-android
        // (April 2, 2015)
        // Builds a mapview on the current location
        map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
        map.setClickable(true);
        mapController = map.getController();
        mapController.setZoom(9);
        mapController.setCenter(mapPoint);

        // Allows for map animations
        myLocationOverlay = new MyLocationOverlay(getApplicationContext(), map);
        map.getOverlays().add(myLocationOverlay);
        myLocationOverlay.enableCompass();

        // Retrieved from
        // http://android-coding.blogspot.ca/2012/07/osmdroid-mapview-to-follow-user.html
        // (April 3, 2015)
        myLocationOverlay.runOnFirstFix(new Runnable() {
            public void run() {
                map.getController()
                        .animateTo(myLocationOverlay.getMyLocation());
            }
        });

        // A marker on the current location
        mapMarker = new Marker(map);
        mapMarker.setPosition(mapPoint);
        mapMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        map.getOverlays().add(mapMarker);

        // Event listener for user touches
        MapEventsOverlay evOverlay = new MapEventsOverlay(this, this);
        map.getOverlays().add(evOverlay);
        map.invalidate();
    }

    private final LocationListener listener = new LocationListener() {
        public void onLocationChanged(Location location) {

        }

        public void onProviderDisabled(String provider) {

        }

        public void onProviderEnabled(String provider) {

        }

        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
    };

    @SuppressWarnings("deprecation")
    @Override
    protected void onResume() {
        super.onResume();
        myLocationOverlay.enableMyLocation();
        myLocationOverlay.enableFollowLocation();
    }

    @SuppressWarnings({ "deprecation" })
    @Override
    protected void onPause() {
        super.onPause();
        myLocationOverlay.disableMyLocation();
        myLocationOverlay.disableFollowLocation();
        map.getTileProvider().clearTileCache();
    }

    protected void onDestroy() {
        final MapTileProviderBase mapTileProvider = this.map.getTileProvider();
        mapTileProvider.clearTileCache();
        super.onDestroy();
    }

    public void onClickGeo(View v) {
        Intent intent = new Intent();
        intent.putExtra("MAP_POINT", (Serializable) mapPoint);
        setResult(RESULT_OK, intent);
        finish();
    }

    public boolean longPressHelper(GeoPoint arg0) {
        return singleTapConfirmedHelper(arg0);
    }

    /**
     * SingleTapConfirmedHelper is an event handler that sets the marker
     * location onto where the user touched the screen and refreshe the map
     *
     */
    @Override
    public boolean singleTapConfirmedHelper(GeoPoint arg0) {
        mapPoint = arg0;
        mapMarker.setPosition(arg0);
        map.invalidate();
        Toast.makeText(LocationActivity.this, mapPoint.toString(), Toast.LENGTH_SHORT).show();
        return false;
    }
}
