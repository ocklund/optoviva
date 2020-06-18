package com.ocklund.optoviva.health;

import com.codahale.metrics.health.HealthCheck;
import com.ocklund.optoviva.db.Storage;

public class StorageHealthCheck extends HealthCheck {
    private final Storage storage;

    public StorageHealthCheck(Storage storage) {
        this.storage = storage;
    }

    @Override
    protected Result check() throws Exception {
        if (storage.isOperational()) {
            return Result.healthy();
        } else {
            return Result.unhealthy("Storage is not operational");
        }
    }
}
