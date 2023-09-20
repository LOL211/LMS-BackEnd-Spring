package com.kush.banbah.soloprojectbackend.database.user;

import com.kush.banbah.soloprojectbackend.database.classes.Class;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User implements UserDetails {

    @ManyToMany
    @JoinTable(
            name = "student_classes",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "class_name")
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    Set<Class> student_classes;


    @OneToMany(mappedBy = "teacher")
    Set<Class> teacher_classes;

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Integer id;

    @NotNull
    private String name;
    @NotNull
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email is not in proper format")
    private String email;
    @ToString.Exclude
    @NotNull
    private String password;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority((role.name())));
    }

    @Override
    public String getUsername() {
        return email;
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

    @Override //Is actually implemented by the lombok tags
    public String getPassword() {
        return password;
    }

    public enum Role {
        TEACHER,
        STUDENT
    }
}
