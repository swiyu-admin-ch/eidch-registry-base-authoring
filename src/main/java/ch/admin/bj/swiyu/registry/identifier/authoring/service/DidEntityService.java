/*
 * SPDX-FileCopyrightText: 2025 Swiss Confederation
 *
 * SPDX-License-Identifier: MIT
 */

package ch.admin.bj.swiyu.registry.identifier.authoring.service;

import ch.admin.bj.swiyu.registry.identifier.authoring.api.DatastoreEntityRequestDto;
import ch.admin.bj.swiyu.registry.identifier.authoring.api.DatastoreEntityResponseDto;
import ch.admin.bj.swiyu.registry.identifier.authoring.api.DidEntityResponseDto;
import ch.admin.bj.swiyu.registry.identifier.authoring.common.config.DidAuthoringProperties;
import ch.admin.bj.swiyu.registry.identifier.authoring.domain.DidEntity;
import ch.admin.bj.swiyu.registry.identifier.authoring.domain.DidEntityRepository;
import ch.admin.bj.swiyu.registry.identifier.authoring.domain.DidType;
import ch.admin.bj.swiyu.registry.identifier.authoring.domain.datastore.DatastoreEntity;
import ch.admin.bj.swiyu.registry.identifier.authoring.service.mapper.DidEntityMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.Map;
import java.util.UUID;

import static ch.admin.bj.swiyu.registry.identifier.authoring.service.DatastoreEntityService.validateCanEdit;
import static ch.admin.bj.swiyu.registry.identifier.authoring.service.mapper.DatastoreEntityMapper.toDatastoreEntityResponseDto;
import static ch.admin.bj.swiyu.registry.identifier.authoring.service.mapper.DidEntityMapper.toNotConfiguredDidEntityResponseDto;
import static java.util.stream.Collectors.toMap;

@Slf4j
@Service
@AllArgsConstructor
public class DidEntityService {

    private final DatastoreEntityService datastoreEntityService;
    private final DidEntityRepository didEntityRepository;
    private final DidAuthoringProperties didAuthoringProperties;

    @Transactional
    public DatastoreEntityResponseDto createDatastoreEntity() {
        log.debug("Creating new DatastoreEntity");
        var datastoreEntity = datastoreEntityService.createEmptyDatastoreEntity();
        var didEntities = getAllDatastoreDidEntities(datastoreEntity.getId());
        return toDatastoreEntityResponseDto(datastoreEntity, didEntities);
    }

    @Transactional(readOnly = true)
    public DatastoreEntityResponseDto getDatastoreEntity(@Valid UUID id) {
        log.debug("Looking up datastore entity for id: {}", id);
        var datastoreEntity = this.datastoreEntityService.getDatastoreEntity(id);
        var didEntities = this.getAllDatastoreDidEntities(id);
        return toDatastoreEntityResponseDto(datastoreEntity, didEntities);
    }

    @Transactional
    public DatastoreEntityResponseDto updateDatastoreEntity(@Valid UUID id, DatastoreEntityRequestDto content) {
        log.debug("Updating datastore entity for id: {}", id);
        this.datastoreEntityService.updateDatastoreEntity(id, content);
        return getDatastoreEntity(id);
    }

    @Transactional
    public DatastoreEntityResponseDto updateDidWebEntry(@Valid UUID id, String content) {
        log.debug("Updating did:web entry for id: {}", id);
        var datastoreEntity = saveNewDidAndActivateDatastore(id, DidType.DID_WEB, content);
        var didEntities = getAllDatastoreDidEntities(id);
        return toDatastoreEntityResponseDto(datastoreEntity, didEntities);
    }

    @Transactional
    public DatastoreEntityResponseDto updateDidTdwEntry(@Valid UUID id, String content) {
        log.debug("Updating did:tdw entry for id: {}", id);
        var datastoreEntity = saveNewDidAndActivateDatastore(id, DidType.DID_TDW, content);
        var didEntities = getAllDatastoreDidEntities(id);
        return toDatastoreEntityResponseDto(datastoreEntity, didEntities);
    }

    private DatastoreEntity saveNewDidAndActivateDatastore(
            UUID datastoreEntityId,
            DidType didType,
            String content
    ) {
        var datastoreEntity = datastoreEntityService.getDatastoreEntity(datastoreEntityId);
        validateCanEdit(datastoreEntity);
        var existing = didEntityRepository.findByBase_IdAndFileType(datastoreEntity.getId(), didType);
        if (existing.isEmpty()) {
            var entity = createNewDid(datastoreEntity, didType, content);
            didEntityRepository.save(entity);
        } else {
            var entity = existing.get();
            entity.updateContent(content);
            didEntityRepository.save(entity);
        }
        return datastoreEntityService.activateDatastoreEntity(datastoreEntity);
    }

    private DidEntity createNewDid(DatastoreEntity base, DidType fileType, String content) {
        String readURI = createReadUri(base.getId(), fileType);
        return new DidEntity(base, fileType, content, readURI);
    }

    private String createReadUri(UUID datastoreEntityId, DidType fileType) {
        return switch (fileType) {
            case DID_WEB -> MessageFormat.format(
                    this.didAuthoringProperties.getDidWebTemplate(),
                    datastoreEntityId
            );
            case DID_TDW -> MessageFormat.format(
                    this.didAuthoringProperties.getDidTdwRouteTemplate(),
                    datastoreEntityId
            );
        };
    }

    private Map<String, DidEntityResponseDto> getAllDatastoreDidEntities(UUID datastoreEntityId) {
        var didEntities = this.didEntityRepository.findByBase_Id(datastoreEntityId);
        var result = didEntities
                .stream()
                .collect(toMap(e -> e.getFileType().name(), DidEntityMapper::toDidEntityResponseDto));
        // add did entities for missing types
        for (DidType type : DidType.values()) {
            if (result.get(type.name()) == null) {
                var readUri = createReadUri(datastoreEntityId, type);
                result.put(type.name(), toNotConfiguredDidEntityResponseDto(readUri));
            }
        }
        return result;
    }
}
