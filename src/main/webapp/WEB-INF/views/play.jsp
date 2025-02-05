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
                const cpuHandImg = document.getElementById('cpu-hand-img');
                const resultText = document.getElementById('result');
                const winStreakText = document.getElementById('win-streak');

                // CPUの手を画像で表示
                cpuHandImg.src = '/JankenGame/images/' + cpuHand + '.png';
                document.getElementById('cpu-hand').style.display = 'block';
<!--                cpuHandImg.style.display = 'block';-->

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

            // プレイヤーの手をリクエストに含めて送信
            xhr.send(`hand=${hand}`);
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
        <style>
        /* 全体のレイアウトを中央に配置 */
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
        }

        /* ゲームコンテンツを中央に配置 */
        .game-container {
            text-align: center;
            padding: 20px;
            border: 2px solid #ccc;
            border-radius: 10px;
            background-color: #fff;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        /* ラジオボタンのスタイル */
        .button-container {
            margin: 10px;
        }

        /* ボタンのスタイル */
        button {
            padding: 10px 20px;
            font-size: 16px;
            cursor: pointer;
            border: none;
            background-color: #4CAF50;
            color: white;
            border-radius: 5px;
            transition: background-color 0.3s;
        }

        button:hover {
            background-color: #45a049;
        }

        /* CPUの手画像のスタイル */
        #cpu-hand-img {
            width: 40%;
            height: 50%;
            margin-top: 20px;
        }

        /* 結果表示のスタイル */
        #result {
            font-size: 20px;
            margin-top: 20px;
        }

        /* 連勝数のスタイル */
        #win-streak {
            margin-top: 10px;
            font-size: 18px;
        }

        /* 履歴表示ボタン */
        .history-button {
            margin-top: 20px;
            padding: 10px 20px;
            font-size: 16px;
            cursor: pointer;
            border: 2px solid #4CAF50;
            background-color: white;
            color: #4CAF50;
            border-radius: 5px;
        }

        .history-button:hover {
            background-color: #4CAF50;
            color: white;
        }
    </style>
</head>
<body>
<div class="game-container">
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
        <img id="cpu-hand-img" src="" alt="CPUの手">
    </div>

    <!-- 結果表示 -->
    <div id="result"></div>

    <!-- 勝敗連勝数表示 -->
    <div id="win-streak"></div>

    <!-- もう一回ボタン -->
    <div class="button-container">
        <button id="retry-button" style="display: none;" onclick="retryGame()">もう一回</button>
    </div>
     <!-- 履歴表示ボタン -->
    <form action="/history" method="post">
            <button type="submit" class="history-button">履歴表示</button>
        </form>
    </div>
</body>
</html>