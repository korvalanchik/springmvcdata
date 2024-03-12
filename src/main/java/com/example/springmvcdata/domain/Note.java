package com.example.springmvcdata.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Note {

    @Id
    @Column(nullable = false, updatable = false, columnDefinition = "long")
    @GenericGenerator(name = "long")
    @GeneratedValue(generator = "long")
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false)
    private String content;

    @CreatedDate
    @Column(updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column
    private OffsetDateTime lastUpdated;

    // Custom method to format OffsetDateTime using DateTimeFormatter
    public String getFormattedDateCreated() {
        return formatDate(dateCreated);
    }

    // Custom method to format OffsetDateTime using DateTimeFormatter
    public String getFormattedLastUpdated() {
        return formatDate(lastUpdated);
    }

    // Utility method to format OffsetDateTime using DateTimeFormatter
    private String formatDate(OffsetDateTime offsetDateTime) {
        if (offsetDateTime == null) {
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm:ss");
        return offsetDateTime.format(formatter);
    }


}
