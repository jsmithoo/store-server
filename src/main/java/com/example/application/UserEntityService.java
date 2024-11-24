package com.example.application;

import com.example.adapters.outbound.repository.UserEntityJpaRepository;
import com.example.adapters.outbound.repository.entities.Product;
import com.example.adapters.outbound.repository.entities.UserEntity;
import com.example.application.paramas.UserEntityUpdateParams;
import com.example.domain.exception.NotFoundException;
import java.util.List;
import java.util.Optional;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserEntityService {

    public final UserEntityJpaRepository userEntityJpaRepository;

    public UserEntityService(UserEntityJpaRepository userEntityJpaRepository) {
        this.userEntityJpaRepository = userEntityJpaRepository;
    }

    public UserEntity findById(Long id) {
        return userEntityJpaRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(UserEntity.class.getSimpleName(), id.toString()));
    }

    public Optional<UserEntity> findByUsername(String username) {
        return userEntityJpaRepository.findByUsername(username);
    }

    public UserEntity saveUserEntity(UserEntity userEntity) {
        return userEntityJpaRepository.save(userEntity);
    }

    public void deleteUserEntity(Long id) {
        userEntityJpaRepository
                .findById(id)
                .ifPresentOrElse((userEntity) -> userEntityJpaRepository.deleteById(id), () -> {
                    throw new NotFoundException(UserEntity.class.getSimpleName(), id.toString());
                });
    }

    public UserEntity updateUserEntity(Long id, UserEntityUpdateParams userEntityUpdateParams) {
        UserEntity userEntityToUpdate = userEntityJpaRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(UserEntity.class.getSimpleName(), id.toString()));

        userEntityToUpdate.setName(userEntityUpdateParams.getName());
        userEntityToUpdate.setEmail(userEntityUpdateParams.getEmail());
        userEntityToUpdate.setAddress(userEntityUpdateParams.getAddress());
        userEntityToUpdate.setPhone(userEntityUpdateParams.getPhone());
        return userEntityJpaRepository.save(userEntityToUpdate);
    }

    @Transactional
    public List<Product> getProductsByUser(Long id) {
        Optional<UserEntity> userId = userEntityJpaRepository.findById(id);
        if (userId.isPresent()) {
            UserEntity userEntity = userId.get();
            Hibernate.initialize(userEntity.getProductList());
            return userEntity.getProductList();
        } else {
            throw new NotFoundException(UserEntity.class.getSimpleName(), id.toString());
        }
    }
}
