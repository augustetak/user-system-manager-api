package com.airfrance.ums.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppConfig {
    @Id
    private String id;
    @Indexed(unique = true)
    private String configName;
    private String configValue;
    private String description;

}
