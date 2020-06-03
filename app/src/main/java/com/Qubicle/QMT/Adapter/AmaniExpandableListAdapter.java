package com.Qubicle.QMT.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.Qubicle.QMT.Listeners.SuraListener;
import com.Qubicle.QMT.Models.AyatList;
import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.R;
import com.Qubicle.QMT.utils.AnimatedExpandableListView;

import java.util.ArrayList;
import java.util.List;

/**
 * adapter for customized expandablelistview(anim)
 */
public class AmaniExpandableListAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {
    public SuraListener mSuraListener;
    private LayoutInflater inflater;
    private List<ChapterList> items=new ArrayList<>();
    private String malayalamFont, arabicfont;


    private Context _context;


    public AmaniExpandableListAdapter(SuraListener suraListener, Context context, List<ChapterList> items, String malayalamFont, String arabicFont) {
        this._context = context;
        this.mSuraListener = suraListener;
        for(int i=0;i<items.size();i++){
            if(i!=0){
                this.items.add(i-1,items.get(i));

            }
            else {

            }

        }

      //  this.items = items;
        this.malayalamFont = malayalamFont;
        this.arabicfont = arabicFont;
    }
    public void updateList(List<ChapterList> list){

        this.items = list;

        //   highligh=highlightword;
        notifyDataSetChanged();
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {

        return items.get(groupPosition).getAyat().get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getRealChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        ViewHolderChild viewHolderChild;
        final AyatList childItem = items.get(groupPosition).getAyat().get(childPosition);

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


        if(items.get(groupPosition).getSurah_no().equalsIgnoreCase("0")){
            viewHolderChild.txtListChildayat.setText(childItem.getAyat_no());
        }
        else {
            viewHolderChild.txtListChildayat.setText("Verse " + childItem.getAyat_no());
        }


        viewHolderChild.txtListChildayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSuraListener.amaniaudioParameter(items.get(groupPosition).getSurah_no(),items.get(groupPosition).getChapter_name(),items.get(groupPosition).getChapter_name_meaning(), childItem.getAyat_no());
            }
        });
        return convertView;
    }


    public class ViewHolderChild {
        TextView txtListChildayat;
    }

    @Override
    public int getRealChildrenCount(int groupPosition) {
        return items.get(groupPosition).getAyat().size();
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


        final ChapterList groupItem = items.get(groupPosition);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.amaniplayergrupitem, null);
            viewHolder.lblListHeader = (TextView) convertView
                    .findViewById(R.id.chapternameTextView);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.dropdown);

            viewHolder.chaptermeaning = (TextView) convertView
                    .findViewById(R.id.chapterMeaningTextView);
            viewHolder.chapterarabictitle = (TextView) convertView
                    .findViewById(R.id.arabic);
            viewHolder.lblListHeader.setTypeface(null, Typeface.BOLD);


            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }

        //  System.out.println("malayalmfont"+malayalamFont);
        if (malayalamFont.equalsIgnoreCase("0")) {
            viewHolder.lblListHeader.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);
            viewHolder.chaptermeaning.setTextSize(TypedValue.COMPLEX_UNIT_SP, 7);
        } else if (malayalamFont.equalsIgnoreCase("1")) {
            viewHolder.lblListHeader.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            viewHolder.chaptermeaning.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8);
        } else if (malayalamFont.equalsIgnoreCase("2")) {
            viewHolder.lblListHeader.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            viewHolder.chaptermeaning.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        } else if (malayalamFont.equalsIgnoreCase("3")) {
            viewHolder.lblListHeader.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            viewHolder.chaptermeaning.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        } else if (malayalamFont.equalsIgnoreCase("4")) {
            viewHolder.lblListHeader.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            viewHolder.chaptermeaning.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        } else if (malayalamFont.equalsIgnoreCase("5")) {
            viewHolder.lblListHeader.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            viewHolder.chaptermeaning.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        } else if (malayalamFont.equalsIgnoreCase("6")) {
            viewHolder.lblListHeader.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            viewHolder.chaptermeaning.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        }


//arabic font

        if (arabicfont.equalsIgnoreCase("0")) {
            viewHolder.chapterarabictitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);

        } else if (arabicfont.equalsIgnoreCase("1")) {
            viewHolder.chapterarabictitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);

        } else if (arabicfont.equalsIgnoreCase("2")) {
            viewHolder.chapterarabictitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);

        } else if (arabicfont.equalsIgnoreCase("3")) {
            viewHolder.chapterarabictitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

        } else if (arabicfont.equalsIgnoreCase("4")) {
            viewHolder.chapterarabictitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

        } else if (arabicfont.equalsIgnoreCase("5")) {
            viewHolder.chapterarabictitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

        } else if (arabicfont.equalsIgnoreCase("6")) {
            viewHolder.chapterarabictitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

        }

        if (isExpanded) {
            viewHolder.img.setImageResource(R.drawable.open);
        } else {
            viewHolder.img.setImageResource(R.drawable.closed);
        }
////////
        viewHolder.chapterarabictitle.setText(groupItem.getArabic_title().trim());
        if (groupItem.getChapter_name_meaning().trim().isEmpty()) {
            viewHolder.chaptermeaning.setVisibility(View.GONE);
        }
        else {
            viewHolder.chaptermeaning.setVisibility(View.VISIBLE);
            viewHolder.chaptermeaning.setText(groupItem.getChapter_name_meaning().trim());
        }


        viewHolder.lblListHeader.setText(groupItem.getSurah_no()+". "+groupItem.getChapter_name().trim());

/////////////
        viewHolder.lblListHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (groupPosition != 0) {
                    //call interface method to pass chapterid
                  //  mSuraListener.chapter_explanation_listener(groupItem.getChapter_id());
                }
            }
        });
        viewHolder.chaptermeaning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // mSuraListener.chapter_explanation_listener(groupItem.getChapter_id());
            }
        });
        /*viewHolder. chapterarabictitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(_context, "next page", Toast.LENGTH_LONG).show();
            }
        });*/
        return convertView;
    }

    public class ViewHolder {
        TextView lblListHeader, chaptermeaning, chapterarabictitle;
        ImageView img;

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
