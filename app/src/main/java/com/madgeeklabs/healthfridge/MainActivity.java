package com.madgeeklabs.healthfridge;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.madgeeklabs.healthfridge.adapters.MenuAdapter;
import com.madgeeklabs.healthfridge.fragments.Goals;
import com.madgeeklabs.healthfridge.fragments.HealthStatus;
import com.madgeeklabs.healthfridge.fragments.Limits;
import com.madgeeklabs.healthfridge.models.Category;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends Activity implements HealthStatus.OnFragmentInteractionListener, Goals.OnFragmentInteractionListener, Limits.OnFragmentInteractionListener{

    @InjectView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @InjectView(R.id.listView_categories)
    ListView drawer;
    private ActionBarDrawerToggle mDrawerToggle;
    @InjectView(R.id.left_drawer)
    RelativeLayout drawerHolder;
    private ArrayList<Category> options = new ArrayList<Category>();
    private String mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                R.drawable.ic_action_name,  /* nav drawer icon to replace 'Up' caret */
                R.string.app_name,  /* "open drawer" description */
                R.string.app_name  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActionBar().setTitle("stuff");
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle("stuff");
            }
        };

        drawerLayout.setDrawerListener(mDrawerToggle);

        options.add(new Category("My Health Today", R.drawable.plus_btn));
        options.add(new Category("My Goals", R.drawable.plus_btn));
        options.add(new Category("My Limits", R.drawable.plus_btn));

        MenuAdapter menuAdapter = new MenuAdapter(MainActivity.this, options);

        drawer.setAdapter(menuAdapter);

        drawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                switch (position) {
                    case 0:
                        mTitle = "My Health Today";
                        HealthStatus selfieFragment = HealthStatus.newInstance(null, null);
                        ft.replace(R.id.fragment_holder, selfieFragment);
                        ft.commit();
                        break;
                    case 1:
                        mTitle = "My Goals";
                        Goals nowfiesListOpen = Goals.newInstance(null, null);
                        ft.replace(R.id.fragment_holder, nowfiesListOpen);
                        ft.commit();
                        break;
                    case 2:
                        mTitle = "My Limits";
                        Limits nowfiesList = Limits.newInstance(null, null);
                        ft.replace(R.id.fragment_holder, nowfiesList);
                        ft.commit();
                        break;
                }

                drawer.setItemChecked(position, true);
                drawerLayout.closeDrawer(drawerHolder);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        FragmentTransaction ft = getFragmentManager().beginTransaction();

        HealthStatus selfieFragment = HealthStatus.newInstance(null, null);
        ft.replace(R.id.fragment_holder, selfieFragment);
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
