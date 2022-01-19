package org.ps.reconciliation.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USER_TBL")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserDetails> userDetails;

    @JsonManagedReference
    public List<UserDetails> getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(List<UserDetails> userDetails) {
        this.userDetails = userDetails;
    }
}

