package com.getfreerecharge.trainschedule.activities;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.getfreerecharge.trainschedule.R;
import com.getfreerecharge.trainschedule.adapters.PnrStatusAdapter;
import com.getfreerecharge.trainschedule.models.pnr.Passenger;
import com.getfreerecharge.trainschedule.models.pnr.PnrStatusPojo;
import com.getfreerecharge.trainschedule.utillss.ConnectionCheck;
import com.getfreerecharge.trainschedule.utillss.RestInterface;
import com.getfreerecharge.trainschedule.utillss.ServiceGenerator;
import com.getfreerecharge.trainschedule.webs.SharedPreference;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.gson.JsonObject;
import com.victor.loading.book.BookLoading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PnrStatus extends AppCompatActivity implements View.OnClickListener {

    AdRequest adRequest;
    SharedPreference myprefs;
    InterstitialAd interstitialAd;
    private Handler handler1 = new Handler();
    LinearLayout bannerAd;

    BookLoading bookLoading;

    RestInterface restInterface;
    Call<PnrStatusPojo> pnrStatusPojoCall;
    LinearLayout ll2, ll1, ll3, ll4;
    EditText input_pnr;
    TextView get_pnr_status;
    TextView train_no, train_name, boardind_date, train_from, train_to, total_passenger, train_bookibg_status, train_charting_status;

    ListView pnr_status_list;
    PnrStatusAdapter pnrStatusAdapter;
    ArrayList<Passenger> passengerArrayList;

    TextView homeactionbar, hometitlequote, hometitlequoteone, errortext;

    String fontPathone = "fonts/Quicksand-Bold.ttf";
    String fontPathtwo = "fonts/Quicksand-Light.ttf";
    String fontPaththree = "fonts/Quicksand-Medium.ttf";
    String fontPathfour = "fonts/Quicksand-Regular.ttf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pnr_status);

        myprefs = new SharedPreference(PnrStatus.this);

        connectionCheck();
        adMobFullPageAd();

        restInterface= ServiceGenerator.createService(RestInterface.class);
        passengerArrayList=new ArrayList<>();
        pnrStatusAdapter = new PnrStatusAdapter(this,passengerArrayList);

        ll1= (LinearLayout) findViewById(R.id.ll1);
        ll2= (LinearLayout) findViewById(R.id.ll2);
        ll3 = (LinearLayout) findViewById(R.id.ll3);
        ll4 = (LinearLayout) findViewById(R.id.ll4);

        input_pnr= (EditText) findViewById(R.id.input_pnr);
        get_pnr_status= (TextView) findViewById(R.id.get_pnr_status);
        Typeface tf1 = Typeface.createFromAsset(getAssets(), fontPaththree);
        get_pnr_status.setTypeface(tf1);
        get_pnr_status.setOnClickListener(this);
        bookLoading = (BookLoading) findViewById(R.id.bookloading);

        initViews();
    }

    private void initViews() {
        train_no= (TextView) findViewById(R.id.train_no);
        train_name= (TextView) findViewById(R.id.train_name);
        boardind_date= (TextView) findViewById(R.id.boardind_date);
        train_from= (TextView) findViewById(R.id.train_from);
        train_to= (TextView) findViewById(R.id.train_to);
        total_passenger= (TextView) findViewById(R.id.total_passenger);
        train_charting_status= (TextView) findViewById(R.id.train_charting_status);
        pnr_status_list= (ListView) findViewById(R.id.pnr_status_list);

        homeactionbar = (TextView) findViewById(R.id.homeactionbar);
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPathone);
        homeactionbar.setTypeface(tf);

        hometitlequoteone = (TextView) findViewById(R.id.hometitlequoteone);
        Typeface tf1 = Typeface.createFromAsset(getAssets(), fontPaththree);
        hometitlequoteone.setTypeface(tf1);

        Typeface tf2 = Typeface.createFromAsset(getAssets(), fontPaththree);
        hometitlequote = (TextView) findViewById(R.id.hometitlequote);
        hometitlequote.setTypeface(tf2);
        errortext = (TextView) findViewById(R.id.errortext);
        errortext.setTypeface(tf2);


    }


    @Override
    public void onClick(View v) {
        if (v == get_pnr_status)
        {
            pnrStatus();
            InputMethodManager inputMethodManager = (InputMethodManager) PnrStatus.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(PnrStatus.this.getCurrentFocus().getWindowToken(), 0);
            ll1.setVisibility(View.GONE);
            new MyTask().execute();

        }
    }

    private void pnrStatus() {
        String pnr = input_pnr.getText().toString();
        System.out.println("Amit pnr " + pnr);
        final JsonObject jsonObject=new JsonObject();
        pnrStatusPojoCall=restInterface.getPnrStatusPojo(pnr, "y98sokxj");
        pnrStatusPojoCall.enqueue(new Callback<PnrStatusPojo>() {
            @Override
            public void onResponse(Call<PnrStatusPojo> call, Response<PnrStatusPojo> response) {
                if (response.isSuccessful())
                {
                    System.out.println("go" + response.toString());
                    System.out.println("go1" + response.body().getResponseCode());
                    System.out.println("go2" + response.body().getTrainNum());
                    PnrStatusPojo pnrStatusPojo = response.body();
                    if (response.body().getResponseCode().toString().equals("200"))
                    {
                        passengerArrayList.clear();
                        List<Passenger> detail = pnrStatusPojo.getPassengers();
                        System.out.println("go3" + response.body().getTrainNum());
                        System.out.println("go3" + response.body().getBoardingPoint().getCode());
                        System.out.println("go3" + response.body().getReservationUpto().getCode());
                        train_no.setText(response.body().getTrainNum().toString());
                        train_name.setText(response.body().getTrainName().toString());
                        boardind_date.setText(response.body().getDoj().toString());
                        train_from.setText(response.body().getBoardingPoint().getCode().toString());
                        train_to.setText(response.body().getReservationUpto().getCode().toString());
                        total_passenger.setText(response.body().getTotalPassengers().toString());
                        for (int i = 0; i<detail.size(); i++)
                        {
                            Passenger passengerdetails = new Passenger();
                            passengerdetails.setCoachPosition(detail.get(i).getCoachPosition());
                            passengerdetails.setBookingStatus(detail.get(i).getBookingStatus());
                            passengerdetails.setNo(detail.get(i).getNo());
                            passengerdetails.setCurrentStatus(detail.get(i).getCurrentStatus());
                            passengerArrayList.add(passengerdetails);
                        }


                        ll2.setVisibility(View.VISIBLE);
                    }
                    pnrStatusAdapter = new PnrStatusAdapter(getApplicationContext(),passengerArrayList);
                    pnr_status_list.setAdapter(pnrStatusAdapter);
                    if (response.body().getResponseCode().toString().equals("204"))
                    {
                        ll3.setVisibility(View.VISIBLE);
                    }
                }
                else
                {
                    ll3.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<PnrStatusPojo> call, Throwable t) {
                ll3.setVisibility(View.VISIBLE);
            }
        });
    }

    private class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ll1.setVisibility(View.GONE);
            ll2.setVisibility(View.GONE);
            ll3.setVisibility(View.GONE);
            ll4.setVisibility(View.VISIBLE);
            bookLoading.start();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (pnrStatusAdapter.getCount() == 0) {
                ll1.setVisibility(View.GONE);
                ll2.setVisibility(View.GONE);
                ll3.setVisibility(View.VISIBLE);
                ll4.setVisibility(View.GONE);
            } else {
                ll1.setVisibility(View.GONE);
                ll2.setVisibility(View.VISIBLE);
                ll3.setVisibility(View.GONE);
                ll4.setVisibility(View.GONE);
            }

            bookLoading.stop();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                for (int i = 0; i < 5; i++) {
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////


    private void connectionCheck() {
        ConnectionCheck connectionCheck = new ConnectionCheck(getApplicationContext());
        interstitialAd = new InterstitialAd(getApplicationContext());
        if (connectionCheck.isConnectionAvailable(this)) {
            interstitialAd.setAdUnitId(myprefs.getAddmob());
            adRequest = new AdRequest.Builder().build();
            interstitialAd.loadAd(adRequest);
            addAdmobAdListner();
            //adMobBannerAd();
        }
    }

    private void addAdmobAdListner() {
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                }
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {

            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();

            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }
        });
    }

