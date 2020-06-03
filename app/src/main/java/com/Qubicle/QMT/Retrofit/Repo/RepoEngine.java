package com.Qubicle.QMT.Retrofit.Repo;

import com.Qubicle.QMT.Controllers.AlltopicsFragmentController;
import com.Qubicle.QMT.Controllers.AlltopicsIndexfragmentController;
import com.Qubicle.QMT.Controllers.AmaniPlayerFragmentController;
import com.Qubicle.QMT.Controllers.ChapterExplanationController;
import com.Qubicle.QMT.Controllers.IndexVersefragmentController;
import com.Qubicle.QMT.Controllers.JuzDetailFragmentController;
import com.Qubicle.QMT.Controllers.JuzFragmentController;
import com.Qubicle.QMT.Controllers.MaintopicsVersefragmentController;
import com.Qubicle.QMT.Controllers.MaintopicsfragmentController;
import com.Qubicle.QMT.Controllers.MemorizationFragmentController;
import com.Qubicle.QMT.Controllers.QuestionAnswerFragmentController;
import com.Qubicle.QMT.Controllers.ReciterActivityController;
import com.Qubicle.QMT.Controllers.SearchFragmentController;
import com.Qubicle.QMT.Controllers.SettingsFragmentController;
import com.Qubicle.QMT.Controllers.SuraFragmentController;
import com.Qubicle.QMT.Controllers.TajweedClassesPlayerFragmentController;
import com.Qubicle.QMT.Controllers.TajweedClassesTitlesFragmentController;
import com.Qubicle.QMT.Controllers.TajweedUlFragmentController;
import com.Qubicle.QMT.Controllers.TajweedUlQuranPlayerFragmentController;
import com.Qubicle.QMT.Controllers.VerseDetailFragmentController;
import com.Qubicle.QMT.Controllers.VerseExplanationController;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Anjumol Johnson on 26/1/19.
 */
public class RepoEngine {
    private static RepoEngine repoEngine;

    private RepoEngine() {
    }

    public static RepoEngine getRepoEngine() {
        if (repoEngine == null)
            repoEngine = new RepoEngine();
        return repoEngine;
    }

    public void init() {
        repoEngine.registerEventListener();
    }

    private void registerEventListener() {
        if (!EventBus.getDefault().isRegistered(repoEngine)) {
            EventBus.getDefault().register(repoEngine);
        }
    }
    @SuppressWarnings("unused")
    public void destroy() {
        unRegisterEventListener();
    }

