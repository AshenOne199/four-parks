package com.groupc.fourparks.domain.port;

import com.groupc.fourparks.infraestructure.adapter.entity.RoleEntity;

import java.util.List;

public interface RolePort {
    List<RoleEntity> findRolesByEnum(List<String> roleNames);
}
