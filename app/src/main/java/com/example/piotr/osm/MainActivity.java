package com.example.piotr.osm;

import android.content.Context;

import org.osmdroid.bonuspack.kml.KmlDocument;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.overlay.FolderOverlay;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;


public class MainActivity extends AppCompatActivity {

    MapView map = null;
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        setContentView(R.layout.activity_main);

        map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
    }

    public void onResume(){
        super.onResume();
        map.onResume();
    }

    public void onPause(){
        super.onPause();
        map.onPause();
    }
    public void GeoJSONParse(String geoJSON){
        KmlDocument kmlDocument = new KmlDocument();
        kmlDocument.parseGeoJSON(geoJSON);
        Drawable defaultMarker = getResources().getDrawable(R.drawable.marker_default);
        Bitmap defaultBitmap = ((BitmapDrawable) defaultMarker).getBitmap();
        Paint.Style defaultStyle = new Paint(defaultBitmap, 0x901010AA, 5f, 0x20AA1010);
        FolderOverlay geoJsonOverlay = (FolderOverlay) kmlDocument.mKmlRoot.buildOverlay(map, defaultStyle, null, kmlDocument);
        map.getOverlays().add(geoJsonOverlay);
        map.invalidate();
    }
}
