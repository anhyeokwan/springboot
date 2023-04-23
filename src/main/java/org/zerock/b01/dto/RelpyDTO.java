package org.zerock.b01.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelpyDTO {

    private long rno;
    @NotNull // null만 허용하지 않음 "", " "은 허용
    private long bno;
    @NotEmpty  // null만 허용하지 않음 "", " "도 허용하지 않음
    private String replyText;
    @NotEmpty
    private String replyer;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regDate;

    @JsonIgnore
    private LocalDateTime modDate;
}
