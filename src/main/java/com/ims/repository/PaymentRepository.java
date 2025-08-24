package com.ims.repository;

import com.ims.model.Payment;
import java.util.List;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface PaymentRepository extends CassandraRepository<Payment, String> {
    @AllowFiltering
    List<Payment> findByInvoiceId(String invoiceId);

    @AllowFiltering
    void deleteByInvoiceId(String invoiceId);
}
