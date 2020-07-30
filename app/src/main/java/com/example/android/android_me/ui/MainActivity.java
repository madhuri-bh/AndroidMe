package com.example.android.android_me.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

public class MainActivity extends AppCompatActivity implements MasterListFragment.OnImageClickListener {

    private int headIndex;
    private int bodyIndex;
    private int legIndex;

    // Track whether to display a two-pane or single-pane UI
    // A single-pane display refers to phone screens, and two-pane to larger tablet screens
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Determine if you're creating a two-pane or single-pane display
        if (findViewById(R.id.android_me_linear_layout) != null) {

            // This LinearLayout will only initially exist in the two-pane tablet case
            mTwoPane = true;

            // Change the GridView to space out the images more on tablet
            GridView gridView = findViewById(R.id.images_grid_view);
            gridView.setNumColumns(2);

            // Getting rid of the "Next" button that appears on phones for launching a separate activity
            Button button = findViewById(R.id.next_button);
            button.setVisibility(View.GONE);

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
        } else {
            // We're in single-pane mode and displaying fragments on a phone in separate activities
            mTwoPane = false;
        }
    }

    // Define the behavior for onImageSelected
    @Override
    public void onImageSelected(int position) {
        // Create a Toast that displays the position that was clicked
        Toast.makeText(this, "Position clicked = " + position, Toast.LENGTH_SHORT).show();

        // bodyPartNumber will be = 0 for the head fragment, 1 for the body, and 2 for the leg fragment
        // Dividing by 12 gives us these integer values because each list of images resources has a size of 12
        int bodyPartNumber = position / 12;

        // Store the correct list index no matter where in the image list has been clicked
        // This ensures that the index will always be a value between 0-11
        int listIndex = position - 12 * bodyPartNumber;

        // Handle the two-pane case and replace existing fragments right when a new image is selected from the master list
        if (mTwoPane) {
            // Create two=pane interaction
            BodyPartFragment newFragment = new BodyPartFragment();

            // Set the currently displayed item for the correct body part fragment
            switch (bodyPartNumber) {

                // A head image has been clicked
                // Give the correct image resources to the new fragment
                case 0:
                    newFragment.setImageId(AndroidImageAssets.getHeads());
                    newFragment.setIndex(listIndex);

                    // Replace the old head fragment with a new one
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.head_container, newFragment)
                            .commit();
                    break;

                case 1:
                    newFragment.setImageId(AndroidImageAssets.getBodies());
                    newFragment.setIndex(listIndex);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.head_container, newFragment)
                            .commit();
                    break;

                case 2:
                    newFragment.setImageId(AndroidImageAssets.getLegs());
                    newFragment.setIndex(listIndex);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.head_container, newFragment)
                            .commit();
                    break;
                default:
                    break;
            }
        } else {
            // Handle the single-pane phone case by passing information in a Bundle attached to an Intent
            switch (bodyPartNumber) {
                case 0:
                    headIndex = listIndex;
                    break;
                case 1:
                    bodyIndex = listIndex;
                    break;
                case 2:
                    legIndex = listIndex;
                    break;
                default:
                    break;
            }
            Bundle b = new Bundle();

            b.putInt("headIndex", headIndex);
            b.putInt("bodyIndex", bodyIndex);
            b.putInt("legIndex", legIndex);

            final Intent intent = new Intent(this, AndroidMeActivity.class);
            intent.putExtras(b);

            Button nextButton = findViewById(R.id.next_button);
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(intent);
                }
            });
        }
    }
}
