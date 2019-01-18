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

	/*
	 * Find all records of entity based in a Entity paginating with page ,size and
	 * sort order params
	 */
	public Optional<Page<T>> findAllPaginetedSorted(Integer page, Integer size, String order);

	/*
	 * Find all records of entity based in a Entity Example.of and pagination with
	 * page and size params
	 */
	public Optional<Page<T>> findAllPaginetedByExample(T t, Integer page, Integer size);

	/*
	 * Find a entity object by param id
	 */
	public Optional<T> findOne(ID id);

	/*
	 * Find a object based on entity object using spring Example.of
	 */
	public Optional<T> findByExample(T t);

	/*
	 * Delete a record by entity object param
	 */
	public void delete(T t);

	/*
	 * Delete a record by param id
	 */
	public void deleteById(ID id);

	/*
	 * Delete all items from list param
	 */
	public void deleteAll(List<T> ts);

	/*
	 * CAUTION, delete all records from table
	 */
	public void deleteAllRecords();
}
