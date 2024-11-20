package org.andersen.service;

import java.util.List;

public interface SortService<T> {

    List<T> sortByField(int pageNumber, int pageSize, String fieldName);
}
