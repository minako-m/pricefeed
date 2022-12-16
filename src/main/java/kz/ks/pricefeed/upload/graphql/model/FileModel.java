package kz.ks.pricefeed.upload.graphql.model;

import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.annotations.types.GraphQLType;
import kz.ks.pricefeed.upload.model.ProcessingState;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@GraphQLType(name = "file", description = "contains uploaded file information")
public class FileModel {
    @GraphQLQuery(name = "id", description = "Unique file code")
    private String id;
    @GraphQLQuery(name = "name", description = "Original file name")
    private String name;
    @GraphQLQuery(name = "type", description = "Original file type")
    private String type;
    @GraphQLQuery(name = "state", description = "Current processing state")
    private ProcessingState state;
}
