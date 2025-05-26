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

    // --- Quản lý Kỳ thi ---
    @GetMapping("/exams")
    public String manageExams(Model model,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size,
                              @RequestParam(defaultValue = "id") String sortBy,
                              @RequestParam(defaultValue = "asc") String sortDir,
                              @RequestParam(required = false) String search) { // Thêm tham số search
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
        model.addAttribute("search", search); // Giữ lại giá trị tìm kiếm trên form
        model.addAttribute("exam", new Exam()); // Để tạo form thêm mới

        return "admin_exams";
    }

    @PostMapping("/exams")
    public String saveExam(@ModelAttribute Exam exam, RedirectAttributes redirectAttributes) {
        if (exam.getStartTime() == null) {
            exam.setStartTime(LocalDateTime.now()); // Đặt thời gian mặc định nếu không có từ form
        }
        examService.saveExam(exam);
        redirectAttributes.addFlashAttribute("message", "Kỳ thi đã được lưu thành công!");
        return "redirect:/admin/exams";
    }

    @PostMapping("/exams/delete/{id}")
    public String deleteExam(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        examService.deleteExamById(id);
        redirectAttributes.addFlashAttribute("message", "Kỳ thi đã được xóa thành công!");
        return "redirect:/admin/exams";
    }

    // --- Quản lý Câu hỏi ---
    @GetMapping("/questions/{examId}")
    public String manageQuestionsByExam(@PathVariable Long examId, Model model,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size,
                                        @RequestParam(defaultValue = "id") String sortBy,
                                        @RequestParam(defaultValue = "asc") String sortDir,
                                        @RequestParam(required = false) String search) { // Thêm tham số search
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
        model.addAttribute("search", search); // Giữ lại giá trị tìm kiếm trên form
        model.addAttribute("question", new Question()); // Để tạo form thêm mới

        return "admin_questions";
    }

    // Xử lý lưu (thêm mới hoặc cập nhật) câu hỏi
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
            answer.setQuestion(question); // Gán Question tạm thời, quan hệ sẽ được xử lý khi lưu
            question.getAnswers().add(answer);
        }
        questionService.saveQuestion(question);
        redirectAttributes.addFlashAttribute("message", "Câu hỏi đã được lưu thành công!");
        return "redirect:/admin/questions/" + examId;
    }

    // Hiển thị form chỉnh sửa câu hỏi
    @GetMapping("/questions/edit/{id}")
    public String editQuestion(@PathVariable Long id, @RequestParam("examId") Long examId, Model model) {
        Optional<Question> questionOptional = questionService.findQuestionById(id);
        Optional<Exam> examOptional = examService.findExamById(examId);

        if (questionOptional.isEmpty() || examOptional.isEmpty()) {
            return "redirect:/admin/questions/" + examId; // Hoặc trang lỗi
        }
        model.addAttribute("question", questionOptional.get());
        model.addAttribute("currentExam", examOptional.get());
        model.addAttribute("editMode", true); // Đánh dấu là chế độ chỉnh sửa

        // Cần lấy tất cả exams cho dropdown khi chỉnh sửa (nếu muốn thay đổi exam của câu hỏi)
        model.addAttribute("exams", examService.findAllExams());

        return "admin_questions_edit"; // Tạo một template mới cho chỉnh sửa
    }

    @PostMapping("/questions/delete/{id}")
    public String deleteQuestion(@PathVariable Long id, @RequestParam("examId") Long examId, RedirectAttributes redirectAttributes) {
        questionService.deleteQuestionById(id);
        redirectAttributes.addFlashAttribute("message", "Câu hỏi đã được xóa thành công!");
        return "redirect:/admin/questions/" + examId;
    }
}