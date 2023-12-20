package com.crio.starter.exchange;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import com.crio.starter.data.MemeEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemeResponse {
    List<MemeEntity> memelist ;

}
