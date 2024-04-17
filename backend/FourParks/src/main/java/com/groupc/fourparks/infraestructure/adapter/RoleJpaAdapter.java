package com.groupc.fourparks.infraestructure.adapter;

import com.groupc.fourparks.domain.port.RolePort;
import com.groupc.fourparks.infraestructure.adapter.entity.RoleEntity;
import com.groupc.fourparks.infraestructure.adapter.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleJpaAdapter implements RolePort {

    final private RoleRepository roleRepository;

    public RoleJpaAdapter(final RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<RoleEntity> findRolesByEnum(List<String> roleNames) {
        return roleRepository.findRoleEntitiesByRoleEnumIn(roleNames);
    }
}
