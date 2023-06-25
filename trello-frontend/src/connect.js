const express = require("express");
const app = express();
const mysql = require("mysql");

// Allow cross-origin requests
app.use(function(req, res, next) {
  res.header("Access-Control-Allow-Origin", "*");
  res.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
  res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
  next();
});

// MySQL database connection
const connection = mysql.createConnection({
  host: "db.cs.dal.ca",
  user: "csci3130_group18",
  password: "eiraegae0O",
  port: 3306,
});

connection.connect((err) => {
  if (err) {
    console.error("Error connecting to the database: ", err);
    return;
  }
  console.log("Connected to the database.");
});

// Register API endpoint
app.post("/api/signup", (req, res) => {
  const { email, password, questionAns, firstName, lastName } = req.body;

  // Insert user into the database
  const query = `INSERT INTO users (email, password, questionAns, firstName, lastName) VALUES (?, ?, ?, ?, ?)`;
  const values = [email, password, questionAns, firstName, lastName];

  connection.query(query, values, (err, result) => {
    if (err) {
      console.error("Error executing the query: ", err);
      res.status(500).json({ success: false, error: "An error occurred while registering the user." });
      return;
    }

    res.status(200).json({ success: true, message: "User registered successfully." });
  });
});

const port = 8001;
app.listen(port, () => console.log(`Server running on port ${port}`));

