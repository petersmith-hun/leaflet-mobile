package hu.psprog.leaflet.mobile.repository.impl.adapter.impl.online;

import hu.psprog.leaflet.bridge.client.exception.CommunicationFailureException;

/**
 * Abstract base implementation for online adapter implementations,
 * that calls Leaflet backend application via Bridge to retrieve data.
 *
 * @author Peter Smith
 */
abstract class BridgeCallerNetworkRequestAdapter {

    /**
     * Calls Bridge via the specified supplier.
     *
     * @param supplier a supplier that calls Bridge
     * @param <T> response type
     * @return Bridge response as type T
     */
    <T> T callBridge(BridgeResultSupplier<T> supplier) {

        T result;
        try {
            result = supplier.get();
        } catch (CommunicationFailureException exc) {
            throw new RuntimeException("Failed to reach API service.", exc);
        }

        return result;
    }

    interface BridgeResultSupplier<T> {
        T get() throws CommunicationFailureException;
    }
}
