package edu.quangtk.controller;

import edu.quangtk.model.Answer;
import edu.quangtk.model.Exam;
import edu.quangtk.model.Question;
import edu.quangtk.service.ExamService;
import edu.quangtk.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ExamService examService;

    @Autowired
    private QuestionService questionService;

    // --- Quản lý Kỳ thi (Danh sách) ---
    @GetMapping("/exams")
    public String listExams(Model model,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size,
                            @RequestParam(defaultValue = "id") String sortBy,
                            @RequestParam(defaultValue = "asc") String sortDir,
                            @RequestParam(required = false) String search) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Exam> examPage = examService.findExams(search, pageable);

        model.addAttribute("exams", examPage.getContent());
        model.addAttribute("currentPage", examPage.getNumber());
        model.addAttribute("totalPages", examPage.getTotalPages());
        model.addAttribute("totalItems", examPage.getTotalElements());
        model.addAttribute("pageSize", size);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("search", search);

        return "admin_exams_list";
    }

    // --- Tạo Kỳ thi mới (Hiển thị form) ---
    @GetMapping("/exams/create")
    public String showCreateExamForm(Model model) {
        model.addAttribute("exam", new Exam());
        return "admin_exams_create";
    }

    // --- Lưu Kỳ thi (cho cả tạo mới và cập nhật) ---
    @PostMapping("/exams/save")
    public String saveExam(@ModelAttribute Exam exam, RedirectAttributes redirectAttributes) {
        if (exam.getStartTime() == null) {
            exam.setStartTime(LocalDateTime.now());
        }
        examService.saveExam(exam);
        redirectAttributes.addFlashAttribute("message", "Kỳ thi đã được lưu thành công!");
        return "redirect:/admin/exams";
    }

    // --- Xóa Kỳ thi ---
    @PostMapping("/exams/delete/{id}")
    public String deleteExam(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        examService.deleteExamById(id);
        redirectAttributes.addFlashAttribute("message", "Kỳ thi đã được xóa thành công!");
        return "redirect:/admin/exams";
    }

    // --- Hiển thị form thêm câu hỏi ---
    @GetMapping("/questions/add")
    public String showAddQuestionForm(@RequestParam("examId") Long examId, Model model) {
        Optional<Exam> examOptional = examService.findExamById(examId);
        if (examOptional.isEmpty()) {
            return "redirect:/admin/exams";
        }
        Exam exam = examOptional.get();
        model.addAttribute("currentExam", exam);
        model.addAttribute("question", new Question());
        return "admin_questions";
    }

    // --- Quản lý Câu hỏi (Danh sách) ---
    @GetMapping("/questions/{examId}")
    public String manageQuestionsByExam(@PathVariable Long examId, Model model,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size,
                                        @RequestParam(defaultValue = "id") String sortBy,
                                        @RequestParam(defaultValue = "asc") String sortDir,
                                        @RequestParam(required = false) String search) {
        Optional<Exam> examOptional = examService.findExamById(examId);
        if (examOptional.isEmpty()) {
            return "redirect:/admin/exams";
        }
        Exam exam = examOptional.get();

        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Question> questionPage = questionService.findQuestionsByExamId(examId, search, pageable);

        model.addAttribute("currentExam", exam);
        model.addAttribute("questions", questionPage.getContent());
        model.addAttribute("currentPage", questionPage.getNumber());
        model.addAttribute("totalPages", questionPage.getTotalPages());
        model.addAttribute("totalItems", questionPage.getTotalElements());
        model.addAttribute("pageSize", size);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("search", search);

        return "admin_questions_list";
    }

    // --- Lưu câu hỏi ---
    @PostMapping("/questions/save")
    public String saveQuestion(@ModelAttribute Question question,
                               @RequestParam("examId") Long examId,
                               @RequestParam("answerContent") String[] answerContents,
                               @RequestParam("isCorrect") int correctAnswerIndex,
                               RedirectAttributes redirectAttributes) {
        Optional<Exam> examOptional = examService.findExamById(examId);
        if (examOptional.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Kỳ thi không tồn tại!");
            return "redirect:/admin/exams";
        }
        question.setExam(examOptional.get());

        question.setAnswers(new ArrayList<>());
        for (int i = 0; i < answerContents.length; i++) {
            Answer answer = new Answer();
            answer.setContent(answerContents[i]);
            answer.setCorrect(i == correctAnswerIndex);
            answer.setQuestion(question);
            question.getAnswers().add(answer);
        }
        questionService.saveQuestion(question);
        redirectAttributes.addFlashAttribute("message", "Câu hỏi đã được lưu thành công!");
        return "redirect:/admin/questions/" + examId;
    }

    // --- Chỉnh sửa câu hỏi ---
    @GetMapping("/questions/edit/{id}")
    public String editQuestion(@PathVariable Long id, @RequestParam("examId") Long examId, Model model) {
        Optional<Question> questionOptional = questionService.findQuestionById(id);
        Optional<Exam> examOptional = examService.findExamById(examId);

        if (questionOptional.isEmpty() || examOptional.isEmpty()) {
            return "redirect:/admin/questions/" + examId;
        }
        model.addAttribute("question", questionOptional.get());
        model.addAttribute("currentExam", examOptional.get());
        model.addAttribute("editMode", true);
        model.addAttribute("exams", examService.findAllExams());

        return "admin_questions_edit";
    }

    // --- Xóa câu hỏi ---
    @PostMapping("/questions/delete/{id}")
    public String deleteQuestion(@PathVariable Long id, @RequestParam("examId") Long examId, RedirectAttributes redirectAttributes) {
        questionService.deleteQuestionById(id);
        redirectAttributes.addFlashAttribute("message", "Câu hỏi đã được xóa thành công!");
        return "redirect:/admin/questions/" + examId;
    }
}