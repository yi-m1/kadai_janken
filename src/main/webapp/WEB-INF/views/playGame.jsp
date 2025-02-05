<!DOCTYPE html>
<html>
<head><title>Play Game</title></head>
<body>
    <h1>Play Rock Paper Scissors</h1>
    <form action="/playGame" method="POST">
        <input type="radio" name="choice" value="rock"> Rock
        <input type="radio" name="choice" value="paper"> Paper
        <input type="radio" name="choice" value="scissors"> Scissors
        <button type="submit">Play</button>
    </form>
</body>
</html>
