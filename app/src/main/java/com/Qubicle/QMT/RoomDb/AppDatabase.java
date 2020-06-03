package com.Qubicle.QMT.RoomDb;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.Qubicle.QMT.Base.BaseApplication;
import com.Qubicle.QMT.Models.AudioCategoryReciterList;
import com.Qubicle.QMT.Models.Bookmark;
import com.Qubicle.QMT.Models.ChapterExplanation;
import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.Models.Favorites;
import com.Qubicle.QMT.Models.JuzDetailNew;
import com.Qubicle.QMT.Models.JuzList;
import com.Qubicle.QMT.Models.Notes;
import com.Qubicle.QMT.Models.Reciter;
import com.Qubicle.QMT.Models.VerseDetail;
import com.Qubicle.QMT.Models.VerseExplanation;
import com.Qubicle.QMT.R;
import com.Qubicle.QMT.RoomDb.Dao.BookMarkDao;
import com.Qubicle.QMT.RoomDb.Dao.ChapterExplanationDao;
import com.Qubicle.QMT.RoomDb.Dao.ChapterListDao;
import com.Qubicle.QMT.RoomDb.Dao.FavouritesDao;
import com.Qubicle.QMT.RoomDb.Dao.JuzDetailListDao;
import com.Qubicle.QMT.RoomDb.Dao.JuzListDao;
import com.Qubicle.QMT.RoomDb.Dao.NoteDao;
import com.Qubicle.QMT.RoomDb.Dao.ReciterAudioListDao;
import com.Qubicle.QMT.RoomDb.Dao.ReciterListDao;
import com.Qubicle.QMT.RoomDb.Dao.VerseDetailListDao;
import com.Qubicle.QMT.RoomDb.Dao.VerseExplanationDao;


@Database(entities = { ChapterList.class, ChapterExplanation.class,AudioCategoryReciterList.class, Favorites.class, Reciter.class, Notes.class, Bookmark.class, JuzList.class, VerseDetail.class, JuzDetailNew.class,VerseExplanation.class}, version = 1)
@TypeConverters(DataConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DB_NAME = BaseApplication.getApplication().getString(R.string.db_name);


    public abstract ChapterListDao chapterlistDao();
    public abstract JuzListDao juzlistDao();
    public abstract VerseDetailListDao verseDetailListDao();
    public abstract JuzDetailListDao juzDetailListDao();
    public abstract ReciterListDao ReciterListDao();
    public abstract ReciterAudioListDao ReciterAudioListDao();
    public abstract FavouritesDao FavouritesDao();

    public abstract ChapterExplanationDao chapterExplanationDao();
    public abstract BookMarkDao bookMarkDao();
    public abstract NoteDao noteDao();

    public abstract VerseExplanationDao verseExplanationDao();
    private static volatile AppDatabase INSTANCE;
    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DB_NAME).allowMainThreadQueries()
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this codelab.
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}