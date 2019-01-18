/**
 * 
 */
package com.creativedrive.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Delano Jr
 *
 */
public abstract class BaseService<T, ID> implements com.creativedrive.service.BaseService<T, ID> {

	private MongoRepository<T, ID> repository;

	public BaseService(MongoRepository<T, ID> repository) {
		this.repository = repository;
	}

	@Override
	public Optional<T> save(T t) {
		return Optional.of(repository.save(t));
	}

	@Override
	public Optional<List<T>> saveAll(List<T> ts) {
		return Optional.of(repository.saveAll(ts));
	}

	@Override
	public Optional<List<T>> findAll() {
		return Optional.of(repository.findAll());
	}

	@Override
	public Optional<Page<T>> findAllPagineted(Integer page, Integer size) {
		return Optional.of(repository.findAll(PageRequest.of(page, size)));
	}

	@Override
	public Optional<Page<T>> findAllPaginetedSorted(Integer page, Integer size, String order) {
		return Optional.of(repository.findAll(PageRequest.of(page, size, Sort.by(order))));
	}

	@Override
	public Optional<T> findOne(ID id) {
		return repository.findById(id);
	}

	@Override
	public Optional<T> findByExample(T t) {
		return repository.findOne(Example.of(t));
	}

	@Override
	public Optional<Page<T>> findAllPaginetedByExample(T t, Integer page, Integer size) {
		return Optional.of(repository.findAll(Example.of(t), PageRequest.of(page, size)));
	}

}
