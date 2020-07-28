package com.example.android.android_me.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

import java.util.ArrayList;
import java.util.List;

public class BodyPartFragment extends Fragment {

    private static final String TAG = "BodyPartFragment";

    private static final String IMAGE_ID_LIST = "imageIds";
    public static final String LIST_INDEX = "listIndex";

private List<Integer> mImageIds;
private int mListIndex;
    public BodyPartFragment() {
    }

    public void setImageId(List<Integer> imageIds) {
        mImageIds = imageIds;
    }

    public void setIndex(int listIndex) {
       mListIndex = listIndex;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(savedInstanceState != null) {
            mImageIds = savedInstanceState.getIntegerArrayList(IMAGE_ID_LIST);
            mListIndex = savedInstanceState.getInt(LIST_INDEX);
        }
        View rootView = inflater.inflate(R.layout.fragment_body_parts, container, false);
        final ImageView bodyPartsIv = rootView.findViewById(R.id.body_part_image_view);

        if(mImageIds != null){
            bodyPartsIv.setImageResource(mImageIds.get(mListIndex));
        } else {
            Log.v(TAG, "This fragment has a null list of image id's");
        }

        bodyPartsIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListIndex < mImageIds.size() - 1){
                    mListIndex++;
                } else {
                    mListIndex = 0;
                }
                bodyPartsIv.setImageResource(mImageIds.get(mListIndex));
            }
        });
        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle currentState) {
       currentState.putIntegerArrayList(IMAGE_ID_LIST, (ArrayList<Integer>) mImageIds);
       currentState.putInt(LIST_INDEX, mListIndex);
    }
}
