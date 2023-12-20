package com.crio.starter.data;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data // @Getters , @Setters , @RequiredArgsConstructor , @toString ,
      // @EqualsAndHashCode
@Document(collection = "memes")
public class MemeEntity {
  @Id // marks fields as primary key. It will be generated automatically.
  private String id;
  @NotBlank
  private String name;
  @NotBlank
  private String url;
  @NotBlank
  private String caption;

}
