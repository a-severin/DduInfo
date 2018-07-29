package ru.severin.dduinfo.service;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.severin.dduinfo.domain.BuildingRegion;
import ru.severin.dduinfo.domain.Record;

public interface InfoService {
    // https://erzrf.ru/erz-rest/api/v1/global/dictionary?type=buildings_regions
    @GET("erz-rest/api/v1/global/dictionary?type=buildings_regions")
    Observable<List<BuildingRegion>> getBuildRegions();

    @GET("erz-rest/api/v1/charts/{id}")
    Observable<List<Record>> getRecords(@Path("id") String id);
}
