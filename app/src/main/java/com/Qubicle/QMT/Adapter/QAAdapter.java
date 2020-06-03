package com.Qubicle.QMT.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Qubicle.QMT.Listeners.JuzListener;
import com.Qubicle.QMT.Listeners.QAListener;
import com.Qubicle.QMT.Models.JuzList;
import com.Qubicle.QMT.Models.QA;
import com.Qubicle.QMT.R;
import com.Qubicle.QMT.utils.RtlGridLayoutManager;

import java.util.List;

/**
 * adapter for customized expandablelistview(anim)
 */
public class QAAdapter extends BaseExpandableListAdapter {

    private Context context;
    QAListener qaListener;
    String highligh=null;
    private List<QA> qaListArrayList;
    public QAAdapter(QAListener qaListener, Context context, List<QA> juzlist) {
        this.qaListArrayList = juzlist;
        this.context = context;
        this.qaListener=qaListener;

    }
    public void updateList(List<QA> list,String highlightword){
        qaListArrayList = list;
        highligh=highlightword;
        notifyDataSetChanged();
    }
    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return qaListArrayList.get(listPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        QA juzchildItem = qaListArrayList.get(listPosition);

        ViewHolderChild viewHolderChild;
        if (convertView == null) {
            viewHolderChild=new ViewHolderChild();
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.qa_listitem, null);
            viewHolderChild. textViewjuzFrom = (TextView) convertView
                    .findViewById(R.id.juzfrom);
           /* viewHolderChild. chapternameTextView = (TextView) convertView
                    .findViewById(R.id.chapternameTextView);*/
            viewHolderChild. reference = (TextView) convertView
                    .findViewById(R.id.reference);

//            viewHolderChild. refrence = (LinearLayout) convertView
//                    .findViewById(R.id.refrence);
           /* viewHolderChild. mWordMeaningRecylerView = (RecyclerView) convertView
                    .findViewById(R.id.mWordMeaningRecylerView);


            viewHolderChild. mWordMeaningRecylerView.setHasFixedSize(true);
            viewHolderChild. layoutManager = new LinearLayoutManager(context);
            viewHolderChild. mWordMeaningRecylerView.setLayoutManager(viewHolderChild.layoutManager); // set LayoutManager to RecyclerView
*/
            convertView.setTag(viewHolderChild);

        }
        else {
            viewHolderChild = (ViewHolderChild)  convertView.getTag();
        }

        viewHolderChild.  textViewjuzFrom.setText("\u200E"+juzchildItem.getAnswers());



        viewHolderChild.  reference.setText("Reference : "+juzchildItem.getReference_surah_no()+ " : " + juzchildItem.getReference_verse_no());


        viewHolderChild.  reference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qaListener.reference(juzchildItem.getReference_surah_no(),juzchildItem.getReference_verse_no());
            }
        });


       /* ReferenceAdapter horizontalImageAdapter = new ReferenceAdapter(juzchildItem.getReference(),qaListener);
        viewHolderChild.mWordMeaningRecylerView.setAdapter(horizontalImageAdapter);
*/


        return convertView;
    }
    public class ViewHolderChild{
        TextView textViewjuzFrom,chapternameTextView,reference;
        LinearLayout refrence;
        RecyclerView mWordMeaningRecylerView;
        RecyclerView.LayoutManager  layoutManager;
    }
    @Override
    public int getChildrenCount(int listPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.qaListArrayList.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.qaListArrayList.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {


        final QA juzList = qaListArrayList.get(listPosition);

        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.qa_group_item, null);
            viewHolder.JuzNameTextView = (TextView) convertView
                    .findViewById(R.id.chapternameTextView);

            viewHolder. imgArrow = (ImageView) convertView.findViewById(R.id.dropdown);
            viewHolder .JuzNameTextView.setTypeface(null, Typeface.BOLD);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)  convertView.getTag();
        }


//for highlighting multiple words
        String desc = juzList.getQuestion();

        if(highligh!=null){
            if(desc.contains(highligh)){
                String rep = desc.replace(highligh,    "<b><font color=#2825A6>"+ highligh+ "</font></b>");
               // viewHolder.JuzNameTextView.setText("\u200E"+Html.fromHtml(rep));

                String newword="\u200E"+rep;
                viewHolder.JuzNameTextView.setText(Html.fromHtml(newword));
            }
        }
        else {
            String newword="\u200E"+desc;
           // viewHolder.JuzNameTextView.setText("\u200E"+desc);
            viewHolder.JuzNameTextView.setText(newword);
        }


///////////////

      //  viewHolder.JuzNameTextView.setText(juzList.getQuestion());
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