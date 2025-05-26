document.addEventListener('DOMContentLoaded', function() {
    // --- JavaScript cho trang admin_questions.html và admin_questions_edit.html ---
    // Xử lý thêm/xóa đáp án động
    const answersContainer = document.getElementById('answersContainer');
    const addAnswerBtn = document.getElementById('addAnswerBtn');

    if (answersContainer && addAnswerBtn) { // Kiểm tra để đảm bảo các phần tử tồn tại trên trang hiện tại
        let answerIndex = answersContainer.children.length;

        function updateAnswerIndices() {
            Array.from(answersContainer.children).forEach((answerGroup, index) => {
                const input = answerGroup.querySelector('input[name="answerContent"]');
                const radio = answerGroup.querySelector('input[name="isCorrect"]');
                const label = answerGroup.querySelector('label[for^="answerRadio"]');
                const hiddenIdInput = answerGroup.querySelector('input[type="hidden"][name^="answers"]'); // Chỉ có ở trang edit

                if (radio) {
                    radio.value = index;
                    // Sử dụng ID phù hợp cho cả trang thêm mới và trang chỉnh sửa
                    radio.id = (window.location.pathname.includes('/edit/') ? 'editAnswerRadio' : 'answerRadio') + index;
                }
                if (label) {
                    label.htmlFor = (window.location.pathname.includes('/edit/') ? 'editAnswerRadio' : 'answerRadio') + index;
                }
                if (input) {
                    input.placeholder = `Đáp án ${index + 1}`;
                }
                if (hiddenIdInput) {
                    hiddenIdInput.name = `answers[${index}].id`; // Cập nhật tên cho input hidden ID
                }
            });
            answerIndex = answersContainer.children.length;
        }

        addAnswerBtn.addEventListener('click', function() {
            const newAnswerGroup = document.createElement('div');
            newAnswerGroup.classList.add('answer-group');
            // Thêm input hidden cho id của answer, giá trị rỗng cho đáp án mới
            const hiddenIdField = window.location.pathname.includes('/edit/') ? '<input type="hidden" name="answers[' + answerIndex + '].id" value="">' : '';

            newAnswerGroup.innerHTML = `
                ${hiddenIdField}
                <input type="text" name="answerContent" class="form-control" placeholder="Đáp án ${answerIndex + 1}" required>
                <div class="form-check">
                    <input type="radio" name="isCorrect" id="${window.location.pathname.includes('/edit/') ? 'editAnswerRadio' : 'answerRadio'}${answerIndex}" value="${answerIndex}" class="form-check-input">
                    <label class="form-check-label" for="${window.location.pathname.includes('/edit/') ? 'editAnswerRadio' : 'answerRadio'}${answerIndex}">Đúng</label>
                </div>
                <button type="button" class="btn btn-sm btn-danger remove-answer-btn">Xóa</button>
            `;
            answersContainer.appendChild(newAnswerGroup);
            answerIndex++;
            updateAnswerIndices();
        });

        answersContainer.addEventListener('click', function(event) {
            if (event.target.classList.contains('remove-answer-btn')) {
                if (answersContainer.children.length > 2) { // Giữ ít nhất 2 đáp án
                    event.target.closest('.answer-group').remove();
                    updateAnswerIndices();
                } else {
                    alert('Phải có ít nhất 2 đáp án cho một câu hỏi.');
                }
            }
        });

        updateAnswerIndices(); // Gọi lần đầu để đảm bảo chỉ số đúng khi load trang
    }

    // --- JavaScript cho trang user_take_exam.html ---
    // Xử lý đồng hồ đếm ngược và tự động nộp bài
    const timerElement = document.getElementById('countdownTimer');
    const examForm = document.getElementById('examForm');

    if (timerElement && examForm) {
        // Lấy thời lượng từ data attribute của body, hoặc từ một biến JS được set bởi Thymeleaf
        // Để đơn giản, giả sử bạn sẽ pass `durationInSeconds` từ Thymeleaf vào một biến JS toàn cục.
        // Ví dụ: <script th:inline="javascript"> var durationInSeconds = [[${exam.durationMinutes}]] * 60; </script>
        // Hoặc truyền qua data attribute của HTML:
        // <body th:data-duration-seconds="${exam.durationMinutes * 60}">
        const durationInSeconds = parseInt(document.body.dataset.durationSeconds); // Lấy từ data attribute của body

        let timer = durationInSeconds;
        const interval = setInterval(function () {
            let minutes = parseInt(timer / 60, 10);
            let seconds = parseInt(timer % 60, 10);

            minutes = minutes < 10 ? "0" + minutes : minutes;
            seconds = seconds < 10 ? "0" + seconds : seconds;

            timerElement.textContent = "Thời gian còn lại: " + minutes + ":" + seconds;

            if (timer <= 60) { // Dưới 1 phút
                timerElement.classList.add('danger');
                timerElement.classList.remove('warning');
            } else if (timer <= 300) { // Dưới 5 phút
                timerElement.classList.add('warning');
                timerElement.classList.remove('danger');
            } else {
                timerElement.classList.remove('warning', 'danger');
            }

            if (--timer < 0) {
                clearInterval(interval);
                timerElement.textContent = "Đã hết thời gian!";
                alert("Đã hết thời gian làm bài! Bài thi sẽ tự động nộp.");
                examForm.submit(); // Tự động nộp bài khi hết giờ
            }
        }, 1000);
    }
});