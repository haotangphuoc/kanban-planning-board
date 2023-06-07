import React from "react";
import { Container, Row, Card, Button } from "react-bootstrap";

const Dashboard = () => {
  return (
    <div style={{ minHeight: "93vh" }}>
      <Container>
        <Row>
          <div>
            <h1 style={{ paddingTop: 38, paddingBottom: 38 }}>
              Welcome to Trello-Clone!
            </h1>
          </div>
        </Row>
        <Row>
          <div>
            <Card border="success">
              <Card.Body>
                <Card.Title>Already have an account?</Card.Title>
                <Card.Text>
                  Login to update your workspace and see what you might have
                  missed.
                </Card.Text>
                <Button variant="primary" href="../Pages/Login.js">
                  Login
                </Button>
              </Card.Body>
            </Card>
            <br />
            <Card border="primary">
              <Card.Body>
                <Card.Title>Don't have an account?</Card.Title>
                <Card.Text>
                  Do you want to stay organized? Trello-Clone lets you keep you
                  keep track of your assignments through workspaces. You can
                  even share them with your friends!
                </Card.Text>
                <Button variant="primary" href="../Pages/Register.js">
                  Register
                </Button>
              </Card.Body>
            </Card>
          </div>
        </Row>
      </Container>
    </div>
  );
};

export default Dashboard;
