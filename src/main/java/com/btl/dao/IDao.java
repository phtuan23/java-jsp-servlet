package com.btl.dao;

import java.util.List;

public interface IDao<T> {
	public List<T> getAll();
	public boolean add(T obj);
	public boolean update(T obj);
	public boolean delete(Object id);
	public T getById(Object id);
}
