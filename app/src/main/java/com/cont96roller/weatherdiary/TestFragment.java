package com.cont96roller.weatherdiary;

import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TestFragment extends Fragment {

//    private LocationManager locationManager;
//    private static final int REQUEST_CODE_LOCATION = 2;



//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//        //사용자의 위치 수신을 위한 세팅
//        locationManager = (LocationManager)getContext().getSystemService(Context.LOCATION_SERVICE);
////사용자의 현재 위치
//        Location userLocation = getMyLocation();
//        if( userLocation != null ) {
//            double latitude = userLocation.getLatitude();
//            double longitude = userLocation.getLongitude();
//            userVO.setLat(latitude);
//            userVO.setLon(longitude);
//            System.out.println("////////////현재 내 위치값 : "+latitude+","+longitude);
//        }
//
//        /**
//         * 사용자의 위치를 수신
//         */
//        private Location getMyLocation() {
//            Location currentLocation = null;
//            // Register the listener with the Location Manager to receive location updates
//            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                System.out.println("////////////사용자에게 권한을 요청해야함");
//                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, this.REQUEST_CODE_LOCATION);
//                getMyLocation(); //이건 써도되고 안써도 되지만, 전 권한 승인하면 즉시 위치값 받아오려고 썼습니다!
//            } else {
//                System.out.println("////////////권한요청 안해도됨");
//
//                // 수동으로 위치 구하기
//                String locationProvider = LocationManager.GPS_PROVIDER;
//                currentLocation = locationManager.getLastKnownLocation(locationProvider);
//                if (currentLocation != null) {
//                    double lng = currentLocation.getLongitude();
//                    double lat = currentLocation.getLatitude();
//                }
//            }
//            return currentLocation;
//        }
//
//
//}
}
