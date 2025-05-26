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

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ExamService examService;

    @Autowired
    private QuestionService questionService;

    // Trang chủ cho người dùng - Liệt kê các kỳ thi
    @GetMapping("/exams")
    public String listExams(Model model) {
        model.addAttribute("exams", examService.findAllExams());
        return "user_exam_list";
    }

    // Bắt đầu làm bài thi
    @GetMapping("/take-exam/{examId}")
    public String takeExam(@PathVariable Long examId, Model model) {
        Optional<Exam> examOptional = examService.findExamById(examId);
        if (examOptional.isEmpty()) {
            return "redirect:/user/exams"; // Hoặc trang lỗi
        }
        Exam exam = examOptional.get();
        List<Question> questions = questionService.findQuestionsByExamId(examId);

        model.addAttribute("exam", exam);
        model.addAttribute("questions", questions);
        return "user_take_exam";
    }

    // Xử lý nộp bài
    @PostMapping("/submit-exam/{examId}")
    public String submitExam(@PathVariable Long examId,
                             @RequestParam Map<String, String> allRequestParams,
                             Model model) {
        Optional<Exam> examOptional = examService.findExamById(examId);
        if (examOptional.isEmpty()) {
            return "redirect:/user/exams";
        }
        Exam exam = examOptional.get();
        List<Question> questions = questionService.findQuestionsByExamId(examId);

        int correctAnswersCount = 0;
        int totalQuestions = questions.size();

        for (Question question : questions) {
            String submittedAnswerId = allRequestParams.get("question_" + question.getId());
            if (submittedAnswerId != null) {
                // Tìm đáp án đúng của câu hỏi
                Optional<Answer> correctActualAnswer = question.getAnswers().stream()
                    .filter(Answer::isCorrect)
                    .findFirst();

                if (correctActualAnswer.isPresent() && correctActualAnswer.get().getId().toString().equals(submittedAnswerId)) {
                    correctAnswersCount++;
                }
            }
        }

        model.addAttribute("exam", exam);
        model.addAttribute("totalQuestions", totalQuestions);
        model.addAttribute("correctAnswers", correctAnswersCount);
        model.addAttribute("score", (double) correctAnswersCount / totalQuestions * 100);
        return "user_exam_result"; // Tạo một trang kết quả
    }
}