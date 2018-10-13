package edu.byui.cit.explore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


/* TODO
 * 1. Copy the following xml layout files and their corresponding Java
 *    files from the kindnessClient app into this app:
 *    about_frag, display_frag, main_activity, mistake_frag
 * 2. Copy the action_menu xml file from kindnessClient into this app.
 *    Remove the privacy item from the menu in this app but don't remove it
 *    from the kindnessClient.
 * 3. Look at the Room database in the Record app. Implement a Room database
 *    with one table named Pin with these columns:
 *        iconName, latitude, longitude, timestamp, notes
 * 4. Brainstorm and review a list of icons that users will choose from,
 *    including Christmas tree, firewood, bathroom, lookout, boulders,
 *    berries, fish, moose, antelope, elk, deer, wolf, bear, tent,
 *    hiking, bicycling, horse
 * 5. Look at the icons in kindnessClient. Choose an icon from logomakr.com
 *    for each icons in the list (see above). Create Android icons from the
 *    logomakr icons and import them into this app.
 * 6. Create this app in the Google play store:
 *    https://play.google.com/apps/publish
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }
}
