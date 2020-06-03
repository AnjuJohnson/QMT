package com.Qubicle.QMT.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.Qubicle.QMT.Listeners.JuzListener;
import com.Qubicle.QMT.Listeners.SuraListener;
import com.Qubicle.QMT.Models.AyatList;
import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.Models.JuzList;
import com.Qubicle.QMT.R;
import com.Qubicle.QMT.utils.AnimatedExpandableListView;

import java.util.List;

/**
 * adapter for customized expandablelistview(anim)
 */
public class JuzExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
JuzListener juzListener;
    private List<JuzList> juzListArrayList;
    public JuzExpandableListAdapter(JuzListener juzListener, Context context, List<JuzList> juzlist) {
        this.juzListArrayList = juzlist;
        this.context = context;
        this.juzListener=juzListener;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return juzListArrayList.get(listPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        JuzList juzchildItem = juzListArrayList.get(listPosition);

        ViewHolderChild viewHolderChild;
        if (convertView == null) {
            viewHolderChild=new ViewHolderChild();
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.juz_child_list_item, null);
            viewHolderChild. textViewjuzFrom = (TextView) convertView
                    .findViewById(R.id.juzfrom);
            viewHolderChild. textViewjuzTo = (TextView) convertView
                    .findViewById(R.id.juzto);
            convertView.setTag(viewHolderChild);

        }
        else {
            viewHolderChild = (ViewHolderChild)  convertView.getTag();
        }

        viewHolderChild.  textViewjuzFrom.setText(":  "+juzchildItem.getFrom());
        viewHolderChild.  textViewjuzTo.setText(":  "+juzchildItem.getTo());

        viewHolderChild.textViewjuzFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                juzListener.fetchjuzdetails(juzchildItem.getName());
            }
        });
        viewHolderChild.textViewjuzTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                juzListener.fetchjuzdetails(juzchildItem.getName());
            }
        });

        return convertView;
    }
    public class ViewHolderChild{
        TextView textViewjuzFrom,textViewjuzTo;
    }
    @Override
    public int getChildrenCount(int listPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.juzListArrayList.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.juzListArrayList.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {


        final JuzList juzList = juzListArrayList.get(listPosition);

        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.juz_group_item, null);
            viewHolder.JuzNameTextView = (TextView) convertView
                    .findViewById(R.id.chapternameTextView);

            viewHolder. imgArrow = (ImageView) convertView.findViewById(R.id.dropdown);
            viewHolder .JuzNameTextView.setTypeface(null, Typeface.BOLD);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)  convertView.getTag();
        }


        viewHolder.JuzNameTextView.setText(juzList.getName());
        if (isExpanded) {
            viewHolder. imgArrow.setImageResource(R.drawable.open);
        } else {
            viewHolder.  imgArrow.setImageResource(R.drawable.closed);
        }

      /*  viewHolder.JuzNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                juzListener.fetchjuzdetails(juzList.getName());
            }
        });*/
        return convertView;
    }
    public class ViewHolder {
        TextView JuzNameTextView;
        ImageView imgArrow;

    }
    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}