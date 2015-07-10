package DataClass;

/**
 * Shaun Thompson - CMP1507
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;
import com.parse.starter.R;
import com.parse.starter.UserActivity;

import java.text.ParseException;
import java.util.ArrayList;

public class ContactAdapter extends ParseQueryAdapter<ParseObject> {

    public static ArrayList<Contacts> mContactList = new ArrayList();

    String name;
    String number;

    public ContactAdapter(Context context) {

        super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery create() {
                // edit to further constrain query
                return null;
            }
        });
    }

    // Customize the layout by overriding getItemView
    @Override
    public View getItemView(ParseObject object, View v, ViewGroup parent) {
        if (v == null) {
            v = View.inflate(getContext(), R.layout.list_item, null);
        }

        super.getItemView(object, v, parent);


        // Add the title view
        TextView nameView = (TextView) v.findViewById(R.id.cName);
        //name = object.getString("name");
        //nameView.setText(name);

        TextView numberView = (TextView) v.findViewById(R.id.cNumber);
        //number = object.getString("number");
        //numberView.setText(number);

        // create local array list
        mContactList.add(new Contacts(name, number));

        Log.i("CONTACT TEST", "Iteration: " + name);

        return v;
    }

}