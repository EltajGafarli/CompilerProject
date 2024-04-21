package com.example.sdpproject.entity.user;

import com.example.sdpproject.entity.algorithm.BaseEntity;
import com.example.sdpproject.entity.algorithm.Submission;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class User extends BaseEntity implements UserDetails, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private boolean isEnabled = false;

    @Column(columnDefinition = "varchar(250)")
    private String password;

    @OneToMany(
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH
            },
            fetch = FetchType.EAGER,
            orphanRemoval = true,
            mappedBy = "user"
    )
    private Set<Role> roles = new HashSet<>();

    @OneToMany(
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH,
                    CascadeType.MERGE
            },
            mappedBy = "user",
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private List<Submission> submissions = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles
                .stream()
                .map(role -> new SimpleGrantedAuthority(
                        role.getRole()
                                .name())
                )
                .toList();
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }

    @Override
    public int hashCode() {
        return Objects
                .hash(this.id, this.email, this.roles, this.firstName, this.lastName, this.password, isEnabled,
                        this.getCreatedAt(), this.getUpdatedAt());
    }

    public boolean equals(Object obj) {
        if(obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        User user = (User) obj;
        return Objects.deepEquals(this.id, user.id)
                && Objects.deepEquals(this.email, user.email)
                && Objects.deepEquals(this.lastName, user.firstName)
                && Objects.deepEquals(this.lastName, user.lastName)
                && Objects.deepEquals(this.password, user.password)
                && Objects.deepEquals(this.isEnabled, user.isEnabled)
                && Objects.deepEquals(this.getCreatedAt(), user.getCreatedAt())
                && Objects.deepEquals(this.getUpdatedAt(), user.getUpdatedAt());
    }

    public void addRole(Role role) {
        if(this.roles == null) {
            this.roles = new HashSet<>();
        }
        this.roles.add(role);
        role.setUser(this);
    }
}