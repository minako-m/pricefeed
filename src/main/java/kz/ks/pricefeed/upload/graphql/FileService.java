package kz.ks.pricefeed.upload.graphql;

import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import kz.ks.pricefeed.upload.graphql.model.FileFirstLineModel;
import kz.ks.pricefeed.upload.graphql.model.FileModel;
import kz.ks.pricefeed.upload.repository.FileDBRepository;
import kz.ks.pricefeed.upload.service.FilesStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
@GraphQLApi
public class FileService {
    private final FileDBRepository repository;
    private final FilesStorageService filesStorageService;

    @GraphQLQuery(name = "listFiles")
    public List<FileModel> listFiles() {
        return repository.findAll().stream()
                .map(
                        f -> FileModel.builder()
                                .id(f.getId())
                                .name(f.getName())
                                .type(f.getType())
                                .state(f.getState())
                                .build()
                ).collect(Collectors.toList());
    }

    @GraphQLQuery(name = "findFile")
    public Optional<FileModel> findFile(String id) {
        return repository.findById(id)
                .map(
                        f -> FileModel.builder()
                                .id(f.getId())
                                .name(f.getName())
                                .type(f.getType())
                                .state(f.getState())
                                .build()
                );
    }

    @GraphQLMutation(name = "deleteFile", description = "deletes file")
    public void delete(String id) {
        repository.findById(id)
                .ifPresentOrElse(
                        repository::delete,
                        () -> {
                            throw new IllegalArgumentException("File does not exist");
                        }
                );
    }

    @GraphQLQuery(name = "firstLine")
    public FileFirstLineModel getFirstLine(@GraphQLContext FileModel file) throws IOException {
        try (
                var is = filesStorageService.openFile(file.getId());
                var isr = new InputStreamReader(is);
                var br = new BufferedReader(isr)
        ) {
            return FileFirstLineModel.builder()
                    .line(br.readLine())
                    .build();
        }

    }
}
