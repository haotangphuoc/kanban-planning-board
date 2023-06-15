import React from "react";
import { Container, Row, Col, Card, Button } from "react-bootstrap";

const Dashboard = () => {
  return (
    <div
      style={{
        minHeight: "100vh",
        background: "linear-gradient(to bottom right, #026AA7, #00CC99)",
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        padding: "30px",
      }}
    >
      <Container>
        <Row>
          <Col>
            <div style={{ textAlign: "center", marginBottom: "50px" }}>
              <h1
                style={{
                  color: "#fff",
                  fontSize: "3rem",
                  fontWeight: "bold",
                  textShadow: "2px 2px 4px rgba(0, 0, 0, 0.3)",
                  marginBottom: "20px",
                  fontFamily: "Helvetica Neue, Arial, sans-serif",
                }}
              >
                Welcome to Trello
              </h1>
              <p
                style={{
                  color: "#fff",
                  fontSize: "1.8rem",
                  marginBottom: "40px",
                  letterSpacing: "1px",
                  fontFamily: "Helvetica Neue, Arial, sans-serif",
                }}
              >
                Organize your tasks with ease!
              </p>
              <p
                style={{
                  color: "#fff",
                  fontSize: "1.4rem",
                  marginBottom: "40px",
                  letterSpacing: "1px",
                  fontFamily: "Helvetica Neue, Arial, sans-serif",
                }}
              >
                Let us help you stay productive and focused on what matters most.
              </p>
            </div>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md={6} lg={4}>
            <Card
              border="success"
              style={{
                backgroundColor: "#fff",
                borderRadius: "10px",
                padding: "30px",
                textAlign: "center",
                height: "100%",
              }}
            >
              <Card.Body>
                <Card.Title
                  style={{
                    fontSize: "2rem",
                    marginBottom: "20px",
                    fontWeight: "bold",
                    color: "#007BFF",
                    fontFamily: "Helvetica Neue, Arial, sans-serif",
                  }}
                >
                  Already have an account?
                </Card.Title>
                <Card.Text
                  style={{
                    fontSize: "1.4rem",
                    marginBottom: "40px",
                    color: "#333",
                    fontFamily: "Helvetica Neue, Arial, sans-serif",
                  }}
                >
                  Login to update your workspace and see what you might have missed.
                </Card.Text>
                <Button
                  variant="primary"
                  href="../Pages/Login.js"
                  style={{
                    fontSize: "1.4rem",
                    padding: "12px 24px",
                    borderRadius: "30px",
                    fontFamily: "Helvetica Neue, Arial, sans-serif",
                  }}
                >
                  Login
                </Button>
              </Card.Body>
            </Card>
          </Col>
          <Col md={6} lg={4}>
            <Card
              border="primary"
              style={{
                backgroundColor: "#fff",
                borderRadius: "10px",
                padding: "30px",
                textAlign: "center",
                height: "100%",
              }}
            >
              <Card.Body>
                <Card.Title
                  style={{
                    fontSize: "2rem",
                    marginBottom: "20px",
                    fontWeight: "bold",
                    color: "#007BFF",
                    fontFamily: "Helvetica Neue, Arial, sans-serif",
                  }}
                >
                  Don't have an account?
                </Card.Title>
                <Card.Text
                  style={{
                    fontSize: "1.4rem",
                    marginBottom: "40px",
                    color: "#333",
                    fontFamily: "Helvetica Neue, Arial, sans-serif",
                  }}
                >
                  Do you want to stay organized? Trello-Clone lets you keep track of your assignments through workspaces. You can even share them with your friends!
                </Card.Text>
                <Button
                  variant="primary"
                  href="../Pages/Register.js"
                  style={{
                    fontSize: "1.4rem",
                    padding: "12px 24px",
                    borderRadius: "30px",
                    fontFamily: "Helvetica Neue, Arial, sans-serif",
                  }}
                >
                  Register
                </Button>
              </Card.Body>
            </Card>
          </Col>
        </Row>
      </Container>
    </div>
  );
};

export default Dashboard;
