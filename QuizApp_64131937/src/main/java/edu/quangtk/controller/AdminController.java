package edu.quangtk.controller;

import edu.quangtk.model.Answer;
import edu.quangtk.model.Exam;
import edu.quangtk.model.Question;
import edu.quangtk.service.ExamService;
import edu.quangtk.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String manageExams(Model model) {
        model.addAttribute("exams", examService.findAllExams());
        model.addAttribute("exam", new Exam()); // Để tạo form thêm mới
        return "admin_exams";
    }

    @PostMapping("/exams")
    public String saveExam(@ModelAttribute Exam exam, RedirectAttributes redirectAttributes) {
        // Đặt thời gian bắt đầu mặc định nếu không có, hoặc lấy từ form
        if (exam.getStartTime() == null) {
            exam.setStartTime(LocalDateTime.now());
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
    // Hiển thị form thêm/xem câu hỏi theo Exam
    @GetMapping("/questions/{examId}")
    public String manageQuestionsByExam(@PathVariable Long examId, Model model) {
        Optional<Exam> examOptional = examService.findExamById(examId);
        if (examOptional.isEmpty()) {
            // Xử lý khi không tìm thấy Exam, có thể chuyển hướng hoặc báo lỗi
            return "redirect:/admin/exams"; // Chuyển hướng về danh sách exam
        }
        Exam exam = examOptional.get();
        model.addAttribute("currentExam", exam);
        model.addAttribute("questions", questionService.findQuestionsByExamId(examId));
        model.addAttribute("question", new Question()); // Để tạo form thêm mới câu hỏi
        return "admin_questions";
    }

    // Xử lý lưu câu hỏi mới (kèm đáp án)
    @PostMapping("/questions/save")
    public String saveQuestion(@ModelAttribute Question question,
                               @RequestParam("examId") Long examId, // Lấy ID Exam từ form
                               @RequestParam("answerContent") String[] answerContents,
                               @RequestParam("isCorrect") int correctAnswerIndex,
                               RedirectAttributes redirectAttributes) {

        Optional<Exam> examOptional = examService.findExamById(examId);
        if (examOptional.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Kỳ thi không tồn tại!");
            return "redirect:/admin/exams";
        }
        question.setExam(examOptional.get()); // Gán Exam cho Question

        question.setAnswers(new ArrayList<>());
        for (int i = 0; i < answerContents.length; i++) { // Đảm bảo duyệt đúng số lượng đáp án
            Answer answer = new Answer();
            answer.setContent(answerContents[i]);
            answer.setCorrect(i == correctAnswerIndex);
            answer.setQuestion(question); // Gán Question tạm thời, sẽ được xử lý khi lưu
            question.getAnswers().add(answer);
        }
        questionService.saveQuestion(question);
        redirectAttributes.addFlashAttribute("message", "Câu hỏi đã được lưu thành công!");
        return "redirect:/admin/questions/" + examId;
    }

    @PostMapping("/questions/delete/{id}")
    public String deleteQuestion(@PathVariable Long id, @RequestParam("examId") Long examId, RedirectAttributes redirectAttributes) {
        questionService.deleteQuestionById(id);
        redirectAttributes.addFlashAttribute("message", "Câu hỏi đã được xóa thành công!");
        return "redirect:/admin/questions/" + examId;
    }
}