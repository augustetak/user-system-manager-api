/**
 * 
 */
package com.airfrance.ums.entities;

import java.time.LocalDateTime;
import java.util.Date;

import com.mongodb.lang.NonNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Augustin Tankam
 *
 */
@Document
@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
	private String userId;
    @NonNull
	private String name;
	@NonNull
	private String surname;
	@Indexed( unique = true)
	private String email;
	@NonNull
	private LocalDateTime birthday;
	@NonNull
	private String country;

	public User(String name, String surname, String email, LocalDateTime birthday, String country) {
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.birthday = birthday;
		this.country = country;
	}
}
