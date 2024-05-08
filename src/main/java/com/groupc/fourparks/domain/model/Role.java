package com.groupc.fourparks.domain.model;

import com.groupc.fourparks.infraestructure.adapter.entity.PermissionsEntity;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    private Long id;

    private RoleEnum roleEnum;

    private Set<PermissionsEntity> permissionsList = new HashSet<>();

}
