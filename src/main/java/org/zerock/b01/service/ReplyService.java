package org.zerock.b01.service;

import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResopneseDTO;
import org.zerock.b01.dto.RelpyDTO;

public interface ReplyService {

    long register(RelpyDTO relpyDTO);

    RelpyDTO read(long rno);

    void modify(RelpyDTO relpyDTO);

    void remove(long rno);

    PageResopneseDTO<RelpyDTO> getListOfBoard(long bno, PageRequestDTO pageRequestDTO);
}