//    private void adMobBannerAd() {
//        bannerAd = (LinearLayout) findViewById(R.id.myAdd);
//        final AdView adView = new AdView(this);
//        adView.setAdUnitId(myprefs.getAddbanner());
//        adView.setAdSize(AdSize.BANNER);
//        bannerAd.addView(adView);
//        final AdListener listener = new AdListener() {
//            @Override
//            public void onAdLoaded() {
//                adView.setVisibility(View.VISIBLE);
//                super.onAdLoaded();
//            }
//        };
//        adView.setAdListener(listener);
//        AdRequest adRequest1 = new AdRequest.Builder().build();
//        adView.loadAd(adRequest1);
//    }

    Runnable mShowFullPageAdTask = new Runnable() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (interstitialAd.isLoaded())
                        interstitialAd.show();
                }
            });
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        handler1.postDelayed(mShowFullPageAdTask, 45 * 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler1.removeCallbacks(mShowFullPageAdTask);

    }

    private void adMobFullPageAd() {
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId((myprefs.getAddmob()));
        requestNewInterstitial();
        interstitialAd.setAdListener(new AdListener() {

            @Override
            public void onAdClosed() {
                requestNewInterstitial();
            }
        });
    }

    private void requestNewInterstitial() {
        adRequest = new AdRequest.Builder().build();
        interstitialAd.loadAd(adRequest);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////



}
