package kz.iamtim.arm.service;

import kz.iamtim.arm.models.Apartment;
import kz.iamtim.arm.repository.ApartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Apartment service layer implementation.
 *
 * @author Timur Tibeyev.
 */
@Service
public class ApartmentServiceImpl implements ApartmentService {

    /** Apartment repository. */
    @Autowired
    ApartmentRepository repository;

    @Override
    @Secured({"ROLE_ADMIN", "ROLE_REALTOR"})
    public Apartment save(final Apartment apartment) {
        apartment.setUpdatedAt(LocalDateTime.now());

        return repository.save(apartment);
    }

    @Override
    public Page<Apartment> getByPage(final Specification<Apartment> specs,
                                     final Pageable pageable,
                                     final String sortBy) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        if (sortBy != null) {
            switch (sortBy) {
                case "priceAsc" :
                    sort = Sort.by(Sort.Direction.ASC, "pricePerMonth");
                    break;
                case "priceDesc" :
                    sort = Sort.by(Sort.Direction.DESC, "pricePerMonth");
                    break;
                case "dateAsc" :
                    sort = Sort.by(Sort.Direction.ASC, "createdAt");
                    break;
                case "dateDesc" :
                    sort = Sort.by(Sort.Direction.DESC, "createdAt");
                    break;
            }
        }
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(),
                pageable.getPageSize(), sort);
        return repository.findAll(specs, pageRequest);
    }

    @Override
    public Apartment findById(final Long id) {
        return repository.findById(id).get();
    }

    @Override
    public boolean apartmentExists(final Long id) {
        if (id == null) {
            return false;
        }
        Optional<Apartment> optional = repository.findById(id);
        return optional.isPresent() && !optional.get().isDeleted();
    }

    @Override
    @Secured({"ROLE_ADMIN", "ROLE_REALTOR"})
    public void delete(final Long id) {
        final Apartment apartment = repository.findById(id).get();
        apartment.setDeleted(true);
        apartment.setDeletedAt(LocalDateTime.now());
        repository.save(apartment);
    }

    @Override
    @Secured({"ROLE_REALTOR"})
    public void setRented(final Long id, final boolean rented) {
        final Apartment apartment = repository.findById(id).get();
        apartment.setRented(rented);
        repository.save(apartment);
    }
}
