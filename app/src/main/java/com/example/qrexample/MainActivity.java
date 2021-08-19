package com.example.qrexample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;


public class MainActivity extends AppCompatActivity{

    WebView wv;
    EditText et, pt;
    private int point = 1000;
    private int bonus = 1000; // switch 나 if로 거리마다 보너스 세팅할 것
    Button bt, reScan;
    IntentIntegrator integrator;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et = findViewById(R.id.et);
        pt = findViewById(R.id.pt);
        wv = findViewById(R.id.wv);
        bt = findViewById(R.id.bt);
        reScan = findViewById(R.id.reScan);

        WebSettings webSettings = wv.getSettings();

        // point 조회 가능
        pt.setText(printInfo());


        //자바 스크립트 사용을 할 수 있도록 합니다.
        webSettings.setJavaScriptEnabled(true);

        wv.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view,String url){
                /*
                웹뷰 로딩 완료 후 로직 구역
                */
            }
        });
        et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    //bt의 onClick을 실행
                    bt.callOnClick();
                    //키보드 숨기기
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                    return true;
                }
                return false;
            }
        });


        integrator = new IntentIntegrator(this);

        integrator.setPrompt("QR 코드를 사각형 안에 위치 시켜주세요");

        //QR 코드 인식 시에 삐- 소리가 나게 할것인지 여부
        integrator.setBeepEnabled(false);

        integrator.setBarcodeImageEnabled(true);

        integrator.setCaptureActivity(CaptureActivity.class);

        //스캐너 시작 메소드
        integrator.initiateScan();
        
    }

    public void onClick(View view){
        String address = et.getText().toString();

        if(!address.startsWith("http://")){
            address = "http://" + address;
        }

        wv.loadUrl(address);
    }

    public void reScan(View view){
        integrator.initiateScan();
    }

    @Override
    public void onBackPressed() {
        if(wv.isActivated()){
            if(wv.canGoBack()){
                wv.goBack();
            }else{
                //스캐너 재시작
                integrator.initiateScan();
            }

        }else{
            super.onBackPressed();
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(result != null){
            if(result.getContents() == null){

            }else{
                //qr코드를 읽어서 EditText에 입력해줍니다.
                et.setText(result.getContents());
                addPoint();

                //Button의 onclick호출
                bt.callOnClick();

                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_SHORT).show();
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    public int getPoint() {
        return point;
    }

    // 적립될 포인트
    public void setPoint(int point) {
        this.point = point;
    }

    public void addPoint() {
        this.point += getBonus();
    }

    public String printInfo(){
        return "현재 Point : " + (getPoint() + getBonus()) + "원" +  "  적립된 Point : " + getBonus() + "원";
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }
}

