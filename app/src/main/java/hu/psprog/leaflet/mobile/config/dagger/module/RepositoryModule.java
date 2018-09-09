package hu.psprog.leaflet.mobile.config.dagger.module;

import dagger.Binds;
import dagger.Module;
import hu.psprog.leaflet.mobile.repository.CategoryRepository;
import hu.psprog.leaflet.mobile.repository.DocumentRepository;
import hu.psprog.leaflet.mobile.repository.EntryRepository;
import hu.psprog.leaflet.mobile.repository.SupportRepository;
import hu.psprog.leaflet.mobile.repository.impl.CategoryRepositoryImpl;
import hu.psprog.leaflet.mobile.repository.impl.DocumentRepositoryImpl;
import hu.psprog.leaflet.mobile.repository.impl.EntryRepositoryImpl;
import hu.psprog.leaflet.mobile.repository.impl.SupportRepositoryImpl;
import hu.psprog.leaflet.mobile.repository.impl.adapter.CategoryAdapter;
import hu.psprog.leaflet.mobile.repository.impl.adapter.DocumentAdapter;
import hu.psprog.leaflet.mobile.repository.impl.adapter.EntryAdapter;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline.CategoryLocalCacheAdapter;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline.DocumentLocalCacheAdapter;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.offline.EntryLocalCacheAdapter;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.online.CategoryNetworkRequestAdapter;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.online.DocumentNetworkRequestAdapter;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.online.EntryNetworkRequestAdapter;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.online.caching.CachingCategoryNetworkRequestAdapter;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.online.caching.CachingDocumentNetworkRequestAdapter;
import hu.psprog.leaflet.mobile.repository.impl.adapter.impl.online.caching.CachingEntryNetworkRequestAdapter;

import javax.inject.Named;

/**
 * Dagger Module configuration for repositories.
 *
 * @author Peter Smith
 */
@Module
public abstract class RepositoryModule {

    @Binds
    public abstract CategoryRepository bindCategoryRepository(CategoryRepositoryImpl categoryRepository);

    @Binds
    public abstract DocumentRepository bindDocumentRepository(DocumentRepositoryImpl documentRepository);

    @Binds
    public abstract EntryRepository bindEntryRepository(EntryRepositoryImpl entryRepository);

    @Binds
    public abstract SupportRepository bindSupportRepository(SupportRepositoryImpl supportRepository);

    @Binds
    @Named("entryLocalCacheAdapter")
    public abstract EntryAdapter bindEntryLocalCacheAdapter(EntryLocalCacheAdapter entryLocalCacheAdapter);

    @Binds
    @Named("entryNetworkRequestAdapter")
    public abstract EntryAdapter bindEntryNetworkRequestAdapter(EntryNetworkRequestAdapter entryNetworkRequestAdapter);

    @Binds
    @Named("cachingEntryNetworkRequestAdapter")
    public abstract EntryAdapter bindCachingEntryNetworkRequestAdapter(CachingEntryNetworkRequestAdapter cachingEntryNetworkRequestAdapter);

    @Binds
    @Named("documentLocalCacheAdapter")
    public abstract DocumentAdapter bindDocumentLocalCacheAdapter(DocumentLocalCacheAdapter documentLocalCacheAdapter);

    @Binds
    @Named("documentNetworkRequestAdapter")
    public abstract DocumentAdapter bindDocumentNetworkRequestAdapter(DocumentNetworkRequestAdapter documentNetworkRequestAdapter);

    @Binds
    @Named("cachingDocumentNetworkRequestAdapter")
    public abstract DocumentAdapter bindCachingDocumentNetworkRequestAdapter(CachingDocumentNetworkRequestAdapter cachingDocumentNetworkRequestAdapter);

    @Binds
    @Named("categoryLocalCacheAdapter")
    public abstract CategoryAdapter bindCategoryLocalCacheAdapter(CategoryLocalCacheAdapter categoryLocalCacheAdapter);

    @Binds
    @Named("categoryNetworkRequestAdapter")
    public abstract CategoryAdapter bindCategoryNetworkRequestAdapter(CategoryNetworkRequestAdapter categoryNetworkRequestAdapter);

    @Binds
    @Named("cachingCategoryNetworkRequestAdapter")
    public abstract CategoryAdapter bindCachingCategoryNetworkRequestAdapter(CachingCategoryNetworkRequestAdapter cachingCategoryNetworkRequestAdapter);
}
