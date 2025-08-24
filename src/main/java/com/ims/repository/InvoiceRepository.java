package com.ims.repository;

import com.ims.model.Invoice;
import java.util.List;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface InvoiceRepository extends CassandraRepository<Invoice, String> {

    @AllowFiltering
    List<Invoice> findAllByStatus(String status);
}


