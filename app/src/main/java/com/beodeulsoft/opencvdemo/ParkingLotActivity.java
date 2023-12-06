package com.beodeulsoft.opencvdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ParkingLotActivity extends AppCompatActivity {
    ImageView[] imgCars = new ImageView[32];

    ImageView imgvSI;
    ProgressBar pbar;
    TextView txtP;

    TextView txtPtotal; //전체 자리
    TextView txtPU;     //사용중
    TextView txtPA;     //사용가능
    TextView txtvST;    //스마일 상태

    ArrayList<Car> carList = new ArrayList<Car>(); //데이터를 담을 자료구조
    ArrayList<String> ll = new ArrayList<String>();
    ArrayAdapter<String> spadapter;
    CarAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_lot);
        initializeViews();
        loadParkingData();
    }

    private void initializeViews() {

        // ImageView 초기화
        imgvSI = (ImageView) findViewById(R.id.imgvSI);
        imgCars[0] = (ImageView)findViewById(R.id.imgCar0);
        imgCars[1] = (ImageView)findViewById(R.id.imgCar1);
        imgCars[2] = (ImageView)findViewById(R.id.imgCar2);
        imgCars[3] = (ImageView)findViewById(R.id.imgCar3);
        imgCars[4] = (ImageView)findViewById(R.id.imgCar4);
        imgCars[5] = (ImageView)findViewById(R.id.imgCar5);
        imgCars[6] = (ImageView)findViewById(R.id.imgCar6);
        imgCars[7] = (ImageView)findViewById(R.id.imgCar7);
        imgCars[8] = (ImageView)findViewById(R.id.imgCar8);
        imgCars[9] = (ImageView)findViewById(R.id.imgCar9);

        imgCars[10] = (ImageView)findViewById(R.id.imgCar10);
        imgCars[11] = (ImageView)findViewById(R.id.imgCar11);
        imgCars[12] = (ImageView)findViewById(R.id.imgCar12);
        imgCars[13] = (ImageView)findViewById(R.id.imgCar13);
        imgCars[14] = (ImageView)findViewById(R.id.imgCar14);
        imgCars[15] = (ImageView)findViewById(R.id.imgCar15);
        imgCars[16] = (ImageView)findViewById(R.id.imgCar16);
        imgCars[17] = (ImageView)findViewById(R.id.imgCar17);
        imgCars[18] = (ImageView)findViewById(R.id.imgCar18);
        imgCars[19] = (ImageView)findViewById(R.id.imgCar19);

        imgCars[20] = (ImageView)findViewById(R.id.imgCar20);
        imgCars[21] = (ImageView)findViewById(R.id.imgCar21);
        imgCars[22] = (ImageView)findViewById(R.id.imgCar22);
        imgCars[23] = (ImageView)findViewById(R.id.imgCar23);
        imgCars[24] = (ImageView)findViewById(R.id.imgCar24);
        imgCars[25] = (ImageView)findViewById(R.id.imgCar25);
        imgCars[26] = (ImageView)findViewById(R.id.imgCar26);
        imgCars[27] = (ImageView)findViewById(R.id.imgCar27);
        imgCars[28] = (ImageView)findViewById(R.id.imgCar28);
        imgCars[29] = (ImageView)findViewById(R.id.imgCar29);

        imgCars[30] = (ImageView)findViewById(R.id.imgCar30);
        imgCars[31] = (ImageView)findViewById(R.id.imgCar31);


        // ProgressBar 초기화
        pbar = (ProgressBar) findViewById(R.id.pbar);

        // TextView 초기화
        txtP = (TextView) findViewById(R.id.txtP);
        txtPtotal = (TextView) findViewById(R.id.txtPtotal);
        txtPU = (TextView) findViewById(R.id.txtPU);
        txtPA = (TextView) findViewById(R.id.txtPA);
        txtvST = (TextView) findViewById(R.id.txtvST);

        // Car 객체를 보여줌
        mAdapter = new CarAdapter(this, R.layout.car_item);

        //새로고침 버튼
        Button btnRefresh = (Button) findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadParkingData();
            }
        });

    }

    private void loadParkingData() {
        String url = "http://winmfgd9.cafe24.com/getParkingData.php";

        // 배열 생성
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        carList.clear(); // 기존 차량 목록을 초기화

                        // JSON 배열 반복 처리
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                // 배열에서 각 JSON 객체를 가져옴
                                JSONObject obj = response.getJSONObject(i);

                                // 각 객체에서 'slot'과 'carEmpty' 정보 추출
                                int slot = obj.getInt("slot");
                                boolean carEmpty = obj.getInt("carEmpty") == 1;

                                // 새 Car 객체를 생성하고 차량 목록에 추가
                                Car car = new Car(slot, carEmpty);
                                carList.add(car);

                                // 각 차량의 상태에 따라 이미지 뷰 업데이트
                                imgCars[slot].setImageResource(car.getCarEmpty() ? R.drawable.car_g : R.drawable.car_r);
                            } catch (JSONException e) {
                                Log.e("ParkingLotActivity", "JSON Error", e);
                                e.printStackTrace();
                            }
                        }

                        // 새 차량 목록으로 초기화하고 업데이트
                        mAdapter.clear();
                        mAdapter.addAll(carList);
                        mAdapter.notifyDataSetChanged();

                        // 주차장 정보 업데이트
                        updateParkingInfo();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );

        // 요청 큐를 생성하고 배열 추가하여 실행
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonArrayRequest);
    }

    private void updateParkingInfo() {
        int emtpy = 0;
        for (Car car : carList) { //carList의 모든 car 객체에 반복
            if (!car.getCarEmpty()) {
                emtpy++;
            }
        }
        int max = imgCars.length; //전체 주차 공간 수
        int used = max - emtpy; //사용 중인 주차 공간 수 계산
        double usage = (double) used / max; // 주차장 사용률 계산

        pbar.setMax(max); //진행바 최대 값 설정
        pbar.setProgress(used); //진행 상태
        txtP.setText(String.format("%.2f%%", 100.0 * used / max)); //백분율로 표시

        txtPtotal.setText(String.valueOf(max)); //전체 자리수
        txtPU.setText(String.valueOf(used)); //사용중
        txtPA.setText(String.valueOf(emtpy)); //사용 가능

        // 스마일 상태 업데이트
        if (usage < 0.33) {
            imgvSI.setImageResource(R.drawable.level1); // 여유 상태 이미지
            txtvST.setText("여유");
        } else if (usage < 0.66) {
            imgvSI.setImageResource(R.drawable.level2); // 보통 상태 이미지
            txtvST.setText("보통");
        } else {
            imgvSI.setImageResource(R.drawable.level3); // 혼잡 상태 이미지
            txtvST.setText("혼잡");
        }
    }

}
