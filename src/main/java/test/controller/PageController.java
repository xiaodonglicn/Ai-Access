package test.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PageController {
    
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("pageTitle", "AI博客简介生成器");
        return "index";
    }
    
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("pageTitle", "AI控制面板");
        return "dashboard";
    }
}