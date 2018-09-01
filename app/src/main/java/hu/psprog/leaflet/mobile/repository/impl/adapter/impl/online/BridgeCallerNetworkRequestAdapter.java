package hu.psprog.leaflet.mobile.repository.impl.adapter.impl.online;

import hu.psprog.leaflet.bridge.client.exception.CommunicationFailureException;

/**
 * @author Peter Smith
 */
abstract class BridgeCallerNetworkRequestAdapter {

    <T> T callBridge(BridgeResultSupplier<T> supplier) {

        T result = null;
        try {
            result = supplier.get();
        } catch (CommunicationFailureException exc) {
            // TODO do something
        }

        return result;
    }

    interface BridgeResultSupplier<T> {
        T get() throws CommunicationFailureException;
    }
}
