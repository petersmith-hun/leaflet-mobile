package hu.psprog.leaflet.mobile.bridge.support;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import java.util.Arrays;
import java.util.List;

/**
 * Feature implementation for Jersey client to exclude unnecessary filters.
 *
 * @author Peter Smith
 */
public class JerseyFilterExclusionFeature implements Feature {

    private static final List<String> EXCLUDED_JERSEY_FILTERS = Arrays.asList(
            "org.glassfish.jersey.message.internal.DataSource",
            "org.glassfish.jersey.message.internal.RenderedImage");

    @Override
    public boolean configure(FeatureContext context) {
        context.register(new AbstractBinder() {
            @Override
            protected void configure() {
                addUnbindFilter(d -> {
                    String implClass = d.getImplementation();
                    return EXCLUDED_JERSEY_FILTERS.stream()
                            .anyMatch(implClass::startsWith);
                });
            }
        });

        return true;
    }
}
