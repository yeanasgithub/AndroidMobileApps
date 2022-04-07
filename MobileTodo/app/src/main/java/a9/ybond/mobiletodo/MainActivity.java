package a9.ybond.mobiletodo;

import static android.widget.TextView.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.icu.text.Edits;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements OnItemListener {

    // 1.
    private TabLayout tabLists;
    private EditText txtTodoItem;
    private RecyclerView listItems;

    // 3.
    // when tab gets changes, we need to store the tab name
    // so that we know which tab is currently selected
    private String selectedTab;

    // 26. making list of our items
    private ArrayList<Item> items = new ArrayList<>();

    // 11. How to write to Firebase
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    // database points to the root of our database
    // so, we need reference to the leaf node in terms of where to put an item
    // go to 12.



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 2.
        // getting references of all private variables
        // tabLists, listItems, txtTodoItem,


        // 4.
        // By default, home tab is initialized as our selectedTab
        selectedTab = getResources().getString(R.string.home);

        listItems = findViewById(R.id.listItems);
        // 25. Setting the adapter now @41:17
        // 36. adding the last this for onItemListener
        listItems.setAdapter(new RecycleAdapter(this, items, this));
        // 26. Setting our layout manager for our items
        listItems.setLayoutManager(new LinearLayoutManager(this));


        tabLists = findViewById(R.id.tabLists);

        // 5.
        // onTabSelected Listener is used to track the tab change
        tabLists.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            // the last two are not to be deleted because they are required to have
            // the first one is what we generally want
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectedTab = Objects.requireNonNull(tab.getText()).toString();
                // 6. Logging to see tab selection is working
                Log.i("TAB_CHANGED", selectedTab);

                // 27. To get previously saved item lists from our database
                getItems();
                // go to 28: getItems() is made inside of Firebase function

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }





        });


        txtTodoItem = findViewById(R.id.txtTodoItem);

        //14. To make Enter key or the search key work when adding a new item
        txtTodoItem.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                Item tmp = new Item(txtTodoItem.getText().toString());
                addItem(tmp);
                txtTodoItem.setText(null);

                // to close the keyboard
                return false;
            }

        });

        // 29. @49:00 so that we can get the previously saved items to the default
        // tab which is home tab
        getItems();

        // 30. RecycleView to accept click to delete items,
        // we need to create a class
        // a9.ybond.mobiletodo > New > JavaClass > Interface > OnItemListener.java


    }

    // 7. After connecting Firebase with this app Tool tab > Firebase >  @14:21
    // onAddClicked
    public void onAddClicked(View view)
    {
        // 8. We need to add text and create an item out of it
        // a9.ybond.mobiletodo > New > Java Class > Class > Item
        // Item.java is created, and constructor and getter are added
        // Method to get a new item
        // Item tmp = new Item(txtTodoItem.getText().toString());
        // clearing out the txtTodoItem text
        // txtTodoItem.setText(null);
        // the below function is the one that adds a new item to the Firebase
        // addItem(tmp);

        // 15. Enter key and the screen keyboard check button to work when adding a new item
        txtTodoItem.onEditorAction(EditorInfo.IME_ACTION_DONE);

        // 16. a9.tbond.mbiletodo > New > Java Class > RecyleAdapter is created

    }

    //------------------------- Firebase function ----------------------------------------//
    private void addItem(Item item)
    {
        // 12.
        try
        {
            String path = URLEncoder.encode(item.getDescription(),
                    String.valueOf(StandardCharsets.UTF_8));
            DatabaseReference ref = database.getReference(selectedTab + "/" + path);
            ref.setValue(item);
        }

        catch (UnsupportedEncodingException e) {    e.printStackTrace();    }

        // 13. Go to manifests folder > AndroidManifest.xml > Added permission to use INTERNET
        // @21:51 test it out maybe network settings?




    }

    // 28.
    private void getItems()
    {
        // we need to reference to the currently selected tab
        DatabaseReference ref = database.getReference(selectedTab);

        ref.addValueEventListener(new ValueEventListener()
        {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                // we need to clear items that is in the recycler adapter items
                items.clear();
                // we need an iterator that goes over the keys which lead us to get the items
                // (values) themselves
                Iterator<DataSnapshot> itr = dataSnapshot.getChildren().iterator();
                while(itr.hasNext())
                {
                    // HashMap is a key-value pair
                    // we need to cast it as a HashMap
                    HashMap val = (HashMap) itr.next().getValue();
                    // key is the "description"
                    Item tmp = new Item(val.get("description").toString());
                    items.add(tmp);
                }

                listItems.getAdapter().notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    // 31. implements onItemListener an added this method
    // Also, in RecycleAdapter, public class ViewHolder needs to what to do with the clicks
    // accordingly once the position is passed
    // go to 32.
    @Override
    public void onItemClicked(int position)
    {
        // 37. to track what item is clicked
        Log.i("ITEM_CLICKED", items.get(position).getDescription());

        // 38. to implement delete, get a reference to our database
        // we need to get a path
        try
        {
            String path = URLEncoder.encode(items.get(position).getDescription(), String.valueOf(StandardCharsets.UTF_8));

            DatabaseReference ref = database.getReference(selectedTab + "/" + path);
            ref.removeValue();
        }

        catch (UnsupportedEncodingException e) {    e.printStackTrace();    }

    }
}