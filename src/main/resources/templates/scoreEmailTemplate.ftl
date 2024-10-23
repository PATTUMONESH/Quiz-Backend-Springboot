

<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <title>Results</title>
    <style>

    .congrats {
                color: green;
            }
            .better-luck {
                color: brown;
            }


        .table-container {
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            background-color: #f9f9f9;
            padding: 20px;
            border:1px solid black;
        }
        .HeadingColor{
        color:blue;
        }
        .table th, .table td {

            border:1px solid black;
            text-align:left

        }
        .header-text {
            font-weight: bold;
            color: #343a40;
        }
        .subheader-text {
            color: #6c757d;
        }
    </style>
</head>
<body>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="text-center mb-4">

                    <h3 class="header-text">Hi ${name},</h3>

    <br>

 <p class="subheader-text">Here are the details of your performance:</p>
                </div>
                <div class="table-container">
                    <table class="table table-bordered table-striped">
                        <thead class="table-dark">
                            <tr class="HeadingColor">
                                <th>Category</th>
                                <th>Details</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <th>Total Questions</th>
                                <td>${totalQuestions}</td>
                            </tr>
                           <tr>
                           <th>Attempted questions</th>
                           <td>${AttemptedQuestions}</td>
                           </tr>

                              <tr>
                                <th>Unattempted Questions</th>
                                <td>${unAttemptedQuestion}</td>
                            </tr>

                            <tr>
                                <th>Correct Answers</th>
                                <td>${correctAnswer}</td>
                            </tr>
                            <tr>
                                <th>Wrong Answers</th>
                                <td>${wrongAnswer}</td>
                            </tr>

                            <tr>
                                <th>Your Score</th>
                                <td>${score}</td>
                            </tr>
                        </tbody>
                    </table>


                </div>
                 <p>Thanks & Regards </p>
<p>Massil Technologies</p>
            </div>
        </div>
    </div>
</body>
</html>

