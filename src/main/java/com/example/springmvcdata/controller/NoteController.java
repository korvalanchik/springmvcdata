package com.example.springmvcdata.controller;

import com.example.springmvcdata.model.NoteDTO;
import com.example.springmvcdata.service.NoteService;
import com.example.springmvcdata.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/note")
public class NoteController {

    private final NoteService noteService;

    public NoteController(final NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/list")
    public String list(final Model model) {
        model.addAttribute("notes", noteService.findAll());
        return "note/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("note") final NoteDTO noteDTO) {
        return "note/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("note") @Valid final NoteDTO noteDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "note/add";
        }
        noteService.create(noteDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("note.create.success"));
        return "redirect:/note/list";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("note", noteService.get(id));
        return "note/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("note") @Valid final NoteDTO noteDTO, final BindingResult bindingResult,
            final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "note/edit";
        }
        noteService.update(id, noteDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("note.update.success"));
        return "redirect:/note/list";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        noteService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("note.delete.success"));
        return "redirect:/note/list";
    }

}
