/*
 * SPDX-FileCopyrightText: 2025 Swiss Confederation
 *
 * SPDX-License-Identifier: MIT
 */

package ch.admin.bj.swiyu.registry.identifier.authoring.service;

import ch.admin.bj.swiyu.registry.identifier.authoring.api.DatastoreEntityRequestDto;
import ch.admin.bj.swiyu.registry.identifier.authoring.common.exception.ResourceNotFoundException;
import ch.admin.bj.swiyu.registry.identifier.authoring.common.exception.ResourceNotReadyException;
import ch.admin.bj.swiyu.registry.identifier.authoring.domain.datastore.DatastoreEntity;
import ch.admin.bj.swiyu.registry.identifier.authoring.domain.datastore.DatastoreEntityRepository;
import ch.admin.bj.swiyu.registry.identifier.authoring.domain.datastore.DatastoreStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static ch.admin.bj.swiyu.registry.identifier.authoring.service.mapper.EnumMapper.mapEnum;

/**
 * Internal (not public) Domain Service for accessing DatastoreEntity directly.
 */
@Service
@AllArgsConstructor
class DatastoreEntityService {

    private final DatastoreEntityRepository datastoreEntityRepository;

    public static void validateCanEdit(DatastoreEntity entry) throws ResourceNotReadyException {
        if (entry.getStatus() == DatastoreStatus.DISABLED) throw new ResourceNotReadyException(
                entry.getId().toString(),
                DatastoreEntity.class
        );
    }

    @Transactional(readOnly = true)
    public DatastoreEntity getDatastoreEntity(UUID id) {
        return this.datastoreEntityRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(id.toString(), DatastoreEntity.class)
        );
    }

    @Transactional
    public DatastoreEntity createEmptyDatastoreEntity() {
        return this.datastoreEntityRepository.save(new DatastoreEntity());
    }

    @Transactional
    public void updateDatastoreEntity(UUID id, DatastoreEntityRequestDto request) {
        DatastoreEntity entity = this.getDatastoreEntity(id);
        if (request.getStatus().isPresent()) {
            var newStatus = mapEnum(DatastoreStatus.class, request.getStatus().get());
            entity.changeStatus(newStatus);
        }
        this.datastoreEntityRepository.save(entity);
    }

    @Transactional
    public DatastoreEntity activateDatastoreEntity(DatastoreEntity base) {
        base.changeStatus(DatastoreStatus.ACTIVE);
        return this.datastoreEntityRepository.save(base);
    }
}
