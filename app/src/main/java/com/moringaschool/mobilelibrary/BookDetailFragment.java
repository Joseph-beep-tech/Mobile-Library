package com.moringaschool.mobilelibrary;

import android.animation.ObjectAnimator;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moringaschool.mobilelibrary.model.Item;
import com.moringaschool.mobilelibrary.model.VolumeInfo;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookDetailFragment extends Fragment implements  View.OnClickListener {

    @BindView(R.id.bookNameTextView) TextView bookName;
    @BindView(R.id.placeOfBirthTextView) TextView placeOfBirth;
    @BindView(R.id.historyTextView) TextView mHistory;
    @BindView(R.id.bookImageView) ImageView imageView;
    @BindView(R.id.type) TextView mType;
    @BindView(R.id.bookTextView) TextView mBook;

    @BindView(R.id.savedBookButton)
    Button mSavedBook;

    private List<Item> bookItems;


    public BookDetailFragment() {
        // Required empty public constructor
    }


    public static BookDetailFragment newInstance(Item volumeInfo) {
        BookDetailFragment fragment = new BookDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("volumeinfo", Parcels.wrap(volumeInfo));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bookItems = Parcels.unwrap(getArguments().getParcelable("volumeinfo"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View heroDetails = inflater.inflate(R.layout.fragment_book_detail, container, false);

        ButterKnife.bind(this, heroDetails);




        mType.setText(("Speed: ")+ bookItems.get(0).getVolumeInfo().getAuthors().listIterator());

        mBook.setText(("Book : ")+ bookItems.get(0).getVolumeInfo().getDescription());
        mHistory.setText(("Occupation: ")+bookItems.get(0).getVolumeInfo().getPublisher());
        bookName.setText(("Book Name: ")+bookItems.get(0).getVolumeInfo().getTitle());


        mSavedBook.setOnClickListener(this);

        return bookName;
    }

    @Override
    public void onClick(View view) {

        if(view == mSavedBook){

            DatabaseReference ref = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.GOOGLE_BASE_URL);
            ref.push().setValue(bookItems);
            Toast.makeText(getContext(), "Book Saved Successfully", Toast.LENGTH_SHORT).show();
        }
    }



}