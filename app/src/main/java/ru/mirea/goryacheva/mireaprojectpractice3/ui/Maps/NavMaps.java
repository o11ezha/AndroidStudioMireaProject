package ru.mirea.goryacheva.mireaprojectpractice3.ui.Maps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.RequestPoint;
import com.yandex.mapkit.RequestPointType;
import com.yandex.mapkit.directions.DirectionsFactory;
import com.yandex.mapkit.directions.driving.DrivingOptions;
import com.yandex.mapkit.directions.driving.DrivingRoute;
import com.yandex.mapkit.directions.driving.DrivingRouter;
import com.yandex.mapkit.directions.driving.DrivingSession;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.layers.GeoObjectTapEvent;
import com.yandex.mapkit.layers.GeoObjectTapListener;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.GeoObjectSelectionMetadata;
import com.yandex.mapkit.map.InputListener;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.map.UserData;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.Error;
import com.yandex.runtime.image.ImageProvider;
import com.yandex.runtime.network.NetworkError;
import com.yandex.runtime.network.RemoteError;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ru.mirea.goryacheva.mireaprojectpractice3.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NavMaps#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NavMaps extends Fragment implements GeoObjectTapListener, InputListener, DrivingSession.DrivingRouteListener  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NavMaps() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NavMaps.
     */
    // TODO: Rename and change types and number of parameters
    public static NavMaps newInstance(String param1, String param2) {
        NavMaps fragment = new NavMaps();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private MapView mapView;
    private final String MAPKIT_API_KEY = "MY_API_KEY";

    private final Point ROUTE_START_LOCATION = new Point(39.901848, 116.391433);
    private final Point ROUTE_END_LOCATION = new Point(55.794229, 37.700772);
    private final Point SCREEN_CENTER = new Point(
            (ROUTE_START_LOCATION.getLatitude() + ROUTE_END_LOCATION.getLatitude()) / 2,
            (ROUTE_START_LOCATION.getLongitude() + ROUTE_END_LOCATION.getLongitude()) / 2);

    private MapObjectCollection mapObjects;

    private DrivingRouter drivingRouter;
    private DrivingSession drivingSession;

    private int[] colors = {0xFFFF0000, 0xFF00FF00, 0x00FFBBBB, 0xFF0000FF, 0xffff00ff};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MapKitFactory.setApiKey(MAPKIT_API_KEY);
        MapKitFactory.initialize(getContext());
        DirectionsFactory.initialize(getContext());

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION}, 1);
        }

        View view = inflater.inflate(R.layout.fragment_nav_maps, container, false);

        mapView =  view.findViewById(R.id.mapview);
        // Устанавливаем начальную точку и масштаб
        mapView.getMap().move(new CameraPosition(SCREEN_CENTER, 10, 0, 0));
        // Ининциализируем объект для создания маршрута водителя
        drivingRouter = DirectionsFactory.getInstance().createDrivingRouter();
        mapObjects = mapView.getMap().getMapObjects().addCollection();
        submitRequest();

        mapView.getMap().addTapListener(this);
        setMarks();

        return view;
    }

    @Override
    public void onDrivingRoutes(@NonNull List<DrivingRoute> list) {
        int color;
        for (int i = 0; i < list.size(); i++) {
            // настроиваем цвета для каждого маршрута
            color = colors[i];
            // добавляем маршрут на карту
            mapObjects.addPolyline(list.get(i).getGeometry()).setStrokeColor(color);
        }
    }

    @Override
    public void onDrivingRoutesError(@NonNull Error error) {
        String errorMessage = getString(R.string.unknown_error_message);
        if (error instanceof RemoteError) {
            errorMessage = getString(R.string.remote_error_message);
        } else if (error instanceof NetworkError) {
            errorMessage = getString(R.string.network_error_message);
        }
    }

    private void setMarks(){
        mapObjects = mapView.getMap().getMapObjects().addCollection();
        PlacemarkMapObject mark1 = mapObjects.addPlacemark(new Point(55.673296, 37.480067), ImageProvider.fromResource(requireContext(), R.drawable.search_layer_pin_selected_default));
        mark1.setUserData(new MireaInfo("Кампус на Проспекте Вернадского, 78","28 мая 1947 г.","Адрес: 119454, ЦФО, г. Москва, Проспект Вернадского, д. 78"));
        PlacemarkMapObject mark2 = mapObjects.addPlacemark(new Point(55.661445, 37.477049), ImageProvider.fromResource(requireContext(), R.drawable.search_layer_pin_selected_default));
        mark2.setUserData(new MireaInfo("Кампус на Проспекте Вернадского, 86","28 мая 1947 г.","Адрес: 119571, ЦФО, г. Москва, Проспект Вернадского, д. 86"));
        PlacemarkMapObject mark3 = mapObjects.addPlacemark(new Point(55.794259, 37.701448), ImageProvider.fromResource(requireContext(),  R.drawable.search_layer_pin_selected_default));
        mark3.setUserData(new MireaInfo("Кампус на улице Стромынка","28 мая 1947 г.","Адрес: 107996, ЦФО, г. Москва, ул. Стромынка, д.20"));
        PlacemarkMapObject mark4 = mapObjects.addPlacemark(new Point(55.731630, 37.574914), ImageProvider.fromResource(requireContext(), R.drawable.search_layer_pin_selected_default));
        mark4.setUserData(new MireaInfo("Кампус на улице Малая Пироговская","28 мая 1947 г.","Адрес: 119435, ЦФО, г. Москва, улица Малая Пироговская, д. 1, стр. 5"));
        PlacemarkMapObject mark5 = mapObjects.addPlacemark(new Point(55.764911, 37.741962), ImageProvider.fromResource(requireContext(), R.drawable.search_layer_pin_selected_default));
        mark5.setUserData(new MireaInfo("Кампус на улице Соколиная Гора","28 мая 1947 г.","Адрес: 105275, ЦФО, г. Москва, 5-я улица Соколиной Горы, д. 22"));
        PlacemarkMapObject mark6 = mapObjects.addPlacemark(new Point(55.728591, 37.572998), ImageProvider.fromResource(requireContext(),  R.drawable.search_layer_pin_selected_default));
        mark6.setUserData(new MireaInfo("Кампус на улице Усачева","28 мая 1947 г.","Адрес: 119048, ЦФО, г. Москва, ул. Усачева, д.7/1"));
        PlacemarkMapObject mark7 = mapObjects.addPlacemark(new Point(45.073636, 41.933735), ImageProvider.fromResource(requireContext(), R.drawable.search_layer_pin_selected_default));
        mark7.setUserData(new MireaInfo("Филиал в г. Ставрополе","28 мая 1947 г.","Адрес: 355035, Ставропольский край, г. Ставрополь, Проспект Кулакова, д. 8 в квартале 601"));
        PlacemarkMapObject mark8 = mapObjects.addPlacemark(new Point(55.965870, 38.048827), ImageProvider.fromResource(requireContext(),  R.drawable.search_layer_pin_selected_default));
        mark8.setUserData(new MireaInfo("Филиал в г. Фрязино","28 мая 1947 г.","Адрес: г. Фрязино, ул. Вокзальная, д.2а (территория предприятия ОАО «НПП «Исток» им. Шокина"));
        tap(mark1);
        tap(mark2);
        tap(mark3);
        tap(mark4);
        tap(mark5);
        tap(mark6);
        tap(mark7);
        tap(mark8);
    }

    public void tap(PlacemarkMapObject mark){
        mark.addTapListener((mapObject, point) -> {
            Toast.makeText(requireContext(), (mapObject.getUserData().toString()), Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    @Override
    public boolean onObjectTap(@NonNull GeoObjectTapEvent geoObjectTapEvent) {
        final GeoObjectSelectionMetadata selectionMetadata = geoObjectTapEvent.getGeoObject()
                .getMetadataContainer()
                .getItem(GeoObjectSelectionMetadata.class);

        if (selectionMetadata != null) {
            mapView.getMap().selectGeoObject(selectionMetadata.getId(), selectionMetadata.getLayerId());
        }

        return selectionMetadata != null;
    }

    @Override
    public void onMapTap(@NonNull Map map, @NonNull Point point) {
        mapView.getMap().deselectGeoObject();
    }

    @Override
    public void onMapLongTap(@NonNull Map map, @NonNull Point point) { }

    @Override
    public void onStop() {
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }
    @Override
    public void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }

    private void submitRequest() {
        DrivingOptions options = new DrivingOptions();
        options.setAlternativeCount(5);

        ArrayList<RequestPoint> requestPoints = new ArrayList<>();

        requestPoints.add(new RequestPoint(ROUTE_START_LOCATION, RequestPointType.WAYPOINT, null));
        requestPoints.add(new RequestPoint(ROUTE_END_LOCATION, RequestPointType.WAYPOINT, null));

        drivingSession = drivingRouter.requestRoutes(requestPoints, options, this);
    }

    private class MireaInfo {
        String name;
        String dateOfCreation;
        String address;

        public MireaInfo(String name, String dateOfCreation, String address) {
            this.name = name;
            this.dateOfCreation = dateOfCreation;
            this.address = address;
        }

        @Override
        public String toString() {
            return name + '\n' + dateOfCreation + '\n' + address;
        }
    }
}