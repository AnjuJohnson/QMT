package com.Qubicle.QMT.RoomDb;

import androidx.room.TypeConverter;

import com.Qubicle.QMT.Models.AyatList;
import com.Qubicle.QMT.Models.ReciterNew;
import com.Qubicle.QMT.Models.Verse;
import com.Qubicle.QMT.Models.VerseDetail;
import com.Qubicle.QMT.Models.WordMeaningList;
import com.Qubicle.QMT.Models.WordMeaningListNew;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Anju on 27-08-2019.
 */
public class DataConverter {
    @TypeConverter // note this annotation


    public String fromOptionValuesList(List<AyatList> optionValues) {
        if (optionValues == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<AyatList>>() {
        }.getType();
        String json = gson.toJson(optionValues, type);
        return json;
    }

    @TypeConverter // note this annotation
    public List<AyatList> toOptionValuesList(String optionValuesString) {
        if (optionValuesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<AyatList>>() {
        }.getType();
        List<AyatList> productCategoriesList = gson.fromJson(optionValuesString, type);
        return productCategoriesList;
    }


    @TypeConverter // note this annotation
    public String fromOptionVerseDetailValuesList(List<WordMeaningList> optionValues) {
        if (optionValues == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<WordMeaningList>>() {
        }.getType();
        String json = gson.toJson(optionValues, type);
        return json;
    }
    @TypeConverter // note this annotation
    public List<WordMeaningList> toOptionValuesListVersedetail(String optionValuesString) {
        if (optionValuesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<WordMeaningList>>() {
        }.getType();
        List<WordMeaningList> productCategoriesList = gson.fromJson(optionValuesString, type);
        return productCategoriesList;
    }



    @TypeConverter // note this annotation
    public String fromOptionJuzDetailValuesList(List<Verse> optionValues) {
        if (optionValues == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Verse>>() {
        }.getType();
        String json = gson.toJson(optionValues, type);
        return json;
    }

    @TypeConverter // note this annotation
    public List<Verse> toOptionValuesListJuzdetail(String optionValuesString) {
        if (optionValuesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Verse>>() {
        }.getType();
        List<Verse> productCategoriesList = gson.fromJson(optionValuesString, type);
        return productCategoriesList;
    }

    ///////////////



    @TypeConverter // note this annotation
    public String fromOptionwordList(List<WordMeaningListNew> optionValues) {
        if (optionValues == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<WordMeaningListNew>>() {
        }.getType();
        String json = gson.toJson(optionValues, type);
        return json;
    }
    @TypeConverter // note this annotation
    public List<WordMeaningListNew> toOptionValuesListword(String optionValuesString) {
        if (optionValuesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<WordMeaningListNew>>() {
        }.getType();
        List<WordMeaningListNew> productCategoriesList = gson.fromJson(optionValuesString, type);
        return productCategoriesList;
    }

//////////reciter



    @TypeConverter // note this annotation
    public String fromReciterList(List<ReciterNew> optionValues) {
        if (optionValues == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<ReciterNew>>() {
        }.getType();
        String json = gson.toJson(optionValues, type);
        return json;
    }
    @TypeConverter // note this annotation
    public List<ReciterNew> toReciterListword(String optionValuesString) {
        if (optionValuesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<ReciterNew>>() {
        }.getType();
        List<ReciterNew> productCategoriesList = gson.fromJson(optionValuesString, type);
        return productCategoriesList;
    }







}
