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

    /*
    id: 1,
    first_name: "Filip",
    last_name: "Exot",
    avatar: "https://gravatar.com/avatar/99df1a5b2917db695be7ad69e46d9164?s=400&d=robohash&r=x"
*/

}
