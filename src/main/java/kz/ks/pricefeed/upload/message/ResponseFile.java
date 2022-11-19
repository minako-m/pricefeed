package kz.ks.pricefeed.upload.message;

import kz.ks.pricefeed.upload.model.ProcessingState;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseFile {
    private String name;
    private String url;
    private String type;
    private ProcessingState state;
}
