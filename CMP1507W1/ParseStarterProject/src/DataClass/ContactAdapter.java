package DataClass;

/**
 * Shaun Thompson - CMP1507
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.R;

import java.text.ParseException;
import java.util.ArrayList;

public class ContactAdapter extends BaseAdapter {

    private static final long ID_CONSTANT = 0x01000000;


    String thisUser;
    Context mContext;

    ArrayList<ParseObject> mContactItems;

    public ContactAdapter(Context context, ArrayList<ParseObject> items) {
        mContext = context;
        mContactItems = items;
    }


    @Override
    public int getCount() {
        return mContactItems.size();
    }

    @Override
    public ParseObject getItem(int position) {
        return mContactItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ID_CONSTANT + position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        }

        ParseObject contact = getItem(position);

        TextView contactName = (TextView) convertView.findViewById(R.id.cName);
        contactName.setText(contact.getNumb);

        TextView contactNumber = (TextView) convertView.findViewById(R.id.cNumber);
        contactNumber.setText("$" + String.format("%.2f", contact.getNumber()));

        return convertView;
    }


    public void GetUserInfo(){

        ParseUser currentUser = ParseUser.getCurrentUser();

        thisUser = currentUser.getUsername();


    }


    public void RetrieveObjects(){

        ParseQuery<ParseObject> query = ParseQuery.getQuery(thisUser);
        query.getInBackground("xWMyZ4YEGZ", new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, com.parse.ParseException e) {

            }

            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    // object will be your game score
                } else {
                    // something went wrong
                }
            }
        });

    }
}
