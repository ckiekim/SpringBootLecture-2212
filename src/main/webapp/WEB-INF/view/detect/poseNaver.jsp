<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>객체 탐지</title>
</head>
<body style="margin: 40px;">
    <h3>네이버 인공지능 API 객체 탐지 결과</h3>
    <hr>
    <canvas id="tcanvas" width="100" height="100"></canvas>
    <br><br>
    <button onclick="location.href='/detect/naver'">재실행</button>
    <script>
        //let jsonStr = '${jsonResult}';
        let jsonStr = '{    "predictions": [        {            "0": {                "score": 0.7880960702896118,                "x": 0.51875,                "y": 0.352112676056338            },            "1": {                "score": 0.687466561794281,                "x": 0.478125,                "y": 0.431924882629108            },            "2": {                "score": 0.5564450025558472,                "x": 0.44375,                "y": 0.42488262910798125            },            "3": {                "score": 0.709658145904541,                "x": 0.453125,                "y": 0.5563380281690141            },            "4": {                "score": 0.6235713958740234,                "x": 0.5203125,                "y": 0.5093896713615024            },            "5": {                "score": 0.660536527633667,                "x": 0.515625,                "y": 0.4413145539906103            },            "6": {                "score": 0.6952189803123474,                "x": 0.5421875,                "y": 0.5633802816901409            },            "7": {                "score": 0.716815173625946,                "x": 0.5453125,                "y": 0.5187793427230047            },            "14": {                "score": 0.8705610036849976,                "x": 0.5078125,                "y": 0.33568075117370894            },            "15": {                "score": 0.8042386174201965,                "x": 0.5203125,                "y": 0.3380281690140845            },            "16": {                "score": 0.8009858727455139,                "x": 0.4765625,                "y": 0.3474178403755869            }        }    ]}';
        let parts = [''];
        let obj = JSON.parse(jsonStr);
        let prediction = obj.predictions[0];
        let num = parseInt(prediction.num_detections);
        let names = prediction.detection_names;
        let scores = prediction.detection_scores;
        let boxes = prediction.detection_boxes;

        const canvas = document.getElementById('tcanvas');
        let ctx = canvas.getContext("2d");
        let img = new Image();
        //img.src = '/upload/${fileName}';
        img.src = '/file/download?fileName=${fileName}';
        img.onload = function() {
            canvas.width = img.width;
            canvas.height = img.height;
            ctx.drawImage(img, 0, 0, img.width, img.height);

            ctx.strokeStyle = 'red';
            ctx.linewidth = 2;
            for (let i=0; i<num; i++) {
                let x = boxes[i][1] * img.width;
                let y = boxes[i][0] * img.height;
                let w = (boxes[i][3] - boxes[i][1]) * img.width;
                let h = (boxes[i][2] - boxes[i][0]) * img.height;
                let label = names[i] + ' (' + parseInt(scores[i] * 100) + '%)';
                ctx.strokeRect(x, y, w, h);
                ctx.strokeText(label, x+5, y-5);
            }
        }
    </script>
</body>
</html>