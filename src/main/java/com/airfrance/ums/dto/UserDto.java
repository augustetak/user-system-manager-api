/**
 * 
 */
package com.airfrance.ums.dto;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.*;

/**
 * @author io
 *
 */
@Data
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UserDto {
	
	private String userId;
	
	private String name;
	
	private String surname;
	
	private String email;
	private LocalDateTime birthday;
	private String country;

	
	

}
