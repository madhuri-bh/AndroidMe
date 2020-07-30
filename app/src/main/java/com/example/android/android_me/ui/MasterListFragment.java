package com.example.android.android_me.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

// This fragment displays all of the AndroidMe images in one large list
// The list appears as a grid of images

public class MasterListFragment extends Fragment {
    private static final String TAG = "MasterListFragment";

    // Mandatory empty constructor
    public MasterListFragment() {
    }

    OnImageClickListener mCallback;

    public interface OnImageClickListener {
        void onImageSelected(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mCallback = (OnImageClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement onImageClickListener");
        }
    }

    // Inflates the GridView of all AndroidMe images
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            final View rootView = inflater.inflate(R.layout.master_list_fragment, container, false);

            // Get a reference to the GridView in the fragment_master_list xml layout file
            GridView masterListGv = rootView.findViewById(R.id.images_grid_view);

            // Create the adapter
            // This adapter takes in the context and an ArrayList of ALL the image resources to display
            MasterListAdapter adapter = new MasterListAdapter(getContext(), AndroidImageAssets.getAll());

// Set the adapter on the GridView
            masterListGv.setAdapter(adapter);

            masterListGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    mCallback.onImageSelected(position);
                }
            });

// Return the root view
            return rootView;
        } catch (Exception e) {
            Log.e(TAG, "onCreateView", e);
            throw e;
        }
    }
}
