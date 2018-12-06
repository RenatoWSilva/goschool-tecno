package br.com.goschool.goschool_mobile.activity.maps;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ModulesMaps.DirectionFinder;
import ModulesMaps.DirectionFinderListener;

import ModulesMaps.Route;
import ModulesMaps.Student;
import br.com.goschool.goschool_mobile.R;
import br.com.goschool.goschool_mobile.api.APIStudent;
import br.com.goschool.goschool_mobile.resource.StudentResource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, DirectionFinderListener {

    private GoogleMap mMap;
    private Button btnFindPath;
    private EditText etOrigin;
    private EditText etDestination;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;
    private List<Marker> waypointsMarkers = new ArrayList<>();
    private List<Student> students = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btnFindPath = findViewById(R.id.btnFindPath);
        etOrigin = findViewById(R.id.etOrigin);
        etDestination = findViewById(R.id.etDestination);

        etOrigin.setText("-16.765557, -49.349650");
        etDestination.setText("-16.671435, -49.236181");

        btnFindPath.setOnClickListener(v -> sendRequest());
    }

    private void sendRequest() {
        String origin = etOrigin.getText().toString();
        String destination = etDestination.getText().toString();
        if (origin.isEmpty()) {
            Toast.makeText(this, "Por favor, informe o endereço de origem!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (destination.isEmpty()) {
            Toast.makeText(this, "Por favor, informe o endereço de destino!", Toast.LENGTH_SHORT).show();
            return;
        }

        createStudentsArray(origin, destination);

//        try {
//            new DirectionFinder(this, origin, destination, students).execute();
//        }catch (UnsupportedEncodingException e){
//            e.printStackTrace();
//        }
    }

    private void createStudentsArray(String origin, String destination){
        Retrofit retrofit = APIStudent.getStudent();

        StudentResource studentResource = retrofit.create(StudentResource.class);

        Call<List<Student>> get = studentResource.get();

        get.enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                students = response.body();
                callGoogleMapsAPI(origin, destination);
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
            }
        });

//        students.add(new Student(4, "Terminal Cruzeiro", "-16.745183","-49.281941"));
//        students.add(new Student(1, "Terminal Isidória", "-16.713944","-49.252724"));
//        students.add(new Student(2, "Terminal Bandeiras", "-16.710327","-49.310519"));
//        students.add(new Student(3, "Terminal Praça A", "-16.673473","-49.284325"));
//        students.add(new Student(5, "Terminal Padre Pelágio", "-16.659399", "-49.326482"));
    }

    // Para ordenar por nome
    private void ordenaPorDistancia(List<Student> lista) {
        Collections.sort(lista, new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                return s1.getDistance().compareTo(s2.getDistance());
            }
        });
    }

    private void callGoogleMapsAPI(String origin, String destination){
        try {
            new DirectionFinder(this, origin, destination, students).execute();
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
    }

    private void orderStudents(){
        List<Student> students = new ArrayList<>();


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng latLng = new LatLng(-16.765557, -49.349650);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 9));

        originMarkers.add(mMap.addMarker(new MarkerOptions()
                .title("Terminal Garavelo")
                .position(latLng)));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mMap.setMyLocationEnabled(true);
    }


    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(this, "Aguarde por favor.",
                "Criando rota...", true);

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline:polylinePaths ) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 12));
            ((TextView) findViewById(R.id.tvDuration)).setText(route.duration.text);
            ((TextView) findViewById(R.id.tvDistance)).setText(route.distance.text);

            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .title(route.startAddress)
                    .position(route.startLocation)));

            for (Student student: students) {
                waypointsMarkers.add(mMap.addMarker(new MarkerOptions()
                        .title(student.getEnrollment())
                        .position(new LatLng(Double.parseDouble(student.getLatitude()), Double.parseDouble(student.getLongitude())))));
            }

            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .title(route.endAddress)
                    .position(route.endLocation)));


            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }
    }
}