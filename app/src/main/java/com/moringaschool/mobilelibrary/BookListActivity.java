package com.moringaschool.mobilelibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.moringaschool.mobilelibrary.adapters.BookListAdapter;
import com.moringaschool.mobilelibrary.model.GoogleBooksSearchResponse;
import com.moringaschool.mobilelibrary.model.Item;
import com.moringaschool.mobilelibrary.network.GoogleApi;
import com.moringaschool.mobilelibrary.network.GoogleClient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookListActivity extends AppCompatActivity {
    private static final String TAG = BookListActivity.class.getSimpleName();



    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    @BindView(R.id.errorTextView) TextView mErrorTextView;
    @BindView(R.id.progressBar) ProgressBar mProgressBar;

//    @BindView(R.id.findNextButton) Button SavedBookActivity;



    private BookListAdapter mAdapter;
    public List<Item> google_book;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        ButterKnife.bind(this);


//                    SavedBookActivity.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(BookListActivity.this, SavedBookActivity.class);
//                    startActivity(intent);
//                }
//
//            });


        final Intent intent = getIntent();
        String book = intent.getStringExtra("book");
        intent.putExtra("book", book);




        GoogleApi client = GoogleClient.getClient();

        Call<GoogleBooksSearchResponse> Call = client.getBooks(book, "AIzaSyD8jXEC9I2wpaXSeilr4Gc9Aqx1ePmw8uo");

        Call.enqueue(new Callback<GoogleBooksSearchResponse>() {


            @Override
            public void onResponse(Call<GoogleBooksSearchResponse> call, Response<GoogleBooksSearchResponse> response) {
                if (response.isSuccessful()) {
                    hideProgressBar();
                    google_book = response.body().getItems();
                    mAdapter = new BookListAdapter(BookListActivity.this, google_book);
                    mRecyclerView.setAdapter(mAdapter);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(BookListActivity.this);
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setHasFixedSize(true);
                    showGoogle_book();
                } else {
                    showUnsuccessfulMessage();
                }
            }
            @Override
            public void onFailure(retrofit2.Call<GoogleBooksSearchResponse> call, Throwable t) {
                Log.e(TAG, "onFailure:", t);
                hideProgressBar();
                showFailureMessage();
            }
        });
    }

    private void showFailureMessage() {
        mErrorTextView.setText("Something went wrong. Please check your Internet connection and try again later");
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void showUnsuccessfulMessage() {
        mErrorTextView.setText("Something went wrong. Please try again later");
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void showGoogle_book() {
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

}

