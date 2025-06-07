package edu.quangtk.controller;

import edu.quangtk.model.Answer;
import edu.quangtk.model.Exam;
import edu.quangtk.model.Question;
import edu.quangtk.service.AnswerService;
import edu.quangtk.service.ExamService;
import edu.quangtk.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Objects; // THÊM IMPORT NÀY
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ExamService examService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    @GetMapping("/exams")
    public String listExams(Model model) {
        model.addAttribute("exams", examService.findAllExams());
        return "user_exam_list";
    }

    @GetMapping("/take-exam/{examId}")
    public String takeExam(@PathVariable Long examId, Model model) {
        Optional<Exam> examOptional = examService.findExamById(examId);
        if (examOptional.isEmpty()) {
            return "redirect:/user/exams";
        }
        Exam exam = examOptional.get();
        List<Question> questions = questionService.findQuestionsByExamId(examId);

        model.addAttribute("exam", exam);
        model.addAttribute("questions", questions);
        return "user_take_exam";
    }
    @GetMapping("/exams/search")
    public String searchExams(@RequestParam(name = "keyword", required = false) String keyword, Model model) {
        List<Exam> foundExams;
        if (keyword != null && !keyword.trim().isEmpty()) {
            foundExams = examService.searchExamsByTitle(keyword.trim());
        } else {
            foundExams = examService.findAllExams();
        }
        model.addAttribute("exams", foundExams);
        model.addAttribute("keyword", keyword);
        return "user_exam_list";
    }
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

        List<Map<String, Object>> resultDetails = new ArrayList<>();

        for (Question question : questions) {
            String submittedAnswerIdStr = allRequestParams.get("question_" + question.getId());
            Long currentSubmittedAnswerId = null; // Dùng biến tạm thời này
            if (submittedAnswerIdStr != null && !submittedAnswerIdStr.isEmpty()) {
                try {
                    currentSubmittedAnswerId = Long.parseLong(submittedAnswerIdStr);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid answer ID format for question " + question.getId() + ": " + submittedAnswerIdStr);
                }
            }

            // Tạo một biến final hoặc effectively final mới để sử dụng trong lambda
            final Long finalSubmittedAnswerId = currentSubmittedAnswerId;

            Optional<Answer> submittedAnswer = question.getAnswers().stream()
                .filter(a -> Objects.equals(a.getId(), finalSubmittedAnswerId)) // Sử dụng biến mới
                .findFirst();

            // Tìm đáp án đúng của câu hỏi
            Optional<Answer> correctActualAnswer = question.getAnswers().stream()
                .filter(Answer::isCorrect)
                .findFirst();

            boolean isCorrect = false;
            // Chỉ so sánh nếu cả hai đáp án (đã nộp và đúng) đều tồn tại
            if (correctActualAnswer.isPresent() && submittedAnswer.isPresent() &&
                correctActualAnswer.get().getId().equals(submittedAnswer.get().getId())) {
                isCorrect = true;
                correctAnswersCount++;
            }

            // Chuẩn bị chi tiết kết quả cho từng câu hỏi
            resultDetails.add(Map.of(
                "question", question,
                "submittedAnswer", submittedAnswer.orElse(null), // Null nếu không chọn
                "correctAnswer", correctActualAnswer.orElse(null),
                "isCorrect", isCorrect
            ));
        }

        model.addAttribute("exam", exam);
        model.addAttribute("totalQuestions", totalQuestions);
        model.addAttribute("correctAnswers", correctAnswersCount);
        model.addAttribute("score", (double) correctAnswersCount / totalQuestions * 100);
        model.addAttribute("resultDetails", resultDetails);

        return "user_exam_result";
    }
}