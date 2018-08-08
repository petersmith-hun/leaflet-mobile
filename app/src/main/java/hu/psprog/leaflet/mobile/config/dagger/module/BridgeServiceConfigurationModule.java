package hu.psprog.leaflet.mobile.config.dagger.module;

import dagger.Module;
import dagger.Provides;
import hu.psprog.leaflet.bridge.client.BridgeClient;
import hu.psprog.leaflet.bridge.service.CategoryBridgeService;
import hu.psprog.leaflet.bridge.service.DocumentBridgeService;
import hu.psprog.leaflet.bridge.service.EntryBridgeService;
import hu.psprog.leaflet.bridge.service.impl.CategoryBridgeServiceImpl;
import hu.psprog.leaflet.bridge.service.impl.DocumentBridgeServiceImpl;
import hu.psprog.leaflet.bridge.service.impl.EntryBridgeServiceImpl;

/**
 * @author Peter Smith
 */
@Module
public class BridgeServiceConfigurationModule {

    @Provides
    public CategoryBridgeService categoryBridgeService(BridgeClient bridgeClient) {
        return new CategoryBridgeServiceImpl(bridgeClient);
    }

    @Provides
    public DocumentBridgeService documentBridgeService(BridgeClient bridgeClient) {
        return new DocumentBridgeServiceImpl(bridgeClient);
    }

    @Provides
    public EntryBridgeService entryBridgeService(BridgeClient bridgeClient) {
        return new EntryBridgeServiceImpl(bridgeClient);
    }
}
