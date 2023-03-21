package org.zerock.b01.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.b01.dto.BoardDTO;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResopneseDTO;
import org.zerock.b01.service.BoardService;

import javax.validation.Valid;

@Controller
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        PageResopneseDTO<BoardDTO> resopneseDTO = boardService.list(pageRequestDTO);

        log.info("resopneseDTO : " + resopneseDTO);

        model.addAttribute("resopneseDTO", resopneseDTO);
    }

    @GetMapping("/register")
    public void registerGET() {

    }

    @PostMapping("/register")
    public String registerPOST(@Valid BoardDTO boardDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        log.info("board POST register.......");

        if (bindingResult.hasErrors()) {
            log.info("has error......");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            return "redirect:/board/register";
        }

        log.info(boardDTO);

        long bno = boardService.register(boardDTO);
        log.info(bno);
        redirectAttributes.addFlashAttribute("result", bno);

        return "redirect:/board/list";
    }

    @GetMapping("/read")
    public void read(long bno, PageRequestDTO pageRequestDTO, Model model) {
        BoardDTO boardDTO = boardService.readOne(bno);

        log.info(boardDTO);

        model.addAttribute("dto", boardDTO);
    }
}