    private void unRegisterEventListener() {
        if (EventBus.getDefault().isRegistered(repoEngine)) {
            EventBus.getDefault().unregister(repoEngine);
        }
    }
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEvent(RepoRequestEvent repoRequestEvent) {
        switch (repoRequestEvent.getRequestType()) {
            case RepoRequestType.REQUEST_TYPE_FETCHCHAPTERLIST:
                SuraFragmentController.getController().Servicerequest();
                SuraFragmentController.getController().destroy();
                break;
            case RepoRequestType.REQUEST_TYPE_FETCH_CHAPTERLIST_MEM:
                MemorizationFragmentController.getController().Servicerequest();
                MemorizationFragmentController.getController().destroy();
                break;
                case RepoRequestType.REQUEST_TYPE_FETCHCHAPTERLISTAUDIO:
                    AlltopicsFragmentController.getController().Servicerequest();
                    AlltopicsFragmentController.getController().destroy();
                break;

            case RepoRequestType.REQUEST_TYPE_FETCHCHAPTER_EXPLANATION:

                ChapterExplanationController.getController().Servicerequest();
                ChapterExplanationController.getController().destroy();
                break;
                case RepoRequestType.REQUEST_TYPE_FETCHPRACTICE:

                MemorizationFragmentController.getController().ServicerequestPractice();
                    MemorizationFragmentController.getController().destroy();
                break;
            case RepoRequestType.REQUEST_TYPE_FETCHJUZLIST:

                JuzFragmentController.getController().Servicerequest();
                JuzFragmentController.getController().destroy();
                break;
            case RepoRequestType.REQUEST_TYPE_FETCHVERSEDETAIL:

                VerseDetailFragmentController.getController().Servicerequest();
                VerseDetailFragmentController.getController().destroy();
                break;
            case RepoRequestType.REQUEST_TYPE_FETCHVERSE_EXPLANATION:

                VerseExplanationController.getController().Servicerequest();
                VerseExplanationController.getController().destroy();
                break;
            case RepoRequestType.REQUEST_TYPE_FETCHJUZDETAIL:

                JuzDetailFragmentController.getController().Servicerequest();
                JuzDetailFragmentController.getController().destroy();
                break;
            case RepoRequestType.REQUEST_TYPE_FETCHAUDIO:

                VerseDetailFragmentController.getController().AudioServicerequest();
                VerseDetailFragmentController.getController().destroy();
                break;
            case RepoRequestType.REQUEST_TYPE_FETCHJUZAUDIO:

                JuzDetailFragmentController.getController().AudioServicerequest();
                JuzDetailFragmentController.getController().destroy();
                break;
            case RepoRequestType.REQUEST_TYPE_FETCHRECITER:
                ReciterActivityController.getController().ServiceCall();
               ReciterActivityController.getController().destroy();
                break;
            case RepoRequestType.REQUEST_TYPE_FETCHRECITERAUDIO:

                VerseDetailFragmentController.getController().ServiceCallReciterAudio();
                VerseDetailFragmentController.getController().destroy();
                break;
                case RepoRequestType.REQUEST_TYPE_FETCHRECITERAUDIO_PRACTICE:

                MemorizationFragmentController.getController().ServiceCallReciterAudio();
                    MemorizationFragmentController.getController().destroy();
                break;
            case RepoRequestType.REQUEST_TYPE_FETCHRECITERAUDIO_JUZ:

                JuzDetailFragmentController.getController().ServiceCallReciterAudio();
                JuzDetailFragmentController.getController().destroy();
                break;
                case RepoRequestType.REQUEST_TYPE_FETCHA_OFFLINE_ALLVERSEDETAIL:

                SettingsFragmentController.getController().Servicerequest();
                    SettingsFragmentController.getController().destroy();
                break;
            case RepoRequestType.REQUEST_TYPE_FETCHA_OFFLINE_CHAPTEREXPLANATION:

                SettingsFragmentController.getController().ServicerequestChapterExplanation();
                SettingsFragmentController.getController().destroy();
                break;
            case RepoRequestType.REQUEST_TYPE_FETCHA_OFFLINE_JUZDETAIL:

                SettingsFragmentController.getController().ServicerequestJuzDetail();
                SettingsFragmentController.getController().destroy();
                break;
            case RepoRequestType.REQUEST_TYPE_FETCHA_OFFLINE_JUZ_FROMJUZ:

                JuzFragmentController.getController().ServicerequestJuzDetail();
                JuzFragmentController.getController().destroy();
                break;
            case RepoRequestType.REQUEST_TYPE_FETCHA_OFFLINE_VERSEEXPLANATIOM:

                SettingsFragmentController.getController().ServicerequestVerseExplanation();
                SettingsFragmentController.getController().destroy();
                break;
            case RepoRequestType.REQUEST_TYPE_FETCHTRANSLATION:

                VerseDetailFragmentController.getController().AudioTranslationServicerequest();
                VerseDetailFragmentController.getController().destroy();
                break;
            case RepoRequestType.REQUEST_TYPE_FETCHQA:

                QuestionAnswerFragmentController.getController().Servicerequest();
                QuestionAnswerFragmentController.getController().destroy();
                break;
            case RepoRequestType.REQUEST_TYPE_FETCH_MAINTOPICS:

                MaintopicsfragmentController.getController().ServiceCall();
                MaintopicsfragmentController.getController().destroy();
                break;
                case RepoRequestType.REQUEST_TYPE_FETCH_MAINTOPICS_VERSE:

                MaintopicsVersefragmentController.getController().ServiceCall();
                MaintopicsVersefragmentController.getController().destroy();
                break;
            case RepoRequestType.REQUEST_TYPE_FETCH_ALLTOPIC_INDEX:

                AlltopicsIndexfragmentController.getController().ServiceCall();
                AlltopicsIndexfragmentController.getController().destroy();
                break;
            case RepoRequestType.REQUEST_TYPE_FETCH_ALLTOPIC_VERSE:

                MaintopicsVersefragmentController.getController().AlltopicServiceCall();
                MaintopicsVersefragmentController.getController().destroy();
                break;

            case RepoRequestType.REQUEST_TYPE_FETCH_INDEXVERSEDETAILS:

                IndexVersefragmentController.getController().ServiceCall();
                IndexVersefragmentController.getController().destroy();
                break;

            case RepoRequestType.REQUEST_TYPE_FETCH_SEARCH:

                SearchFragmentController.getController().Servicerequest();
                SearchFragmentController.getController().destroy();
                break;

            case RepoRequestType.REQUEST_TYPE_FETCH_AMANIAUDIO:

                AmaniPlayerFragmentController.getController().Servicerequest();
                AmaniPlayerFragmentController.getController().destroy();
                break;
            case RepoRequestType.REQUEST_TYPE_FETCH_TAJWEED_TITLES:

                TajweedClassesTitlesFragmentController.getController().Servicerequest();
                TajweedClassesTitlesFragmentController.getController().destroy();
                break;
            case RepoRequestType.REQUEST_TYPE_FETCH_TAJWEED_CHAPTERLIST:
                TajweedUlFragmentController.getController().Servicerequest();
                TajweedUlFragmentController.getController().destroy();
                break;
            case RepoRequestType.REQUEST_TYPE_FETCH_TAJWEEDCLASS_AUDIO:
                TajweedClassesPlayerFragmentController.getController().Servicerequest();
                TajweedClassesPlayerFragmentController.getController().destroy();
                break;
                case RepoRequestType.REQUEST_TYPE_FETCH_TAJWEEDUL_QURAN_AUDIO:
                TajweedUlQuranPlayerFragmentController.getController().Servicerequest();
                    TajweedUlQuranPlayerFragmentController.getController().destroy();
                break;
                case RepoRequestType.REQUEST_TYPE_FETCH_MEMORIZATION_TEST:
                MemorizationFragmentController.getController().ServicerequestMemorytest();
                    MemorizationFragmentController.getController().destroy();
                break;
        }
    }

}
