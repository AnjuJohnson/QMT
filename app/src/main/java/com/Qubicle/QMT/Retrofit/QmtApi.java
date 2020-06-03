package com.Qubicle.QMT.Retrofit;

import com.Qubicle.QMT.Models.AmaniAudio;
import com.Qubicle.QMT.Models.Audio;
import com.Qubicle.QMT.Models.AudioCategoryReciterList;
import com.Qubicle.QMT.Models.ChapterExplanation;
import com.Qubicle.QMT.Models.ChapterList;
import com.Qubicle.QMT.Models.IndexVerseList;
import com.Qubicle.QMT.Models.JuzDetail;
import com.Qubicle.QMT.Models.JuzDetailNew;
import com.Qubicle.QMT.Models.JuzList;
import com.Qubicle.QMT.Models.Keyword;
import com.Qubicle.QMT.Models.MaintopicIndex;
import com.Qubicle.QMT.Models.OfflineJuzDetailNew;
import com.Qubicle.QMT.Models.Practice;
import com.Qubicle.QMT.Models.QA;
import com.Qubicle.QMT.Models.Reciter;
import com.Qubicle.QMT.Models.Tajweed;
import com.Qubicle.QMT.Models.TajweedClassAudio;
import com.Qubicle.QMT.Models.TajweedUlQuranAudio;
import com.Qubicle.QMT.Models.Verse;
import com.Qubicle.QMT.Models.VerseDetail;
import com.Qubicle.QMT.Models.VerseExplanation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Anju on 23-08-2019.
 */
public interface QmtApi {
    @GET("chapter_list/chapter_ayat")
    Call<ResultObject<ChapterList>> getAllChapterListVerse();

    @FormUrlEncoded
    @POST("chapter_list/get_chapter_explanation")
    Call<ResultObjectSingleData<ChapterExplanation>> getexplanation(@Field("chapter_id") String chapter_id);

    @FormUrlEncoded
    @POST("chapter_list/ayat_explanation")
    Call<ResultObjectSingleData<VerseExplanation>> getVerseexplanation(@Field("chapter_no") String chapter_id,
                                                                       @Field("verse_no") String verseno);


    @GET("Juz_list/list")
    Call<ResultObject<JuzList>> getAllJuzList();


    @GET("ayat/chapterExplanation")
    Call<ResultObject<ChapterExplanation>> getAllOfflineChapterExplanation();


    @GET("ayat/allAyat")
    Call<ResultObject<VerseDetail>> getAllOfflineVerseList();


    @GET("ayat/allJuz")
    Call<ResultObject<OfflineJuzDetailNew>> getAllOfflineJuzList();


    @FormUrlEncoded
    @POST("ayat/ayatExplanation")
    Call<ResultObject<VerseExplanation>> getAllOfflineVerseExplanation(@Field("from") String from,
                                                                       @Field("to") String to);

    @FormUrlEncoded
    @POST("chapter_list/ayat_meaning")
    Call<ResultObject<VerseDetail>> getVerseDetail(@Field("chapter_no") String chapter_id,
                                                   @Field("verse_no") String verse_no);

    @FormUrlEncoded
    @POST("Chapter_QA/questions")
    Call<ResultObject<QA>> getQA(@Field("chapter_no") String chapter_no);

    /*@FormUrlEncoded
    @POST("Juz_detail/list_detail")
    Call<ResultObject<JuzDetailNew>> getJuzDetail(@Field("juz_no") String juzno);
*/
@FormUrlEncoded
    @POST("Juz_detail/new_juz_list")
    Call<ResultObject<JuzDetailNew>> getJuzDetail(@Field("juz_no") String juzno);


    @FormUrlEncoded
    @POST("Select_audio/surah_audios")
    Call<ResultObject<Audio>> getAudio(@Field("chapter_no") String chapter_no,
                                       @Field("verse_from") String verse_from,
                                       @Field("verse_to") String verse_to,
                                       @Field("reciter_name") String reciter_name,
                                       @Field("audio_category") String audio_category);


