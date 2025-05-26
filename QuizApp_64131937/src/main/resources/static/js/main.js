var durationMinutes = [[${exam.durationMinutes}]];
    var durationInSeconds = durationMinutes * 60;
    var timerElement = document.getElementById('countdownTimer');
    var examForm = document.getElementById('examForm');

    function startTimer(duration, display) {
        var timer = duration, minutes, seconds;
        var interval = setInterval(function () {
            minutes = parseInt(timer / 60, 10);
            seconds = parseInt(timer % 60, 10);

            minutes = minutes < 10 ? "0" + minutes : minutes;
            seconds = seconds < 10 ? "0" + seconds : seconds;

            display.textContent = "Thời gian còn lại: " + minutes + ":" + seconds;

            if (--timer < 0) {
                clearInterval(interval);
                display.textContent = "Đã hết thời gian!";
                alert("Đã hết thời gian làm bài! Bài thi sẽ tự động nộp.");
                examForm.submit(); // Tự động nộp bài khi hết giờ
            }
        }, 1000);
    }

    window.onload = function () {
        startTimer(durationInSeconds, timerElement);
    };