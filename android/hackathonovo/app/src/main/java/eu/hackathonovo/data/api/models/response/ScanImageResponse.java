package eu.hackathonovo.data.api.models.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ScanImageResponse {

    @SerializedName("Predictions")
    public final List<Predictions> predictions;

    public ScanImageResponse(final List<Predictions> predictions) {
        this.predictions = predictions;
    }

    public class Predictions {

        @SerializedName("Tag")
        public final String tag;

        @SerializedName("Probability")
        public final double probability;

        public Predictions(final String tag, final double probability) {
            this.tag = tag;
            this.probability = probability;
        }
    }
}
