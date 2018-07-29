package ru.severin.dduinfo.ui.building_region_list;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import ru.severin.dduinfo.domain.BuildingRegion;
import ru.severin.dduinfo.service.InfoService;

public class BuildingRegionListViewModel extends ViewModel {
    private InfoService mInfoService;
    private CompositeDisposable mDisposableContainer = new CompositeDisposable();

    public MutableLiveData<List<BuildingRegion>> getBuildingRegionList() {
        return mBuildingRegionList;
    }

    private final MutableLiveData<List<BuildingRegion>> mBuildingRegionList = new MutableLiveData<>();



    public BuildingRegionListViewModel init(InfoService infoService) {
        mInfoService = infoService;
        load();
        return this;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mDisposableContainer.dispose();
    }

    private void load() {
        Disposable disposable = mInfoService.getBuildRegions()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<BuildingRegion>>() {
                    @Override
                    public void accept(List<BuildingRegion> buildingRegions) throws Exception {
                        mBuildingRegionList.setValue(buildingRegions);
                    }
                });
        mDisposableContainer.add(disposable);
    }
}
