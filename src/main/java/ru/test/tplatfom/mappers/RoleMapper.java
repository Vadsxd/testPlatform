package ru.test.tplatfom.mappers;

import ru.test.tplatfom.DTO.role.RoleDTO;
import ru.test.tplatfom.domain.Role;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RoleMapper {
    public static RoleDTO fromRoleToDto(Role role) {
        RoleDTO dto = new RoleDTO();
        dto.setId(role.getId());
        dto.setName(role.getName());
        return dto;
    }

    public static List<RoleDTO> fromRolesToDTOs(Set<Role> roles) {
        return roles.stream().map(RoleMapper::fromRoleToDto).collect(Collectors.toList());
    }
}
