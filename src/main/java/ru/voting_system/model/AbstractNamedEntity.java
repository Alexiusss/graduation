package ru.voting_system.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public abstract class AbstractNamedEntity extends AbstractBaseEntity {

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(name = "name", nullable = false)
    protected String name;

    public AbstractNamedEntity(Integer id, String name) {
        super(id);
        this.name = name;
    }

}
