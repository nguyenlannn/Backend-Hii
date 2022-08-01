package com.example.backendhii.entities;

import com.example.backendhii.basess.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity(name = "user")
public class UserEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, columnDefinition = "text")
    private String password;
//
    @OneToMany(mappedBy = "user")
    private Collection<DeviceEntity> devices;

    @Column(length = 50)
    private String firstName;

    @Column(length = 50)
    private String middleName;

    @Column(length = 50)
    private String lastName;

    @Column(columnDefinition = "text")
    private String avatar;

    @Column(columnDefinition = "bit")
    private String isActive;
//
    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<RoleEntity> roles;
    //
    @OneToMany(mappedBy = "user")
    private Collection<UserHistoryEntity> userHistoryEntities;
//
    @OneToMany(mappedBy = "user")
    private Collection<UserChannelEntity> userChanelEntities;
//
    @OneToMany(mappedBy = "sender")
    private Collection<MessageEntity> messageEntities;
}