    @FormUrlEncoded
    @POST("AllAudio/translation_audio")
    Call<ResultObject<Audio>> getAudioTranslation(@Field("chapter_no") String chapter_no,

                                       @Field("reciter_name") String reciter_name,
                                       @Field("audio_category") String audio_category
                                                  );


    @GET("Reciter/fetch_reciters")
    Call<ResultObject<Reciter>> getAllReciterListVerse();
    @GET("Reciter/reciters_categoryList")
    Call<ResultObject<AudioCategoryReciterList>> getAllReciterAudioListVerse();


    @GET("QuranIndex/main_topics")
    Call<ResultObject<MaintopicIndex>> getAllMaintopicindex();

    @FormUrlEncoded
@POST("QuranIndex/main_topics_ref")
    Call<ResultObject<IndexVerseList>> getAllMainVerse( @Field("id") String ref_id,
                                                        @Field("translation") String translation);


    @FormUrlEncoded
    @POST("QuranIndex/all_topics")
    Call<ResultObject<Keyword>> getAlltopicIndex(@Field("chapter_no") String chapter_no);


    @FormUrlEncoded
    @POST("QuranIndex/all_topics_ref")
    Call<ResultObject<IndexVerseList>> getAlltopicVerse( @Field("id") String ref_id,
                                                         @Field("translation") String translation);

    @FormUrlEncoded
    @POST("Select_audio/juz_audios")
    Call<ResultObject<Audio>> getjuzAudio(@Field("chapter_from") String chapter_from,
                                       @Field("chapter_to") String chapter_to,
                                       @Field("verse_from") String verse_from,
                                       @Field("verse_to") String verse_to,
                                       @Field("reciter_name") String reciter_name,
                                       @Field("audio_category") String audio_category);



    @FormUrlEncoded
    @POST("QuranIndex/verse_details")
    Call<ResultObject<VerseDetail>> getIndexVerseDetail(@Field("keyword_id") String keyword_id,
                                                   @Field("status") String status);


    @FormUrlEncoded
    @POST("verse_search/search")
    Call<ResultObject<VerseDetail>> getsearchresult(@Field("chapter_no") String chapter_no,
                                          @Field("verse_no") String verse_no,
                                          @Field("keyword") String keyword,
                                          @Field("translation") String translation);


    @FormUrlEncoded
    @POST("Memorization/fetch_all")
    Call<ResultObject<Verse>> getmemTestVerse(@Field("chapter_no") String chapter_no,
                                              @Field("verse_from") String verse_from,
                                              @Field("verse_to") String verse_to,
                                              @Field("translation") String translation); @FormUrlEncoded
    @POST("Practice/list_audios")
    Call<ResultObject<Practice>> getPractice(@Field("chapter_no") String chapter_no,
                                             @Field("verse_from") String verse_from,
                                             @Field("verse_to") String verse_to,
                                             @Field("translation") String translation,
                                             @Field("reciter_name") String reciter_name);

    @FormUrlEncoded
    @POST("Chapter_Audios/list_audios")
    Call<ResultObject<AmaniAudio>> getAmaniAudio(@Field("chapter_no") String chapter_no,
                                                 @Field("verse_no") String verse_no,
                                                 @Field("audio_category") String audio_category);


    @FormUrlEncoded
    @POST("tajweedClasses/fetchTajweed")
    Call<ResultObject<TajweedClassAudio>> getTajweedClassesAudio(@Field("title_id") String title_id);

@FormUrlEncoded
    @POST("tajweedQuran/fetchTajweed")
    Call<ResultObject<TajweedUlQuranAudio>> getTajweedUlQuranAudio(@Field("chapter_no") String chapter_no);

    @GET("tajweedClasses/list_titles")
    Call<ResultObject<Tajweed>> getAllTitles();


}
