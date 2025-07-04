package com.dakson.hr.common.service;

public interface CrudService<
  RQCreate, RQUpdate, RSCreate, RSRead, RSUpdate, RSDelete, ID
> {
  RSCreate create(RQCreate body);

  RSRead findById(ID id);

  RSUpdate updateById(RQUpdate body, ID id);

  RSDelete deleteById(ID id);
}
