<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>じゃんけんゲーム</title>
<!-- BootstrapのCDN -->
<link
    href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css"
    rel="stylesheet"
    integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1"
    crossorigin="anonymous">
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
            xhr.open("POST", "/JankenGame/game/Play?hand=" + hand, true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

            xhr.onload = function() {
                const response = JSON.parse(xhr.responseText);
                const cpuHand = response.cpuHand;
                const result = response.result;
                const cpuHandImg = document.getElementById('cpu-hand-img');
                const resultText = document.getElementById('result');
                const winStreakText = document.getElementById('win-streak');

                // CPUの手を画像で表示
                if(cpuHand){
                cpuHandImg.src = '/JankenGame/images/' + cpuHand + '.png';
                document.getElementById('cpu-hand').style.display = 'block';
                }else{
                    console.error("CPUの手が未定義です");
                }

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
            
            // 「もう一回」ボタンを表示
            document.getElementById('retry-button').style.display = 'inline-block'; // ここで再表示
        }
    </script>
<style>
body {
    margin: 0; /* ページ全体の余白を0に設定 */
    height: 100vh; /* ビューポートの高さを100%に */
    display: flex; /* Flexboxレイアウトを使用 */
    justify-content: center; /* 水平方向に中央揃え */ <!--
    align-items: center; /* 垂直方向に中央揃え */ -->
    flex-direction: column; /* 要素を縦に並べる */
    text-align: center; /* テキストを中央揃え */
}

h1 {
    margin-top: 10px; /* 上部に少し余白を追加 */
}

.button-container {
    margin-top: 20px; /* ボタンの上に余白を追加 */
    display: flex; /* 横並びに配置 */
    justify-content: center; /* ボタンを中央揃え */
    align-items: center; /* 垂直方向で中央揃え */
}

.button-container label {
    margin: 0 15px; /* ラベル間の間隔を開ける */
    font-size: 18px; /* フォントサイズを少し大きく */
}

button {
    margin-top: 20px;
}

#cpu-hand {
    display: none; /* 初期状態では非表示 */
    position: relative; /* 相対配置 */
    text-align: center; /* 画像を中央揃え */
}

#cpu-hand-img {
    width: 150px; /* 画像のサイズ調整 */
    height: auto; /* アスペクト比を保つ */
}

.button-history {
    margin-top: 20px; /* ボタンの上に余白を追加 */
    display: flex; /* 横並びに配置 */
    justify-content: center; /* 中央揃え */
    align-items: center; /* 垂直方向で中央揃え */
}
</style>
</head>
<body>
    <div class="game-container">
        <h1>じゃんけんゲーム</h1>

        <!-- ラジオボタンで手を選ぶ -->
        <div class="button-container">
            <label> <input type="radio" name="hand" value="rock"
                id="rock" onclick="handleSelection('rock')"> グー
            </label> <label> <input type="radio" name="hand" value="scissors"
                id="scissors" onclick="handleSelection('scissors')"> チョキ
            </label> <label> <input type="radio" name="hand" value="paper"
                id="paper" onclick="handleSelection('paper')"> パー
            </label>
        </div>

        <!-- 決定ボタン -->
        <div class="button-container">
            <button id="submit-button" onclick="playGame()">決定</button>
        </div>

        <!-- 結果表示 -->
        <div id="result"></div>

        <!-- 勝敗連勝数表示 -->
        <div id="win-streak"></div>

        <!-- もう一回ボタン -->
        <div class="button-container">
            <button id="retry-button" style="display: none;"
                onclick="retryGame()">もう一回</button>
        </div>
        <!-- CPUの手を表示 -->
        <div id="cpu-hand" style="display: none;">
            <img id="cpu-hand-img" src="" alt="CPUの手">
        </div>

        <!-- 履歴表示ボタン -->
        <div class="button-history">
            <form action="/JankenGame/history" method="post">
                <button type="submit" class="history-button">履歴表示</button>
            </form>
        </div>
        <form action="<%=request.getContextPath()%>/logout" method="post">
            <div>
                <button type="submit">ログアウト</button>
            </div>
        </form>
    </div>
    </div>
</body>
</html>