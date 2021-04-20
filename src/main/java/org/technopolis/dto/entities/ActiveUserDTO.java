package org.technopolis.dto.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.technopolis.entity.actors.ActiveUser;

import javax.annotation.Nonnull;

@Data
@NoArgsConstructor
public class ActiveUserDTO {

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    @JsonProperty("email")
    private String email;

    @JsonProperty("firebaseToken")
    private String firebaseToken;

    @JsonProperty("pictureUrl")
    private String pictureUrl;

    public ActiveUserDTO(@Nonnull final ActiveUser user) {
        this.email = user.getEmail();
        this.firebaseToken = user.getFirebaseToken();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.pictureUrl = user.getPictureUrl();
    }
}
