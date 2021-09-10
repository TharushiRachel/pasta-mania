package com.pastamania.entity.converter;

import com.pastamania.enums.SyncStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class SyncStatusConverter implements AttributeConverter<SyncStatus, String> {

    @Override
    public String convertToDatabaseColumn(SyncStatus syncStatus) {
        return syncStatus != null ? syncStatus.getValue() : null;
    }

    @Override
    public SyncStatus convertToEntityAttribute(String s) {
        return s != null ? SyncStatus.getEnum(s) : null;
    }
}
