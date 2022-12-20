package kz.ks.pricefeed.upload.message;

import io.swagger.v3.oas.annotations.media.Schema;
import kz.ks.pricefeed.upload.model.ProcessingState;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Schema(name = "FileDetails", description = "Contains file information")
public class ResponseFile {
    @Schema(
            description = "Original file name",
            example = "offers.xml"
    )
    private String name;
    @Schema(
            description = "File download url",
            example = "http://example.com/offers.xml"
    )
    private String url;
    @Schema(
            description = "File mime type",
            example = "application/xml"
    )
    private String type;
    @Schema(
            description = "Current processing state",
            example = "PROCESSED"
    )
    private ProcessingState state;
}
