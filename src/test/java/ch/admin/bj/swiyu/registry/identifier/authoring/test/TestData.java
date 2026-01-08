package ch.admin.bj.swiyu.registry.identifier.authoring.test;

import ch.admin.bj.swiyu.registry.identifier.authoring.domain.DidEntity;
import ch.admin.bj.swiyu.registry.identifier.authoring.domain.DidType;
import ch.admin.bj.swiyu.registry.identifier.authoring.domain.datastore.DatastoreEntity;
import ch.admin.bj.swiyu.registry.identifier.authoring.domain.datastore.DatastoreStatus;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TestData {

    public static DidEntity didEntityTdw(DatastoreEntity datastoreEntity) {
        return new DidEntity(datastoreEntity, DidType.DID_TDW, "dummy_json_log", "TEST_READ");
    }

    public static DidEntity didEntityWeb(DatastoreEntity datastoreEntity) {
        return new DidEntity(datastoreEntity, DidType.DID_WEB, "{\"json\":\"code\"}", "TEST_READ");
    }

    public static DatastoreEntity datastoreEntity(DatastoreStatus status) {
        var entity = new DatastoreEntity();
        entity.changeStatus(status);
        return entity;
    }
}
