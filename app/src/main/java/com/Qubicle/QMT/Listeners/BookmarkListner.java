package com.Qubicle.QMT.Listeners;

import com.Qubicle.QMT.Models.Reciter;

import java.util.List;

/**
 * Created by Anju on 13-09-2019.
 */
public interface BookmarkListner {

void callversedetails(String page,String chapterno,String verseno,String juzno);
    void passVerseDetails(String page,String chaptername,String chapterno,String verseno,String juzno);

    void sharestring(String sharestring);

    void passdetailsForNotes(String chaptername,String chapterno,String verseno );
    void shownotes(String chaptername,String chapterno,String verseno);
    void popupdetailpage();
    void deletenote(int noteid,int position);
}
