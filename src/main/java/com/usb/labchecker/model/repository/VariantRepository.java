package com.usb.labchecker.model.repository;

import com.usb.labchecker.model.entity.Lab;
import com.usb.labchecker.model.entity.Variant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VariantRepository extends CrudRepository<Variant, Integer> {
    List<Variant> findAllByLab(Lab lab);
}
