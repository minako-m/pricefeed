package kz.ks.pricefeed.upload.graphql.model;

import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.annotations.types.GraphQLType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@GraphQLType(name = "fileFirstLine", description = "first line of contains uploaded file")
public class FileFirstLineModel {
    @GraphQLQuery(name = "line", description = "FirstLineContents")
    String line;
}
