package com.Qubicle.QMT.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Qubicle.QMT.Listeners.SuraListener;
import com.Qubicle.QMT.Listeners.TajweedListner;
import com.Qubicle.QMT.Models.AyatList;
import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.Models.Tajweed;
import com.Qubicle.QMT.Models.Topic;
import com.Qubicle.QMT.R;
import com.Qubicle.QMT.utils.AnimatedExpandableListView;

import java.util.ArrayList;
import java.util.List;

/**
 * adapter for customized expandablelistview(anim)
 */
public class TajweedClassExpandableAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {
    public TajweedListner tajweedListner;
    private LayoutInflater inflater;
    private List<Tajweed> items = new ArrayList<>();
    private List<Tajweed> singleitems = new ArrayList<>();
    private String malayalamFont, arabicfont;


    private Context _context;


    public TajweedClassExpandableAdapter(TajweedListner tajweedListner, Context context, List<Tajweed> items, List<Tajweed> singleitems) {
        this._context = context;
        this.tajweedListner = tajweedListner;


        this.items = items;
        this.singleitems = singleitems;
        this.malayalamFont = malayalamFont;

    }

    public void updateList(List<Tajweed> list) {

        this.items = list;

        //   highligh=highlightword;
        notifyDataSetChanged();
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {

        return items.get(groupPosition).getTopics().get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getRealChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        ViewHolderChild viewHolderChild;
        final Topic childItem = items.get(groupPosition).getTopics().get(childPosition);

        if (convertView == null) {

            viewHolderChild = new ViewHolderChild();
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
            viewHolderChild.txtListChildayat = (TextView) convertView
                    .findViewById(R.id.textHint);

            convertView.setTag(viewHolderChild);
        } else {
            viewHolderChild = (ViewHolderChild) convertView.getTag();
        }

        viewHolderChild.txtListChildayat.setText(childItem.getTopic());

        viewHolderChild.txtListChildayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tajweedListner.passaudioparameter(childItem.getTopic_id(), items.get(groupPosition).getId(), childItem.getTopic());
            }
        });
        return convertView;
    }


    public class ViewHolderChild {
        TextView txtListChildayat;
    }

    @Override
    public int getRealChildrenCount(int groupPosition) {
        return items.get(groupPosition).getTopics().size();
      /*  if (items.get(groupPosition).getTopics() == null) {
            return 0;
        } else {
            return items.get(groupPosition).getTopics().size();
        }
*/
    }


    @Override
    public Object getGroup(int groupPosition) {
        return this.items.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.items.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {


        final Tajweed groupItem = items.get(groupPosition);
        ViewHolder viewHolder;
        if (convertView == null) {

            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.tajweedtitles_item, null);
            viewHolder = new ViewHolder();

            viewHolder.lblListHeader = (TextView) convertView
                    .findViewById(R.id.chapternameTextView);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.play);
            viewHolder.constraintlayout = (RelativeLayout) convertView.findViewById(R.id.constraintlayout);


            viewHolder.lblListHeader.setTypeface(null, Typeface.BOLD);


            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        viewHolder.lblListHeader.setText(groupItem.getTitle());

        if (groupItem.getTopics() != null) {

            if (isExpanded) {
                viewHolder.img.setImageResource(R.drawable.open);
            } else {
                viewHolder.img.setImageResource(R.drawable.closed);
            }

        }



        if (groupItem.getTopics().size() == 1) {
            viewHolder.constraintlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tajweedListner.passaudioparameter(groupItem.getTopics().get(0).getTopic_id(), items.get(groupPosition).getId(),groupItem.getTopics().get(0).getTopic());
                }
            });
        }


        return convertView;
    }

    public class ViewHolder {
        TextView lblListHeader, chaptermeaning, chapterarabictitle;
        ImageView img;
        RelativeLayout constraintlayout;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}
