package com.example.spincarsearch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.spincarsearch.Models.GImageSearch;
import com.example.spincarsearch.Services.GoogleService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    private RecyclerViewAdapter mAdapter;
    private Integer mStartIndex = 1;
    private boolean mNoMoreResults = false;
    private String mQuery;
    private int mMaxResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        RecyclerView mRecyclerView = findViewById(R.id.recycler_view);

        mAdapter = new RecyclerViewAdapter();
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mQuery = getIntent().getStringExtra("query");
        mMaxResults = getIntent().getIntExtra("primeNumber", 10);

        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                if (!mNoMoreResults) {
                    getImages(mQuery);
                }
            }
        });

        getImages(mQuery);
    }

    public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
        private int previousTotal = 0;
        private boolean loading = true;
        private int visibleThreshold = 5;
        int firstVisibleItem, visibleItemCount, totalItemCount;

        private int current_page = 1;

        private LinearLayoutManager mLinearLayoutManager;

        EndlessRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager) {
            this.mLinearLayoutManager = linearLayoutManager;
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            visibleItemCount = recyclerView.getChildCount();
            totalItemCount = mLinearLayoutManager.getItemCount();
            firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

            if (loading) {
                if (totalItemCount > previousTotal) {
                    loading = false;
                    previousTotal = totalItemCount;
                }
            }
            if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                current_page++;
                onLoadMore(current_page);
                loading = true;

            }
        }

        public abstract void onLoadMore(int current_page);
    }

    private void getImages(String query) {
        GoogleService googleService = new GoogleService();
        int pageQuantity = 10;
        if (mMaxResults < 10) {
            pageQuantity = mMaxResults;
        }
        if (mMaxResults - mStartIndex + 1 < 10) {
            pageQuantity = mMaxResults - mStartIndex + 1;
        }
        if (mStartIndex < mMaxResults) {
            googleService.googleSearchCall(query, mStartIndex, pageQuantity).enqueue(new Callback<GImageSearch>() {
                @Override
                public void onResponse(Call<GImageSearch> call, Response<GImageSearch> response) {
                    if (response.code() == 400 || response.code() == 403) {
                        Toast.makeText(SearchActivity.this, "Daily Limit Exceeded", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 200) {
                        mAdapter.addItems(response.body().getItems());
                        mAdapter.notifyDataSetChanged();

                        mStartIndex = response.body().getQueries().getNextPage().get(0).getStartIndex();
                    }

                    Log.d("TAG", "Working!");
                }

                @Override
                public void onFailure(Call<GImageSearch> call, Throwable t) {
                    Log.d("TAG", "Not working yet.");
                }
            });
        }
    }
}
