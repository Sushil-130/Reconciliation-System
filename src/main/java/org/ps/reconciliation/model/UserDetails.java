package org.ps.reconciliation.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "user_details")
public class UserDetails {

    @Id
    @Column(name = "userdetails_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "source_name")
    private String sourceName;

    @Column(name = "sourcefiletype")
    private String sourceFileType;

    @Column(name="target_name")
    private String targetName;

    @Column(name="targetfiletype")
    private String targetFileType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonBackReference
    private User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getSourceFileType() {
        return sourceFileType;
    }

    public void setSourceFileType(String sourceFileType) {
        this.sourceFileType = sourceFileType;
    }
    public String getTargetName(){
        return targetName;
    }
    public void setTargetName(String targetName){
        this.targetName=targetName;
    }

    public String getTargetFileType(){
        return targetFileType;
    }

    public void setTargetFileType(String targetFileType){
        this.targetFileType=targetFileType;
    }


}
