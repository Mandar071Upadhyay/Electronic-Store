package com.lcwd.electroinic.store.dtos;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="roles")
public class RoleDTO {
    @Id
    private String roleId;
    private String roleName;
}