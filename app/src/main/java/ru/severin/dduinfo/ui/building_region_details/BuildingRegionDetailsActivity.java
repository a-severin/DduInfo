package ru.severin.dduinfo.ui.building_region_details;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.severin.dduinfo.AppDelegate;
import ru.severin.dduinfo.R;
import ru.severin.dduinfo.domain.Record;

public class BuildingRegionDetailsActivity extends AppCompatActivity implements Observer<List<Record>> {

    private static final String EXTRA_ID = "ru.severin.dduinfo.ui.building_region_details.BuildingRegionDetailsActivity.id";
    private String mId;
    private RecyclerView mRecordsListView;
    private LinearLayoutManager mLinearLayoutManager;
    private RecordListAdapter mRecordsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_region_details);

        Intent intent = getIntent();
        mId = intent.getStringExtra(EXTRA_ID);
        setTitle(mId);

        mRecordsListView = findViewById(R.id.records_list);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecordsListView.setLayoutManager(mLinearLayoutManager);
        mRecordsListAdapter = new RecordListAdapter();
        mRecordsListView.setAdapter(mRecordsListAdapter);

        AppDelegate app = (AppDelegate) this.getApplication();

        BuildingRegionDetailsViewModel viewModel = ViewModelProviders.of(this).get(BuildingRegionDetailsViewModel.class);
        viewModel.getRecords().observe(this, this);
        viewModel.init(app.getInfoServiceInstance(), mId);
    }

    public static void start(Context context, String id) {
        Intent intent = new Intent(context, BuildingRegionDetailsActivity.class);
        intent.putExtra(EXTRA_ID, id);
        context.startActivity(intent);
    }

    @Override
    public void onChanged(@Nullable List<Record> records) {
        mRecordsListAdapter.update(records);
    }

    public class RecordViewHolder extends RecyclerView.ViewHolder {

        public RecordViewHolder(View itemView) {
            super(itemView);
        }

        public void bind(Record record) {
            ((TextView)itemView.findViewById(R.id.record_text)).setText(record.Date);
        }
    }

    public class RecordListAdapter extends RecyclerView.Adapter<RecordViewHolder> {

        private final List<Record> mRecord = new ArrayList<>();

        @Override
        public RecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_record_list, parent, false);
            return new RecordViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecordViewHolder holder, int position) {
            holder.bind(mRecord.get(position));
        }

        @Override
        public int getItemCount() {
            return mRecord.size();
        }

        public void update(List<Record> records) {
            mRecord.clear();
            if (records != null) {
                mRecord.addAll(records);
                notifyDataSetChanged();
            }
        }
    }
}
