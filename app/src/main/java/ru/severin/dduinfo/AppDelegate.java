package ru.severin.dduinfo;

import android.app.Application;

import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.severin.dduinfo.service.InfoService;

public class AppDelegate extends Application {

    private InfoService mInfoService;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public InfoService getInfoServiceInstance(){
        if (mInfoService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://erzrf.ru/")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mInfoService = retrofit.create(InfoService.class);
        }
        return mInfoService;
    }
}
