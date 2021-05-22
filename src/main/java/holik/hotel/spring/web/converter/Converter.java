package holik.hotel.spring.web.converter;

public interface Converter<S,T> {
    T convertToEntity(S entity);
}
