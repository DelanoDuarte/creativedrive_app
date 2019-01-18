/**
 * 
 */
package com.creativedrive.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

/**
 * @author Delano Jr
 *
 */
public interface BaseService<T, ID> {

	public Optional<T> save(T t);

	public Optional<List<T>> saveAll(List<T> ts);

	public Optional<List<T>> findAll();

	public Optional<Page<T>> findAllPagineted(Integer page, Integer size);

	public Optional<Page<T>> findAllPaginetedSorted(Integer page, Integer size, String order);

	public Optional<Page<T>> findAllPaginetedByExample(T t, Integer page, Integer size);

	public Optional<T> findOne(ID id);

	public Optional<T> findByExample(T t);
}
