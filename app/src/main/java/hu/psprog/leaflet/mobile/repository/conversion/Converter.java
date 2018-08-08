package hu.psprog.leaflet.mobile.repository.conversion;

/**
 * @author Peter Smith
 */
public interface Converter<S, T> {

    T convert(S source);
}
