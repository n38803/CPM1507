package DataClass;

/**
 * Shaun Thompson - CMP1507
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.parse.starter.R;

import java.util.ArrayList;

public class ContactAdapter extends BaseAdapter {

    private static final long ID_CONSTANT = 0x01000000;

    Context mContext;
    ArrayList<Contacts> mContactItems;

    public ContactAdapter(Context context, ArrayList<Contacts> items) {
        mContext = context;
        mContactItems = items;
    }


    @Override
    public int getCount() {
        return mContactItems.size();
    }

    @Override
    public Contacts getItem(int position) {
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

        Contacts contact = getItem(position);

        TextView contactName = (TextView) convertView.findViewById(R.id.cName);
        contactName.setText(contact.getName());

        TextView contactNumber = (TextView) convertView.findViewById(R.id.cNumber);
        contactNumber.setText("$" + String.format("%.2f", contact.getNumber()));

        return convertView;
    }
}
