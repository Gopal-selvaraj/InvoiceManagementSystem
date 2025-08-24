package com.ims.repository;

import com.ims.model.Overdue;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface OverdueRepository extends CassandraRepository<Overdue, String> {

}
