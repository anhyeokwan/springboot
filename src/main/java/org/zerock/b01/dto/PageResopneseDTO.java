package org.zerock.b01.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter // dto의 getter 생성
@ToString
public class PageResopneseDTO<E> {
    private int page;
    private int size;
    private int total;

    // 시작페이지 번호
    private int start;

    // 끝페이지 번호
    private int end;

    // 이전 페이지 존재 여부
    private boolean prev;

    // 다음페이지 존재 여부
    private boolean next;

    private List<E> dtoList;

    @Builder(builderMethodName = "withALl")
    public PageResopneseDTO(PageRequestDTO pageRequestDTO, List<E> dtoList, int total) {
        if (total <= 0) {
            return;
        }

        this.page = pageRequestDTO.getPage();
        this.size = pageRequestDTO.getSize();

        this.total = total;
        this.dtoList = dtoList;

        this.end = (int) (Math.ceil(this.page / 10.0)) * 10; // 화면의 마지막 번호 ceil : 올림
        this.start = this.end - 9; // 화면에서의 시작번호

        int last = (int) (Math.ceil((total / (double) size))); // 데이터의 개수를 계산한 마지막 페이지 번호

        this.end = end > last ? last : end;

        this.prev = this.start > 1;
        this.next = total > this.end * this.size;
    }
}
