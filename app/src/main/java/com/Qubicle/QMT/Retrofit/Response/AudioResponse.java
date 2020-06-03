package com.Qubicle.QMT.Retrofit.Response;
import com.Qubicle.QMT.Models.Audio;
import com.Qubicle.QMT.Models.VerseExplanation;

import java.util.List;

public class AudioResponse extends BaseResponse<Audio> {

    public AudioResponse(List<Audio> data) {
        super(data);
    }

}
