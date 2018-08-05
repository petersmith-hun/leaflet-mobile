package hu.psprog.leaflet.mobile.repository.impl;

import android.util.Log;
import hu.psprog.leaflet.mobile.model.DocumentDetails;
import hu.psprog.leaflet.mobile.repository.DocumentRepository;
import io.reactivex.Observable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Dummy implementation of {@link DocumentRepository}.
 *
 * @author Peter Smith
 */
public class DummyDocumentRepositoryImpl implements DocumentRepository {

    private static final String DEFAULT_CONTENT = "default_content";

    private ObservableFactory observableFactory;

    public DummyDocumentRepositoryImpl() {
        // TODO configure Dagger DI
        observableFactory = new ObservableFactory();
    }

    @Override
    public Observable<DocumentDetails> getDocument(String link) {
        return observableFactory.create(() -> {
            Thread.sleep(2000); // simulating long network call
            return DocumentDetails.getBuilder()
                    .withLink(link)
                    .withTitle("Introduction")
                    .withContent(getContent())
                    .build();
        });
    }

    private String getContent() {

        return getEntryContent()
                .map(inputStream -> {
                    String result;
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                        result = reader.lines()
                                .collect(Collectors.joining());
                    } catch (IOException exc) {
                        Log.e(DummyDocumentRepositoryImpl.class.getSimpleName(), "Failed to read dummy entry content", exc);
                        result = DEFAULT_CONTENT;
                    }

                    return result;
                })
                .orElse(DEFAULT_CONTENT);
    }

    private Optional<InputStream> getEntryContent() {
        return Optional.ofNullable(getClass().getResourceAsStream("/res/raw/entry.html"));
    }
}
