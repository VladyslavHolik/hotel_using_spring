package holik.hotel.spring.web.converter;

public interface Converter<S,T> {
    T covertToEntity(S entity);
}
