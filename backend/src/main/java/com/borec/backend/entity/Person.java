package com.borec.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    /*
     * Use this for sequence increment:
     *     @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "PERSON_id_Sequence")
     *     @SequenceGenerator(name = "PERSON_id_Sequence", sequenceName = "PERSON_SEQ_ID")
     */
    Long id;

    @Column(name = "first_name", nullable = false)
    String firstName;

    @Column(name = "last_name", nullable = false)
    String lastName;

    @Column(name = "avatar", length = 2048)
    String avatar;

    @Column(name = "score", nullable = false)
    double score;

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Person() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    //@Id
    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}


}
