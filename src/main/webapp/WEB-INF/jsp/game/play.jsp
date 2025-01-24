<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>じゃんけんゲーム</title>
</head>
    <script>
        let winStreak = 0;
        const userId = "user123";  //仮のユーザーID

        function handleSelection(hand) {
            const buttons = document.querySelectorAll('input[name="hand"]');
            buttons.forEach(button => button.classList.remove('selected'));
            document.getElementById(hand).classList.add('selected');
        }

        function playGame() {
            const selectedHand = document.querySelector('input[name="hand"]:checked');
            if (!selectedHand) {
                alert('手を選択してください');
                return;
            }

            const hand = selectedHand.value;
            const submitButton = document.getElementById('submit-button');
            submitButton.disabled = true;

            // AJAXリクエストをバックエンドに送信
            const xhr = new XMLHttpRequest();
            xhr.open("POST", "/JankenGame/game/Play", true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

            xhr.onload = function() {
                const response = JSON.parse(xhr.responseText);
                const cpuHand = response.cpuHand;
                const result = response.result;
                const cpuHandImg = document.getElementById('cpu-hand');
                const resultText = document.getElementById('result');
                const winStreakText = document.getElementById('win-streak');

                // CPUの手を画像で表示
                cpuHandImg.src = 'JankenGame/images/' + cpuHand + '.png';
<!--                document.getElementById('cpu-hand').style.display = 'block';-->
                cpuHandImg.style.display = 'block';

                // 勝敗を表示
                resultText.innerText = result.message;
                if (result.winner === 'player') {
                    winStreak++;
                } else {
                    winStreak = 0;
                }
                winStreakText.innerText = `連勝数: ${winStreak}`;

                // もう一回ボタンを表示
                document.getElementById('retry-button').style.display = 'inline-block';
            };

            // プレイヤーの手とユーザーIDをリクエストに含めて送信
            xhr.send(`hand=${hand}&userId=${userId}`);
        }

        function retryGame() {
            document.getElementById('submit-button').disabled = false;
            document.getElementById('cpu-hand').style.display = 'none';
            document.getElementById('result').innerText = '';
            document.getElementById('retry-button').style.display = 'none';

            // 選択した手をリセット
            const buttons = document.querySelectorAll('input[name="hand"]');
            buttons.forEach(button => button.classList.remove('selected'));
        }
    </script>
</head>
<body>
    <h1>じゃんけんゲーム</h1>
    
    <!-- ラジオボタンで手を選ぶ -->
    <div class="button-container">
        <label>
            <input type="radio" name="hand" value="rock" id="rock" onclick="handleSelection('rock')"> グー
        </label>
        <label>
            <input type="radio" name="hand" value="scissors" id="scissors" onclick="handleSelection('scissors')"> チョキ
        </label>
        <label>
            <input type="radio" name="hand" value="paper" id="paper" onclick="handleSelection('paper')"> パー
        </label>
    </div>
    
    <!-- 決定ボタン -->
    <div class="button-container">
        <button id="submit-button" onclick="playGame()">決定</button>
    </div>
    
    <!-- CPUの手を表示 -->
    <div id="cpu-hand" style="display: none;">
        <img src="" alt="CPUの手">
    </div>

    <!-- 結果表示 -->
    <div id="result" style="font-size: 20px; margin-top: 20px;"></div>

    <!-- 勝敗連勝数表示 -->
    <div id="win-streak"></div>

    <!-- もう一回ボタン -->
    <div class="button-container">
        <button id="retry-button" style="display: none;" onclick="retryGame()">もう一回</button>
    </div>
</body>
</html>