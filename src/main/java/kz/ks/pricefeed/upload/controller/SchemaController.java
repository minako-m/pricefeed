package kz.ks.pricefeed.upload.controller;

import graphql.schema.GraphQLSchema;
import graphql.schema.idl.SchemaPrinter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SchemaController {
    private final GraphQLSchema schema;

    @GetMapping("/schema")
    public String getSchema() {
        return new SchemaPrinter(
                SchemaPrinter.Options.defaultOptions()
                        .includeScalarTypes(true)
                        .includeSchemaDefinition(true)
                        .includeDirectives(false)
        ).print(schema);
    }
}
