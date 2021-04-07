package org.technopolis.entity.logic;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.technopolis.entity.actors.ActiveUser;
import org.technopolis.entity.enums.Category;
import org.technopolis.entity.enums.CategoryConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "initiative")
public class Initiative implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Convert(converter = CategoryConverter.class)
    @Column(name = "category", nullable = false)
    private Category category;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "active_user_id", nullable = false)
    private ActiveUser activeUser;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "milestone", nullable = false)
    private LocalDate milestone;

    @Column(name = "price")
    private Integer price;

    @Column(name = "contractor")
    private Boolean contractor;

    @Column(name = "image_url")
    private String image_url;

    @JsonBackReference
    @OneToMany(mappedBy = "initiative", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    protected Set<Task> tasks = new HashSet<>();

    @JsonBackReference
    @OneToMany(mappedBy = "initiative", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<FavouriteInitiative> favouriteUsers = new HashSet<>();
}
