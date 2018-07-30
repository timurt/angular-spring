package kz.iamtim.arm.service;

import kz.iamtim.arm.models.Apartment;
import kz.iamtim.arm.repository.ApartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * Documentation for {@code ApartmentServiceImpl}.
 *
 * @author Timur Tibeyev.
 */
@Service
public class ApartmentServiceImpl implements ApartmentService {

    @Autowired
    ApartmentRepository repository;

    @Override
    public Apartment save(Apartment apartment) {
        return repository.save(apartment);
    }

    @Override
    public Page<Apartment> getByPage(Specification<Apartment> specs, Pageable pageable) {
        return repository.findAll(specs, pageable);
    }

    @Override
    public Apartment findById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public boolean apartmentExists(Long id) {
        if (id == null) {
            return false;
        }
        final Apartment apartment = repository.findById(id).get();
        return !(apartment == null || apartment.isDeleted());
    }

    @Override
    public void delete(Long id) {
        final Apartment apartment = repository.findById(id).get();
        apartment.setDeleted(true);
        repository.save(apartment);
    }
}
