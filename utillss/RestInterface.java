package com.getfreerecharge.trainschedule.utillss;


import com.getfreerecharge.trainschedule.models.cancelledtrains.CancelledTrainPojo;
import com.getfreerecharge.trainschedule.models.fairenquirey.FairEnquiry;
import com.getfreerecharge.trainschedule.models.pnr.PnrStatusPojo;
import com.getfreerecharge.trainschedule.models.rescheduledtrains.RescheduledTrainPojo;
import com.getfreerecharge.trainschedule.models.scheduleoftrains.ScheduleOfTrainByNameOrNumber;
import com.getfreerecharge.trainschedule.models.seatavailabilites.SeatAvailability;
import com.getfreerecharge.trainschedule.models.stationsuggest.StationSuggestion;
import com.getfreerecharge.trainschedule.models.trainarrival.TrainArrivalStationPojo;
import com.getfreerecharge.trainschedule.models.trainbetweenstation.TrainBetweenStation;
import com.getfreerecharge.trainschedule.models.trainroutes.TrainRoute;
import com.getfreerecharge.trainschedule.models.trainsugession.AutoCompleteTrain;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Amit Kumar Tiwar on 25/07/16.
 */
public interface RestInterface {

    String API_KEY = "f2igrqx8";

    /*******
     200	OK. Your request was successfully processed.
     204	Empty response. Not able to fetch required data.
     401	Authentication Error. You passed an unknown API Key.
     403	Quota for the day exhausted. Applicable only for FREE users.
     405	Account Expired. Renewal was not completed on time.
     */

//    http://api.railwayapi.com/between/source/shg/dest/cnb/date/19-03/apikey/f2igrqx8/
//    http://api.railwayapi.com/pnr_status/pnr/1234567890/apikey/f2igrqx8/
//    http://api.railwayapi.com/route/train/12046/apikey/f2igrqx8/
//    http://api.railwayapi.com/name_number/train/kaifiyat/apikey/f2igrqx8/
//    http://api.railwayapi.com/cancelled/date/26-12-2015/apikey/myapikey/
//    http://api.railwayapi.com/rescheduled/date/2-03-2017/apikey/f2igrqx8/
//    http://api.railwayapi.com/arrivals/station/ndls/hours/2/apikey/f2igrqx8/


    @GET("pnr_status/pnr/{pnrs}/apikey/{api_key}")
    Call<PnrStatusPojo> getPnrStatusPojo(@Path("pnrs") String pnr,
                                         @Path("api_key") String apikey);

    @GET("/route/train/{train_no}/apikey/{api_key}")
    Call<TrainRoute> getTrainRoute(@Path("train_no") String train,
                                   @Path("api_key") String apikey);

    @GET("name_number/train/{name_no}/apikey/{api_key}")
    Call<ScheduleOfTrainByNameOrNumber> getScheduleoftrain(@Path("name_no") String train,
                                                           @Path("api_key") String apikey);

    @GET("cancelled/date/{date}/apikey/{api_key}")
    Call<CancelledTrainPojo> getCancelledTrain(@Path("date") String date,
                                               @Path("api_key") String pikey);

    @GET("rescheduled/date/{date}/apikey/{api_key}")
    Call<RescheduledTrainPojo> getRescheduledTrain(@Path("date") String date,
                                                   @Path("api_key") String apikey);

    @GET("arrivals/station/{name}/hours/{time}/apikey/{api_key}")
    Call<TrainArrivalStationPojo> getListOfTrains(@Path("name") String station,
                                                      @Path("time") String hours,
                                                      @Path("api_key") String apikey);

    @GET("check_seat/train/{number}/source/{source}/dest/{dest}/date/{date}/class/{class}/quota/{quota}/apikey/{api_key}")
    Call<SeatAvailability> getSeatAvailability(@Path("number") String number,
                                               @Path("source") String source,
                                               @Path("dest") String dest,
                                               @Path("date") String date,
                                               @Path("class") String clas,
                                               @Path("quota") String quota,
                                               @Path("api_key") String apikey);

    @GET("fare/train/{number}/source/{source}/dest/{dest}/age/{age}/quota/{quota}/doj/{doj}/apikey/{api_key}")
    Call<FairEnquiry> getFair(@Path("number") String number,
                              @Path("source") String source,
                              @Path("dest") String dest,
                              @Path("age") String age,
                              @Path("quota") String quota,
                              @Path("doj") String doj,
                              @Path("api_key") String apikey);

    @GET("between/source/{source}/dest/{dest}/date/{date}/apikey/{api_key}")
    Call<TrainBetweenStation> getTrainBetweenStation(@Path("source") String source,
                                                     @Path("dest") String dest,
                                                     @Path("date") String date,
                                                     @Path("api_key") String apikey);

//    http://api.railwayapi.com/suggest_train/trains/Kai/apikey/f2igrqx8/
    @GET("suggest_train/trains/{trains}/apikey/{api_key}")
    Call<AutoCompleteTrain> getData(@Path("trains") String trains,
                                    @Path("api_key") String apikey);

//    http://api.railwayapi.com/suggest_station/name/mum/apikey/f2igrqx8/
    @GET("suggest_station/name/{name}/apikey/{api_key}/")
    Call<StationSuggestion> getStation(@Path("name") String name,
                                       @Path("api_key") String apikey);

}
