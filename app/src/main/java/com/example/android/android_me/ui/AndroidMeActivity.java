/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.android_me.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

// This activity will display a custom Android image composed of three body parts: head, body, and legs
public class AndroidMeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_me);

// Only create new fragments when there is no previously saved state
        if (savedInstanceState == null) {

            // Retrieve list index values that were sent through an intent; use them to display the desired Android-Me body part image
            // Use setIndex(int index) to set the list index for all BodyPartFragments

            // Create a new head BodyPartFragment
            BodyPartFragment headFragment = new BodyPartFragment();

            // Set the list of image id's for the head fragment and set the position to the second image in the list
            headFragment.setImageId(AndroidImageAssets.getHeads());

            // Get the correct index to access in the array of head images from the intent
            // Set the default value to 0
            int headIndex = getIntent().getIntExtra("headIndex", 0);
            headFragment.setIndex(headIndex);

            // Add the fragment to its container using a FragmentManager and a Transaction
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.head_container, headFragment)
                    .commit();

            // Create and display the body and leg BodyPartFragments
            BodyPartFragment bodyPartFragment = new BodyPartFragment();
            bodyPartFragment.setImageId(AndroidImageAssets.getBodies());

            int bodyIndex = getIntent().getIntExtra("bodyIndex", 0);
            bodyPartFragment.setIndex(bodyIndex);
            FragmentManager fragmentManager1 = getSupportFragmentManager();
            fragmentManager1.beginTransaction()
                    .add(R.id.body_container, bodyPartFragment)
                    .commit();

            BodyPartFragment legsFragment = new BodyPartFragment();
            legsFragment.setImageId(AndroidImageAssets.getLegs());
            int legsIndex = getIntent().getIntExtra("headIndex", 0);
            legsFragment.setIndex(legsIndex);
            FragmentManager fragmentManager2 = getSupportFragmentManager();
            fragmentManager2.beginTransaction()
                    .add(R.id.legs_container, legsFragment)
                    .commit();
        }
    }
}
