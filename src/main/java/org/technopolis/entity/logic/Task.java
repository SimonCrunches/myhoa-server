package org.technopolis.entity.logic;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.technopolis.entity.enums.Progress;
import org.technopolis.entity.enums.ProgressConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "task")
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(name = "originalEstimate", nullable = false)
    private Integer originalEstimate;

    @Column(name = "timeLogged", nullable = false)
    private LocalDateTime timeLogged;

    @Column(name = "remainingEstimate", nullable = false)
    private Integer remainingEstimate;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "initiative_id", nullable = false)
    private Initiative initiative;

    @Convert(converter = ProgressConverter.class)
    @Column(name = "progress", nullable = false)
    private Progress progress;

}
