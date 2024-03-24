package com.example.sdpproject.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
    private Set<Roles> roles = new HashSet<>();

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
        return true;
    }

    @Override
    public int hashCode() {
        return Objects
                .hash(this.id, this.email, this.roles, this.firstName, this.lastName, this.password,
                        this.getCreatedAt(), this.getUpdatedAt());
    }

    public boolean equals(Object obj) {
        if(obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        User user = (User) obj;
        return Objects.deepEquals(this.id, user.id)
                && Objects.deepEquals(this.roles, user.roles)
                && Objects.deepEquals(this.email, user.email)
                && Objects.deepEquals(this.lastName, user.firstName)
                && Objects.deepEquals(this.lastName, user.lastName)
                && Objects.deepEquals(this.password, user.password)
                && Objects.deepEquals(this.getCreatedAt(), user.getCreatedAt())
                && Objects.deepEquals(this.getUpdatedAt(), user.getUpdatedAt());
    }
}
