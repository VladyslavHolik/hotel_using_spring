package holik.hotel.spring.web.sort;

import org.springframework.data.domain.Sort;

/**
 * Enumeration for sorting methods.
 */
public enum SortMethod {
    PRICE(Sort.by("price")), SPACE(Sort.by("space")),
    CLASS(Sort.by("roomClass")), STATUS(Sort.by("roomStatus"));

    private final Sort sort;

    SortMethod(Sort sort) {
        this.sort = sort;
    }

    public Sort getSort() {
        return sort;
    }

    public static SortMethod getMethod(String method) {
        if (isMethodInvalid(method)) {
            throw new IllegalArgumentException("method is invalid");
        }

        SortMethod result = null;
        for (SortMethod sortMethod : SortMethod.values()) {
            if (sortMethod.name().equals(method.toUpperCase())) {
                result = sortMethod;
                break;
            }
        }
        return result;
    }

    public static boolean isMethodInvalid(String method) {
        boolean result = false;
        for (SortMethod sortMethod : SortMethod.values()) {
            if (sortMethod.name().equals(method.toUpperCase())) {
                result = true;
                break;
            }
        }
        return !result;
    }
}
