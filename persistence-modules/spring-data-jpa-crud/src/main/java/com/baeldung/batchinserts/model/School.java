package com.baeldung.batchinserts.model;

import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

@Entity
public class School {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private long id;

  private String name;

  @OneToMany(//
      mappedBy = "school", //
      cascade = CascadeType.ALL, //
      fetch = FetchType.EAGER, //
      orphanRemoval = true)
  private List<Student> students = new LinkedList<>();

  @Version
  private long concurrencyVersion;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Student> getStudents() {
    return students;
  }

  public void setStudents(List<Student> students) {
    this.students = students;
  }

  public long getConcurrencyVersion() {
    return concurrencyVersion;
  }

  public void setConcurrencyVersion(long concurrencyVersion) {
    this.concurrencyVersion = concurrencyVersion;
  }


}
