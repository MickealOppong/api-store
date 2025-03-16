package com.repo.api.model.user;

import com.repo.api.util.LogEntity;
import com.repo.api.util.PaymentAccountInformation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@Builder
public class Customer extends LogEntity implements UserDetails {

    @Id @GeneratedValue
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String telephone;
    private String password;
    private String peselNumber;
    private String accountNumber;

    //credit information
    private BigDecimal creditLimit ;

    //account status
    private boolean isEnabled;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;

    private Instant lastLogin;



    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "customers_roles",
            joinColumns = @JoinColumn(
                    name = "fk_role", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "fk_customer", referencedColumnName = "id"))
    private Set<Roles> customerRoles = new HashSet<>();


    @ManyToOne
    @JoinColumn(name = "ac_id",referencedColumnName = "id")
    private PaymentAccountInformation
     paymentAccountInformation;




    //default constructor
    public Customer(){}


    public Customer(String firstName, String lastName, String username, String password) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }



    public void  userDefaultSettings(){
        this.isAccountNonExpired = true;
        this.isAccountNonLocked  = true;
        this.isCredentialsNonExpired   = true;
        this.isEnabled =true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for(Roles role: customerRoles){
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return authorities;
    }

    @Override
    public String getUsername(){
        return this.username;
    }

    @Override
    public String getPassword(){
        return this.password;
    }
    @Override
    public boolean isAccountNonExpired() {
        return this.isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }
}
