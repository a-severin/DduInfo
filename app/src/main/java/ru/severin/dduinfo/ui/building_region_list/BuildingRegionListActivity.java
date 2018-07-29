package ru.severin.dduinfo.ui.building_region_list;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.DisposableContainer;
import ru.severin.dduinfo.AppDelegate;
import ru.severin.dduinfo.R;
import ru.severin.dduinfo.domain.BuildingRegion;
import ru.severin.dduinfo.ui.building_region_details.BuildingRegionDetailsActivity;

public class BuildingRegionListActivity extends AppCompatActivity implements Observer<List<BuildingRegion>>{

    private RecyclerView mBuildingRegionListView;
    private LinearLayoutManager mLinearLayoutManager1;
    private BuildingRegionListAdapter mBuildingRegionAdapter;

    private void openDetails(String id) {
        if (id == null) {
            return;
            //            Toast.makeText(this, id, Toast.LENGTH_LONG).show();
        }
        BuildingRegionDetailsActivity.start(this, id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_region_list);

        mBuildingRegionListView = findViewById(R.id.building_region_list);
        mLinearLayoutManager1 = new LinearLayoutManager(this);
        mBuildingRegionListView.setLayoutManager(mLinearLayoutManager1);
        mBuildingRegionAdapter = new BuildingRegionListAdapter();
        mBuildingRegionListView.setAdapter(mBuildingRegionAdapter);

        AppDelegate app = (AppDelegate) this.getApplication();

        BuildingRegionListViewModel viewModel = ViewModelProviders.of(this).get(BuildingRegionListViewModel.class);
        viewModel.getBuildingRegionList().observe(this, this);
        viewModel.init(app.getInfoServiceInstance());
    }

    @Override
    public void onChanged(@Nullable List<BuildingRegion> buildingRegions) {
        mBuildingRegionAdapter.update(buildingRegions);
    }

    public class BuildingRegionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public String mId;

        public BuildingRegionViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            BuildingRegionListActivity.this.openDetails(mId);
        }

        public void bind(BuildingRegion buildingRegion) {
            ((TextView) itemView.findViewById(R.id.building_region_text)).setText(buildingRegion.Text);
            this.mId = buildingRegion.Id;
        }
    }

    public class BuildingRegionListAdapter extends RecyclerView.Adapter<BuildingRegionViewHolder> {

        private final List<BuildingRegion> mBuildingRegions = new ArrayList<>();

        @Override
        public BuildingRegionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_building_region_list, parent, false);
            return new BuildingRegionViewHolder(view);
        }

        @Override
        public void onBindViewHolder(BuildingRegionViewHolder holder, int position) {
            holder.bind(mBuildingRegions.get(position));
        }

        @Override
        public int getItemCount() {
            return mBuildingRegions.size();
        }

        public void update(List<BuildingRegion> buildingRegions) {
            mBuildingRegions.clear();
            if (buildingRegions != null) {
                mBuildingRegions.addAll(buildingRegions);
                notifyDataSetChanged();
            }
        }
    }
}
