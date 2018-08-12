package hu.psprog.leaflet.mobile.repository.conversion;

/**
 * Interface for implementations providing conversion capabilities.
 *
 * @param <S> source object
 * @param <T> target object
 * @author Peter Smith
 */
public interface Converter<S, T> {

    /**
     * Converts objects of type S to type T.
     *
     * @param source source object to convert
     * @return converted object as type of T
     */
    T convert(S source);
}
