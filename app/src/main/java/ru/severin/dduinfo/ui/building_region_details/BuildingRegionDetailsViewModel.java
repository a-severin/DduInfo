package ru.severin.dduinfo.ui.building_region_details;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import ru.severin.dduinfo.domain.Record;
import ru.severin.dduinfo.service.InfoService;

public class BuildingRegionDetailsViewModel extends ViewModel {

    private final MutableLiveData<List<Record>> mRecords = new MutableLiveData<>();
    private CompositeDisposable mDisposableContainer = new CompositeDisposable();
    private InfoService mInfoService;

    public BuildingRegionDetailsViewModel init(InfoService infoService, String id) {
        mInfoService = infoService;
        load(id);
        return this;
    }

    public MutableLiveData<List<Record>> getRecords() {
        return mRecords;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mDisposableContainer.dispose();
    }

    private void load(String id) {
        Disposable disposable = mInfoService.getRecords(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Record>>() {
                    @Override
                    public void accept(List<Record> records) throws Exception {
                        mRecords.setValue(records);
                    }
                });
        mDisposableContainer.add(disposable);
    }
}
